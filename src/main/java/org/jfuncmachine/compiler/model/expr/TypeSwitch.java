package org.jfuncmachine.compiler.model.expr;

import org.jfuncmachine.compiler.classgen.*;
import org.jfuncmachine.compiler.model.expr.bool.*;
import org.jfuncmachine.compiler.model.expr.boxing.Box;
import org.jfuncmachine.compiler.model.expr.constants.IntConstant;
import org.jfuncmachine.compiler.model.expr.constants.StringConstant;
import org.jfuncmachine.compiler.model.expr.javainterop.CallJavaMethod;
import org.jfuncmachine.compiler.model.expr.javainterop.CallJavaStaticMethod;
import org.jfuncmachine.compiler.model.types.IntType;
import org.jfuncmachine.compiler.model.types.ObjectType;
import org.jfuncmachine.compiler.model.types.SimpleTypes;
import org.jfuncmachine.compiler.model.types.Type;

import java.util.ArrayList;
import java.util.List;

/** A switch expression that matches on cases, and then allows additional
 * comparisons.
 *
 * This expression relies on a feature that was added as a preview feature
 * in Java 17, and became a full part of the JVM in Java 21. JFuncMachine
 * will, by default, generate equivalent if statements for the switch
 * if generating for Java 20 or lower, although if the usePreviewFeatures
 * generator option is set, it will only generate the if version for
 * Java 16 or below.
 *
 * The idea here is that you may want to do pattern matching against objects
 * where there may be several patterns for the same class. Each TypeSwitchClass
 * contains an optional additional comparison expression, which is executed
 * if a case matches the target class. If the additional comparison is
 * true, then the case expression is executed. If it is false, then the
 * switch jumps to the next case that matches, or to the default case.
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
                if (cases[i].target instanceof Integer ix) {
                    newCases[i] = new TypeSwitchCase(ix, cases[i].expr.convertToFullTailCalls(true),
                            filename, lineNumber);
                } else if (cases[i].target instanceof String s) {
                    newCases[i] = new TypeSwitchCase(s, cases[i].expr.convertToFullTailCalls(true),
                            filename, lineNumber);

                } else {
                    newCases[i] = new TypeSwitchCase((ObjectType) cases[i].target, cases[i].additionalComparison,
                            cases[i].expr.convertToFullTailCalls(true), filename, lineNumber);
                }
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
        expr.generate(generator, env, false);
        targetVar.generateSet(generator);

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
            Label caseExprLabel = new Label();
            Environment castEnv = new Environment(switchEnv);
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
            targetVar.generateGet(generator);
            if (cases[i].target instanceof ObjectType objectType) {
                generator.instGen.checkcast(objectType.className);
            } else if (cases[i].target instanceof Integer) {
                generator.instGen.checkcast("java/lang/Integer");
                generator.instGen.invokevirtual("java/lang/Integer", "intValue",
                        "()I");
            }
            castTargetVar.generateSet(generator);

            if (cases[i].additionalComparison != null) {
                List<BooleanExpr> testSequence = new ArrayList<>();
                Result trueResult = new Result(null);
                Result falseResult = new Result(null);
                cases[i].additionalComparison.computeSequence(trueResult, falseResult, testSequence);

                for (int j=testSequence.size()-1; j >= 0; j--) {
                    BooleanExpr booleanExpr = testSequence.get(j);
                    BooleanExpr nextExpr = null;
                    if (j > 0) {
                        nextExpr = testSequence.get(j-1);
                    }
                    if (booleanExpr instanceof UnaryComparison unary) {
                        unary.generate(generator, castEnv, nextExpr);
                    } else if (booleanExpr instanceof BinaryComparison binary) {
                        binary.generate(generator, castEnv, nextExpr);
                    } else if (booleanExpr instanceof InstanceofComparison instOf) {
                        instOf.generate(generator, castEnv, nextExpr);
                    }
                }
                if (falseResult.label != null) {
                    generator.instGen.label(falseResult.label);
                }
                targetVar.generateGet(generator);
                new IntConstant(i+1).generate(generator, env, false);
                generator.instGen.gotolabel(restartLabel);
                if (trueResult.label != null) {
                    generator.instGen.label(trueResult.label);
                }
            }
            cases[i].expr.generate(generator, castEnv, inTailPosition);
            generator.instGen.label(caseExprLabel);
            generator.instGen.gotolabel(switchEndLabel);
        }
        if (defaultCase != null) {
            generator.instGen.label(defaultLabel);
            defaultCase.generate(generator, env, inTailPosition);
        }
        generator.instGen.label(switchEndLabel);
    }

    public void generateIf(ClassGenerator generator, Environment env, boolean inTailPosition) {
        Label switchStartLabel = new Label();
        generator.instGen.label(switchStartLabel);

        Label switchEndLabel = new Label();

        Environment switchEnv = new Environment(env);
        EnvVar targetVar = switchEnv.allocate(expr.getType());
        generator.instGen.generateLocalVariable(targetVar.name, targetVar.type,
                switchStartLabel, switchEndLabel, targetVar.index);
        expr.generate(generator, env, false);
        targetVar.generateSet(generator);

        Label nextTest = new Label();

        for (int i=cases.length-1; i >= 0; i--) {
            Label currTest = nextTest;
            generator.instGen.label(nextTest);
            nextTest = new Label();

            if (cases[i].target instanceof String s) {
                targetVar.generateGet(generator);
                generator.instGen.instance_of("java/lang/String");
                generator.instGen.ifeq(nextTest);
                targetVar.generateGet(generator);
                new StringConstant(s).generate(generator, switchEnv, false);
                generator.instGen.if_acmpne(nextTest);
                cases[i].expr.generate(generator, switchEnv, inTailPosition);
                generator.instGen.gotolabel(switchEndLabel);
                continue;
            } else if (cases[i].target instanceof Integer ix) {
                targetVar.generateGet(generator);
                generator.instGen.instance_of("java/lang/Integer");
                generator.instGen.ifeq(nextTest);
                targetVar.generateGet(generator);
                new Box(new IntConstant(ix)).generate(generator, switchEnv, false);
                generator.instGen.if_acmpne(nextTest);
                cases[i].expr.generate(generator, switchEnv, inTailPosition);
                generator.instGen.gotolabel(switchEndLabel);
                continue;
            }

            ObjectType testObj = (ObjectType) cases[i].target;
            targetVar.generateGet(generator);
            generator.instGen.instance_of(generator.className(testObj.className));
            generator.instGen.ifeq(nextTest);

            if (cases[i].additionalComparison != null) {
                Environment castEnv = new Environment(switchEnv);
                EnvVar castTargetVar;
                castTargetVar = castEnv.allocate("$caseMatchVar", testObj);
                generator.instGen.generateLocalVariable(castTargetVar.name, castTargetVar.type,
                        currTest, nextTest, castTargetVar.index);
                targetVar.generateGet(generator);
                generator.instGen.checkcast(testObj.className);
                castTargetVar.generateSet(generator);

                Result trueResult = new Result(null);
                trueResult.label = nextTest;
                Result falseResult = new Result(null);

                List<BooleanExpr> testSequence = new ArrayList<>();
                cases[i].additionalComparison.computeSequence(trueResult, falseResult, testSequence);

                for (int j = testSequence.size() - 1; j >= 0; j--) {
                    BooleanExpr booleanExpr = testSequence.get(j);
                    BooleanExpr nextExpr = null;
                    if (j > 0) {
                        nextExpr = testSequence.get(j - 1);
                    }
                    if (booleanExpr instanceof UnaryComparison unary) {
                        unary.generate(generator, castEnv, nextExpr);
                    } else if (booleanExpr instanceof BinaryComparison binary) {
                        binary.generate(generator, castEnv, nextExpr);
                    } else if (booleanExpr instanceof InstanceofComparison instOf) {
                        instOf.generate(generator, castEnv, nextExpr);
                    }
                }

                if (falseResult.label != null) {
                    generator.instGen.label(falseResult.label);
                }
                cases[i].expr.generate(generator, castEnv, inTailPosition);
                generator.instGen.gotolabel(switchEndLabel);
            }
        }
        generator.instGen.label(nextTest);
        defaultCase.generate(generator, switchEnv, inTailPosition);
        generator.instGen.label(switchEndLabel);
    }
}
