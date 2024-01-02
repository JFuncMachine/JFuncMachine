package org.jfuncmachine.compiler.model.expr;

import org.jfuncmachine.compiler.classgen.*;
import org.jfuncmachine.compiler.model.expr.bool.*;
import org.jfuncmachine.compiler.model.expr.boxing.Box;
import org.jfuncmachine.compiler.model.expr.constants.IntConstant;
import org.jfuncmachine.compiler.model.expr.constants.StringConstant;
import org.jfuncmachine.compiler.model.types.ObjectType;
import org.jfuncmachine.compiler.model.types.SimpleTypes;
import org.jfuncmachine.compiler.model.types.Type;

import java.util.ArrayList;
import java.util.List;

/** A switch expression that matches on enums
 *
 * Each case contains the name of an enum value for the enum being matched.
 * If the name of a EnumSwitchCase matches the enum value returned by expr, then it executes
 * the expression associated with that case. Otherwise it executes the default case.
 *
 * This expression relies on a feature that was added as a preview feature
 * in Java 17, and became a full part of the JVM in Java 21. JFuncMachine
 * will, by default, generate equivalent if statements for the switch
 * if generating for Java 20 or lower, although if the usePreviewFeatures
 * generator option is set, it will only generate the if version for
 * Java 16 or below.
 *
 */
public class EnumSwitch extends Expression {
    /** The expression generating the value to be switched on */
    public final Expression expr;
    /** A case containing a numeric value and an expression to be executed if the switch expression
     * equals the case value
     */
    public final EnumSwitchCase[] cases;
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
    public EnumSwitch(Expression expr, EnumSwitchCase[] cases, Expression defaultCase) {
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
    public EnumSwitch(Expression expr, EnumSwitchCase[] cases, Expression defaultCase,
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
        for (EnumSwitchCase enumSwitchCase : cases) {
            enumSwitchCase.expr.reset();
        }
    }

    public void findCaptured(Environment env) {
        expr.findCaptured(env);
        for (EnumSwitchCase enumse : cases) {
            enumse.expr.findCaptured(env);
        }
    }

    @Override
    public Expression convertToFullTailCalls(boolean inTailPosition) {
        if (inTailPosition) {
            EnumSwitchCase[] newCases = new EnumSwitchCase[cases.length];
            for (int i=0; i < newCases.length; i++) {
                newCases[i] = new EnumSwitchCase(cases[i].target,
                        cases[i].expr.convertToFullTailCalls(true), filename, lineNumber);
            }
            return new EnumSwitch(expr, newCases, defaultCase.convertToFullTailCalls(true),
                    filename, lineNumber);
        }
        return this;
    }

    @Override
    public void generate(ClassGenerator generator, Environment env, boolean inTailPosition) {
        Label switchStartLabel = new Label();
        generator.instGen.label(switchStartLabel);

        Label switchEndLabel = new Label();

        if (generator.options.javaVersion < 17 ||
                (generator.options.javaVersion < 21 && !generator.options.usePreviewFeatures)) {
            if (!generator.options.convertSwitchesToIf) {
                if (!generator.options.usePreviewFeatures) {
                    throw generateException("TypeSwitch requires Java 21 or newer");
                } else {
                    throw generateException("TypeSwitch requires Java 17 or newer");
                }
            }
            generateIf(generator, env, inTailPosition);
        }

        Environment switchEnv = new Environment(env);
        EnvVar targetVar = switchEnv.allocate(expr.getType());
        generator.instGen.generateLocalVariable(targetVar.name, targetVar.type,
                switchStartLabel, switchEndLabel, targetVar.index);
        expr.generate(generator, switchEnv, false);
        targetVar.generateSet(generator);

        targetVar.generateGet(generator);
        generator.instGen.iconst_0();

        Object[] enumLabels = new Object[cases.length];
        Label[] switchLabels = new Label[cases.length];
        for (int i=0; i < cases.length; i++) {
            enumLabels[i] = cases[i].target;
            switchLabels[i] = new Label();
        }

        generator.instGen.lineNumber(lineNumber);

        Label restartLabel = new Label();
        generator.instGen.label(restartLabel);

        String switchArg = "java/lang/Enum";
        if (expr.getType() instanceof ObjectType objectType) {
            switchArg = objectType.className.replace('.', '/');
        }
        generator.instGen.invokedynamic("enumSwitch",
            "(L"+switchArg+";I)I",
                new Handle(Handle.INVOKESTATIC,
                        generator.className("java.lang.runtime.SwitchBootstraps"), "enumSwitch",
                        "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;[Ljava/lang/Object;)Ljava/lang/invoke/CallSite;", false),
                (Object[]) enumLabels);

        Label defaultLabel = switchEndLabel;
        if (defaultCase != null) {
            defaultLabel = new Label();
        }

        generator.instGen.tableswitch(0, cases.length-1, defaultLabel, switchLabels);

        for (int i=0; i < cases.length; i++) {
            generator.instGen.label(switchLabels[i]);
            cases[i].expr.generate(generator, switchEnv, inTailPosition);
            generator.instGen.gotolabel(switchEndLabel);
        }
        if (defaultCase != null) {
            generator.instGen.label(defaultLabel);
            defaultCase.generate(generator, switchEnv, inTailPosition);
        }
        generator.instGen.label(switchEndLabel);
    }

    protected void generateIf(ClassGenerator generator, Environment env, boolean inTailPosition) {
        Label switchStartLabel = new Label();
        generator.instGen.label(switchStartLabel);

        Label switchEndLabel = new Label();

        Environment switchEnv = new Environment(env);
        EnvVar targetVar = switchEnv.allocate(expr.getType());
        generator.instGen.generateLocalVariable(targetVar.name, targetVar.type,
                switchStartLabel, switchEndLabel, targetVar.index);
        expr.generate(generator, switchEnv, false);
        targetVar.generateSet(generator);

        Label nextTest = new Label();

        for (int i=cases.length-1; i >= 0; i--) {
            generator.instGen.label(nextTest);
            nextTest = new Label();

            targetVar.generateGet(generator);
            generator.instGen.invokevirtual("java/lang/Enum", "name", "()Ljava/lang/String;");
            new StringConstant(cases[i].target).generate(generator, switchEnv, false);
            generator.instGen.if_acmpne(nextTest);
            cases[i].expr.generate(generator, switchEnv, inTailPosition);
            generator.instGen.gotolabel(switchEndLabel);
        }
        generator.instGen.label(nextTest);
        defaultCase.generate(generator, switchEnv, inTailPosition);
        generator.instGen.label(switchEndLabel);
    }
}
