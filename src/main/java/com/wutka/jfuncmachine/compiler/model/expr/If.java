package com.wutka.jfuncmachine.compiler.model.expr;

import com.wutka.jfuncmachine.compiler.model.types.Type;

public class If extends Expression {
    public final Expression testExpr;
    public final Expression trueExpr;
    public final Expression falseExpr;

    public If(Expression testExpr, Expression trueExpr, Expression falseExpr, String filename, int lineNumber) {
        super(filename, lineNumber);
        if (trueExpr.getType() != falseExpr.getType()) {
            throw new RuntimeException(
                    "True expression type is different from false expression type");
        }
        this.testExpr = testExpr;
        this.trueExpr = trueExpr;
        this.falseExpr = falseExpr;
    }

    public Type getType() {
        return trueExpr.getType();
    }
}
