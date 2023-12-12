package org.jfuncmachine.compiler.model.expr.bool;

import org.jfuncmachine.compiler.classgen.ClassGenerator;
import org.jfuncmachine.compiler.classgen.Environment;
import org.jfuncmachine.compiler.classgen.Label;
import org.jfuncmachine.compiler.model.expr.Expression;
import org.jfuncmachine.compiler.model.expr.bool.tests.Test;
import org.jfuncmachine.compiler.model.expr.bool.tests.Tests;
import org.jfuncmachine.compiler.model.expr.boxing.Unbox;
import org.jfuncmachine.compiler.model.types.*;
import org.objectweb.asm.Opcodes;

import java.util.List;

/** Represents a comparison of two expressions.
 * This class takes care of figuring out how to do the requested comparisons based on
 * the available instructions in the Java Virtual Machine. For example, to compare double values,
 * it has to use the dcmpl and dcmpg instructions in combination with ifeq, ifne, etc.
 * For integers, it can use if_icmpeq, if_icmpne, etc. It does any autoboxing necessary (if enabled), so
 * it can compare java.lang.Float values, for example. It has support for string comparisons, including
 * those that ignore case. It calls the appropriate compareTo or compareToIgnoreCase and then uses
 * ifeq, ifne, etc. to evaluate the comparison.
 */
public class BinaryComparison extends BooleanExpr {
    /** The type of comparison to be done. */
    public Test test;
    /** The expression on the left side of the comparison */
    public Expression left;
    /** The expression on the right side of the comparison */
    public Expression right;
    /** The expression to evaluate if the comparison is true */
    public BooleanExpr truePath;
    /** The expression to evaluate if the comparison is false */
    public BooleanExpr falsePath;

    /** Create a new BinaryComparison with the given test, and left and right expressions
     * @param test The test to perform
     * @param left The left-hand side of the comparison
     * @param right The right-hand side of the comparison
     */
    public BinaryComparison(Test test, Expression left, Expression right) {
        super(null, 0);
        this.test = test;
        this.left = left;
        this.right = right;
    }

    /** Create a new BinaryComparison with the given test, and left and right expressions
     * @param test The test to perform
     * @param left The left-hand side of the comparison
     * @param right The right-hand side of the comparison
     * @param filename The source filename this expression is associated with
     * @param lineNumber The source line number this expression is associated with
     */
    public BinaryComparison(Test test, Expression left, Expression right, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.test = test;
        this.left = left;
        this.right = right;
    }

    public BooleanExpr invert() {
        this.test = test.invert();
        return this;
    }

    public BooleanExpr removeNot() {
        return this;
    }

    @Override
    public void reset() {
        super.reset();
        left.reset();
        right.reset();
        truePath = null;
        falsePath = null;
    }

    public BooleanExpr computeSequence(BooleanExpr trueNext, BooleanExpr falseNext, List<BooleanExpr> tests) {
        this.falsePath = falseNext;
        this.truePath = trueNext;

        tests.add(this);

        return this;
    }

    /** Identify any variables captured by the left and right hand expressions */
    public void findCaptured(Environment env) {
        left.findCaptured(env);
        right.findCaptured(env);
    }

