package com.wutka.jfuncmachine.compiler.model;

import com.wutka.jfuncmachine.compiler.exceptions.JFuncMachineException;
import com.wutka.jfuncmachine.compiler.model.expr.Expression;
import com.wutka.jfuncmachine.compiler.model.types.Field;
import com.wutka.jfuncmachine.compiler.model.types.Type;
import org.objectweb.asm.tree.MethodNode;

public class Method extends SourceElement {
    public final String name;
    public final int access;
    public final Field[] parameters;
    public final Expression body;
    public final Type expectedReturnType;
    public final boolean tailCallable;

    public Method(String name, int access, Field[] parameters, Expression body, Type expectedReturnType) {
        super(null, 0);
        this.name = name;
        this.access = access;
        this.parameters = parameters;
        this.tailCallable = true;
        this.body = body;
        this.expectedReturnType = expectedReturnType;
        if (!body.getType().equals(expectedReturnType)) {
            throw new JFuncMachineException(
                    String.format("Function expects return type of %s but the return type is %s",
                            expectedReturnType, body.getType()));
        }
    }

    public Method(String name, int access, Field[] parameters, boolean tailCallable, Expression body,
                  Type expectedReturnType) {
        super(null, 0);
        this.name = name;
        this.access = access;
        this.parameters = parameters;
        this.tailCallable = tailCallable;
        this.body = body;
        this.expectedReturnType = expectedReturnType;
        if (!body.getType().equals(expectedReturnType)) {
            throw new JFuncMachineException(
                    String.format("Function expects return type of %s but the return type is %s",
                            expectedReturnType, body.getType()));
        }
    }

    public Method(String name, int access, Field[] parameters, Expression body, Type expectedReturnType,
                  String filename, int lineNumber) {
        super(filename, lineNumber);
        this.name = name;
        this.access = access;
        this.parameters = parameters;
        this.tailCallable = true;
        this.body = body;
        this.expectedReturnType = expectedReturnType;
        if (!body.getType().equals(expectedReturnType)) {
            throw new JFuncMachineException(
                    String.format("Function expects return type of %s but the return type is %s",
                            expectedReturnType, body.getType()));
        }
    }

    public Method(String name, int access, Field[] parameters, boolean tailCallable, Expression body,
                  Type expectedReturnType,
                  String filename, int lineNumber) {
        super(filename, lineNumber);
        this.name = name;
        this.access = access;
        this.parameters = parameters;
        this.tailCallable = tailCallable;
        this.body = body;
        this.expectedReturnType = expectedReturnType;
        if (!body.getType().equals(expectedReturnType)) {
            throw new JFuncMachineException(
                    String.format("Function expects return type of %s but the return type is %s",
                            expectedReturnType, body.getType()));
        }
    }

    public Type getReturnType() {
        return body.getType();
    }
}
