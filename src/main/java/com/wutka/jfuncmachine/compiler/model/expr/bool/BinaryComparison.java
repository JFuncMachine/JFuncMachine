package com.wutka.jfuncmachine.compiler.model.expr.bool;

import com.wutka.jfuncmachine.compiler.classgen.ClassGenerator;
import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.classgen.Label;
import com.wutka.jfuncmachine.compiler.model.expr.Expression;
import com.wutka.jfuncmachine.compiler.model.expr.bool.tests.Test;
import com.wutka.jfuncmachine.compiler.model.expr.bool.tests.Tests;
import com.wutka.jfuncmachine.compiler.model.expr.boxing.Unbox;
import com.wutka.jfuncmachine.compiler.model.expr.javaintop.CallJavaMethod;
import com.wutka.jfuncmachine.compiler.model.types.DoubleType;
import com.wutka.jfuncmachine.compiler.model.types.FloatType;
import com.wutka.jfuncmachine.compiler.model.types.LongType;
import com.wutka.jfuncmachine.compiler.model.types.ObjectType;
import com.wutka.jfuncmachine.compiler.model.types.SimpleTypes;
import com.wutka.jfuncmachine.compiler.model.types.StringType;
import com.wutka.jfuncmachine.compiler.model.types.Type;
import org.objectweb.asm.Opcodes;

import java.util.List;
import java.util.Stack;

public class BinaryComparison extends BooleanExpr {
    public Test test;
    public Expression left;
    public Expression right;
    public BooleanExpr truePath;
    public BooleanExpr falsePath;

    public BinaryComparison(Test test, Expression left, Expression right) {
        super(null, 0);
        this.test = test;
        this.left = left;
        this.right = right;
    }

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

    public BooleanExpr computeSequence(BooleanExpr trueNext, BooleanExpr falseNext, List<BooleanExpr> tests) {
        this.falsePath = falseNext;
        this.truePath = trueNext;

        tests.add(this);

        return this;
    }

    public void findCaptured(Environment env) {
        left.findCaptured(env);
        right.findCaptured(env);
    }

