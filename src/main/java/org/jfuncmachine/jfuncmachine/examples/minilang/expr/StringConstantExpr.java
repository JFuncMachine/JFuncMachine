package org.jfuncmachine.jfuncmachine.examples.minilang.expr;

public class StringConstantExpr extends StringExpr {
    public final String value;
    public StringConstantExpr(String value, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.value = value;
    }
}
