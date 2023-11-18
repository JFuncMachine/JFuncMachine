package com.wutka.jfuncmachine.compiler.model.expr.bool;

import com.wutka.jfuncmachine.compiler.classgen.ClassGenerator;
import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.classgen.Label;
import com.wutka.jfuncmachine.compiler.model.expr.Expression;

import java.util.List;
import java.util.Stack;

/** Represents a short-circuiting and of boolean expressions. */
public class And extends BooleanExpr {
    /** The left-hand side of the and expression */
    public BooleanExpr left;
    /** The right-hand side of the and expression */
    public BooleanExpr right;

    /** Create an And expression with the given left- and right-hand sides
     * @param left The left-hand side of the and expression
     * @param right The right-hand side of the and expression
     */
    public And(BooleanExpr left, BooleanExpr right) {
        super(null, 0);
        this.left = left;
        this.right = right;
    }

    /** Create an And expression with the given left- and right-hand sides
     * @param left The left-hand side of the and expression
     * @param right The right-hand side of the and expression
     * @param filename The source filename this expression is associated with
     * @param lineNumber The source line number this expression is associated with
     */
    public And(BooleanExpr left, BooleanExpr right, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.left = left;
        this.right = right;
    }

    public BooleanExpr invert() {
        /* Invert this expression by converting it to an or with the left- and right-hand sides inverted.
         * That is, A and B is not true when not A or not B
         */
        return new Or(left.invert(), right.invert());
    }

    public BooleanExpr removeNot() {
        this.left = this.left.removeNot();
        this.right = this.right.removeNot();
        return this;
    }

    public BooleanExpr computeSequence(BooleanExpr trueNext, BooleanExpr falseNext, List<BooleanExpr> tests) {
        BooleanExpr rightPath = right.computeSequence(trueNext, falseNext, tests);
        return left.computeSequence(rightPath, falseNext, tests);
    }
}
