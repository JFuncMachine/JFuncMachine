package com.wutka.jfuncmachine.compiler.model.types;

public record LongType() implements Type {
    public static final String BOX_TYPE = "java.lang.Long";

    public String getBoxType() { return BOX_TYPE; }

    public String getTypeDescriptor() { return "J"; }

    public boolean isUnboxableFrom(String className) {
        return className.equals("java.lang.Long") ||
                className.equals("java.lang.Integer") ||
                className.equals("java.lang.Short") ||
                className.equals("java.lang.Byte") ||
                className.equals("java.lang.Float") ||
                className.equals("java.lang.Double");
    }

    public boolean equals(Object other) {
        return other instanceof LongType;
    }

    public int hash() { return 13; }

    public String toString() { return "LongType"; }
}