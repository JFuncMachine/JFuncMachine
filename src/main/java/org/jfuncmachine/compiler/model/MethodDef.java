package org.jfuncmachine.compiler.model;

import org.jfuncmachine.compiler.classgen.Label;
import org.jfuncmachine.compiler.model.expr.Expression;
import org.jfuncmachine.compiler.model.types.Field;
import org.jfuncmachine.compiler.model.types.Type;

public class MethodDef extends SourceElement {
    public final String name;
    public final int access;
    public final Field[] parameters;
    public final Expression body;
    public final Type returnType;
    public final Label startLabel;
    public final boolean isTailCallable;

    public MethodDef(String name, int access, Field[] parameters, Type returnType, Expression body) {
        super(null, 0);
        this.name = name;
        this.access = access;
        this.parameters = parameters;
        this.body = body;
        this.returnType = returnType;
        this.startLabel = new Label();
        this.isTailCallable = false;
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
        this.isTailCallable = false;
    }

    public MethodDef(String name, int access, Field[] parameters, Type returnType, boolean isTailCallable,
                     Expression body) {
        super(null, 0);
        this.name = name;
        this.access = access;
        this.parameters = parameters;
        this.body = body;
        this.returnType = returnType;
        this.startLabel = new Label();
        this.isTailCallable = isTailCallable;
    }

    public MethodDef(String name, int access, Field[] parameters, Type returnType, boolean isTailCallable,
                    Expression body, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.name = name;
        this.access = access;
        this.parameters = parameters;
        this.body = body;
        this.returnType = returnType;
        this.startLabel = new Label();
        this.isTailCallable = isTailCallable;
    }

    public MethodDef getTailCallVersion() {
        return new MethodDef(name+"$$TC$$", access, parameters, returnType, true, body, filename, lineNumber);
    }

    public Type getReturnType() {
        return returnType;
    }

    public void reset() {
        startLabel.reset();
        if (body != null) {
            body.reset();
        }
    }
}
