package org.jfuncmachine.jfuncmachine.compiler.model.expr.bool;

import java.util.List;

/** Represents the negation of a boolean expression */
public class Not extends BooleanExpr {
    /** The expression to negate */
    public BooleanExpr expr;

    /** Create a negation of a boolean expression
     * @param expr The expression to negate
     */
    public Not(BooleanExpr expr) {
        super(null, 0);
        this.expr = expr;
    }

    /** Create a negation of a boolean expression
     * @param expr The expression to negate
     * @param filename The source filename this expression is associated with
     * @param lineNumber The source line number this expression is associated with
     */
    public Not(BooleanExpr expr, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.expr = expr;
    }

    public BooleanExpr invert() {
        return expr;
    }

    public BooleanExpr removeNot() {
        this.expr = expr.invert();
        return expr.removeNot();
    }

    @Override
    public void resetLabels() {
        expr.resetLabels();
    }

    @Override
    public BooleanExpr computeSequence(BooleanExpr trueNext, BooleanExpr falseNext, List<BooleanExpr> tests) {
        return this;
    }
}
