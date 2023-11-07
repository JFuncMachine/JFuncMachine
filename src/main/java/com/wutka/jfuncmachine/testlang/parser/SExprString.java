package com.wutka.jfuncmachine.testlang.parser;

public final class SExprString extends SExprItem {
    public final String value;

    public SExprString(String value, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.value = value;
    }
}
