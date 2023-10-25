package com.wutka.jfuncmachine.compiler.model.types;

public record Function(Type[] parameterTypes, Type returnType) implements Type {
}
