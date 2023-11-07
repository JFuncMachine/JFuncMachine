package com.wutka.jfuncmachine.testlang.parser;

public final class SExprInt extends SExprItem {
    public final int value;

    public SExprInt(int value, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.value = value;
    }
}
