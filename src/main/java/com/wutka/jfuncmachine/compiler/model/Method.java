package com.wutka.jfuncmachine.compiler.model;

import com.wutka.jfuncmachine.compiler.model.expr.Expression;
import com.wutka.jfuncmachine.compiler.model.types.Field;
import com.wutka.jfuncmachine.compiler.model.types.Type;

public class Method extends SourceElement {
    public final String name;
    public final Field[] parameters;
    public final Expression body;
    public final boolean isStatic;


    public Method(String name, Field[] parameters, boolean isStatic, Expression body,
                  String filename, int lineNumber) {
        super(filename, lineNumber);
        this.name = name;
        this.parameters = parameters;
        this.isStatic = isStatic;
        this.body = body;
    }
    public Type getReturnType() {
        return body.getType();
    }
}
