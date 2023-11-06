package com.wutka.jfuncmachine.compiler.model.expr;

import com.wutka.jfuncmachine.compiler.model.ClassField;
import com.wutka.jfuncmachine.compiler.model.types.Type;

public class Loop extends Expression {
    public final String name;
    public final ClassField[] loopVariables;
    public final Expression expression;

    public Loop(String name, ClassField[] loopVariables, Expression expression) {
        super(null, 0);
        this.name = name;
        this.loopVariables = loopVariables;
        this.expression = expression;
    }

    public Loop(String name, ClassField[] loopVariables, Expression expression,
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
