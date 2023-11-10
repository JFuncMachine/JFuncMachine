package com.wutka.jfuncmachine.compiler.model;

import com.wutka.jfuncmachine.compiler.classgen.ClassGenerator;
import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.model.types.Type;

public abstract class InlineFunction {
    public final Type[] parameterTypes;
    public final Type returnType;


    public InlineFunction(Type[] parameterTypes, Type returnType) {
        this.parameterTypes = parameterTypes;
        this.returnType = returnType;
    }
    public Type getReturnType() {
        return returnType;
    }

    public abstract void generate(ClassGenerator generator, Environment env);
}
