package com.wutka.jfuncmachine.compiler.model.expr;

import com.wutka.jfuncmachine.compiler.model.expr.Expression;
import com.wutka.jfuncmachine.compiler.model.types.Field;
import com.wutka.jfuncmachine.compiler.model.types.Type;

public record LambdaFunction(String name, Field[] parameters, Field[] capturedValues,
                             Expression body) {
    public Type getReturnType() {
        return body.getType();
    }
}
