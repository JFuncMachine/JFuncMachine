package com.wutka.jfuncmachine.compiler.model.expr.javaintop;

import com.wutka.jfuncmachine.compiler.model.expr.Expression;
import com.wutka.jfuncmachine.compiler.model.types.Type;

public class GetJavaStaticField extends Expression {
    public final String className;
    public final String fieldName;
    public final Type fieldType;

    public GetJavaStaticField(String className, String fieldName, Type fieldType) {
        this.className = className;
        this.fieldName = fieldName;
        this.fieldType = fieldType;
    }

    public Type getType() {
        return this.fieldType;
    }
}
