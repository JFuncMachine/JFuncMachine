package com.wutka.jfuncmachine.compiler.model;

public abstract class SourceElement {
    public final String filename;
    public final int lineNumber;

    public SourceElement(String filename, int lineNumber) {
        this.filename = filename;
        this.lineNumber = lineNumber;
    }

    public RuntimeException generateException(String message) {
        return new RuntimeException(
                String.format("%s %d: %s", filename, lineNumber, message));
    }
}
