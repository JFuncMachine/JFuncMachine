package com.wutka.jfuncmachine.compiler.model.types;

public record BooleanType() implements Type {
    public String getBoxType() { return "java.lang.Boolean"; }

    public String getTypeDescriptor() { return "Z"; }

    public boolean equals(Object other) {
        return other instanceof BooleanType;
    }

    public int hash() { return 2; }
}