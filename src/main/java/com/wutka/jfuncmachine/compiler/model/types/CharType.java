package com.wutka.jfuncmachine.compiler.model.types;

public record CharType() implements Type {
    public boolean equals(Object other) {
        return other instanceof CharType;
    }

    public int hash() { return 3; }
}