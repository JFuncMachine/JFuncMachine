package org.jfuncmachine.jfuncmachine.compiler.model;

import org.jfuncmachine.jfuncmachine.compiler.model.expr.Expression;
import org.jfuncmachine.jfuncmachine.compiler.model.types.Field;
import org.jfuncmachine.jfuncmachine.compiler.model.types.Type;

public class ConstructorDef extends MethodDef {
    public ConstructorDef(int access, Field[] parameters, Type returnType, Expression body) {
        super("<init>", access, parameters, returnType, body);
    }

    public ConstructorDef(int access, Field[] parameters, Type returnType, Expression body,
                          String filename, int lineNumber) {
        super("<init>", access, parameters, returnType, body, filename, lineNumber);
    }
}
