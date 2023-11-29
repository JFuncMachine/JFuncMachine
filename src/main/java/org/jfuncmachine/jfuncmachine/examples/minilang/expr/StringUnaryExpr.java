package org.jfuncmachine.jfuncmachine.examples.minilang.expr;

import org.jfuncmachine.jfuncmachine.examples.minilang.Environment;
import org.jfuncmachine.jfuncmachine.examples.minilang.types.StringType;
import org.jfuncmachine.jfuncmachine.util.unification.TypeHolder;
import org.jfuncmachine.jfuncmachine.util.unification.UnificationException;

public class StringUnaryExpr extends StringExpr {
    public enum ExprType {
        ToUpper,
        ToLower,
    }

    public final ExprType exprType;
    public final Expr expr;
    public StringUnaryExpr(ExprType exprType, Expr expr, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.exprType = exprType;
        this.expr = expr;
    }

    @Override
    public void unify(TypeHolder other, Environment<TypeHolder> env) throws UnificationException {
        TypeHolder stringType = new TypeHolder(new StringType(filename, lineNumber));
        other.unify(stringType);
        expr.unify(stringType, env);
    }
}
