package com.wutka.jfuncmachine.compiler.classgen;

/**
 * A container for generated class bytes and optionally the loaded class.
 */
public class GeneratedClass {
    /** The fully-qualified name of this class */
    public final String className;

    /** The byte array representing the class (exactly the bytes that would be in a .class file) */
    public final byte[] classBytes;

     /** The class as loaded by the generator's internal classloader */
    public Class loadedClass;

    /**
     * Create a new generated class
     * @param className The fully-qualified class name
     * @param classBytes The byte[] array containing the class
     */
    public GeneratedClass(String className, byte[] classBytes) {
        this.className = className;
        this.classBytes = classBytes;
    }
}
