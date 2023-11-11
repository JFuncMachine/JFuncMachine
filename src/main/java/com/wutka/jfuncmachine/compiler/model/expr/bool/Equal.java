package com.wutka.jfuncmachine.compiler.model.expr.bool;

public class Equal extends BooleanExpr {
    public BooleanExpr left;
    public BooleanExpr right;

    public Equal(BooleanExpr left, BooleanExpr right) {
        super(null, 0);
        this.left = left;
        this.right = right;
    }

    public Equal(BooleanExpr left, BooleanExpr right, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.left = left;
        this.right = right;
    }
}
