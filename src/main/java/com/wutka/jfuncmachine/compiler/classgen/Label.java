package com.wutka.jfuncmachine.compiler.classgen;

public class Label {
    protected org.objectweb.asm.Label label;

    public Label() {
        this.label = new org.objectweb.asm.Label();
    }
}
