package com.wutka.jfuncmachine.compiler.model.expr;

import com.wutka.jfuncmachine.compiler.model.types.Type;

public class Iterate extends Expression {
    public final String loopName;
    public final Expression[] values;
    public final Type type;

    public Iterate(String loopName, Expression[] values, Type type) {
        super(null, 0);
        this.loopName = loopName;
        this.values = values;
        this.type = type;
    }

    public Iterate(String loopName, Expression[] values, Type type,
                   String filename, int lineNumber) {
        super(filename, lineNumber);
        this.loopName = loopName;
        this.values = values;
        this.type = type;
    }

    public Type getType() {
        return type;
    }
}
