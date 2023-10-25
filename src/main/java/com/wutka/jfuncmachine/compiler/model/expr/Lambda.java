package com.wutka.jfuncmachine.compiler.model.expr;

import com.wutka.jfuncmachine.compiler.model.types.Function;
import com.wutka.jfuncmachine.compiler.model.types.Type;

public class Lambda extends Expression {
    public final Type[] parameterTypes;
    public final Expression body;
    public final String[] capturedValues;

    public Lambda(Type[] parameterTypes, Expression body, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.parameterTypes = parameterTypes;
        this.body = body;
        this.capturedValues = computeCaptured();
    }

    public Type getType() {
        return new Function(parameterTypes, body.getType());
    }

    protected String[] computeCaptured() {
        //TODO - Implement computeCaptured
        return new String[0];
    }
}
