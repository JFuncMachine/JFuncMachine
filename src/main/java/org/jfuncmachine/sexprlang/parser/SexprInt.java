package org.jfuncmachine.sexprlang.parser;

public final class SexprInt extends SexprItem {
    public final int value;

    public SexprInt(int value, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.value = value;
    }

    public String toString() { return Integer.toString(value); }
}
