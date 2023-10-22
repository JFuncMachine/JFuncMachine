package com.wutka.jfuncmachine.compiler.model.expr.constants;

import com.wutka.jfuncmachine.compiler.model.expr.Expression;
import com.wutka.jfuncmachine.compiler.model.types.SimpleTypes;
import com.wutka.jfuncmachine.compiler.model.types.Type;

public class StringConstant extends Expression {
    public final String value;

    public StringConstant(String value) {
        this.value = value;
    }

    public Type getType() {
        return SimpleTypes.STRING;
    }
}
