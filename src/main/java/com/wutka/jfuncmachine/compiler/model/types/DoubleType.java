package com.wutka.jfuncmachine.compiler.model.types;

public record DoubleType() implements Type {
    public boolean equals(Object other) {
        return other instanceof DoubleType;
    }

    public int hash() { return 5; }
}