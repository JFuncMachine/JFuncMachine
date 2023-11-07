package com.wutka.jfuncmachine.testlang.parser;

public final class SExprFloat extends SExprItem {
    public final double value;

    public SExprFloat(double value, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.value = value;
    }
}
