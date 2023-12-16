package org.jfuncmachine.compiler.model.types;

import java.util.Arrays;
import java.util.Objects;

/** The type of a function */
public final class FunctionType implements Type {
    /** The types of the function parameters */
    public final Type[] parameterTypes;
    /** The function's return type */
    public final Type returnType;

    /** Create a function type
     *
     * @param parameterTypes The types of the function parameters
     * @param returnType The function's return type
     */
    public FunctionType(Type[] parameterTypes, Type returnType) {
        this.parameterTypes = parameterTypes;
        this.returnType = returnType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FunctionType that = (FunctionType) o;
        return Arrays.equals(parameterTypes, that.parameterTypes) && Objects.equals(returnType, that.returnType);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(returnType);
        result = 31 * result + Arrays.hashCode(parameterTypes);
        return result;
    }
}
