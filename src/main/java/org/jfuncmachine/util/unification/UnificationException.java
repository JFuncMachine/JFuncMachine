package org.jfuncmachine.util.unification;

/** An exception thrown by the type unification utility */
public class UnificationException extends Exception {
    /** Create an empty unification exception */
    public UnificationException() {
    }

    /** Create a unification exception with a message
     *
     * @param message The exception message
     */
    public UnificationException(String message) {
        super(message);
    }

    /** Create a unification exception with a message and a cause
     *
     * @param message The exception message
     * @param cause The exception cause
     */
    public UnificationException(String message, Throwable cause) {
        super(message, cause);
    }

    /** Create a unification exception with a cause
     *
     * @param cause The exception cause
     */
    public UnificationException(Throwable cause) {
        super(cause);
    }
}
