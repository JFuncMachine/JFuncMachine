package com.wutka.jfuncmachine.compiler.model.expr;

import com.wutka.jfuncmachine.compiler.model.types.ArrayType;
import com.wutka.jfuncmachine.compiler.model.types.SimpleTypes;
import com.wutka.jfuncmachine.compiler.model.types.Type;

public class ArraySet extends Expression {
    public final Expression array;
    public final Expression index;
    public final Expression value;

    public ArraySet(Expression array, Expression index, Expression value) {
        super(null, 0);
        this.array = array;
        this.index = index;
        this.value = value;
        if (!(array.getType() instanceof ArrayType)) {
            throw generateException("Target of array reference must be an array");
        }
    }

    public ArraySet(Expression array, Expression index, Expression value,
                    String filename, int lineNumber) {
        super(filename, lineNumber);
        this.array = array;
        this.index = index;
        this.value = value;
        if (!(array.getType() instanceof ArrayType)) {
            throw generateException("Target of array reference must be an array");
        }
    }

    public Type getType() {
        return SimpleTypes.UNIT;
    }
}
