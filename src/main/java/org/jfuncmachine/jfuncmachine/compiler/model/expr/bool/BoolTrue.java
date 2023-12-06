package org.jfuncmachine.jfuncmachine.compiler.model.expr.bool;

import org.jfuncmachine.jfuncmachine.compiler.classgen.ClassGenerator;
import org.jfuncmachine.jfuncmachine.compiler.classgen.Environment;
import org.jfuncmachine.jfuncmachine.compiler.classgen.Label;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.bool.tests.Test;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.bool.tests.Tests;
import org.objectweb.asm.Opcodes;

import java.util.List;

public class BoolTrue extends BooleanExpr {
    /** The test to execute if this one is true */
    public BooleanExpr truePath;
    public BoolTrue(String filename, int lineNumber) {
        super(filename, lineNumber);
    }
    @Override
    public BooleanExpr invert() {
        return new BoolFalse(filename, lineNumber);
    }

    public BooleanExpr removeNot() {
        return this;
    }

    @Override
    public BooleanExpr computeSequence(BooleanExpr trueNext, BooleanExpr falseNext, List<BooleanExpr> tests) {
        this.truePath = trueNext;
        tests.add(this);
        return this;
    }

    public void generate(ClassGenerator generator, Environment env, BooleanExpr next) {
        if (label != null) {
            generator.instGen.label(label);
        }

        BooleanExpr generateTruePath = truePath;

        if (generateTruePath.label == null) {
            generateTruePath.label = new Label();
        }
        generator.instGen.gotolabel(generateTruePath.label);
    }
}
