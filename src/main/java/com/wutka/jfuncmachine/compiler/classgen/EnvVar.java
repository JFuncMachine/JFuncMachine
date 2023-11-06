package com.wutka.jfuncmachine.compiler.classgen;

import com.wutka.jfuncmachine.compiler.model.types.Type;

import java.util.Objects;

public class EnvVar {
    public final String name;
    public final Type type;
    public final int value;

    public EnvVar(String name, Type type, int value) {
        this.name = name;
        this.type = type;
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EnvVar envVar = (EnvVar) o;
        return value == envVar.value && Objects.equals(name, envVar.name) && Objects.equals(type, envVar.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, value);
    }
}
