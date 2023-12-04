package org.jfuncmachine.jfuncmachine.examples.minilang.expr;

import org.jfuncmachine.jfuncmachine.compiler.model.InlineFunction;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.Expression;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.InlineCall;
import org.jfuncmachine.jfuncmachine.compiler.model.inline.Inlines;
import org.jfuncmachine.jfuncmachine.examples.minilang.Environment;
import org.jfuncmachine.jfuncmachine.examples.minilang.types.IntType;
import org.jfuncmachine.jfuncmachine.sexprlang.translate.ModelItem;
import org.jfuncmachine.jfuncmachine.util.unification.TypeHolder;
import org.jfuncmachine.jfuncmachine.util.unification.UnificationException;

public class IntUnaryExpr extends IntExpr {
    @ModelItem(isExprStart = true, exprLength=2)
    public enum ExprType {
        Neg,
    }

    public final ExprType exprType;
    public final Expr expr;
    public IntUnaryExpr(ExprType exprType, Expr expr, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.exprType = exprType;
        this.expr = expr;
    }

    @Override
    public void unify(TypeHolder other, Environment<TypeHolder> env) throws UnificationException {
        TypeHolder intType = new TypeHolder(new IntType(filename, lineNumber));
        other.unify(intType);
        expr.unify(intType, env);
    }

    public Expression generate() {
        InlineFunction inlineFunc = switch(exprType) {
            case Neg -> Inlines.IntNeg;
        };
        return new InlineCall(inlineFunc, new Expression[] { expr.generate() },
                filename, lineNumber);
    }
}
