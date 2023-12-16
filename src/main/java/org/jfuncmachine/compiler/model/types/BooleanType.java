package org.jfuncmachine.compiler.model.types;

/** A boolean type */
public record BooleanType() implements Type {
    /** The Java class name of the equivalent box type */
    public static final String BOX_TYPE = "java.lang.Boolean";

    public String getBoxTypeName() { return BOX_TYPE; }

    public boolean isUnboxableFrom(String className) {
        return className.equals("java.lang.Boolean");
    }

    public boolean hasIntRepresentation() { return true; }

    public boolean equals(Object other) {
        return other instanceof BooleanType;
    }

    @Override
    public int hashCode() {
        return 1;
    }

    public String toString() { return "BooleanType"; }
}