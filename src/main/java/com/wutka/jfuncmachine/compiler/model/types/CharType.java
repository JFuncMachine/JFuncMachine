package com.wutka.jfuncmachine.compiler.model.types;

public record CharType() implements Type {
    public String getBoxType() { return "java.lang.Character"; }

    public String getTypeDescriptor() { return "C"; }

    public boolean equals(Object other) {
        return other instanceof CharType;
    }

    public int hash() { return 3; }
}