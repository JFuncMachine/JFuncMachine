package com.wutka.jfuncmachine.compiler.model.types;

public record Short() implements Type {
    public boolean equals(Object other) {
        return other instanceof Short;
    }

    public int hash() { return 17; }
}
