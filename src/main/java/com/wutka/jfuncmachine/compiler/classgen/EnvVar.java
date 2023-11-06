package com.wutka.jfuncmachine.compiler.classgen;

import com.wutka.jfuncmachine.compiler.model.types.Type;

public class EnvVar {
    public final String name;
    public final Type type;
    public final int value;

    public EnvVar(String name, Type type, int value) {
        this.name = name;
        this.type = type;
        this.value = value;
    }
}
