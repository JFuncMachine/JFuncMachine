package com.wutka.jfuncmachine.compiler.model.expr.javaintop;

import com.wutka.jfuncmachine.compiler.model.expr.Expression;
import com.wutka.jfuncmachine.compiler.model.types.SimpleTypes;
import com.wutka.jfuncmachine.compiler.model.types.Type;

public class CallJavaStaticMethod extends Expression {
    public final String className;
    public final String methodName;
    public final Expression[] arguments;
    public final boolean isVoid;

    public CallJavaStaticMethod(String className, String methodName, Expression[] arguments, boolean isVoid,
                                String filename, int lineNumber) {
        super(filename, lineNumber);
        this.className = className;
        this.methodName = methodName;
        this.arguments = arguments;
        this.isVoid = isVoid;
    }

    public Type getType() {
        if (isVoid) {
            return SimpleTypes.UNIT;
        } else {
            return SimpleTypes.JAVA_OBJECT;
        }
    }
}
