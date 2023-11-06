package com.wutka.jfuncmachine.compiler.model.types;

import java.util.Objects;

public final class ObjectType implements Type {
    public final String className;

    public ObjectType(String className) {
        this.className = className;
    }

    public boolean equals(Object other) {
        if (other instanceof ObjectType jOther) {
            return className.equals(jOther.className);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(className);
    }
}
