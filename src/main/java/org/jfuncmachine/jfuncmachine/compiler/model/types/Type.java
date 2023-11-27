package org.jfuncmachine.jfuncmachine.compiler.model.types;

public sealed interface Type
    permits BooleanType, ByteType, CharType, ShortType, IntType, LongType, FloatType, DoubleType, ArrayType, ObjectType, UnitType, StringType, FunctionType {

    default String getBoxTypeName() { return null; }

    default Type getBoxType() {
        String boxTypeName = getBoxTypeName();
        if (boxTypeName != null) {
            return new ObjectType(boxTypeName);
        }
        return null;
    }

    default Type getUnboxedType() { return null; }
    default boolean isBoxType() { return false; }


    default boolean isUnboxableFrom(String className) { return false; }
    default int getStackSize() { return 1; }

    default boolean hasIntRepresentation() { return false; }

}
