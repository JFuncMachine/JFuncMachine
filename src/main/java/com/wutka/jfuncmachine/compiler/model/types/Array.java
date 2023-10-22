package com.wutka.jfuncmachine.compiler.model.types;

record Array(Type containedType, int size) implements Type {}