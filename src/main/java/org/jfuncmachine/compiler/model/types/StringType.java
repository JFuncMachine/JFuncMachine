package org.jfuncmachine.compiler.model.types;

/** A string type */
public record StringType() implements Type {
    public boolean equals(Object other) {
        return other instanceof StringType;
    }

    public int hashCode() { return 17; }


    public String toString() { return "StringType"; }
}