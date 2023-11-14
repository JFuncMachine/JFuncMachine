package com.wutka.jfuncmachine.compiler.model.expr.bool;

import com.wutka.jfuncmachine.compiler.classgen.ClassGenerator;
import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.classgen.Label;
import com.wutka.jfuncmachine.compiler.model.expr.Expression;
import com.wutka.jfuncmachine.compiler.model.expr.bool.tests.Test;

public class Comparison extends BooleanExpr {
    public Test test;
    public Expression left;
    public Expression right;

    public Comparison(Test test, Expression left, Expression right) {
        super(null, 0);
        this.test = test;
        this.left = left;
        this.right = right;
    }

    public Comparison(Test test, Expression left, Expression right, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.test = test;
        this.left = left;
        this.right = right;
    }

    public BooleanExpr invert() {
        this.test = test.invert();
        return this;
    }

    public BooleanExpr removeNot() {
        return this;
    }

    public BooleanExpr computeSequence(BooleanExpr trueNext, BooleanExpr falseNext) {
        this.next = falseNext;
        falseNext.numTargeted++;
        this.shortCircuit = trueNext;
        trueNext.numTargeted++;

        return this;
    }
}
