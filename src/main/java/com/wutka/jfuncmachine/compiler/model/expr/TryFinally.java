package com.wutka.jfuncmachine.compiler.model.expr;

import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.model.types.Type;

public class TryFinally extends Expression {
    public final Expression tryBody;
    public final Expression finallyBody;

    public TryFinally(Expression tryBody, Expression finallyBody) {
        super(null, 0);
        this.tryBody = tryBody;
        this.finallyBody = finallyBody;
    }

    public TryFinally(Expression tryBody, Expression finallyBody,
                      String filename, int lineNumber) {
        super(filename, lineNumber);
        this.tryBody = tryBody;
        this.finallyBody = finallyBody;
    }

    public Type getType() {
        return tryBody.getType();
    }

    public void findCaptured(Environment env) {}

}
