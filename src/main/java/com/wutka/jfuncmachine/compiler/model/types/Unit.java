package com.wutka.jfuncmachine.compiler.model.types;

public record Unit() implements Type {
    public boolean equals(Object other) {
        return other instanceof Unit;
    }

    public int hash() { return 19; }
}
