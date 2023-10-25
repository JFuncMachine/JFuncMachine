package com.wutka.jfuncmachine.compiler.model.expr;

import com.wutka.jfuncmachine.compiler.model.types.Type;

public class GetValue extends Expression {
    public String name;
    public Type type;

    public GetValue(String name, Type type, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.name = name;
        this.type = type;
    }

    public Type getType() {
        return type;
    }
}
