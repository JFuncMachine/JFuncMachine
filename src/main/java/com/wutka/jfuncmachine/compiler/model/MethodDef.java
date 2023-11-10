package com.wutka.jfuncmachine.compiler.model;

import com.wutka.jfuncmachine.compiler.classgen.Label;
import com.wutka.jfuncmachine.compiler.model.expr.Expression;
import com.wutka.jfuncmachine.compiler.model.types.Field;
import com.wutka.jfuncmachine.compiler.model.types.Type;

public class MethodDef extends SourceElement {
    public final String name;
    public final int access;
    public final Field[] parameters;
    public final Expression body;
    public final Type returnType;
    public final boolean tailCallable;
    public final Label startLabel;

    public MethodDef(String name, int access, Field[] parameters, Type returnType, Expression body) {
        super(null, 0);
        this.name = name;
        this.access = access;
        this.parameters = parameters;
        this.tailCallable = true;
        this.body = body;
        this.returnType = returnType;
        /*
        if (body != null && !body.getType().equals(returnType) && !returnType.equals(new ObjectType())) {
            throw new JFuncMachineException(
                    String.format("Function expects return type of %s but the return type is %s",
                            returnType, body.getType()));
        }
         */
        this.startLabel = new Label();
    }

    public MethodDef(String name, int access, Field[] parameters, boolean tailCallable, Type returnType, Expression body) {
        super(null, 0);
        this.name = name;
        this.access = access;
        this.parameters = parameters;
        this.tailCallable = tailCallable;
        this.body = body;
        this.returnType = returnType;
        /*
        if (body != null && !body.getType().equals(returnType) && !returnType.equals(new ObjectType())) {
            throw new JFuncMachineException(
                    String.format("Function expects return type of %s but the return type is %s",
                            returnType, body.getType()));
        }
         */
        this.startLabel = new Label();
    }

    public MethodDef(String name, int access, Field[] parameters, Type returnType, Expression body,
                     String filename, int lineNumber) {
        super(filename, lineNumber);
        this.name = name;
        this.access = access;
        this.parameters = parameters;
        this.tailCallable = true;
        this.body = body;
        this.returnType = returnType;
        /*
        if (body != null && !body.getType().equals(returnType) && !returnType.equals(new ObjectType())) {
            throw new JFuncMachineException(
                    String.format("Function expects return type of %s but the return type is %s",
                            returnType, body.getType()));
        }
         */
        this.startLabel = new Label();
    }

    public MethodDef(String name, int access, Field[] parameters, boolean tailCallable, Type returnType, Expression body,
                     String filename, int lineNumber) {
        super(filename, lineNumber);
        this.name = name;
        this.access = access;
        this.parameters = parameters;
        this.tailCallable = tailCallable;
        this.body = body;
        this.returnType = returnType;
        /*
        if (body != null && !body.getType().equals(returnType) && !returnType.equals(new ObjectType())) {
            throw new JFuncMachineException(
                    String.format("Function expects return type of %s but the return type is %s",
                            returnType, body.getType()));
        }
         */
        this.startLabel = new Label();
    }

    public Type getReturnType() {
        return returnType;
    }
}
