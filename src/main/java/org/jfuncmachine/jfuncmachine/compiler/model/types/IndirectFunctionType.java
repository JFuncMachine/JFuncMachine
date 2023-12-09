package org.jfuncmachine.jfuncmachine.compiler.model.types;

public record IndirectFunctionType(FunctionType containedType) implements Type {

    public String toString() {
        return "IndirectFunctionType["+containedType.toString()+"]";
    }
}