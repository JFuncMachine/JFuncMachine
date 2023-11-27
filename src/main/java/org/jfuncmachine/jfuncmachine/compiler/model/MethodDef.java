package org.jfuncmachine.jfuncmachine.compiler.model;

import org.jfuncmachine.jfuncmachine.compiler.classgen.Label;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.Expression;
import org.jfuncmachine.jfuncmachine.compiler.model.types.Field;
import org.jfuncmachine.jfuncmachine.compiler.model.types.Type;

public class MethodDef extends SourceElement {
    public final String name;
    public final int access;
    public final Field[] parameters;
    public final Expression body;
    public final Type returnType;
    public final Label startLabel;

    public MethodDef(String name, int access, Field[] parameters, Type returnType, Expression body) {
        super(null, 0);
        this.name = name;
        this.access = access;
        this.parameters = parameters;
        this.body = body;
        this.returnType = returnType;
        this.startLabel = new Label();
    }

    public MethodDef(String name, int access, Field[] parameters, Type returnType, Expression body,
                     String filename, int lineNumber) {
        super(filename, lineNumber);
        this.name = name;
        this.access = access;
        this.parameters = parameters;
        this.body = body;
        this.returnType = returnType;
        this.startLabel = new Label();
    }

    public Type getReturnType() {
        return returnType;
    }
}
