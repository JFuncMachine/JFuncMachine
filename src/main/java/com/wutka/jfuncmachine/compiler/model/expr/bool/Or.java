package com.wutka.jfuncmachine.compiler.model.expr.bool;

import java.util.List;
import java.util.Stack;

/** Represents a short-circuiting or of two boolean expressions. */
public class Or extends BooleanExpr {
    /** The left-hand expression */
    public BooleanExpr left;
    /** The right-hand expression */
    public BooleanExpr right;

    /** Create a new Or expression
     * @param left The left-hand expression
     * @param right The right-hand expression
     */
    public Or(BooleanExpr left, BooleanExpr right) {
        super(null, 0);
        this.left = left;
        this.right = right;
    }

    /** Create a new Or expression
     * @param left The left-hand expression
     * @param right The right-hand expression
     * @param filename The source filename this expression is associated with
     * @param lineNumber The source line number this expression is associated with
     */
    public Or(BooleanExpr left, BooleanExpr right, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.left = left;
        this.right = right;
    }

    public BooleanExpr invert() {
        return new And(left.invert(), right.invert());
    }

    public BooleanExpr removeNot() {
        this.left = this.left.removeNot();
        this.right = this.right.removeNot();
        return this;
    }

    public BooleanExpr computeSequence(BooleanExpr trueNext, BooleanExpr falseNext, List<BooleanExpr> tests) {
        BooleanExpr rightPath = right.computeSequence(trueNext, falseNext, tests);
        return left.computeSequence(trueNext, rightPath, tests);
    }
}
