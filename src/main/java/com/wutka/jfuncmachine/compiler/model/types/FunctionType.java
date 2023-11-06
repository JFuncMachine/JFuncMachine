package com.wutka.jfuncmachine.compiler.model.types;

public record FunctionType(Type[] parameterTypes, Type returnType) implements Type {
    // TODO: implement FunctionType
    public String getTypeDescriptor() { return null; }
}
