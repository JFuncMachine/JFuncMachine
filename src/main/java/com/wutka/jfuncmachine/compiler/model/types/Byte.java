package com.wutka.jfuncmachine.compiler.model.types;

record Byte() implements Type {
    public boolean equals(Object other) {
        return other instanceof Byte;
    }

    public int hash() { return 2; }
}