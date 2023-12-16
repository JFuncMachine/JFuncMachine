package org.jfuncmachine.sexprlang.translate;

/** An exception coming from an S-expression mapper */
public class MappingException extends Exception {
    /** Create a new mapping exception */
    public MappingException() {
    }

    /** Create a new mapping exception
     *
     * @param message The exception message
     */
    public MappingException(String message) {
        super(message);
    }

    /** Create a new mapping exception
     *
     * @param message The exception message
     * @param cause The exception cause
     */
    public MappingException(String message, Throwable cause) {
        super(message, cause);
    }

    /** Create a new mapping exception
     *
     * @param cause The exception cause
     */
    public MappingException(Throwable cause) {
        super(cause);
    }

    /** Create a new mapping exception
     *
     * @param cause The exception cause
     * @param filename The source filename where this exception originates
     * @param lineNumber The line number in the source file where this exception occurs
     */
    public MappingException(Throwable cause, String filename, int lineNumber) {
        super(String.format("%s %d: Exception %s", filename, lineNumber, cause.getMessage()), cause);
    }

    /** Create a new mapping exception
     *
     * @param message The exception message
     * @param filename The source filename where this exception originates
     * @param lineNumber The line number in the source file where this exception occurs
     */
    public MappingException(String message, String filename, int lineNumber) {
        super(String.format("%s %d: %s", filename, lineNumber, message));
    }

    /**
     * Create a new mapping exception
     *
     * @param message    The exception message
     * @param cause      The exception cause
     * @param filename   The source filename where this exception originates
     * @param lineNumber The line number in the source file where this exception occurs
     */
    public MappingException(String message, Throwable cause, String filename, int lineNumber) {
        super(String.format("%s %d: %s", filename, lineNumber, message), cause);
    }
}