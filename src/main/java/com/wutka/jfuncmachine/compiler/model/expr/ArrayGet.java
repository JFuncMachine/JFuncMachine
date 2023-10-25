package com.wutka.jfuncmachine.compiler.model.expr;

import com.wutka.jfuncmachine.compiler.model.types.Array;
import com.wutka.jfuncmachine.compiler.model.types.Type;

public class ArrayGet extends Expression {
    public final Expression array;
    public final Expression index;

    public ArrayGet(Expression array, Expression index, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.array = array;
        this.index = index;
        if (!(array.getType() instanceof Array)) {
            throw generateException("Target of array reference must be an array");
        }
    }

    public Type getType() {
        return array.getType();
    }
}
