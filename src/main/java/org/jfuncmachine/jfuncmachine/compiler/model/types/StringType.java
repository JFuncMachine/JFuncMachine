package org.jfuncmachine.jfuncmachine.compiler.model.types;

public record StringType() implements Type {
    public boolean equals(Object other) {
        return other instanceof StringType;
    }

    public int hash() { return 17; }


    public String toString() { return "StringType"; }
}