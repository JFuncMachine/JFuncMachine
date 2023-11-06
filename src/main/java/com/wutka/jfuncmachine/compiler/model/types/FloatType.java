package com.wutka.jfuncmachine.compiler.model.types;

public record FloatType() implements Type {
    public boolean equals(Object other) {
        return other instanceof FloatType;
    }

    public int hash() { return 7; }
}