package com.wutka.jfuncmachine.compiler.model.expr;

import com.wutka.jfuncmachine.compiler.model.SourceElement;
import com.wutka.jfuncmachine.compiler.model.types.Type;

public abstract class Expression extends SourceElement {
    public abstract Type getType();

    public Expression(String filename, int lineNumber) {
        super(filename, lineNumber);
    }


}
