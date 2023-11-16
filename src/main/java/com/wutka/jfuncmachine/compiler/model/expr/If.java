package com.wutka.jfuncmachine.compiler.model.expr;

import com.wutka.jfuncmachine.compiler.classgen.ClassGenerator;
import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.classgen.Label;
import com.wutka.jfuncmachine.compiler.model.expr.bool.*;
import com.wutka.jfuncmachine.compiler.model.types.Type;

import java.util.List;
import java.util.Stack;

public class If extends Expression {
    public final BooleanExpr test;
    public final Expression trueExpr;
    public final Expression falseExpr;
    public final boolean hasFalse;
    public List<BooleanExpr> testSequence;
    public Result trueResult;
    public Result falseResult;

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
        trueResult = new Result(trueExpr);
        falseResult = new Result(falseExpr);

        testSequence = new Stack<>();
        test.computeSequence(trueResult, falseResult, testSequence);
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
        for (int i=testSequence.size()-1; i >= 0; i--) {
            BooleanExpr booleanExpr = testSequence.get(i);
            BooleanExpr nextExpr = null;
            if (i > 0) {
                nextExpr = testSequence.get(i-1);
            }
            if (booleanExpr instanceof UnaryComparison unary) {
                unary.generate(generator, env, nextExpr);
            } else if (booleanExpr instanceof BinaryComparison binary) {
                binary.generate(generator, env, nextExpr);
            } else if (booleanExpr instanceof InstanceofComparison instOf) {
                instOf.generate(generator, env, nextExpr);
            }
        }
        if (hasFalse) {
            if (falseResult.label != null) {
                generator.instGen.label(falseResult.label);
            }
            falseExpr.generate(generator, env);
            generator.instGen.gotolabel(endLabel);
        }
        if (trueResult.label != null) {
            generator.instGen.label(trueResult.label);
        }
        trueExpr.generate(generator, env);
        if (!hasFalse && falseResult.label != null) {
            generator.instGen.label(falseResult.label);
        }
        generator.instGen.label(endLabel);
    }
}
