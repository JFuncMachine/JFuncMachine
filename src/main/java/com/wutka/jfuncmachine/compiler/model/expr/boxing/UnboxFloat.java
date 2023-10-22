package com.wutka.jfuncmachine.compiler.model.expr.boxing;

import com.wutka.jfuncmachine.compiler.model.expr.Expression;
import com.wutka.jfuncmachine.compiler.model.types.SimpleTypes;
import com.wutka.jfuncmachine.compiler.model.types.Type;

public class UnboxFloat extends Expression {
    Expression expr;

    public UnboxFloat(Expression expr) {
        this.expr = expr;
    }

    public Type getType() { return SimpleTypes.FLOAT; }
}
