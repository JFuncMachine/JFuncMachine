package org.jfuncmachine.jfuncmachine.examples.minilang.expr;

import org.jfuncmachine.jfuncmachine.examples.minilang.types.StringType;
import org.jfuncmachine.jfuncmachine.examples.minilang.types.Type;

public class StringExpr extends Expr {
    public StringExpr(String filename, int lineNumber) {
        super(filename, lineNumber);
        this.type.setType(new StringType(filename, lineNumber));
    }
}
