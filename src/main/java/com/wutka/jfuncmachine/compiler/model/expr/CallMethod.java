package com.wutka.jfuncmachine.compiler.model.expr;

import com.wutka.jfuncmachine.compiler.model.Method;
import com.wutka.jfuncmachine.compiler.model.types.Type;

public class CallMethod extends Expression {
    public final Method func;
    public final Expression[] parameters;

    public CallMethod(Method func, String filename, int lineNumber, Expression[] parameters) {
        super(filename, lineNumber);
        this.func = func;
        this.parameters = parameters;
    }

    public Type getType() {
        return func.getReturnType();
    }
}
