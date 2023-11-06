package com.wutka.jfuncmachine.compiler.model.types;

public record FloatType() implements Type {
    public static final String BOX_TYPE = "java.lang.Float";

    public String getBoxType() { return BOX_TYPE; }

    public String getTypeDescriptor() { return "F"; }

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

    public int hash() { return 7; }

    public String toString() { return "FloatType"; }
}