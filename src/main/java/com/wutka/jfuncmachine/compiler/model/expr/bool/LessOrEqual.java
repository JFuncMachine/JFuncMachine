package com.wutka.jfuncmachine.compiler.model.expr.bool;

public class LessOrEqual extends BooleanExpr {
    public BooleanExpr left;
    public BooleanExpr right;

    public LessOrEqual(BooleanExpr left, BooleanExpr right) {
        super(null, 0);
        this.left = left;
        this.right = right;
    }

    public LessOrEqual(BooleanExpr left, BooleanExpr right, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.left = left;
        this.right = right;
    }
}
