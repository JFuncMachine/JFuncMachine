package com.wutka.jfuncmachine.compiler.model.expr.javaintop;

import com.wutka.jfuncmachine.compiler.model.expr.Expression;
import com.wutka.jfuncmachine.compiler.model.types.SimpleTypes;
import com.wutka.jfuncmachine.compiler.model.types.Type;

public class SetJavaStaticField extends Expression {
    public final String className;
    public final String fieldName;
    public final Expression expr;

    public SetJavaStaticField(String className, String fieldName, Expression expr, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.className = className;
        this.fieldName = fieldName;
        this.expr = expr;
    }

    public Type getType() {
        return SimpleTypes.UNIT;
    }
}
