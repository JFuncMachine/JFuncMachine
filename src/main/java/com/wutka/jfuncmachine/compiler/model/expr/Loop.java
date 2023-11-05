package com.wutka.jfuncmachine.compiler.model.expr;

import com.wutka.jfuncmachine.compiler.model.Field;
import com.wutka.jfuncmachine.compiler.model.types.Type;

public class Loop extends Expression {
    public final String name;
    public final Field[] loopVariables;
    public final Expression expression;

    public Loop(String name, Field[] loopVariables, Expression expression,
                String filename, int lineNumber) {
        super(filename, lineNumber);
        this.name = name;
        this.loopVariables = loopVariables;
        this.expression = expression;
    }

    public Type getType() {
        return expression.getType();
    }
}
