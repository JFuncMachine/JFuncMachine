package org.jfuncmachine.jfuncmachine.examples.minilang.expr;

import org.jfuncmachine.jfuncmachine.compiler.model.InlineFunction;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.Expression;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.InlineCall;
import org.jfuncmachine.jfuncmachine.compiler.model.inline.Inlines;
import org.jfuncmachine.jfuncmachine.examples.minilang.Environment;
import org.jfuncmachine.jfuncmachine.examples.minilang.types.IntType;
import org.jfuncmachine.jfuncmachine.examples.minilang.types.Type;
import org.jfuncmachine.jfuncmachine.sexprlang.translate.ModelItem;
import org.jfuncmachine.jfuncmachine.util.unification.TypeHolder;
import org.jfuncmachine.jfuncmachine.util.unification.UnificationException;

@ModelItem(includeStartSymbol = true)
public class IntBinaryExpr extends IntExpr {
    @ModelItem(isExprStart = true, exprLength=3)
    public enum ExprType {
        Add("+"),
        Sub("-"),
        Mul("*"),
        Div("/"),
        And("&"),
        Or("|"),
        Xor("^"),
        Lshr(">>"),
        Ashr(">>>"),
        Shl("<<");

        public final String symbol;
        ExprType(String symbol) {
            this.symbol = symbol;
        }
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
    public void unify(TypeHolder<Type> other, Environment<TypeHolder<Type>> env) throws UnificationException {
        TypeHolder<Type> intType = new TypeHolder<>(new IntType(filename, lineNumber));
        left.unify(intType, env);
        right.unify(intType, env);
        other.unify(intType);
        type.unify(intType);
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
