package org.jfuncmachine.compiler.model.expr;

import org.jfuncmachine.compiler.classgen.*;
import org.jfuncmachine.compiler.model.expr.constants.IntConstant;
import org.jfuncmachine.compiler.model.types.ObjectType;
import org.jfuncmachine.compiler.model.types.SimpleTypes;
import org.jfuncmachine.compiler.model.types.Type;

/** A switch expression that matches on cases, and then allows additional
 * comparisons.
 *
 * This expression relies on a feature that was added as a preview feature
 * in Java 17, and became a full part of the JVM in Java 21. JFuncMachine will
 * throw an exception if you try to use this expression to generate code for
 * a JVM version lower than 17.
 *
 * The idea here is that you may want to do pattern matching against objects
 * where there may be several patterns for the same class. Each ClassSwitchClass
 * contains an optional additional comparison expression, which is executed
 * if a case matches the target class. If the additional comparison returns
 * a true (non-zero integer) value, then the case expression is executed.
 * If it returns 0, then the switch jumps to the next case that matches, or
 * to the default case.
 *
 * In order to facilitate the comparisons, a variable named $caseMatchVar is
 * made available in the additional comparison expression's environment, meaning
 * it can be fetched with a GetValue expression.
 *
 */
public class TypeSwitch extends Expression {
    /** The expression generating the value to be switched on */
    public final Expression expr;
    /** A case containing a numeric value and an expression to be executed if the switch expression
     * equals the case value
     */
    public final TypeSwitchCase[] cases;
    /** The expression to be executed if none of the cases match the switch value */
    public final Expression defaultCase;
    /** The maximum number of empty spaces between switch values allowed before the switch
     * is converted to a lookup switch.
     */

    /** Create a Switch expression
     * @param expr The expression generating the value to be switched on
     * @param cases A case containing a numeric value and an expression to be executed if the switch expression
     *              equals the case value
     * @param defaultCase The expression to be executed if none of the cases match the switch value
     */
    public TypeSwitch(Expression expr, TypeSwitchCase[] cases, Expression defaultCase) {
        super(null, 0);
        this.expr = expr;
        this.cases = cases;
        this.defaultCase = defaultCase;

        if (cases.length == 0) {
            throw generateException("Switch must contain at least one case");
        }

        Type switchType = cases[0].expr.getType();
        for (int i=1; i < cases.length; i++) {
            if (!switchType.equals(cases[i].expr.getType())) {
                throw cases[i].generateException("Switch expressions must all be the same type");
            }
        }
    }


    /** Create a Switch expression
     * @param expr The expression generating the value to be switched on
     * @param cases A case containing a numeric value and an expression to be executed if the switch expression
     *              equals the case value
     * @param defaultCase The expression to be executed if none of the cases match the switch value
     * @param filename The name of the source file where this switch is defined
     * @param lineNumber The line number in the source file where this switch starts
     */
    public TypeSwitch(Expression expr, TypeSwitchCase[] cases, Expression defaultCase,
                      String filename, int lineNumber) {
        super(filename, lineNumber);
        this.expr = expr;
        this.cases = cases;
        this.defaultCase = defaultCase;

        if (cases.length == 0) {
            throw generateException("Switch must contain at least one case");
        }

        Type switchType = cases[0].expr.getType();
        for (int i=1; i < cases.length; i++) {
            if (!switchType.equals(cases[i].expr.getType())) {
                throw cases[i].generateException("Switch expressions must all be the same type");
            }
        }
    }
    public Type getType() {
        return cases[0].expr.getType();
    }

    @Override
    public void reset() {
        expr.reset();
        for (TypeSwitchCase typeSwitchCase : cases) {
            typeSwitchCase.expr.reset();
        }
    }

    public void findCaptured(Environment env) {
        expr.findCaptured(env);
        for (TypeSwitchCase typeSwitchCase : cases) {
            typeSwitchCase.expr.findCaptured(env);
        }
    }

