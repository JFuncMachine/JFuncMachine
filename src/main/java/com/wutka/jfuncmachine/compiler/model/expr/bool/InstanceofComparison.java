package com.wutka.jfuncmachine.compiler.model.expr.bool;

import com.wutka.jfuncmachine.compiler.classgen.ClassGenerator;
import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.classgen.Label;
import com.wutka.jfuncmachine.compiler.model.expr.Expression;
import com.wutka.jfuncmachine.compiler.model.expr.bool.tests.Test;
import com.wutka.jfuncmachine.compiler.model.expr.bool.tests.Tests;
import org.objectweb.asm.Opcodes;

import java.util.Stack;

public class InstanceofComparison extends BooleanExpr {
    public Test test;
    public Expression expr;
    public String className;
    public BooleanExpr truePath;
    public BooleanExpr falsePath;

    public InstanceofComparison(Test test, Expression expr, String className) {
        super(null, 0);
        this.test = test;
        this.className = className;
        this.expr = expr;
    }

    public InstanceofComparison(Test test, Expression expr, String className, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.test = test;
        this.className = className;
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
        generator.instGen.instance_of(className);

        Test generateTest = test;
        BooleanExpr generateTruePath = truePath;
        BooleanExpr generateFalsePath = falsePath;

        if (truePath == next) {
            generateTest = test.invert();
            generateTruePath = falsePath;
            generateFalsePath = truePath;
        }

        int opcode = switch (generateTest) {
            case Tests.EQTest t -> Opcodes.IFEQ;
            case Tests.NETest t -> Opcodes.IFNE;
            default -> throw generateException("Invalid test for instanceof comparison");
        };

        if (generateTruePath.label == null) {
            generateTruePath.label = new Label();
        }
        generator.instGen.rawJumpOpcode(opcode, generateTruePath.label);
    }
}
