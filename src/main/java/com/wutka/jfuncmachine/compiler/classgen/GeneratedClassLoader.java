package com.wutka.jfuncmachine.compiler.classgen;

public class GeneratedClassLoader extends ClassLoader {
    protected Class defineClass(String name, byte[] classBytes) {
        return super.defineClass(name, classBytes, 0, classBytes.length);
    }

    protected void resolve(Class clazz) {
        super.resolveClass(clazz);
    }
}
