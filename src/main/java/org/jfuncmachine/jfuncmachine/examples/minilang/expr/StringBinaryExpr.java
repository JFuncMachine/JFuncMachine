package org.jfuncmachine.jfuncmachine.examples.minilang.expr;

import org.jfuncmachine.jfuncmachine.compiler.model.expr.Expression;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.javainterop.CallJavaMethod;
import org.jfuncmachine.jfuncmachine.compiler.model.types.SimpleTypes;
import org.jfuncmachine.jfuncmachine.examples.minilang.Environment;
import org.jfuncmachine.jfuncmachine.examples.minilang.types.StringType;
import org.jfuncmachine.jfuncmachine.util.unification.TypeHolder;
import org.jfuncmachine.jfuncmachine.util.unification.UnificationException;

public class StringBinaryExpr extends StringExpr {
    public enum ExprType {
        Concat
    }

    public final ExprType exprType;
    public final Expr left;
    public final Expr right;
    public StringBinaryExpr(ExprType exprType, Expr left, Expr right, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.exprType = exprType;
        this.left = left;
        this.right = right;
    }

    @Override
    public void unify(TypeHolder other, Environment<TypeHolder> env) throws UnificationException {
        TypeHolder stringType = new TypeHolder(new StringType(filename, lineNumber));
        other.unify(stringType);
        left.unify(stringType, env);
        right.unify(stringType, env);
    }

    public Expression generate() {
        String methodName = switch(exprType) {
            case Concat -> "concat";
        };

        return new CallJavaMethod("java.lang.String", methodName, SimpleTypes.STRING,
                left.generate(), new Expression[] { right.generate() }, filename, lineNumber);
    }
}
