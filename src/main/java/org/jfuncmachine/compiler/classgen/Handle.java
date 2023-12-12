package org.jfuncmachine.compiler.classgen;

/**
 * A handle to a field or method that eventually gets mapped to a MethodHandle
 * for bootstrap methods.
 */
public class Handle {
    /** This handle is for fetching a field value */
    public static final int GETFIELD = 1;
    /** This handle is for fetching a static field value */
    public static final int GETSTATIC = 2;
    /** This handle is for setting a field value */
    public static final int PUTFIELD = 3;
    /** This handle is for setting a static field value */
    public static final int PUTSTATIC = 4;
    /** This handle is for a non-static Java method */
    public static final int INVOKEVIRTUAL = 5;
    /** This handle is for a static Java method */
    public static final int INVOKESTATIC = 6;
    /** This handle is for a Java private method, constructor, or super method */
    public static final int INVOKESPECIAL = 7;
    /** This handle is for a Java private method, constructor, or super method */
    public static final int NEWINVOKESPECIAL = 8;
    /** This handle is for a method on an interface */
    public static final int INVOKEINTERFACE = 9;

    /** The type of handle this is */
    public final int tag;

    /** The class containing this handle */
    public final String owner;

    /** The name of the method/field this handle refers to */
    public final String name;

    /** The type descriptor of the field or method */
    public final String descriptor;

    /** If true, this handle refers to a member of an interface */
    public final boolean isInterface;

    public Handle(int tag, String owner, String name, String descriptor, boolean isInterface) {
        this.tag = tag;
        this.owner = owner;
        this.name = name;
        this.descriptor = descriptor;
        this.isInterface = isInterface;
    }

    /**
     * Create an ObjectWeb ASM version of this handle
     * @return An ASM version of the handle
     */
    protected org.objectweb.asm.Handle getAsmHandle() {
        return new org.objectweb.asm.Handle(tag, owner, name, descriptor, isInterface);
    }
}
