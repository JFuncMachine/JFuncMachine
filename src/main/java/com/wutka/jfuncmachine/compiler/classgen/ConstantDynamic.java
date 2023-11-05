package com.wutka.jfuncmachine.compiler.classgen;

public class ConstantDynamic {
    public final String name;
    public final String descriptor;
    public final Handle bootstrapMethod;
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
