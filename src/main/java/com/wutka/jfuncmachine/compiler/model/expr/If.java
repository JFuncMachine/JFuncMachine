package com.wutka.jfuncmachine.compiler.model.expr;

import com.wutka.jfuncmachine.compiler.classgen.ClassGenerator;
import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.classgen.Label;
import com.wutka.jfuncmachine.compiler.model.expr.bool.BinaryComparison;
import com.wutka.jfuncmachine.compiler.model.expr.bool.BooleanExpr;
import com.wutka.jfuncmachine.compiler.model.expr.bool.Result;
import com.wutka.jfuncmachine.compiler.model.expr.bool.UnaryComparison;
import com.wutka.jfuncmachine.compiler.model.types.Type;

import java.util.Stack;

public class If extends Expression {
    public final BooleanExpr test;
    public final Expression trueExpr;
    public final Expression falseExpr;
    public final boolean hasFalse;
    public Stack<BooleanExpr> testSequence;

    public If(BooleanExpr test, Expression trueExpr, Expression falseExpr) {
        super(null, 0);
        if (trueExpr.getType() != falseExpr.getType()) {
            generateException(
                    "True expression type is different from false expression type");
        }
        this.test = test;
        this.trueExpr = trueExpr;
        this.falseExpr = falseExpr;
        this.hasFalse = true;
        this.computeTestSequence();
    }

    public If(BooleanExpr test, Expression trueExpr, Expression falseExpr,
              String filename, int lineNumber) {
        super(filename, lineNumber);
        if (trueExpr.getType() != falseExpr.getType()) {
            generateException(
                    "True expression type is different from false expression type");
        }
        this.test = test;
        this.trueExpr = trueExpr;
        this.falseExpr = falseExpr;
        this.hasFalse = true;
        this.computeTestSequence();
    }

    public If(BooleanExpr test, Expression trueExpr) {
        super(null, 0);
        this.test = test;
        this.trueExpr = trueExpr;
        this.falseExpr = null;
        this.hasFalse = false;
        this.computeTestSequence();
    }

    public If(BooleanExpr test, Expression trueExpr,
              String filename, int lineNumber) {
        super(filename, lineNumber);
        this.test = test;
        this.trueExpr = trueExpr;
        this.falseExpr = null;
        this.hasFalse = false;
        this.computeTestSequence();
    }

    public void computeTestSequence() {
        Result truePath = new Result(trueExpr);
        Result falsePath = new Result(falseExpr);

        test.computeSequence(truePath, falsePath, new Stack<>());
    }

    public Type getType() {
        return trueExpr.getType();
    }

    public void findCaptured(Environment env) {
        for (BooleanExpr booleanExpr: testSequence) {
            if (booleanExpr instanceof UnaryComparison unary) {
                unary.expr.findCaptured(env);
            } else if (booleanExpr instanceof BinaryComparison binary) {
                binary.left.findCaptured(env);
                binary.right.findCaptured(env);
            }
        }
        trueExpr.findCaptured(env);
        if (hasFalse) {
            falseExpr.findCaptured(env);
        }
    }

    @Override
    public void generate(ClassGenerator generator, Environment env) {
        Label endLabel = new Label();
        for (BooleanExpr booleanExpr: testSequence) {
            if (booleanExpr instanceof UnaryComparison unary) {
                unary.generate(generator, env, endLabel);
            } else if (booleanExpr instanceof BinaryComparison binary) {
                binary.generate(generator, env, endLabel);
            }
        }
        generator.instGen.label(endLabel);
    }
}
