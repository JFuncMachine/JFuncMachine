package org.jfuncmachine.compiler.model.expr;

import org.jfuncmachine.compiler.model.SourceElement;

public class SwitchCase extends SourceElement {
    public final int value;
    public final Expression expr;

    public SwitchCase(int value, Expression expr) {
        super(null, 0);
        this.value = value;
        this.expr = expr;

    }

    public SwitchCase(int value, Expression expr, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.value = value;
        this.expr = expr;

    }
}
