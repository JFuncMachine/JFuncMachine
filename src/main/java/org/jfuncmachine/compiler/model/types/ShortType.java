package org.jfuncmachine.compiler.model.types;

public record ShortType() implements Type {
    public static final String BOX_TYPE = "java.lang.Short";

    public String getBoxTypeName() { return BOX_TYPE; }

    public boolean isUnboxableFrom(String className) {
        return className.equals("java.lang.Short") ||
                className.equals("java.lang.Integer") ||
                className.equals("java.lang.Long") ||
                className.equals("java.lang.Byte") ||
                className.equals("java.lang.Float") ||
                className.equals("java.lang.Double");
    }

    public boolean hasIntRepresentation() { return true; }

    public boolean equals(Object other) {
        return other instanceof ShortType;
    }

    public int hash() { return 17; }

    public String toString() { return "ShortType"; }
}