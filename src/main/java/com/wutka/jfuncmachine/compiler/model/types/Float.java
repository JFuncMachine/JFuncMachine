package com.wutka.jfuncmachine.compiler.model.types;

record Float() implements Type {
    public boolean equals(Object other) {
        return other instanceof Float;
    }

    public int hash() { return 7; }
}