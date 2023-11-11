package com.wutka.jfuncmachine.compiler.model.expr.bool;

public class Less extends BooleanExpr {
    public BooleanExpr left;
    public BooleanExpr right;

    public Less(BooleanExpr left, BooleanExpr right) {
        super(null, 0);
        this.left = left;
        this.right = right;
    }

    public Less(BooleanExpr left, BooleanExpr right, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.left = left;
        this.right = right;
    }
}
