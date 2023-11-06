package com.wutka.jfuncmachine.compiler.model.types;

public record ArrayType(Type containedType) implements Type {}