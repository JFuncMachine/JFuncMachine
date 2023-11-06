package com.wutka.jfuncmachine.compiler.model.types;

import com.wutka.jfuncmachine.compiler.classgen.Naming;

import java.util.Objects;

public final class ObjectType implements Type {
    public final String className;

    public ObjectType() {
        this.className = "java.lang.Object";
    }

    public Type getUnboxedType() {
        return switch (className) {
            case BooleanType.BOX_TYPE -> SimpleTypes.BOOLEAN;
            case ByteType.BOX_TYPE -> SimpleTypes.BYTE;
            case CharType.BOX_TYPE -> SimpleTypes.CHAR;
            case DoubleType.BOX_TYPE -> SimpleTypes.DOUBLE;
            case FloatType.BOX_TYPE -> SimpleTypes.FLOAT;
            case IntType.BOX_TYPE -> SimpleTypes.INT;
            case LongType.BOX_TYPE -> SimpleTypes.LONG;
            case ShortType.BOX_TYPE -> SimpleTypes.SHORT;
            default -> this;
        };
    }

    public ObjectType(String className) {

        this.className = className;
    }

    public String getTypeDescriptor() {
        return "L" + Naming.className(className) + ";";
    }

    public boolean equals(Object other) {
        if (other instanceof ObjectType jOther) {
            return className.equals(jOther.className);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(className);
    }


    public String toString() { return "ObjectType("+className+")"; }
}
