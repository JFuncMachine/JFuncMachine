package org.jfuncmachine.jfuncmachine.examples.minilang.expr;

import org.jfuncmachine.jfuncmachine.compiler.model.expr.Expression;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.Lambda;
import org.jfuncmachine.jfuncmachine.examples.minilang.Environment;
import org.jfuncmachine.jfuncmachine.examples.minilang.types.LambdaType;
import org.jfuncmachine.jfuncmachine.examples.minilang.types.Type;
import org.jfuncmachine.jfuncmachine.util.unification.TypeHolder;
import org.jfuncmachine.jfuncmachine.util.unification.UnificationException;

public class LambdaExpr extends Expr {
    public final Field[] fields;
    public final Expr body;

    public LambdaExpr(Field[] fields, Expr body, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.fields = fields;
        this.body = body;
    }

    @Override
    public void unify(TypeHolder other, Environment<TypeHolder> env) throws UnificationException {
        TypeHolder bodyType = new TypeHolder();
        Environment<TypeHolder> newEnv = new Environment<TypeHolder>();
        TypeHolder[] paramTypes = new TypeHolder[fields.length];
        for (int i=0; i < fields.length; i++) {
            newEnv.define(fields[i].name, fields[i].type);
            paramTypes[i] = fields[i].type;
        }

        body.unify(bodyType, newEnv);
        TypeHolder returnType = new TypeHolder();
        body.unify(returnType, env);
        LambdaType lambdaType = new LambdaType(paramTypes, returnType, filename, lineNumber);
        other.unify(new TypeHolder(lambdaType));
        type.unify(other);
    }

    public Expression generate() {
        org.jfuncmachine.jfuncmachine.compiler.model.types.Field[] jfmFields =
                new org.jfuncmachine.jfuncmachine.compiler.model.types.Field[fields.length];
        for (int i=0; i < fields.length; i++) {
            jfmFields[i] = new org.jfuncmachine.jfuncmachine.compiler.model.types.Field(fields[i].name,
                    ((Type)fields[i].type.concreteType).toJFMType());
        }
        return new Lambda(jfmFields, ((Type)type.concreteType).toJFMType(),
                body.generate());
    }
}
