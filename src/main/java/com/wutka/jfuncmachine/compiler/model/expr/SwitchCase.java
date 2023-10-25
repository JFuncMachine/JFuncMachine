package com.wutka.jfuncmachine.compiler.model.expr;

public record SwitchCase(int value, Expression expr, String filename, int lineNumber) {
    public RuntimeException generateException(String message) {
        return new RuntimeException(
                String.format("%s %d: %s", filename, lineNumber, message));
    }
}
