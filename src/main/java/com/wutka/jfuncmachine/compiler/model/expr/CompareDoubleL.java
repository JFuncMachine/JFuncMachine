package com.wutka.jfuncmachine.compiler.model.expr;

import org.objectweb.asm.Opcodes;

public class CompareDoubleL extends Compare {
    public CompareDoubleL(Expression expr1, Expression expr2) {
        super(expr1, expr2);
    }

    public CompareDoubleL(Expression expr1, Expression expr2, String filename, int lineNumber) {
        super(expr1, expr2, filename, lineNumber);
    }

    public int getOpcode() { return Opcodes.DCMPL; }
}
