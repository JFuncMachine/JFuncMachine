package com.wutka.jfuncmachine.compiler.model.types;

public record LongType() implements Type {
    public boolean equals(Object other) {
        return other instanceof LongType;
    }

    public int hash() { return 13; }
}