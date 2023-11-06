package com.wutka.jfuncmachine.compiler.model.expr;

import com.wutka.jfuncmachine.compiler.exceptions.JFuncMachineException;

public record SwitchCase(int value, Expression expr, String filename, int lineNumber) {

    public JFuncMachineException generateException(String message) {
        return new JFuncMachineException(filename, lineNumber, message);
    }
}
