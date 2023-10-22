package com.wutka.jfuncmachine.compiler.model.types;

record Long() implements Type {
    public boolean equals(Object other) {
        return other instanceof Long;
    }

    public int hash() { return 13; }
}