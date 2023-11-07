package com.wutka.jfuncmachine.testlang.parser;

public final class SexprFloat extends SexprItem {
    public final double value;

    public SexprFloat(double value, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.value = value;
    }
}
