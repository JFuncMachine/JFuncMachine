package com.wutka.jfuncmachine.compiler.model;

import com.wutka.jfuncmachine.compiler.model.expr.Expression;
import com.wutka.jfuncmachine.compiler.model.instructions.Instruction;
import com.wutka.jfuncmachine.compiler.model.types.Field;
import com.wutka.jfuncmachine.compiler.model.types.Type;

public class InlineFunction extends SourceElement {
    public final Type[] parameterTypes;
    public final Type returnType;
    public final Instruction[] instructions;


    public InlineFunction(Type[] parameterTypes, Type returnType, Instruction[] instructions,
                          String filename, int lineNumber) {
        super(filename, lineNumber);
        this.parameterTypes = parameterTypes;
        this.returnType = returnType;
        this.instructions = instructions;
    }
    public Type getReturnType() {
        return returnType;
    }
}
