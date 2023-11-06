package com.wutka.jfuncmachine.compiler.model.types;

public record ByteType() implements Type {
    public String getBoxType() { return "java.lang.Byte"; }

    public String getTypeDescriptor() { return "B"; }

    public boolean equals(Object other) {
        return other instanceof ByteType;
    }

    public int hash() { return 2; }
}