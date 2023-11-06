package com.wutka.jfuncmachine.compiler.model;

import com.wutka.jfuncmachine.compiler.exceptions.JFuncMachineException;

public abstract class SourceElement {
    public final String filename;
    public final int lineNumber;

    public SourceElement(String filename, int lineNumber) {
        this.filename = filename;
        this.lineNumber = lineNumber;
    }

    public JFuncMachineException generateException(String message) {
        return new JFuncMachineException(filename, lineNumber, message);
    }
}
