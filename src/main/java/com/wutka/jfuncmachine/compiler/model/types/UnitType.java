package com.wutka.jfuncmachine.compiler.model.types;

public record UnitType() implements Type {
    public String getTypeDescriptor() { return "V"; }

    public boolean equals(Object other) {
        return other instanceof UnitType;
    }

    public int hash() { return 19; }
}
