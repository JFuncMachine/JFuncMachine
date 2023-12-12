package org.jfuncmachine.sexprlang.parser;

public final class SexprDouble extends SexprItem {
    public final double value;

    public SexprDouble(double value, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.value = value;
    }

    public String toString() { return Double.toString(value); }
}
