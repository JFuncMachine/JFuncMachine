package com.wutka.jfuncmachine.compiler.model.types;

public record Double() implements Type {
    public boolean equals(Object other) {
        return other instanceof Double;
    }

    public int hash() { return 5; }
}