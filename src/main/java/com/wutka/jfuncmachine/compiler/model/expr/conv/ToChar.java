package com.wutka.jfuncmachine.compiler.model.expr.conv;

import com.wutka.jfuncmachine.compiler.model.expr.Expression;
import com.wutka.jfuncmachine.compiler.model.types.SimpleTypes;
import com.wutka.jfuncmachine.compiler.model.types.Type;

public class ToChar extends Expression {
    protected Expression expr;

    public ToChar(Expression expr) {
        this.expr = expr;
    }

    public Type getType() { return SimpleTypes.CHAR; }
}
