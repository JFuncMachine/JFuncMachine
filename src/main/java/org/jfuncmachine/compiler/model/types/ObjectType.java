package org.jfuncmachine.compiler.model.types;

import java.util.Objects;

/** An object type */
public final class ObjectType implements Type {
    /** The class name of the object */
    public final String className;

    /** True if this class represents an enum */
    public final boolean representsEnum;

    /** Create an object type for java.lang.Object */
    public ObjectType() {
        this.className = "java.lang.Object";
        this.representsEnum = false;
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

    /** Tests whether this object type can be unboxed to a specific type
     *
     * @param desiredType The type that the object should be unboxed to
     * @return True if this object type can be unboxed to the desired type
     */
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

    /** Tests whether this object type can be a box for the specified type
     *
     * @param desiredType The type that is supposed to be boxed
     * @return True if this object type can serve as a box from the desired type
     */
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

    /** Create an object type for the given class name
     *
     * @param className The class name of this object type
     */
    public ObjectType(String className) {

        this.className = className;
        this.representsEnum = false;
    }

    /** Create an object type from the given class
     *
     * @param clazz The class to extract the class name from
     */
    public ObjectType(Class clazz) {
        this.className = clazz.getName();
        this.representsEnum = clazz.isEnum();
    }

    /** Create an object type for the given class name
     *
     * @param className The class name of this object type
     * @param representsEnum True if this class is an enum
     */
    public ObjectType(String className, boolean representsEnum) {

        this.className = className;
        this.representsEnum = representsEnum;
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
