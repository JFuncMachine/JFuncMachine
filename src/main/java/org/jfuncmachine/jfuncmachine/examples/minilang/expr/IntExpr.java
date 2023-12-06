package org.jfuncmachine.jfuncmachine.examples.minilang.expr;

import org.jfuncmachine.jfuncmachine.examples.minilang.types.IntType;

public abstract class IntExpr extends Expr {
    public IntExpr(String filename, int lineNumber) {
        super(filename, lineNumber);
        this.type.setType(new IntType(filename, lineNumber));
    }
}
