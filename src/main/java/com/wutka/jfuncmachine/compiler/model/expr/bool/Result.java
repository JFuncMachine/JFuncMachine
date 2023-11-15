package com.wutka.jfuncmachine.compiler.model.expr.bool;

import com.wutka.jfuncmachine.compiler.classgen.Label;
import com.wutka.jfuncmachine.compiler.model.expr.Expression;

import java.util.List;
import java.util.Stack;

public class Result extends BooleanExpr {
    Expression expr;

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
}
