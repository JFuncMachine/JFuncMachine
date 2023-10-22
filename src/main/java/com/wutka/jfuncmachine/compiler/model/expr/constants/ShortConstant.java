package com.wutka.jfuncmachine.compiler.model.expr.constants;

import com.wutka.jfuncmachine.compiler.model.expr.Expression;
import com.wutka.jfuncmachine.compiler.model.types.SimpleTypes;
import com.wutka.jfuncmachine.compiler.model.types.Type;

public class ShortConstant extends Expression {
    public final short value;

    public ShortConstant(short value) {
        this.value = value;
    }

    public Type getType() {
        return SimpleTypes.SHORT;
    }
}
