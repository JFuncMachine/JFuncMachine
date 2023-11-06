package com.wutka.jfuncmachine.compiler.model.types;

public record ShortType() implements Type {
    public String getBoxType() { return "java.lang.Short"; }

    public String getTypeDescriptor() { return "S"; }

    public boolean equals(Object other) {
        return other instanceof ShortType;
    }

    public int hash() { return 17; }
}
