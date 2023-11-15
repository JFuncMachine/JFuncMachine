package com.wutka.jfuncmachine.compiler.model.expr.bool;

import com.wutka.jfuncmachine.compiler.classgen.ClassGenerator;
import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.classgen.Label;
import com.wutka.jfuncmachine.compiler.model.expr.Expression;
import com.wutka.jfuncmachine.compiler.model.expr.bool.tests.Test;
import com.wutka.jfuncmachine.compiler.model.expr.bool.tests.Tests;
import org.objectweb.asm.Opcodes;

import java.util.Stack;

public class UnaryComparison extends BooleanExpr {
    public Test test;
    public Expression expr;
    public BooleanExpr truePath;
    public BooleanExpr falsePath;

    public UnaryComparison(Test test, Expression expr) {
        super(null, 0);
        this.test = test;
        this.expr = expr;
    }

    public UnaryComparison(Test test, Expression expr, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.test = test;
        this.expr = expr;
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
        expr.findCaptured(env);
    }

    public void generate(ClassGenerator generator, Environment env, Label endLabel) {
        if (label != null) {
            generator.instGen.label(label);
        }
        expr.generate(generator, env);

        int opcode = switch (test) {
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
