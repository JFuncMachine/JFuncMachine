package org.jfuncmachine.jfuncmachine.examples.minilang.expr;

import org.jfuncmachine.jfuncmachine.examples.minilang.Environment;
import org.jfuncmachine.jfuncmachine.examples.minilang.types.IntType;
import org.jfuncmachine.jfuncmachine.examples.minilang.types.Type;
import org.jfuncmachine.jfuncmachine.util.unification.TypeHolder;
import org.jfuncmachine.jfuncmachine.util.unification.UnificationException;

public abstract class IntExpr extends Expr {
    public IntExpr(String filename, int lineNumber) {
        super(filename, lineNumber);
        this.type.setType(new IntType(filename, lineNumber));
    }
}
