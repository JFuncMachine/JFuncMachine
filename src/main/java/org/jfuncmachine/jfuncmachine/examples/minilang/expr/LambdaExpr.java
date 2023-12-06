package org.jfuncmachine.jfuncmachine.examples.minilang.expr;

import org.jfuncmachine.jfuncmachine.compiler.model.expr.Expression;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.Lambda;
import org.jfuncmachine.jfuncmachine.examples.minilang.Environment;
import org.jfuncmachine.jfuncmachine.examples.minilang.types.LambdaType;
import org.jfuncmachine.jfuncmachine.examples.minilang.types.Type;
import org.jfuncmachine.jfuncmachine.sexprlang.translate.ModelItem;
import org.jfuncmachine.jfuncmachine.util.unification.TypeHolder;
import org.jfuncmachine.jfuncmachine.util.unification.UnificationException;

@ModelItem(symbol="lambda")
public class LambdaExpr extends Expr {
    public final String[] fields;
    public final Expr body;
    protected ParamField[] paramParamFields;

    public LambdaExpr(String[] fields, Expr body, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.fields = fields;
        this.body = body;
    }

    @Override
    public void unify(TypeHolder<Type> other, Environment<TypeHolder<Type>> env) throws UnificationException {
        TypeHolder<Type> bodyType = new TypeHolder<>();
        Environment<TypeHolder<Type>> newEnv = new Environment<>();
        TypeHolder<Type>[] paramTypes = new TypeHolder[fields.length];

        paramParamFields = new ParamField[fields.length];
        for (int i=0; i < fields.length; i++) {
            paramParamFields[i] = new ParamField(fields[i]);
        }

        for (int i=0; i < fields.length; i++) {
            newEnv.define(fields[i], paramParamFields[i].type);
        }
        body.unify(bodyType, newEnv);
        TypeHolder<Type> returnType = new TypeHolder<>();
        body.unify(returnType, env);
        LambdaType lambdaType = new LambdaType(paramTypes, returnType, filename, lineNumber);
        other.unify(new TypeHolder<>(lambdaType));
        type.unify(other);

    }

    public Expression generate() {
        org.jfuncmachine.jfuncmachine.compiler.model.types.Field[] jfmFields =
                new org.jfuncmachine.jfuncmachine.compiler.model.types.Field[fields.length];
        for (int i=0; i < fields.length; i++) {
            jfmFields[i] = new org.jfuncmachine.jfuncmachine.compiler.model.types.Field(paramParamFields[i].name,
                    ((Type) paramParamFields[i].type.concreteType).toJFMType());
        }
        return new Lambda(jfmFields, ((Type)type.concreteType).toJFMType(),
                body.generate());
    }
}