    /** Generate Java bytecode for this comparison */
    public void generate(ClassGenerator generator, Environment env, BooleanExpr next) {
        // If this test has a non-null label, generate the label
        if (label != null) {
            generator.instGen.label(label);
        }

        Test generateTest = test;
        BooleanExpr generateTruePath = truePath;

        /* Java if instructions always jump if the test is true, and otherwise just execute the next
           instruction if the test is false. If the next test after this is supposed to occur if the
           comparison is true, invert the test so that the next test is executed when the test is false
           instead of true.
         */
        if (truePath == next) {
            generateTest = test.invert();
            generateTruePath = falsePath;
        }

        int opcode;

        if (left.getType() instanceof DoubleType ||
                (generator.options.autobox && left.getType().getUnboxedType() != null &&
                        left.getType().getUnboxedType() instanceof DoubleType)) {
            if (!(right.getType() instanceof DoubleType ||
                    (generator.options.autobox && right.getType().getUnboxedType() != null &&
                            right.getType().getUnboxedType() instanceof DoubleType))) {
                throw generateException("Left side of comparison is a double type, but right side is not");
            }

            if (!(left.getType() instanceof DoubleType)) {
                new Unbox(left).generate(generator, env, false);
            } else {
                left.generate(generator, env, false);
            }

            if (!(right.getType() instanceof DoubleType)) {
                new Unbox(right).generate(generator, env, false);
            } else {
                right.generate(generator, env, false);
            }

            if (generateTest instanceof Tests.LETest || generateTest instanceof Tests.LTTest) {
                generator.instGen.dcmpl();
            } else {
                generator.instGen.dcmpg();
            }

            opcode = switch (generateTest) {
                case Tests.EQTest t -> Opcodes.IFEQ;
                case Tests.NETest t -> Opcodes.IFNE;
                case Tests.LTTest t -> Opcodes.IFLT;
                case Tests.LETest t -> Opcodes.IFLE;
                case Tests.GTTest t -> Opcodes.IFGT;
                case Tests.GETest t -> Opcodes.IFGE;
                default -> throw generateException("Invalid comparison for double type");
            };
        } else if (left.getType() instanceof FloatType ||
                    (generator.options.autobox && left.getType().getUnboxedType() != null &&
                            left.getType().getUnboxedType() instanceof FloatType)) {
                if (!(right.getType() instanceof FloatType ||
                        (generator.options.autobox && right.getType().getUnboxedType() != null &&
                                right.getType().getUnboxedType() instanceof FloatType))) {
                    throw generateException("Left side of comparison is a float type, but right side is not");
                }

                if (!(left.getType() instanceof FloatType)) {
                    new Unbox(left).generate(generator, env, false);
                } else {
                    left.generate(generator, env, false);
                }

                if (!(right.getType() instanceof FloatType)) {
                    new Unbox(right).generate(generator, env, false);
                } else {
                    right.generate(generator, env, false);
                }

                if (generateTest instanceof Tests.LETest || generateTest instanceof Tests.LTTest) {
                    generator.instGen.fcmpl();
                } else {
                    generator.instGen.fcmpg();
                }

                opcode = switch (generateTest) {
                    case Tests.EQTest t -> Opcodes.IFEQ;
                    case Tests.NETest t -> Opcodes.IFNE;
                    case Tests.LTTest t -> Opcodes.IFLT;
                    case Tests.LETest t -> Opcodes.IFLE;
                    case Tests.GTTest t -> Opcodes.IFGT;
                    case Tests.GETest t -> Opcodes.IFGE;
                    default -> throw generateException("Invalid comparison for float type");
                };
        } else if (left.getType() instanceof LongType ||
                (generator.options.autobox && left.getType().getUnboxedType() != null &&
                        left.getType().getUnboxedType() instanceof LongType)) {
            if (!(right.getType() instanceof LongType ||
                    (generator.options.autobox && right.getType().getUnboxedType() != null &&
                            right.getType().getUnboxedType() instanceof LongType))) {
                throw generateException("Left side of comparison is a long type, but right side is not");
            }

            if (!(left.getType() instanceof LongType)) {
                new Unbox(left).generate(generator, env, false);
            } else {
                left.generate(generator, env, false);
            }

            if (!(right.getType() instanceof LongType)) {
                new Unbox(right).generate(generator, env, false);
            } else {
                right.generate(generator, env, false);
            }

            generator.instGen.lcmp();

            opcode = switch (generateTest) {
                case Tests.EQTest t -> Opcodes.IFEQ;
                case Tests.NETest t -> Opcodes.IFNE;
                case Tests.LTTest t -> Opcodes.IFLT;
                case Tests.LETest t -> Opcodes.IFLE;
                case Tests.GTTest t -> Opcodes.IFGT;
                case Tests.GETest t -> Opcodes.IFGE;
                default -> throw generateException("Invalid comparison for long type");
            };
        } else if (left.getType().hasIntRepresentation() ||
                (generator.options.autobox && left.getType().getUnboxedType() != null &&
                        left.getType().getUnboxedType().hasIntRepresentation())) {
            if (!(right.getType().hasIntRepresentation() ||
                    (generator.options.autobox && right.getType().getUnboxedType() != null &&
                            right.getType().getUnboxedType().hasIntRepresentation()))) {
                throw generateException("Left side of comparison is a int type, but right side is not");
            }

            if (left.getType().isBoxType()) {
                new Unbox(left).generate(generator, env, false);
            } else {
                left.generate(generator, env, false);
            }

            if (right.getType().isBoxType()) {
                new Unbox(right).generate(generator, env, false);
            } else {
                right.generate(generator, env, false);
            }

            opcode = switch (generateTest) {
                case Tests.EQTest t -> Opcodes.IF_ICMPEQ;
                case Tests.NETest t -> Opcodes.IF_ICMPNE;
                case Tests.LTTest t -> Opcodes.IF_ICMPLT;
                case Tests.LETest t -> Opcodes.IF_ICMPLE;
                case Tests.GTTest t -> Opcodes.IF_ICMPGT;
                case Tests.GETest t -> Opcodes.IF_ICMPGE;
                default -> throw generateException("Invalid comparison for long type");
            };
        } else if (left.getType() instanceof ObjectType && !left.getType().isBoxType()) {
            if (!(right.getType() instanceof ObjectType && !right.getType().isBoxType())) {
                throw generateException("Left side of comparison is an object type, but right side is not");
            }

            left.generate(generator, env, false);
            right.generate(generator, env, false);

            opcode = switch (generateTest) {
                case Tests.EQTest t -> Opcodes.IF_ACMPEQ;
                case Tests.NETest t -> Opcodes.IF_ACMPNE;
                default -> throw generateException("Invalid comparison for object type");
            };
        } else if (left.getType() instanceof StringType) {
            if (!(right.getType() instanceof StringType)) {
                throw generateException("Right side of comparison is a string type, but right side is not");
            }

            left.generate(generator, env, false);
            right.generate(generator, env, false);

            if (generateTest instanceof Tests.EQTest) {
                opcode = Opcodes.IF_ACMPEQ;
            } else if (generateTest instanceof Tests.NETest) {
                opcode = Opcodes.IF_ACMPNE;
            } else {
                if (generateTest instanceof Tests.LETest || generateTest instanceof Tests.LTTest ||
                    generateTest instanceof Tests.GETest || generateTest instanceof Tests.GTTest) {
                    generator.instGen.invokevirtual(
                            generator.className("java.lang.String"),
                            "compareTo", generator.methodDescriptor(
                                    new Type[] { SimpleTypes.STRING }, SimpleTypes.INT));
                    opcode = switch (generateTest) {
                        case Tests.LETest t -> Opcodes.IFLE;
                        case Tests.LTTest t -> Opcodes.IFLT;
                        case Tests.GETest t -> Opcodes.IFGE;
                        case Tests.GTTest t -> Opcodes.IFGT;
                        default -> throw generateException("Internal error, String comparison generation failed");
                    };
                } else if (generateTest instanceof Tests.EQIgnoreCaseTest ||
                        generateTest instanceof Tests.NEIgnoreCaseTest ||
                        generateTest instanceof Tests.LEIgnoreCaseTest ||
                        generateTest instanceof Tests.LTIgnoreCaseTest ||
                        generateTest instanceof Tests.GEIgnoreCaseTest ||
                        generateTest instanceof Tests.GTIgnoreCaseTest) {

                    generator.instGen.invokevirtual(
                            generator.className("java.lang.String"),
                            "compareToIgnoreCase", generator.methodDescriptor(
                                    new Type[] { SimpleTypes.STRING }, SimpleTypes.INT));

                    opcode = switch (generateTest) {
                        case Tests.EQIgnoreCaseTest t -> Opcodes.IFEQ;
                        case Tests.NEIgnoreCaseTest t -> Opcodes.IFNE;
                        case Tests.LTIgnoreCaseTest t -> Opcodes.IFLT;
                        case Tests.LEIgnoreCaseTest t -> Opcodes.IFLE;
                        case Tests.GTIgnoreCaseTest t -> Opcodes.IFGT;
                        case Tests.GEIgnoreCaseTest t -> Opcodes.IFGE;
                        default -> throw generateException("Internal error, String comparison generation failed");
                    };
                } else {
                    throw generateException("Invalid comparison for string type");
                }
            }
        } else {
            throw generateException(String.format("Unable to compare %s with %s", left.getType(), right.getType()));
        }

        if (generateTruePath.label == null) {
            generateTruePath.label = new Label();
        }
        generator.instGen.rawJumpOpcode(opcode, generateTruePath.label);
    }
}
