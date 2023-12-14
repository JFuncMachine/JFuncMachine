package org.jfuncmachine.compiler.model;

import org.objectweb.asm.Opcodes;

/**
 * Values describing the various access options for Java classes, interfaces, and methods.
 *
 * The values in this class can be combined by addition, so "public static"
 * is represented as Access.PUBLIC + Access.STATIC.
 */
public class Access {
    public static final int ABSTRACT = Opcodes.ACC_ABSTRACT;
    public static final int FINAL = Opcodes.ACC_FINAL;
    public static final int INTERFACE = Opcodes.ACC_INTERFACE;
    public static final int PRIVATE = Opcodes.ACC_PRIVATE;
    public static final int PROTECTED = Opcodes.ACC_PROTECTED;
    public static final int PUBLIC = Opcodes.ACC_PUBLIC;
    public static final int STATIC = Opcodes.ACC_STATIC;
    public static final int SYNTHETIC = Opcodes.ACC_SYNTHETIC;
    public static final int SUPER = Opcodes.ACC_SUPER;
}
