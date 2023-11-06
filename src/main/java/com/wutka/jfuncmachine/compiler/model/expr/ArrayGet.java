package com.wutka.jfuncmachine.compiler.model.expr;

import com.wutka.jfuncmachine.compiler.model.types.ArrayType;
import com.wutka.jfuncmachine.compiler.model.types.Type;

public class ArrayGet extends Expression {
    public final Expression array;
    public final Expression index;

    public ArrayGet(Expression array, Expression index) {
        super(null, 0);
        this.array = array;
        this.index = index;
        if (!(array.getType() instanceof ArrayType)) {
            throw generateException("Target of array reference must be an array");
        }
    }

    public ArrayGet(Expression array, Expression index, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.array = array;
        this.index = index;
        if (!(array.getType() instanceof ArrayType)) {
            throw generateException("Target of array reference must be an array");
        }
    }

    public Type getType() {
        return array.getType();
    }
}
