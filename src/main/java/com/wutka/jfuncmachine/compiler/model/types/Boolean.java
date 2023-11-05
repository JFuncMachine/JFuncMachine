package com.wutka.jfuncmachine.compiler.model.types;

public record Boolean() implements Type {
    public boolean equals(Object other) {
        return other instanceof Boolean;
    }

    public int hash() { return 2; }
}