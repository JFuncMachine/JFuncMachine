package com.wutka.jfuncmachine.compiler.model.types;

public record BooleanType() implements Type {
    public static final String BOX_TYPE = "java.lang.Boolean";

    public String getBoxType() { return BOX_TYPE; }

    public boolean isUnboxableFrom(String className) {
        return className.equals("java.lang.Boolean");
    }

    public boolean hasIntRepresentation() { return true; }

    public boolean equals(Object other) {
        return other instanceof BooleanType;
    }

    public int hash() { return 2; }

    public String toString() { return "BooleanType"; }
}