package com.wutka.jfuncmachine.compiler.model.expr.javaintop;

import com.wutka.jfuncmachine.compiler.model.expr.Expression;
import com.wutka.jfuncmachine.compiler.model.types.SimpleTypes;
import com.wutka.jfuncmachine.compiler.model.types.Type;

public class CallJavaConstructor extends Expression {
    public final String className;
    public final String constructorName;

    public CallJavaConstructor(String className, String constructorName, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.className = className;
        this.constructorName = constructorName;
    }

    public Type getType() {
        return SimpleTypes.JAVA_OBJECT;
    }
}
