package com.wutka.jfuncmachine.compiler.model.expr.constants;

import com.wutka.jfuncmachine.compiler.model.expr.Expression;
import com.wutka.jfuncmachine.compiler.model.types.SimpleTypes;
import com.wutka.jfuncmachine.compiler.model.types.Type;

public class IntConstant extends Expression {
    public final int value;

    public IntConstant(int value) {
        this.value = value;
    }

    public Type getType() {
        return SimpleTypes.INT;
    }
}
