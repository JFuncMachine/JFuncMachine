package com.wutka.jfuncmachine.compiler.model.expr.bool;

import com.wutka.jfuncmachine.compiler.model.expr.Expression;

public class Result extends BooleanExpr {
    public final Expression expr;

    public Result(Expression expr) {
        super(null, 0);
        this.expr = expr;
    }

    public Result(Expression expr, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.expr = expr;
    }

    public BooleanExpr invert() { return this; }
    public BooleanExpr removeNot() { return this; }

    @Override
    public BooleanExpr computeSequence(BooleanExpr trueNext, BooleanExpr falseNext) {
        return this;
    }
}
