package com.wutka.jfuncmachine.compiler.model.expr;

import com.wutka.jfuncmachine.compiler.model.InlineFunction;
import com.wutka.jfuncmachine.compiler.model.types.Type;

public class InlineCall extends Expression {
    public final InlineFunction func;
    public final Expression[] parameters;

    public InlineCall(InlineFunction func, String filename, int lineNumber, Expression[] parameters) {
        super(filename, lineNumber);
        this.func = func;
        this.parameters = parameters;
    }

    public Type getType() {
        return func.getReturnType();
    }
}
