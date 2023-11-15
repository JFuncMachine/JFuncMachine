package com.wutka.jfuncmachine.compiler.model.expr.bool;

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

    @Override
    public BooleanExpr computeSequence(BooleanExpr trueNext, BooleanExpr falseNext, Stack<BooleanExpr> tests) {
        return this;
    }
}
