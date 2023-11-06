package com.wutka.jfuncmachine.compiler.model.types;

public record IntType() implements Type {
    public boolean equals(Object other) {
        return other instanceof IntType;
    }

    public int hash() { return 11; }
}