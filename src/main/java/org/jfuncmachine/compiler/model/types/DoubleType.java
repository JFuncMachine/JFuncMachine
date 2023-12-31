package org.jfuncmachine.compiler.model.types;

/** A double type */
public record DoubleType() implements Type {
    /** The Java class name of the equivalent box type */
    public static final String BOX_TYPE = "java.lang.Double";
    public String getBoxTypeName() { return BOX_TYPE; }

    public boolean isUnboxableFrom(String className) {
        return className.equals("java.lang.Double") ||
                className.equals("java.lang.Float") ||
                className.equals("java.lang.Integer") ||
                className.equals("java.lang.Long") ||
                className.equals("java.lang.Short") ||
                className.equals("java.lang.Byte");
    }

    public int getStackSize() { return 2; }

    public boolean equals(Object other) {
        return other instanceof DoubleType;
    }

    public int hashCode() { return 5; }

    public String toString() { return "DoubleType"; }
}