package org.jfuncmachine.jfuncmachine.examples.minilang.expr;

import org.jfuncmachine.jfuncmachine.compiler.model.expr.Expression;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.If;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.bool.And;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.bool.BooleanExpr;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.bool.Or;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.bool.UnaryComparison;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.bool.tests.Tests;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.constants.IntConstant;
import org.jfuncmachine.jfuncmachine.examples.minilang.Environment;
import org.jfuncmachine.jfuncmachine.examples.minilang.types.BoolType;
import org.jfuncmachine.jfuncmachine.util.unification.TypeHolder;
import org.jfuncmachine.jfuncmachine.util.unification.UnificationException;

public class BoolBinaryExpr extends BoolExpr {
    public enum ExprType {
        And,
        Or
    }

    public final ExprType exprType;
    public final Expr left;
    public final Expr right;
    public BoolBinaryExpr(ExprType exprType, Expr left, Expr right, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.exprType = exprType;
        this.left = left;
        this.right = right;
    }

    @Override
    public void unify(TypeHolder other, Environment<TypeHolder> env) throws UnificationException {
        TypeHolder boolType = new TypeHolder(new BoolType(filename, lineNumber));
        left.unify(boolType, env);
        right.unify(boolType, env);
        other.unify(boolType);
    }

    public Expression generate() {
        return new If(generateBooleanExpr(), new IntConstant(1), new IntConstant(0));
    }

    public BooleanExpr generateBooleanExpr() {
        BooleanExpr leftExpr;
        if (left instanceof BoolExpr leftBool) {
            leftExpr = leftBool.generateBooleanExpr();
        } else {
            leftExpr = new UnaryComparison(Tests.IsTrue, left.generate(), left.filename, left.lineNumber);
        }

        BooleanExpr rightExpr;
        if (right instanceof BoolExpr rightBool) {
            rightExpr = rightBool.generateBooleanExpr();
        } else {
            rightExpr = new UnaryComparison(Tests.IsTrue, right.generate(), right.filename, right.lineNumber);
        }

        return switch (exprType) {
            case And -> new And(leftExpr, rightExpr, filename, lineNumber);
            case Or -> new Or(leftExpr, rightExpr, filename, lineNumber);
        };
    }
}
