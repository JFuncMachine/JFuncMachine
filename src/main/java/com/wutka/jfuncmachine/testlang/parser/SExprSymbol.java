package com.wutka.jfuncmachine.testlang.parser;

public final class SExprSymbol extends SExprItem {
    public final String value;

    public SExprSymbol(String value, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.value = value;
    }
}
