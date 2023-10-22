package com.wutka.jfuncmachine.compiler.model.expr.javaintop;

import com.wutka.jfuncmachine.compiler.model.expr.Expression;
import com.wutka.jfuncmachine.compiler.model.types.SimpleTypes;
import com.wutka.jfuncmachine.compiler.model.types.Type;

public class SetJavaField extends Expression {
    public final String className;
    public final String fieldName;
    public final Expression expr;

    public SetJavaField(String className, String fieldName, Expression expr) {
        this.className = className;
        this.fieldName = fieldName;
        this.expr = expr;
    }

    public Type getType() {
        return SimpleTypes.UNIT;
    }
}