    public void generate(ClassGenerator generator, Environment env, Label endLabel) {
        if (label != null) {
            generator.instGen.label(label);
        }
        left.generate(generator, env);
        right.generate(generator, env);

        int opcode;

        if (left.getType() instanceof DoubleType ||
                (generator.options.autobox && left.getType().getUnboxedType() instanceof DoubleType)) {
            if (!(right.getType() instanceof DoubleType ||
                    (generator.options.autobox && right.getType().getUnboxedType() instanceof DoubleType))) {
                throw generateException("Left side of comparison is a double type, but right side is not");
            }

            if (!(left.getType() instanceof DoubleType)) {
                new Unbox(left).generate(generator, env);
            } else {
                left.generate(generator, env);
            }

            if (!(right.getType() instanceof DoubleType)) {
                new Unbox(right).generate(generator, env);
            } else {
                right.generate(generator, env);
            }

            if (test instanceof Tests.LETest || test instanceof Tests.LTTest) {
                generator.instGen.dcmpl();
            } else {
                generator.instGen.dcmpg();
            }

            opcode = switch (test) {
                case Tests.EQTest t -> Opcodes.IFEQ;
                case Tests.NETest t -> Opcodes.IFNE;
                case Tests.LTTest t -> Opcodes.IFLT;
                case Tests.LETest t -> Opcodes.IFLE;
                case Tests.GTTest t -> Opcodes.IFGT;
                case Tests.GETest t -> Opcodes.IFGE;
                default -> throw generateException("Invalid comparison for double type");
            };
        } else if (left.getType() instanceof FloatType ||
                    (generator.options.autobox && left.getType().getUnboxedType() instanceof FloatType)) {
                if (!(right.getType() instanceof FloatType ||
                        (generator.options.autobox && right.getType().getUnboxedType() instanceof FloatType))) {
                    throw generateException("Left side of comparison is a float type, but right side is not");
                }

                if (!(left.getType() instanceof FloatType)) {
                    new Unbox(left).generate(generator, env);
                } else {
                    left.generate(generator, env);
                }

                if (!(right.getType() instanceof FloatType)) {
                    new Unbox(right).generate(generator, env);
                } else {
                    right.generate(generator, env);
                }

                if (test instanceof Tests.LETest || test instanceof Tests.LTTest) {
                    generator.instGen.fcmpl();
                } else {
                    generator.instGen.fcmpg();
                }

                opcode = switch (test) {
                    case Tests.EQTest t -> Opcodes.IFEQ;
                    case Tests.NETest t -> Opcodes.IFNE;
                    case Tests.LTTest t -> Opcodes.IFLT;
                    case Tests.LETest t -> Opcodes.IFLE;
                    case Tests.GTTest t -> Opcodes.IFGT;
                    case Tests.GETest t -> Opcodes.IFGE;
                    default -> throw generateException("Invalid comparison for float type");
                };
        } else if (left.getType() instanceof LongType ||
                (generator.options.autobox && left.getType().getUnboxedType() instanceof LongType)) {
            if (!(right.getType() instanceof LongType ||
                    (generator.options.autobox && right.getType().getUnboxedType() instanceof LongType))) {
                throw generateException("Left side of comparison is a long type, but right side is not");
            }

            if (!(left.getType() instanceof LongType)) {
                new Unbox(left).generate(generator, env);
            } else {
                left.generate(generator, env);
            }

            if (!(right.getType() instanceof LongType)) {
                new Unbox(right).generate(generator, env);
            } else {
                right.generate(generator, env);
            }

            generator.instGen.lcmp();

            opcode = switch (test) {
                case Tests.EQTest t -> Opcodes.IFEQ;
                case Tests.NETest t -> Opcodes.IFNE;
                case Tests.LTTest t -> Opcodes.IFLT;
                case Tests.LETest t -> Opcodes.IFLE;
                case Tests.GTTest t -> Opcodes.IFGT;
                case Tests.GETest t -> Opcodes.IFGE;
                default -> throw generateException("Invalid comparison for long type");
            };
        } else if (left.getType().hasIntRepresentation() ||
                (generator.options.autobox && left.getType().getUnboxedType().hasIntRepresentation())) {
            if (!(right.getType().hasIntRepresentation() ||
                    (generator.options.autobox && right.getType().getUnboxedType().hasIntRepresentation()))) {
                throw generateException("Left side of comparison is a int type, but right side is not");
            }

            if (left.getType().isBoxType()) {
                new Unbox(left).generate(generator, env);
            } else {
                left.generate(generator, env);
            }

            if (right.getType().isBoxType()) {
                new Unbox(right).generate(generator, env);
            } else {
                right.generate(generator, env);
            }

            opcode = switch (test) {
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

            opcode = switch (test) {
                case Tests.EQTest t -> Opcodes.IF_ACMPEQ;
                case Tests.NETest t -> Opcodes.IF_ACMPNE;
                default -> throw generateException("Invalid comparison for object type");
            };
        } else if (left.getType() instanceof StringType) {
            if (!(right.getType() instanceof StringType)) {
                throw generateException("Right side of comparison is a string type, but right side is not");
            }

            if (test instanceof Tests.EQTest) {
                opcode = Opcodes.IF_ACMPEQ;
            } else if (test instanceof Tests.NETest) {
                opcode = Opcodes.IF_ACMPNE;
            } else {
                if (test instanceof Tests.LETest || test instanceof Tests.LTTest ||
                    test instanceof Tests.GETest || test instanceof Tests.GTTest) {
                    generator.instGen.invokevirtual(
                            generator.className("java.lang.String"),
                            "compareTo", generator.methodDescriptor(
                                    new Type[] { SimpleTypes.STRING }, SimpleTypes.STRING));
                    opcode = switch (test) {
                        case Tests.LETest t -> Opcodes.IFLE;
                        case Tests.LTTest t -> Opcodes.IFLT;
                        case Tests.GETest t -> Opcodes.IFGE;
                        case Tests.GTTest t -> Opcodes.IFGT;
                        default -> throw generateException("Internal error, String comparison generation failed");
                    };
                } else if (test instanceof Tests.EQIgnoreCaseTest ||
                        test instanceof Tests.NEIgnoreCaseTest ||
                        test instanceof Tests.LEIgnoreCaseTest ||
                        test instanceof Tests.LTIgnoreCaseTest ||
                        test instanceof Tests.GEIgnoreCaseTest ||
                        test instanceof Tests.GTIgnoreCaseTest) {

                    generator.instGen.invokevirtual(
                            generator.className("java.lang.String"),
                            "compareTo", generator.methodDescriptor(
                                    new Type[] { SimpleTypes.STRING }, SimpleTypes.STRING));

                    opcode = switch (test) {
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

        generator.instGen.rawJumpOpcode(opcode, truePath.label);
    }
}
