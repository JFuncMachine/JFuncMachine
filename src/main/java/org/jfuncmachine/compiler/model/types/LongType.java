package org.jfuncmachine.compiler.model.types;

/** A long type */
public record LongType() implements Type {
    /** The Java class name of the equivalent box type */
    public static final String BOX_TYPE = "java.lang.Long";

    public String getBoxTypeName() { return BOX_TYPE; }

    public boolean isUnboxableFrom(String className) {
        return className.equals("java.lang.Long") ||
                className.equals("java.lang.Integer") ||
                className.equals("java.lang.Short") ||
                className.equals("java.lang.Byte") ||
                className.equals("java.lang.Float") ||
                className.equals("java.lang.Double");
    }

    public int getStackSize() { return 2; }

    public boolean equals(Object other) {
        return other instanceof LongType;
    }

    public int hashCode() { return 13; }

    public String toString() { return "LongType"; }
}