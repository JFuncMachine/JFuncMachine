package com.wutka.jfuncmachine.compiler.model.types;

public sealed interface Type
    permits BooleanType, ByteType, CharType, ShortType, IntType, LongType, FloatType, DoubleType, ArrayType, ObjectType, UnitType, StringType, FunctionType {

    public default String getBoxType() { return null; }
    public String getTypeDescriptor();
}
