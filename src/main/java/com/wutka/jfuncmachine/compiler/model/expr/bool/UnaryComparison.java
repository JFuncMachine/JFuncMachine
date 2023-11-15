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

        if (test instanceof Tests.EQTest || test instanceof Tests.NETest ||
            test instanceof Tests.LTTest || test instanceof Tests.LETest ||
            test instanceof Tests.GTTest || test instanceof Tests.GETest) {
            if (!expr.getType().hasIntRepresentation()) {
                throw generateException("Unary test for EQ,NE,LT,LE,GT,GE requires an int expression");
            }
        }
        int opcode = switch (test) {
            case Tests.IsNullTest t -> Opcodes.IFNULL;
            case Tests.IsNotNullTest t -> Opcodes.IFNONNULL;
            case Tests.IsTrueTest t -> Opcodes.IFNE;
            case Tests.IsFalseTest t -> Opcodes.IFEQ;
            case Tests.EQTest t -> Opcodes.IFEQ;
            case Tests.NETest t -> Opcodes.IFNE;
            case Tests.LTTest t -> Opcodes.IFLT;
            case Tests.LETest t -> Opcodes.IFLE;
            case Tests.GTTest t -> Opcodes.IFGT;
            case Tests.GETest t -> Opcodes.IFGE;
            default -> throw generateException("Invalid test for unary comparison");
        };

        generator.instGen.rawJumpOpcode(opcode, truePath.label);
    }
}
