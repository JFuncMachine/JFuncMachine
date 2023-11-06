package com.wutka.jfuncmachine.compiler.model.expr;

import com.wutka.jfuncmachine.compiler.model.types.SimpleTypes;
import com.wutka.jfuncmachine.compiler.model.types.Type;

public class Block extends Expression {
    public final Expression[] expressions;

    public Block (Expression[] expressions) {
        super(null, 0);
        this.expressions = expressions;
    }

    public Block (Expression[] expressions, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.expressions = expressions;
    }

    public Type getType() {
        if (expressions.length > 0) {
            return expressions[expressions.length - 1].getType();
        } else {
            return SimpleTypes.UNIT;
        }
    }
}
