package com.wutka.jfuncmachine.compiler.model.expr;

import com.wutka.jfuncmachine.compiler.model.types.Type;

public abstract class Expression {
    public abstract Type getType();
    public final String filename;
    public final int lineNumber;

    public Expression(String filename, int lineNumber) {
        this.filename = filename;
        this.lineNumber = lineNumber;
    }
}
