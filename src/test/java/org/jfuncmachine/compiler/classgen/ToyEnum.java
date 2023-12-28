package org.jfuncmachine.compiler.classgen;

public enum ToyEnum {
    Moe(100),
    Larry(500),
    Curly(1000),
    Shemp(2000),
    CurlyJoe(10000);

    public final int value;

    ToyEnum(int value) {
        this.value = value;
    }
}
