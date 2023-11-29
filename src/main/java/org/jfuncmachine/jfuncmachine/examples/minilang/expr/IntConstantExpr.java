package org.jfuncmachine.jfuncmachine.examples.minilang.expr;

import org.jfuncmachine.jfuncmachine.compiler.model.expr.Expression;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.constants.IntConstant;

public class IntConstantExpr extends IntExpr {
    public final int value;

    public IntConstantExpr(int value, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.value = value;
    }

    public Expression generate() {
        return new IntConstant(value);
    }
}
