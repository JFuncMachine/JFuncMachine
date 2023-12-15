package org.jfuncmachine.compiler.model.types;

/** An int type */
public record IntType() implements Type {
    /** The Java class name of the equivalent box type */
    public static final String BOX_TYPE = "java.lang.Integer";

    public String getBoxTypeName() { return BOX_TYPE; }

    public boolean isUnboxableFrom(String className) {
        return className.equals("java.lang.Integer") ||
                className.equals("java.lang.Long") ||
                className.equals("java.lang.Short") ||
                className.equals("java.lang.Byte") ||
                className.equals("java.lang.Float") ||
                className.equals("java.lang.Double");
    }

    public boolean hasIntRepresentation() { return true; }

    public boolean equals(Object other) {
        return other instanceof IntType;
    }

    public int hashCode() { return 11; }

    public String toString() { return "IntType"; }
}