    @Override
    public Expression convertToFullTailCalls(boolean inTailPosition) {
        if (inTailPosition) {
            TypeSwitchCase[] newCases = new TypeSwitchCase[cases.length];
            for (int i=0; i < newCases.length; i++) {
                newCases[i] = new TypeSwitchCase(cases[i].target, cases[i].additionalComparison,
                        cases[i].expr.convertToFullTailCalls(true), filename, lineNumber);
            }
            return new TypeSwitch(expr, newCases, defaultCase.convertToFullTailCalls(true),
                    filename, lineNumber);
        }
        return this;
    }

    @Override
    public void generate(ClassGenerator generator, Environment env, boolean inTailPosition) {
        Label switchStartLabel = new Label();
        generator.instGen.label(switchStartLabel);

        Label switchEndLabel = new Label();

        if (generator.options.javaVersion < 17) {
            throw generateException("ClassSwitch requires Java 17 or newer");
        }

        EnvVar targetVar = env.allocate(expr.getType());
        generator.instGen.generateLocalVariable(targetVar.name, targetVar.type,
                switchStartLabel, switchEndLabel, targetVar.index);
        expr.generate(generator, env, false);
        targetVar.generateSet(generator);

        Handle handle = new Handle(Handle.INVOKESTATIC,
                generator.className("java.lang.runtime.SwitchBootstraps"), "typeSwitch",
                "(Ljava/lang/Object;I)I", false);

        targetVar.generateGet(generator);
        generator.instGen.iconst_0();

        Object[] typeLabels = new Object[cases.length];
        Label[] switchLabels = new Label[cases.length];
        for (int i=0; i < cases.length; i++) {
            if (cases[i].target instanceof ObjectType) {
                typeLabels[i] = org.objectweb.asm.Type.getType(
                        generator.getTypeDescriptor((Type) cases[i].target));
            } else if (cases[i].target instanceof String) {
                typeLabels[i] = cases[i].target;
            } else if (cases[i].target instanceof Integer) {
                typeLabels[i] = cases[i].target;
            } else {
                throw generateException("TypeSwitchCase target must be a ObjectType, String, or Integer");
            }
            switchLabels[i] = new Label();
        }

        generator.instGen.lineNumber(lineNumber);

        Label restartLabel = new Label();
        generator.instGen.label(restartLabel);

        generator.instGen.invokedynamic("typeSwitch",
            "(Ljava/lang/Object;I)I",
                new Handle(Handle.INVOKESTATIC,
                        generator.className("java.lang.runtime.SwitchBootstraps"), "typeSwitch",
                        "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;[Ljava/lang/Object;)Ljava/lang/invoke/CallSite;", false),
                (Object[]) typeLabels);

        Label defaultLabel = switchEndLabel;
        if (defaultCase != null) {
            defaultLabel = new Label();
        }

        generator.instGen.tableswitch(0, cases.length-1, defaultLabel, switchLabels);

        for (int i=0; i < cases.length; i++) {
            generator.instGen.label(switchLabels[i]);
            if (cases[i].additionalComparison != null) {
                Label caseExprLabel = new Label();
                targetVar.generateGet(generator);
                if (cases[i].target instanceof ObjectType objectType) {
                    generator.instGen.checkcast(objectType.className);
                }
                Environment castEnv = new Environment(env);
                EnvVar castTargetVar;
                 castTargetVar = castEnv.allocate("$caseMatchVar",
                         switch (cases[i].target) {
                            case ObjectType ot -> ot;
                            case String s -> SimpleTypes.STRING;
                            case Integer ix -> SimpleTypes.INT;
                            default -> new ObjectType(); // This can't happen
                         });
                generator.instGen.generateLocalVariable(castTargetVar.name, castTargetVar.type,
                        switchLabels[i], caseExprLabel, castTargetVar.index);
                castTargetVar.generateSet(generator);
                cases[i].additionalComparison.generate(generator, castEnv, false);
                generator.instGen.ifne(caseExprLabel);
                targetVar.generateGet(generator);
                new IntConstant(i+1).generate(generator, env, false);
                generator.instGen.gotolabel(restartLabel);
                generator.instGen.label(caseExprLabel);
            }
            cases[i].expr.generate(generator, env, inTailPosition);
            generator.instGen.gotolabel(switchEndLabel);
        }
        if (defaultCase != null) {
            generator.instGen.label(defaultLabel);
            defaultCase.generate(generator, env, inTailPosition);
        }
        generator.instGen.label(switchEndLabel);
    }
}
