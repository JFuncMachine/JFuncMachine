package com.wutka.jfuncmachine.compiler.model.expr.conv;

import com.wutka.jfuncmachine.compiler.model.expr.Expression;
import com.wutka.jfuncmachine.compiler.model.types.SimpleTypes;
import com.wutka.jfuncmachine.compiler.model.types.Type;

public class ToShort extends Expression {
    protected Expression expr;

    public ToShort(Expression expr) {
        this.expr = expr;
    }

    public Type getType() { return SimpleTypes.SHORT; }
}
