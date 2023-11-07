package com.wutka.jfuncmachine.compiler.model.types;

import com.wutka.jfuncmachine.compiler.classgen.Naming;

import java.util.Arrays;
import java.util.Objects;

public final class FunctionType implements Type {
    public final String functionName;
    public final Type[] parameterTypes;
    public final Type returnType;

    public FunctionType(String functionName, Type[] parameterTypes, Type returnType) {
        this.functionName = functionName;
        this.parameterTypes = parameterTypes;
        this.returnType = returnType;
    }

    public String getTypeDescriptor() {
        return Naming.methodDescriptor(parameterTypes, returnType);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FunctionType that = (FunctionType) o;
        return Objects.equals(functionName, that.functionName) && Arrays.equals(parameterTypes, that.parameterTypes) && Objects.equals(returnType, that.returnType);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(functionName, returnType);
        result = 31 * result + Arrays.hashCode(parameterTypes);
        return result;
    }
}
