package com.wutka.jfuncmachine.compiler.classgen;

/**
 * A class loader used by the generator to immediately load classes into the JVM.
 */
public class GeneratedClassLoader extends ClassLoader {
    /**
     * Create a new GeneratedClassLoader with the specified class loader as a parent
     * @param parent The parent of this class loader
     */
    protected GeneratedClassLoader(ClassLoader parent) {
        super(parent);
    }

    /**
     * Define a new class from a byte array
     * @param name The fully-qualified name of the class
     * @param classBytes The byte array defining the class
     * @return The loaded class file (loaded but not yet resolved)
     */
    protected Class defineClass(String name, byte[] classBytes) {
        return super.defineClass(name, classBytes, 0, classBytes.length);
    }

    /**
     * Resolve the class (perform the runtime linking)
     * @param clazz The class to resolve
     */
    protected void resolve(Class clazz) {
        super.resolveClass(clazz);
    }
}
