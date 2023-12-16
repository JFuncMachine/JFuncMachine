package org.jfuncmachine.compiler.model.expr.bool;

import org.jfuncmachine.compiler.classgen.ClassGenerator;
import org.jfuncmachine.compiler.classgen.Environment;
import org.jfuncmachine.compiler.classgen.Label;

import java.util.List;

/** A false constant for boolean expressions */
public class BoolFalse extends BooleanExpr {
    /** The test to execute if this one is false */
    public BooleanExpr falsePath;

    /** Create a false constant
     *
     * @param filename The source file containing this constant
     * @param lineNumber The line number in the source file where this constant is declared
     */
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

    /** Generate Java bytecode for this constant
     *
     * In the boolean expressions, a false constant means to take the false path.
     *
     * @param generator The class generator that is generating the current class
     * @param env The environment containing all visible variables
     * @param next The next boolean expr in the chain
     */
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
