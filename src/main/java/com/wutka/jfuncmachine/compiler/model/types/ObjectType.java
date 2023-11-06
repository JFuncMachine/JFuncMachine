package com.wutka.jfuncmachine.compiler.model.types;

import com.wutka.jfuncmachine.compiler.classgen.Naming;

import java.util.Objects;

public final class ObjectType implements Type {
    public final String className;

    public ObjectType() {
        this.className = "java.lang.Object";
    }

    public ObjectType(String className) {

        this.className = className;
    }

    public String getTypeDescriptor() {
        return "L" + Naming.className(className) + ";";
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
