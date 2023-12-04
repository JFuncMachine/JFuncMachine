package org.jfuncmachine.jfuncmachine.examples.minilang.expr;

import org.jfuncmachine.jfuncmachine.compiler.model.expr.Expression;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.If;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.bool.And;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.bool.BinaryComparison;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.bool.BooleanExpr;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.bool.Or;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.bool.UnaryComparison;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.bool.tests.Test;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.bool.tests.Tests;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.constants.IntConstant;
import org.jfuncmachine.jfuncmachine.examples.minilang.Environment;
import org.jfuncmachine.jfuncmachine.examples.minilang.types.BoolType;
import org.jfuncmachine.jfuncmachine.sexprlang.translate.ModelItem;
import org.jfuncmachine.jfuncmachine.util.unification.TypeHolder;
import org.jfuncmachine.jfuncmachine.util.unification.UnificationException;

public class BoolComparison extends BoolExpr {
    @ModelItem(isExprStart = true)
    public enum CompType {
        Equal("="),
        NotEqual("!="),
        LessThan("<"),
        LessOrEqual("<="),
        GreaterThan(">"),
        GreaterOrEqual(">=");

        public final String symbol;
        CompType(String symbol) {
            this.symbol = symbol;
        }
    }

    public final CompType compType;
    public Expr left;
    public Expr right;

    public BoolComparison(CompType compType, Expr left, Expr right, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.compType = compType;
        this.left = left;
        this.right = right;
    }

    @Override
    public void unify(TypeHolder other, Environment<TypeHolder> env) throws UnificationException {
        TypeHolder leftType = new TypeHolder();
        TypeHolder rightType = new TypeHolder();
        left.unify(leftType, env);
        right.unify(rightType, env);
        leftType.unify(rightType);
        TypeHolder boolType = new TypeHolder(new BoolType(filename, lineNumber));
        other.unify(boolType);
    }


    public Expression generate() {
        return new If(generateBooleanExpr(), new IntConstant(1), new IntConstant(0));
    }

    public BooleanExpr generateBooleanExpr() {
        Test test = switch (compType) {
            case Equal -> Tests.EQ;
            case NotEqual -> Tests.NE;
            case LessThan -> Tests.LT;
            case LessOrEqual -> Tests.LE;
            case GreaterThan -> Tests.GT;
            case GreaterOrEqual -> Tests.GE;
        };
        return new BinaryComparison(test, left.generate(), right.generate(), filename, lineNumber);
    }
}
