package com.wutka.jfuncmachine.compiler.classgen;

public class Label {
    protected final org.objectweb.asm.Label label;

    public Label() {
        this.label = new org.objectweb.asm.Label();
    }
}
