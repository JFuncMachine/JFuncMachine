package com.wutka.jfuncmachine.compiler.model.expr.javaintop;

import com.wutka.jfuncmachine.compiler.model.expr.Expression;
import com.wutka.jfuncmachine.compiler.model.types.Type;

public class GetJavaField extends Expression {
    public final String className;
    public final String fieldName;
    public final Type fieldType;

    public GetJavaField(String className, String fieldName, Type fieldType) {
        this.className = className;
        this.fieldName = fieldName;
        this.fieldType = fieldType;
    }

    public Type getType() {
        return this.fieldType;
    }
}
