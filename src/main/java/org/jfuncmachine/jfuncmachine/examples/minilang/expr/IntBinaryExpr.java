package org.jfuncmachine.jfuncmachine.examples.minilang.expr;

import org.jfuncmachine.jfuncmachine.compiler.model.InlineFunction;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.Expression;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.InlineCall;
import org.jfuncmachine.jfuncmachine.compiler.model.inline.Inlines;
import org.jfuncmachine.jfuncmachine.examples.minilang.Environment;
import org.jfuncmachine.jfuncmachine.examples.minilang.types.IntType;
import org.jfuncmachine.jfuncmachine.util.unification.TypeHolder;
import org.jfuncmachine.jfuncmachine.util.unification.UnificationException;

public class IntBinaryExpr extends IntExpr {
    public enum ExprType {
        Add,
        Sub,
        Mul,
        Div,
        And,
        Or,
        Xor,
        Lshr,
        Ashr,
        Shl

    }

    public final ExprType exprType;
    public final Expr left;
    public final Expr right;
    public IntBinaryExpr(ExprType exprType, Expr left, Expr right, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.exprType = exprType;
        this.left = left;
        this.right = right;
    }

    @Override
    public void unify(TypeHolder other, Environment<TypeHolder> env) throws UnificationException {
        TypeHolder intType = new TypeHolder(new IntType(filename, lineNumber));
        left.unify(intType, env);
        right.unify(intType, env);
        other.unify(intType);
    }

    public Expression generate() {
        InlineFunction inlineFunc = switch(exprType) {
            case Add -> Inlines.IntAdd;
            case Sub -> Inlines.IntSub;
            case Mul -> Inlines.IntMul;
            case Div -> Inlines.IntDiv;
            case And -> Inlines.IntAnd;
            case Or -> Inlines.IntOr;
            case Xor -> Inlines.IntXor;
            case Lshr -> Inlines.IntLogicalShiftRight;
            case Ashr -> Inlines.IntArithShiftRight;
            case Shl -> Inlines.IntShiftLeft;
        };
        return new InlineCall(inlineFunc, new Expression[] { left.generate(), right.generate()},
                filename, lineNumber);
    }
}
