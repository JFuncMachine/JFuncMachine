package org.jfuncmachine.jfuncmachine.examples.minilang.expr;

import org.jfuncmachine.jfuncmachine.compiler.model.expr.Expression;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.If;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.bool.Not;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.bool.BooleanExpr;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.bool.UnaryComparison;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.bool.tests.Tests;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.constants.IntConstant;
import org.jfuncmachine.jfuncmachine.examples.minilang.Environment;
import org.jfuncmachine.jfuncmachine.examples.minilang.types.BoolType;
import org.jfuncmachine.jfuncmachine.util.unification.TypeHolder;
import org.jfuncmachine.jfuncmachine.util.unification.UnificationException;

public class BoolNotExpr extends BoolExpr {
    public final Expr expr;
    public BoolNotExpr(Expr expr, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.expr = expr;
    }

    @Override
    public void unify(TypeHolder other, Environment<TypeHolder> env) throws UnificationException {
        TypeHolder boolType = new TypeHolder(new BoolType(filename, lineNumber));
        other.unify(boolType);
    }

    public Expression generate() {
        return new If(generateBooleanExpr(), new IntConstant(1), new IntConstant(0));
    }

    public BooleanExpr generateBooleanExpr() {
        BooleanExpr boolExpr;
        if (expr instanceof BoolExpr leftBool) {
            boolExpr = leftBool.generateBooleanExpr();
        } else {
            boolExpr = new UnaryComparison(Tests.IsTrue, expr.generate(), expr.filename, expr.lineNumber);
        }

        return new Not(boolExpr, filename, lineNumber);
    }
}
