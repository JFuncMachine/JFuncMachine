package com.wutka.jfuncmachine.compiler.model.types;

public record Array(Type containedType, int size) implements Type {}