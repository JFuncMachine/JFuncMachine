package com.wutka.jfuncmachine.compiler.model.expr.constants;

import com.wutka.jfuncmachine.compiler.model.expr.Expression;
import com.wutka.jfuncmachine.compiler.model.types.SimpleTypes;
import com.wutka.jfuncmachine.compiler.model.types.Type;

public class LongConstant extends Expression {
    public final long value;

    public LongConstant(long value) {
        this.value = value;
    }

    public Type getType() {
        return SimpleTypes.LONG;
    }
}
