package com.wutka.jfuncmachine.compiler.model.instructions;

import com.wutka.jfuncmachine.compiler.model.types.Type;

public abstract class Instruction {
    public abstract Type[] getArgumentTypes();
    public abstract Type getResultType();
}
