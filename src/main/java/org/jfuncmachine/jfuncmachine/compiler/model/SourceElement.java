package org.jfuncmachine.jfuncmachine.compiler.model;

import org.jfuncmachine.jfuncmachine.compiler.exceptions.JFuncMachineException;

public abstract class SourceElement {
    public final String filename;
    public final int lineNumber;

    public SourceElement(String filename, int lineNumber) {
        this.filename = filename;
        this.lineNumber = lineNumber;
    }

    public JFuncMachineException generateException(String message) {
        if (filename == null) {
            return new JFuncMachineException(message);
        } else {
            return new JFuncMachineException(filename, lineNumber, message);
        }
    }
}
