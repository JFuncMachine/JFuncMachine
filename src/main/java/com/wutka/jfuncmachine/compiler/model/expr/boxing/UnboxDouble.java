package com.wutka.jfuncmachine.compiler.model.expr.boxing;

import com.wutka.jfuncmachine.compiler.model.expr.Expression;
import com.wutka.jfuncmachine.compiler.model.types.SimpleTypes;
import com.wutka.jfuncmachine.compiler.model.types.Type;

public class UnboxDouble extends Expression {
    Expression expr;

    public UnboxDouble(Expression expr, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.expr = expr;
    }

    public Type getType() { return SimpleTypes.DOUBLE; }
}
