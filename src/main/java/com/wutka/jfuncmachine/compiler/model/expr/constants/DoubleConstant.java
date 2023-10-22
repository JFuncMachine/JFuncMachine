package com.wutka.jfuncmachine.compiler.model.expr.constants;

import com.wutka.jfuncmachine.compiler.model.expr.Expression;
import com.wutka.jfuncmachine.compiler.model.types.SimpleTypes;
import com.wutka.jfuncmachine.compiler.model.types.Type;

public class DoubleConstant extends Expression {
    public final double value;

    public DoubleConstant(double value, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.value = value;
    }

    public Type getType() {
        return SimpleTypes.DOUBLE;
    }
}
