package com.wutka.jfuncmachine.compiler.model.func;

import com.wutka.jfuncmachine.compiler.model.expr.Expression;
import com.wutka.jfuncmachine.compiler.model.types.Field;
import com.wutka.jfuncmachine.compiler.model.types.Type;

public record Function(String name, Field[] parameters, Expression body) {
    public Type getReturnType() {
        return body.getType();
    }
}
