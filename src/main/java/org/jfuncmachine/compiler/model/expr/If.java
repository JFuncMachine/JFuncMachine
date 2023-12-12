package org.jfuncmachine.compiler.model.expr;

import org.jfuncmachine.compiler.classgen.ClassGenerator;
import org.jfuncmachine.compiler.classgen.Environment;
import org.jfuncmachine.compiler.classgen.Label;
import org.jfuncmachine.compiler.model.expr.bool.*;
import org.jfuncmachine.compiler.model.types.Type;

import java.util.List;
import java.util.Stack;

/** A expression to perform a boolean test and return an expression depending on whether the
 * test is true or false;
 */
public class If extends Expression {
    /** The boolean test to perform */
    public final BooleanExpr test;
    /** The expression to return if the test is true */
    public final Expression trueExpr;
    /** The expression to return if the test is false. This may be null */
    public final Expression falseExpr;
    /** True if this expression has a falseExpr */
    public final boolean hasFalse;
    /** The sequence of comparisons executed by this test (this is computed when the Java byte code is generated */
    public List<BooleanExpr> testSequence;
    /** A placeholder used for generation to mark when the true expression is executed */
    public Result trueResult;
    /** A placeholder used for generation to mark when the false expression is executed */
    public Result falseResult;

    /** Create an if expression
     * @param test The test to perform
     * @param trueExpr The expression to return if the test is true
     * @param falseExpr The expression to return if the test is false
     */
    public If(BooleanExpr test, Expression trueExpr, Expression falseExpr) {
        super(null, 0);
        if (trueExpr.getType() != falseExpr.getType()) {
            generateException(
                    "True expression type is different from false expression type");
        }
        this.test = test.removeNot();
        this.trueExpr = trueExpr;
        this.falseExpr = falseExpr;
        this.hasFalse = true;
        this.computeTestSequence();
    }

    /** Create an if expression
     * @param test The test to perform
     * @param trueExpr The expression to return if the test is true
     * @param falseExpr The expression to return if the test is false
     * @param filename The source filename this expression is associated with
     * @param lineNumber The source line number this expression is associated with
     */
    public If(BooleanExpr test, Expression trueExpr, Expression falseExpr,
              String filename, int lineNumber) {
        super(filename, lineNumber);
        if (trueExpr.getType() != falseExpr.getType()) {
            generateException(
                    "True expression type is different from false expression type");
        }
        this.test = test.removeNot();
        this.trueExpr = trueExpr;
        this.falseExpr = falseExpr;
        this.hasFalse = true;
        this.computeTestSequence();
    }

    /** Create an if expression
     * @param test The test to perform
     * @param trueExpr The expression to return if the test is true
     */
    public If(BooleanExpr test, Expression trueExpr) {
        super(null, 0);
        this.test = test.removeNot();
        this.trueExpr = trueExpr;
        this.falseExpr = null;
        this.hasFalse = false;
        this.computeTestSequence();
    }

    /** Create an if expression
     * @param test The test to perform
     * @param trueExpr The expression to return if the test is true
     * @param filename The source filename this expression is associated with
     * @param lineNumber The source line number this expression is associated with
     */
    public If(BooleanExpr test, Expression trueExpr,
              String filename, int lineNumber) {
        super(filename, lineNumber);
        this.test = test.removeNot();
        this.trueExpr = trueExpr;
        this.falseExpr = null;
        this.hasFalse = false;
        this.computeTestSequence();
    }

    public void computeTestSequence() {
        trueResult = new Result(trueExpr);
        falseResult = new Result(falseExpr);

        testSequence = new Stack<>();
        test.computeSequence(trueResult, falseResult, testSequence);
    }

    public Type getType() {
        return trueExpr.getType();
    }

    @Override
    public void reset() {
        test.reset();
        trueExpr.reset();
        falseExpr.reset();
        computeTestSequence();
    }

    public void findCaptured(Environment env) {
        for (BooleanExpr booleanExpr: testSequence) {
            if (booleanExpr instanceof UnaryComparison unary) {
                unary.expr.findCaptured(env);
            } else if (booleanExpr instanceof BinaryComparison binary) {
                binary.left.findCaptured(env);
                binary.right.findCaptured(env);
            } else if (booleanExpr instanceof InstanceofComparison inst) {
                inst.expr.findCaptured(env);
            }
        }
        trueExpr.findCaptured(env);
        if (hasFalse) {
            falseExpr.findCaptured(env);
        }
    }

    @Override
    public void generate(ClassGenerator generator, Environment env, boolean inTailPosition) {
        Label endLabel = new Label();
        for (int i=testSequence.size()-1; i >= 0; i--) {
            BooleanExpr booleanExpr = testSequence.get(i);
            BooleanExpr nextExpr = null;
            if (i > 0) {
                nextExpr = testSequence.get(i-1);
            }
            if (booleanExpr instanceof UnaryComparison unary) {
                unary.generate(generator, env, nextExpr);
            } else if (booleanExpr instanceof BinaryComparison binary) {
                binary.generate(generator, env, nextExpr);
            } else if (booleanExpr instanceof InstanceofComparison instOf) {
                instOf.generate(generator, env, nextExpr);
            }
        }
        if (hasFalse) {
            if (falseResult.label != null) {
                generator.instGen.label(falseResult.label);
            }
            falseExpr.generate(generator, env, inTailPosition);
            generator.instGen.gotolabel(endLabel);
        }
        if (trueResult.label != null) {
            generator.instGen.label(trueResult.label);
        }
        trueExpr.generate(generator, env, inTailPosition);
        if (!hasFalse && falseResult.label != null) {
            generator.instGen.label(falseResult.label);
        }
        generator.instGen.label(endLabel);
    }
}
