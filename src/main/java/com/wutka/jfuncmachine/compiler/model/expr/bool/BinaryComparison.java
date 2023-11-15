package com.wutka.jfuncmachine.compiler.model.expr.bool;

import com.wutka.jfuncmachine.compiler.classgen.ClassGenerator;
import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.classgen.Label;
import com.wutka.jfuncmachine.compiler.model.expr.Expression;
import com.wutka.jfuncmachine.compiler.model.expr.bool.tests.Test;
import com.wutka.jfuncmachine.compiler.model.expr.bool.tests.Tests;
import com.wutka.jfuncmachine.compiler.model.expr.boxing.Unbox;
import com.wutka.jfuncmachine.compiler.model.types.DoubleType;
import com.wutka.jfuncmachine.compiler.model.types.ObjectType;
import org.objectweb.asm.Opcodes;

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

    public BooleanExpr computeSequence(BooleanExpr trueNext, BooleanExpr falseNext, Stack<BooleanExpr> tests) {
        this.falsePath = falseNext;
        this.truePath = trueNext;

        tests.push(this);

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
        }
        opcode = switch (test) {
            case Tests.IsNullTest t -> Opcodes.IFNULL;
            case Tests.IsNotNullTest t -> Opcodes.IFNONNULL;
            case Tests.IsTrueTest t -> Opcodes.IFNE;
            case Tests.IsFalseTest t -> Opcodes.IFEQ;
            default -> throw generateException("Invalid test for unary comparison");
        };

        generator.instGen.rawJumpOpcode(opcode, truePath.label);

        if (falsePath instanceof Result falseRes) {
            if (falseRes.expr != null) {
                if (falseRes.label != null) {
                    generator.instGen.label(falseRes.label);
                }
                falseRes.expr.generate(generator, env);
                generator.instGen.gotolabel(endLabel);
            }

        }

        if (truePath instanceof Result trueRes) {
            if (trueRes.label != null) {
                generator.instGen.label(trueRes.label);
            }
            trueRes.expr.generate(generator, env);
            if ((falsePath != null) && (falsePath instanceof Result falseRes) &&
                    (falseRes.expr != null)) {
                generator.instGen.gotolabel(endLabel);
            }
        }

        if (falsePath instanceof Result falseRes) {
            if (falseRes.expr == null) {
                if (falseRes.label != null) {
                    generator.instGen.label(falseRes.label);
                }
            }
        }

    }
}
