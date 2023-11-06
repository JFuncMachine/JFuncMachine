package com.wutka.jfuncmachine.compiler.model.types;

public record CharType() implements Type {
    public static final String BOX_TYPE = "java.lang.Character";

    public String getBoxType() { return BOX_TYPE; }

    public String getTypeDescriptor() { return "C"; }

    public boolean isUnboxableFrom(String className) {
        return className.equals("java.lang.Character");
    }

    public boolean equals(Object other) {
        return other instanceof CharType;
    }

    public int hash() { return 3; }

    public String toString() { return "CharType"; }
}