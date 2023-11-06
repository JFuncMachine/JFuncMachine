package com.wutka.jfuncmachine.compiler.model.expr;

import com.wutka.jfuncmachine.compiler.model.types.SimpleTypes;
import com.wutka.jfuncmachine.compiler.model.types.Type;

public class SetValue extends Expression {
    public String name;
    public Expression expression;

    public SetValue(String name, Expression expression) {
        super(null, 0);
        this.name = name;
        this.expression = expression;
    }

    public SetValue(String name, Expression expression, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.name = name;
        this.expression = expression;
    }

    public Type getType() {
        return SimpleTypes.UNIT;
    }
}
