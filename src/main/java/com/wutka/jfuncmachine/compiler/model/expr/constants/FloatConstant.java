package com.wutka.jfuncmachine.compiler.model.expr.constants;

import com.wutka.jfuncmachine.compiler.model.expr.Expression;
import com.wutka.jfuncmachine.compiler.model.types.SimpleTypes;
import com.wutka.jfuncmachine.compiler.model.types.Type;

public class FloatConstant extends Expression {
    public final float value;

    public FloatConstant(float value) {
        this.value = value;
    }

    public Type getType() {
        return SimpleTypes.FLOAT;
    }
}
