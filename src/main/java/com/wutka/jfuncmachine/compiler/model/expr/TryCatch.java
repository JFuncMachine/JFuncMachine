package com.wutka.jfuncmachine.compiler.model.expr;

import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.model.types.Type;

public class TryCatch extends Expression {
    public final Expression tryBody;
    public final String catchClass;
    public final String catchVariable;
    public final Expression catchBody;

    public TryCatch(Expression tryBody, String catchClass, String catchVariable,
                    Expression catchBody) {
        super(null, 0);
        this.tryBody = tryBody;
        this.catchClass = catchClass;
        this.catchVariable = catchVariable;
        this.catchBody = catchBody;
    }

    public TryCatch(Expression tryBody, String catchClass, String catchVariable,
                    Expression catchBody, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.tryBody = tryBody;
        this.catchClass = catchClass;
        this.catchVariable = catchVariable;
        this.catchBody = catchBody;
    }

    public Type getType() {
        return tryBody.getType();
    }

    public void findCaptured(Environment env) {}

}
