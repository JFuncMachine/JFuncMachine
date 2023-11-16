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

    public void generate(ClassGenerator generator, Environment env, BooleanExpr next) {
        if (label != null) {
            generator.instGen.label(label);
        }
        expr.generate(generator, env);

        Test generateTest = test;
        BooleanExpr generateTruePath = truePath;
        BooleanExpr generateFalsePath = falsePath;

        if (truePath == next) {
            generateTest = test.invert();
            generateTruePath = falsePath;
            generateFalsePath = truePath;
        }
        if (generateTest instanceof Tests.EQTest || generateTest instanceof Tests.NETest ||
            generateTest instanceof Tests.LTTest || generateTest instanceof Tests.LETest ||
            generateTest instanceof Tests.GTTest || generateTest instanceof Tests.GETest) {
            if (!expr.getType().hasIntRepresentation()) {
                throw generateException("Unary test for EQ,NE,LT,LE,GT,GE requires an int expression");
            }
        }
        int opcode = switch (generateTest) {
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

        if (generateTruePath.label == null) {
            generateTruePath.label = new Label();
        }
        generator.instGen.rawJumpOpcode(opcode, generateTruePath.label);
    }
}
