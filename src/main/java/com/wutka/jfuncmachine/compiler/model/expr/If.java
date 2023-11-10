package com.wutka.jfuncmachine.compiler.model.expr;

import com.wutka.jfuncmachine.compiler.classgen.ClassGenerator;
import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.classgen.Label;
import com.wutka.jfuncmachine.compiler.model.types.Type;

public class If extends Expression {
    public final Comparison comparison;
    public final Expression trueExpr;
    public final Expression falseExpr;
    public final boolean hasFalse;

    public If(Comparison comparison, Expression trueExpr, Expression falseExpr) {
        super(null, 0);
        if (trueExpr.getType() != falseExpr.getType()) {
            generateException(
                    "True expression type is different from false expression type");
        }
        this.comparison = comparison;
        this.trueExpr = trueExpr;
        this.falseExpr = falseExpr;
        this.hasFalse = true;
    }

    public If(Comparison comparison, Expression trueExpr, Expression falseExpr,
              String filename, int lineNumber) {
        super(filename, lineNumber);
        if (trueExpr.getType() != falseExpr.getType()) {
            generateException(
                    "True expression type is different from false expression type");
        }
        this.comparison = comparison;
        this.trueExpr = trueExpr;
        this.falseExpr = falseExpr;
        this.hasFalse = true;
    }

    public If(Comparison comparison, Expression trueExpr) {
        super(null, 0);
        this.comparison = comparison;
        this.trueExpr = trueExpr;
        this.falseExpr = null;
        this.hasFalse = false;
    }

    public If(Comparison comparison, Expression trueExpr,
              String filename, int lineNumber) {
        super(filename, lineNumber);
        this.comparison = comparison;
        this.trueExpr = trueExpr;
        this.falseExpr = null;
        this.hasFalse = false;
    }

    public Type getType() {
        return trueExpr.getType();
    }

    public void findCaptured(Environment env) {
        if (comparison.numArgs > 0) {
            comparison.expr1.findCaptured(env);
        }
        if (comparison.numArgs > 1) {
            comparison.expr2.findCaptured(env);
        }
        trueExpr.findCaptured(env);
        if (hasFalse) {
            falseExpr.findCaptured(env);
        }
    }

    @Override
    public void generate(ClassGenerator generator, Environment env) {
        if (comparison.numArgs > 0) {
            comparison.expr1.generate(generator, env);
        }
        if (comparison.numArgs > 1) {
            comparison.expr2.generate(generator, env);
        }
        if (comparison.compareFirst) {
            generator.instGen.rawOpcode(comparison.compareOpcode);
        }
        Label endLabel = new Label();
        if (hasFalse) {
            Label falseLabel = new Label();
            generator.instGen.rawJumpOpcode(comparison.opcode, falseLabel);
            trueExpr.generate(generator, env);
            generator.instGen.gotolabel(endLabel);
            generator.instGen.label(falseLabel);
            falseExpr.generate(generator, env);
        } else {
            generator.instGen.rawJumpOpcode(comparison.opcode, endLabel);
            trueExpr.generate(generator, env);
        }
        generator.instGen.label(endLabel);
    }
}
