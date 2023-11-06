package com.wutka.jfuncmachine.compiler.model.types;

public record LongType() implements Type {
    public String getBoxType() { return "java.lang.Long"; }

    public String getTypeDescriptor() { return "J"; }

    public boolean equals(Object other) {
        return other instanceof LongType;
    }

    public int hash() { return 13; }
}