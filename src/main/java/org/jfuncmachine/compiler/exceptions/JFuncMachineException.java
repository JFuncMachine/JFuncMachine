package org.jfuncmachine.compiler.exceptions;

public class JFuncMachineException extends RuntimeException {
    public JFuncMachineException() {
        super();
    }

    public JFuncMachineException(String message) {
        super(message);
    }

    public JFuncMachineException(String message, Throwable cause) {
        super(message, cause);
    }

    public JFuncMachineException(Throwable cause) {
        super(cause);
    }

    protected JFuncMachineException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public JFuncMachineException(String filename, int lineNumber, String message) {
        super(String.format("%s:%d %s", filename, lineNumber, message));
    }
}
