package com.wutka.jfuncmachine.compiler.model.expr.javaintop;

import com.wutka.jfuncmachine.compiler.model.expr.Expression;
import com.wutka.jfuncmachine.compiler.model.types.SimpleTypes;
import com.wutka.jfuncmachine.compiler.model.types.Type;

public class CallJavaInterface extends Expression {
    public final String interfaceName;
    public final String methodName;
    public final Expression[] arguments;

    public CallJavaInterface(String interfaceName, String methodName, Expression[] arguments) {
        this.interfaceName = interfaceName;
        this.methodName = methodName;
        this.arguments = arguments;
    }

    public Type getType() {
        return SimpleTypes.JAVA_OBJECT;
    }
}
