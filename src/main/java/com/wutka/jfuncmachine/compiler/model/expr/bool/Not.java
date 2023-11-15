package com.wutka.jfuncmachine.compiler.model.expr.bool;

import java.util.List;
import java.util.Stack;

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

    public BooleanExpr invert() {
        return expr;
    }

    public BooleanExpr removeNot() {
        this.expr = expr.invert();
        return expr.removeNot();
    }
}
