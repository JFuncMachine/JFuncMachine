package com.wutka.jfuncmachine.compiler.model.types;

public record Byte() implements Type {
    public boolean equals(Object other) {
        return other instanceof Byte;
    }

    public int hash() { return 2; }
}