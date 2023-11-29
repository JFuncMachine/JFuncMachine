package org.jfuncmachine.jfuncmachine.examples.minilang.expr;

import org.jfuncmachine.jfuncmachine.compiler.model.expr.bool.BooleanExpr;
import org.jfuncmachine.jfuncmachine.examples.minilang.Environment;
import org.jfuncmachine.jfuncmachine.examples.minilang.types.BoolType;
import org.jfuncmachine.jfuncmachine.examples.minilang.types.Type;
import org.jfuncmachine.jfuncmachine.util.unification.TypeHolder;
import org.jfuncmachine.jfuncmachine.util.unification.UnificationException;

public abstract class BoolExpr extends Expr {
    public BoolExpr(String filename, int lineNumber) {
        super(filename, lineNumber);
        this.type.setType(new BoolType(filename, lineNumber));
    }

    public abstract BooleanExpr generateBooleanExpr();
}
