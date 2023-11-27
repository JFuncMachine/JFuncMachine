package org.jfuncmachine.jfuncmachine.compiler.model.types;

public record ArrayType(Type containedType) implements Type {

    public String toString() {
        return "ArrayType["+containedType.toString()+"]";
    }
}