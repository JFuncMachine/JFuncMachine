package com.wutka.jfuncmachine.compiler.model.types;

public class Field {
    public final String name;
    public final Type type;

    public Field(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
