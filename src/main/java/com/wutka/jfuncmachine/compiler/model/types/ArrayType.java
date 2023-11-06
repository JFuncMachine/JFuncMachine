package com.wutka.jfuncmachine.compiler.model.types;

public record ArrayType(Type containedType) implements Type {
    public String getTypeDescriptor() {
        return "["+containedType.getTypeDescriptor();
    }

    public String toString() {
        return "ArrayType["+containedType.toString()+"]";
    }
}