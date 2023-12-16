package org.jfuncmachine.compiler.model.types;

/** A byte type */
public record ByteType() implements Type {
    /** The Java class name of the equivalent box type */
    public static final String BOX_TYPE = "java.lang.Byte";

    public String getBoxTypeName() { return BOX_TYPE; }

    public boolean isUnboxableFrom(String className) {
        return className.equals("java.lang.Byte") ||
                className.equals("java.lang.Integer") ||
                className.equals("java.lang.Long") ||
                className.equals("java.lang.Short") ||
                className.equals("java.lang.Float") ||
                className.equals("java.lang.Double");
    }

    public boolean hasIntRepresentation() { return true; }

    public boolean equals(Object other) {
        return other instanceof ByteType;
    }

    public int hashCode() { return 2; }

    public String toString() { return "ByteType"; }
}