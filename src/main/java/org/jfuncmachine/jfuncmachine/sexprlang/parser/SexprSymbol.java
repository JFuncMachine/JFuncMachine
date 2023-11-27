package org.jfuncmachine.jfuncmachine.sexprlang.parser;

public final class SexprSymbol extends SexprItem {
    public final String value;

    public SexprSymbol(String value, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.value = value;
    }

    public String toString() { return value; }
}
