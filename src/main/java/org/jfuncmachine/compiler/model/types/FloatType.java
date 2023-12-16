package org.jfuncmachine.compiler.model.types;

/** A float type */
public record FloatType() implements Type {
    /** The Java class name of the equivalent box type */
    public static final String BOX_TYPE = "java.lang.Float";

    public String getBoxTypeName() { return BOX_TYPE; }

    public boolean isUnboxableFrom(String className) {
        return className.equals("java.lang.Float") ||
                className.equals("java.lang.Double") ||
                className.equals("java.lang.Integer") ||
                className.equals("java.lang.Long") ||
                className.equals("java.lang.Short") ||
                className.equals("java.lang.Byte");
    }

    public boolean equals(Object other) {
        return other instanceof FloatType;
    }

    public int hashCode() { return 7; }

    public String toString() { return "FloatType"; }
}