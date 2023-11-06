package com.wutka.jfuncmachine.compiler.model.expr;

import com.wutka.jfuncmachine.compiler.model.types.Type;

public class TryCatchFinally extends Expression {
    public final Expression tryBody;
    public final String catchClass;
    public final String catchVariable;
    public final Expression catchBody;
    public final Expression finallyBody;

    public TryCatchFinally(Expression tryBody, String catchClass, String catchVariable,
                           Expression catchBody, Expression finallyBody) {
        super(null, 0);
        this.tryBody = tryBody;
        this.catchClass = catchClass;
        this.catchVariable = catchVariable;
        this.catchBody = catchBody;
        this.finallyBody = finallyBody;
    }

    public TryCatchFinally(Expression tryBody, String catchClass, String catchVariable,
                           Expression catchBody, Expression finallyBody,
                           String filename, int lineNumber) {
        super(filename, lineNumber);
        this.tryBody = tryBody;
        this.catchClass = catchClass;
        this.catchVariable = catchVariable;
        this.catchBody = catchBody;
        this.finallyBody = finallyBody;
    }

    public Type getType() {
        return tryBody.getType();
    }

}
