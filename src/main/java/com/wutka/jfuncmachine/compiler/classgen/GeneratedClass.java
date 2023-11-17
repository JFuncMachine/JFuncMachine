package com.wutka.jfuncmachine.compiler.classgen;

public class GeneratedClass {
    public final String className;
    public final byte[] classBytes;
    public Class loadedClass;

    public GeneratedClass(String className, byte[] classBytes) {
        this.className = className;
        this.classBytes = classBytes;
    }
}
