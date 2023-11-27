package org.jfuncmachine.jfuncmachine.compiler.classgen;

/**
 * Defines a dynamic constant, that allows constant values to be determined at runtime by
 * a bootstrap method.
 *
 */
public class ConstantDynamic {
    /** The name of the constant */
    public final String name;

    /** A descriptor of the constant's type */
    public final String descriptor;

    /** A handle describing the bootstream method to be invoked */
    public final Handle bootstrapMethod;

    /** The arguments to be passed to the bootstrap method. */
    public final Object[] bootstrapMethodArguments;

    public ConstantDynamic(String name, String descriptor, Handle bootstrapMethod,
                           Object... bootstrapMethodArguments) {
        this.name = name;
        this.descriptor = descriptor;
        this.bootstrapMethod = bootstrapMethod;
        this.bootstrapMethodArguments = bootstrapMethodArguments;
    }

    protected org.objectweb.asm.ConstantDynamic getAsmConstantDynamic() {
        return new org.objectweb.asm.ConstantDynamic(
                name, descriptor, bootstrapMethod.getAsmHandle(), bootstrapMethodArguments);
    }
}
