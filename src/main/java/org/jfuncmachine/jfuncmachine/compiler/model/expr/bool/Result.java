package org.jfuncmachine.jfuncmachine.compiler.model.expr.bool;

import org.jfuncmachine.jfuncmachine.compiler.model.expr.Expression;

import java.util.List;

/** Represents the end point of a boolean expression, containing the expression to be
 * executed depending on whether the boolean test is true or false.
 * This class doesn't actually generate the code to execute the expression, it is
 * more of a placeholder.
 */
public class Result extends BooleanExpr {
    /** The expression to be executed */
    Expression expr;

    /** Create a new Result for an expression
     * @param expr The expression that should be executed if this result is reached
     */
    public Result(Expression expr) {
        super(null, 0);
        this.expr = expr;
    }

    /** Create a new Result for an expression
     * @param expr The expression that should be executed if this result is reached
     * @param filename The source filename this expression is associated with
     * @param lineNumber The source line number this expression is associated with
     */
    public Result(Expression expr, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.expr = expr;
    }

    public BooleanExpr invert() { return this; }
    public BooleanExpr removeNot() { return this; }

    @Override
    public BooleanExpr computeSequence(BooleanExpr trueNext, BooleanExpr falseNext, List<BooleanExpr> tests) {
        return this;
    }
}
