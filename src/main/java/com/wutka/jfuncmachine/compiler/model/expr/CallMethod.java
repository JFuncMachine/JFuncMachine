package com.wutka.jfuncmachine.compiler.model.expr;

import com.wutka.jfuncmachine.compiler.model.Method;
import com.wutka.jfuncmachine.compiler.model.types.Type;

public class CallMethod extends Expression {
    public final Class clazz;
    public final Method func;
    public final Expression[] parameters;

    public CallMethod(Class clazz, Method func, Expression[] parameters) {
        super(null, 0);
        this.clazz = clazz;
        this.func = func;
        this.parameters = parameters;
    }

    public CallMethod(Class clazz, Method func, Expression[] parameters, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.clazz = clazz;
        this.func = func;
        this.parameters = parameters;
    }

    public Type getType() {
        return func.getReturnType();
    }
}
