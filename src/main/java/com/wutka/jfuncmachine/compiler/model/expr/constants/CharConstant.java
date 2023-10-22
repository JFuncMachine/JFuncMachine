package com.wutka.jfuncmachine.compiler.model.expr.constants;

import com.wutka.jfuncmachine.compiler.model.expr.Expression;
import com.wutka.jfuncmachine.compiler.model.types.SimpleTypes;
import com.wutka.jfuncmachine.compiler.model.types.Type;

public class CharConstant extends Expression {
    public final char value;

    public CharConstant(char value) {
        this.value = value;
    }

    public Type getType() {
        return SimpleTypes.CHAR;
    }
}
