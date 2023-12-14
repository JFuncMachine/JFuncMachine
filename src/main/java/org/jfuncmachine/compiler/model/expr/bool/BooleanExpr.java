package org.jfuncmachine.compiler.model.expr.bool;

import org.jfuncmachine.compiler.classgen.Label;
import org.jfuncmachine.compiler.model.SourceElement;

import java.util.List;

/** An abstract class for different types of boolean expressions */
public abstract class BooleanExpr extends SourceElement {
    /** The label associated with this expression (may be null) */
    public Label label;

    /** Create a new BooleanExpr associated with a particular source filename and line number
     * @param filename The source filename this expression is associated with
     * @param lineNumber The source line number this expression is associated with
     */
    public BooleanExpr(String filename, int lineNumber) {
        super(filename, lineNumber);
    }

    /** Invert this expression, which may involve reversing comparisons or changing and to or with inverted
     * tests, etc.
     */
    public abstract BooleanExpr invert();

    /** Resets any values modified during code generation */
    public void reset() {
        label = null;
    }

    /** Remove any not expressions from a tree of boolean expressions by inverting the expression under the not */
    public abstract BooleanExpr removeNot();

    /** Compute a sequence of tests to determine if this expression is true or false
     * @param trueNext The next expression to evaluate if this expression is true
     * @param falseNext The next expression to evaluate if this expression is false
     * @param tests An accumulator for the tests to be executed
     */
    public abstract BooleanExpr computeSequence(BooleanExpr trueNext, BooleanExpr falseNext, List<BooleanExpr> tests);
}
