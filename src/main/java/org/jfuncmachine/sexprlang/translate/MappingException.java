package org.jfuncmachine.sexprlang.translate;

public class MappingException extends Exception {
    public MappingException() {
    }

    public MappingException(String message) {
        super(message);
    }

    public MappingException(String message, Throwable cause) {
        super(message, cause);
    }

    public MappingException(Throwable cause) {
        super(cause);
    }

    public MappingException(Throwable cause, String filename, int lineNumber) {
        super(String.format("%s %d: Exception %s", filename, lineNumber, cause.getMessage()), cause);
    }

    public MappingException(String message, String filename, int lineNumber) {
        super(String.format("%s %d: %s", filename, lineNumber, message));
    }

    public MappingException(String message, String filename, int lineNumber, Throwable cause) {
        super(String.format("%s %d: %s", filename, lineNumber, message), cause);
    }
}