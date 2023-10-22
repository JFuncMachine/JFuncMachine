package com.wutka.jfuncmachine.compiler.model.expr.conv;

import com.wutka.jfuncmachine.compiler.model.expr.Expression;
import com.wutka.jfuncmachine.compiler.model.types.SimpleTypes;
import com.wutka.jfuncmachine.compiler.model.types.Type;

public class ToLong extends Expression {
    protected Expression expr;

    public ToLong(Expression expr, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.expr = expr;
    }

    public Type getType() { return SimpleTypes.LONG; }
}
