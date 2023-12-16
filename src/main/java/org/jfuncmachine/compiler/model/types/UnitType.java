package org.jfuncmachine.compiler.model.types;

/** A string type */
public record UnitType() implements Type {

    public boolean equals(Object other) {
        return other instanceof UnitType;
    }

    public int hashCode() { return 19; }

    public String toString() { return "UnitType"; }
}