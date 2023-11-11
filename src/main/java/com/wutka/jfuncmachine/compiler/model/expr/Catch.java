package com.wutka.jfuncmachine.compiler.model.expr;

import com.wutka.jfuncmachine.compiler.model.SourceElement;

public class Catch extends SourceElement {
    public final String catchClass;
    public final String catchVariable;
    public final Expression body;

    public Catch(String catchClass, String catchVariable, Expression body) {
        super(null, 0);
        this.catchClass = catchClass;
        this.catchVariable = catchVariable;
        this.body = body;
    }

    public Catch(String catchClass, String catchVariable, Expression body, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.catchClass = catchClass;
        this.catchVariable = catchVariable;
        this.body = body;
    }
}
