package com.wutka.jfuncmachine.compiler.model.types;

public sealed interface Type
    permits Boolean, Byte, Char, Short, Int, Long, Float, Double, Array, JavaObject, Unit, String, Function {
}
