package com.wutka.jfuncmachine.compiler.model.types;

record Int() implements Type {
    public boolean equals(Object other) {
        return other instanceof Int;
    }

    public int hash() { return 11; }
}