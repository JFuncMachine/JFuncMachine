package org.jfuncmachine.compiler.model.expr.bool;

import org.jfuncmachine.compiler.classgen.ClassGenerator;
import org.jfuncmachine.compiler.classgen.Environment;
import org.jfuncmachine.compiler.classgen.Label;
import org.jfuncmachine.compiler.model.expr.Expression;
import org.jfuncmachine.compiler.model.expr.bool.tests.Test;
import org.jfuncmachine.compiler.model.expr.bool.tests.Tests;
import org.objectweb.asm.Opcodes;

import java.util.List;

/** Represents an instanceof comparison (or not instanceof) */
public class InstanceofComparison extends BooleanExpr {
    /** The test to use */
    public Test test;
    /** The expression to test */
    public Expression expr;
    /** The class name to test against */
    public String className;
    /** The test to execute if this one is true */
    public BooleanExpr truePath;
    /** The test to execute if this one is false */
    public BooleanExpr falsePath;

    /** Create an InstanceofComparison for a given test, expression and class name
     * @param test The test to perform
     * @param expr The expression to test
     * @param className The class name to test against
     */
    public InstanceofComparison(Test test, Expression expr, String className) {
        super(null, 0);
        this.test = test;
        this.className = className;
        this.expr = expr;
    }

    /** Create an InstanceofComparison for a given test, expression and class name
     * @param test The test to perform
     * @param expr The expression to test
     * @param className The class name to test against
     * @param filename The source filename this expression is associated with
     * @param lineNumber The source line number this expression is associated with
     */
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

    @Override
    public void reset() {
        super.reset();
        falsePath = null;
        truePath = null;
        expr.reset();
    }

    public BooleanExpr computeSequence(BooleanExpr trueNext, BooleanExpr falseNext, List<BooleanExpr> tests) {
        this.falsePath = falseNext;
        this.truePath = trueNext;

        tests.add(this);

        return this;
    }

    /** Identify any variables captured by the test expression
     *
     * @param env The environment chain to be searched
     */
    public void findCaptured(Environment env) {
        expr.findCaptured(env);
    }

    /** Generate Java bytecode for this comparison
     *
     * @param generator The class generator that is generating the current class
     * @param env The environment containing all visible variables
     * @param next The next boolean expr in the chain
     */
    public void generate(ClassGenerator generator, Environment env, BooleanExpr next) {
        if (label != null) {
            generator.instGen.label(label);
        }
        expr.generate(generator, env, false);
        generator.instGen.instance_of(generator.className(className));

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

        int opcode = switch (generateTest) {
            case Tests.IsTrueTest t -> Opcodes.IFNE;
            case Tests.IsFalseTest t -> Opcodes.IFEQ;
            default -> throw generateException("Invalid test for instanceof comparison");
        };

        if (generateTruePath.label == null) {
            generateTruePath.label = new Label();
        }
        generator.instGen.rawJumpOpcode(opcode, generateTruePath.label);
    }
}
