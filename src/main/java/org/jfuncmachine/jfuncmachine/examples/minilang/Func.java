package org.jfuncmachine.jfuncmachine.examples.minilang;

import org.jfuncmachine.jfuncmachine.compiler.model.Access;
import org.jfuncmachine.jfuncmachine.compiler.model.MethodDef;
import org.jfuncmachine.jfuncmachine.examples.minilang.expr.Expr;
import org.jfuncmachine.jfuncmachine.examples.minilang.expr.Field;
import org.jfuncmachine.jfuncmachine.examples.minilang.types.Type;
import org.jfuncmachine.jfuncmachine.sexprlang.translate.ModelItem;
import org.jfuncmachine.jfuncmachine.util.unification.TypeHolder;
import org.jfuncmachine.jfuncmachine.util.unification.UnificationException;

@ModelItem(symbol="define")
public class Func {
    public final String name;
    public final Field[] paramTypes;
    public final Expr body;
    public final String filename;
    public final int lineNumber;

    protected TypeHolder returnType;

    public Func(String name, Field[] paramTypes, Expr body, String filename, int lineNumber) {
        this.name = name;
        this.paramTypes = paramTypes;
        this.body = body;
        this.filename = filename;
        this.lineNumber = lineNumber;
    }

    public void unify() throws UnificationException {
        Environment<TypeHolder> env = new Environment<>();
        for (int i=0; i < paramTypes.length; i++) {
            env.define(paramTypes[i].name, paramTypes[i].type);
        }
        returnType = new TypeHolder();
        body.unify(returnType, env);
    }

    public MethodDef generate() {
        org.jfuncmachine.jfuncmachine.compiler.model.types.Field[] methodFields =
                new org.jfuncmachine.jfuncmachine.compiler.model.types.Field[paramTypes.length];

        if (!returnType.isFull()) {
            throw new MinilangException(
                    String.format("%s %d: Unable to determine return type of func %s",
                            filename, lineNumber, name));
        }

        for (int i=0; i < paramTypes.length; i++) {
            Field paramType = paramTypes[i];
            if (!paramType.type.isFull()) {
                throw new MinilangException(
                        String.format("%s %d: Unable to determine type of func %s param %s",
                                filename, lineNumber, name, paramType.name));
            }
            methodFields[i] = new org.jfuncmachine.jfuncmachine.compiler.model.types.Field(
                    paramType.name, ((Type) paramType.type.concreteType).toJFMType());
        }
        return new MethodDef(name, Access.PUBLIC + Access.STATIC,
                methodFields, ((Type)returnType.concreteType).toJFMType(),
                body.generate(), filename, lineNumber);
    }
}
