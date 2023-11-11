package com.wutka.jfuncmachine.compiler.model.expr.bool;

public class Not extends BooleanExpr {
    public BooleanExpr expr;

    public Not(BooleanExpr expr) {
        super(null, 0);
        this.expr = expr;
    }

    public Not(BooleanExpr expr, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.expr = expr;
    }
}
