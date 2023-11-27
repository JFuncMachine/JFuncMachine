package org.jfuncmachine.jfuncmachine.compiler.model.types;

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

    public boolean isUnboxableTo(Type desiredType) {
        return switch (className) {
            case BooleanType.BOX_TYPE -> desiredType.hasIntRepresentation();
            case ByteType.BOX_TYPE -> desiredType.hasIntRepresentation();
            case CharType.BOX_TYPE -> desiredType.hasIntRepresentation();
            case DoubleType.BOX_TYPE -> desiredType instanceof DoubleType;
            case FloatType.BOX_TYPE -> desiredType instanceof FloatType;
            case IntType.BOX_TYPE -> desiredType.hasIntRepresentation();
            case LongType.BOX_TYPE -> desiredType instanceof LongType;
            case ShortType.BOX_TYPE -> desiredType.hasIntRepresentation();
            default -> false;
        };
    }

    public boolean isBoxableFrom(Type desiredType) {
        if (className.equals("java.lang.Object")) return true;
        return isUnboxableTo(desiredType);
    }

    public boolean isBoxType() {
        return switch (className) {
            case BooleanType.BOX_TYPE, ByteType.BOX_TYPE, CharType.BOX_TYPE,
                    DoubleType.BOX_TYPE, FloatType.BOX_TYPE, IntType.BOX_TYPE,
                    LongType.BOX_TYPE, ShortType.BOX_TYPE -> true;
            default -> false;
        };
    }

    public ObjectType(String className) {

        this.className = className;
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
