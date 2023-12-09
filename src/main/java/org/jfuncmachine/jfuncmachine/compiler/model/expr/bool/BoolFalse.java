package org.jfuncmachine.jfuncmachine.compiler.model.expr.bool;

import org.jfuncmachine.jfuncmachine.compiler.classgen.ClassGenerator;
import org.jfuncmachine.jfuncmachine.compiler.classgen.Environment;
import org.jfuncmachine.jfuncmachine.compiler.classgen.Label;

import java.util.List;

public class BoolFalse extends BooleanExpr {
    /** The test to execute if this one is false */
    public BooleanExpr falsePath;
    public BoolFalse(String filename, int lineNumber) {
        super(filename, lineNumber);
    }
    @Override
    public BooleanExpr invert() {
        return new BoolTrue(filename, lineNumber);
    }

    public BooleanExpr removeNot() {
        return this;
    }

    @Override
    public void reset() {
        super.reset();
        falsePath = null;
    }

    @Override
    public BooleanExpr computeSequence(BooleanExpr trueNext, BooleanExpr falseNext, List<BooleanExpr> tests) {
        this.falsePath = trueNext;
        tests.add(this);
        return this;
    }

    public void generate(ClassGenerator generator, Environment env, BooleanExpr next) {
        if (label != null) {
            generator.instGen.label(label);
        }

        BooleanExpr generateFalsePath = falsePath;

        if (generateFalsePath.label == null) {
            generateFalsePath.label = new Label();
        }
        generator.instGen.gotolabel(generateFalsePath.label);
    }
}
