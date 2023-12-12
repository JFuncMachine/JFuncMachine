package org.jfuncmachine.sexprlang.parser;

public final class SexprString extends SexprItem {
    public final String value;

    public SexprString(String value, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.value = value;
    }

    public String toString() { return "\"" + value + "\""; }
}
