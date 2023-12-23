package org.jfuncmachine.compiler.model;

import org.objectweb.asm.Opcodes;

/**
 * Values describing the various access options for Java classes, interfaces, and methods.
 *
 * The values in this class can be combined by addition, so "public static"
 * is represented as Access.PUBLIC + Access.STATIC.
 */
public class Access {
    /** The class or method is abstract */
    public static final int ABSTRACT = Opcodes.ACC_ABSTRACT;
    /** The class or field is final */
    public static final int FINAL = Opcodes.ACC_FINAL;
    /** The class is an interface */
    public static final int INTERFACE = Opcodes.ACC_INTERFACE;
    /** The class, field, or method is private */
    public static final int PRIVATE = Opcodes.ACC_PRIVATE;
    /** The class, field, or method is protected */
    public static final int PROTECTED = Opcodes.ACC_PROTECTED;
    /** The class, field, or method is public */
    public static final int PUBLIC = Opcodes.ACC_PUBLIC;
    /** The field or method is static */
    public static final int STATIC = Opcodes.ACC_STATIC;
    /** The class is an enum */
    public static final int ENUM = Opcodes.ACC_ENUM;

    /** The field or method is synthetic (compiler-generated, doesn't appear in source code) */
    public static final int SYNTHETIC = Opcodes.ACC_SYNTHETIC;
    /** Use the newer invokespecial instruction for super classes */
    public static final int SUPER = Opcodes.ACC_SUPER;

    /** An unused default constructor */
    private Access() {}
}
