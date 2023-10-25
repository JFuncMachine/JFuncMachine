package com.wutka.jfuncmachine.compiler.model.types;

public record Char() implements Type {
    public boolean equals(Object other) {
        return other instanceof Char;
    }

    public int hash() { return 3; }
}