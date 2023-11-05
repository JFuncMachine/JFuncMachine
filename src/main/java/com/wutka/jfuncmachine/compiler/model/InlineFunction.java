package com.wutka.jfuncmachine.compiler.model;

import com.wutka.jfuncmachine.compiler.model.types.Type;

public class InlineFunction extends SourceElement {
    public final Type[] parameterTypes;
    public final Type returnType;


    public InlineFunction(Type[] parameterTypes, Type returnType,
                          String filename, int lineNumber) {
        super(filename, lineNumber);
        this.parameterTypes = parameterTypes;
        this.returnType = returnType;
    }
    public Type getReturnType() {
        return returnType;
    }
}
