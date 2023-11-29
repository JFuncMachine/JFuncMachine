package org.jfuncmachine.jfuncmachine.examples.minilang.expr;

import org.jfuncmachine.jfuncmachine.compiler.model.expr.Expression;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.constants.StringConstant;

public class StringConstantExpr extends StringExpr {
    public final String value;
    public StringConstantExpr(String value, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.value = value;
    }

    public Expression generate() {
        return new StringConstant(value);
    }
}
