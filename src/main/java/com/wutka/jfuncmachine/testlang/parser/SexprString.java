package com.wutka.jfuncmachine.testlang.parser;

public final class SexprString extends SexprItem {
    public final String value;

    public SexprString(String value, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.value = value;
    }
}
