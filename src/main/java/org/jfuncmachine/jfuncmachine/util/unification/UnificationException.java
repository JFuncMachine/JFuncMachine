package org.jfuncmachine.jfuncmachine.util.unification;

public class UnificationException extends Exception {
    public UnificationException() {
    }

    public UnificationException(String message) {
        super(message);
    }

    public UnificationException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnificationException(Throwable cause) {
        super(cause);
    }

    public UnificationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
