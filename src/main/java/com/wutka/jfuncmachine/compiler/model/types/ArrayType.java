package com.wutka.jfuncmachine.compiler.model.types;

public record ArrayType(Type containedType, int size) implements Type {}