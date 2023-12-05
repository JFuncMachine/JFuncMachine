package org.jfuncmachine.jfuncmachine.examples.minilang;

import org.jfuncmachine.jfuncmachine.examples.minilang.expr.Expr;
import org.jfuncmachine.jfuncmachine.examples.minilang.expr.Field;
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
        TypeHolder returnType = new TypeHolder();
        body.unify(returnType, env);
    }
}
