package org.jfuncmachine.compiler.exceptions;

/** A JFuncMachine-specific exception, which can contain a filename and line number
 *
 */
public class JFuncMachineException extends RuntimeException {
    /** Create an empty exception */
    public JFuncMachineException() {
        super();
    }

    /** Create an exception with a message
     *
     * @param message The exception message
     */
    public JFuncMachineException(String message) {
        super(message);
    }

    /** Create an exception with a message and a cause
     *
     * @param message The exception message
     * @param cause The exception cause
     */
    public JFuncMachineException(String message, Throwable cause) {
        super(message, cause);
    }

    /** Create an exception with a cause
     *
     * @param cause The exception cause
     */
    public JFuncMachineException(Throwable cause) {
        super(cause);
    }

    /** Create an exception with a message, cause, and other options
     *
     * @param message The exception message
     * @param cause The exception cause
     * @param enableSuppression whether or not suppression is enabled or disabled
     * @param writableStackTrace whether or not the stack trace should be writeble
     */
    protected JFuncMachineException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * Create an exception with a filename, line number, and message
     *
     * @param message    The exception message
     * @param filename   The name of the source file where this exception is generated from
     * @param lineNumber The line number in the source file where this exception is generated
     */
    public JFuncMachineException(String message, String filename, int lineNumber) {
        super(String.format("%s:%d %s", filename, lineNumber, message));
    }

    /**
     * Create an exception with a filename, line number, and message
     *
     * @param message    The exception message
     * @param cause      The exception cause
     * @param filename   The name of the source file where this exception is generated from
     * @param lineNumber The line number in the source file where this exception is generated
     */
    public JFuncMachineException(String message, Throwable cause, String filename, int lineNumber) {
        super(String.format("%s:%d %s", filename, lineNumber, message), cause);
    }
}
