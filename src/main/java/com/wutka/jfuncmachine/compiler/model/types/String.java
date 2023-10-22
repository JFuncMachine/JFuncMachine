package com.wutka.jfuncmachine.compiler.model.types;

public record String() implements Type {
    public boolean equals(Object other) {
        return other instanceof String;
    }

    public int hash() { return 17; }
}
