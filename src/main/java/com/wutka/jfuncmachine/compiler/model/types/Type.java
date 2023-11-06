package com.wutka.jfuncmachine.compiler.model.types;

public sealed interface Type
    permits BooleanType, ByteType, CharType, ShortType, IntType, LongType, FloatType, DoubleType, ArrayType, ObjectType, UnitType, StringType, FunctionType {

    public default String getBoxType() { return null; }
    public String getTypeDescriptor();
    public default Type getUnboxedType() { return null; }

    public default boolean isUnboxableFrom(String className) { return false; }
    public default int getStackSize() { return 1; }

}
