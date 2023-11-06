package com.wutka.jfuncmachine.compiler.model.types;

public record ShortType() implements Type {
    public boolean equals(Object other) {
        return other instanceof ShortType;
    }

    public int hash() { return 17; }
}
