package org.jfuncmachine.compiler.model.types;

/** A char type */
public record CharType() implements Type {
    /** The Java class name of the equivalent box type */
    public static final String BOX_TYPE = "java.lang.Character";

    public String getBoxTypeName() { return BOX_TYPE; }

    public boolean isUnboxableFrom(String className) {
        return className.equals("java.lang.Character");
    }

    public boolean hasIntRepresentation() { return true; }

    public boolean equals(Object other) {
        return other instanceof CharType;
    }

    public int hashCode() { return 3; }

    public String toString() { return "CharType"; }
}