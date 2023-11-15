package com.wutka.jfuncmachine.compiler.model.expr.bool;

import com.wutka.jfuncmachine.compiler.classgen.Label;
import com.wutka.jfuncmachine.compiler.model.expr.Expression;

import java.util.Stack;

public class Result extends BooleanExpr {
    Expression expr;

    public Result(Expression expr) {
        super(null, 0);
        this.expr = expr;
        label = new Label();
    }

    public Result(Expression expr, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.expr = expr;
        label = new Label();
    }

    public BooleanExpr invert() { return this; }
    public BooleanExpr removeNot() { return this; }

    @Override
    public BooleanExpr computeSequence(BooleanExpr trueNext, BooleanExpr falseNext, Stack<BooleanExpr> tests) {
        return this;
    }
}
