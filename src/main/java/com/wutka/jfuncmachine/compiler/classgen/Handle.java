package com.wutka.jfuncmachine.compiler.classgen;

public class Handle {
    public final int tag;
    public final String owner;
    public final String name;
    public final String descriptor;
    public final boolean isInterface;

    public Handle(int tag, String owner, String name, String descriptor, boolean isInterface) {
        this.tag = tag;
        this.owner = owner;
        this.name = name;
        this.descriptor = descriptor;
        this.isInterface = isInterface;
    }

    protected org.objectweb.asm.Handle getAsmHandle() {
        return new org.objectweb.asm.Handle(tag, owner, name, descriptor, isInterface);
    }
}
