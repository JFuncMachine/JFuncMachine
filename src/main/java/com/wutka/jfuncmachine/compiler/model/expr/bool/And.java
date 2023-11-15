package com.wutka.jfuncmachine.compiler.model.expr.bool;

import com.wutka.jfuncmachine.compiler.classgen.ClassGenerator;
import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.classgen.Label;
import com.wutka.jfuncmachine.compiler.model.expr.Expression;

import java.util.Stack;

public class And extends BooleanExpr {
    public BooleanExpr left;
    public BooleanExpr right;

    public And(BooleanExpr left, BooleanExpr right) {
        super(null, 0);
        this.left = left;
        this.right = right;
    }

    public And(BooleanExpr left, BooleanExpr right, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.left = left;
        this.right = right;
    }

    public BooleanExpr invert() {
        return new Or(left.invert(), right.invert());
    }

    public BooleanExpr removeNot() {
        this.left = this.left.removeNot();
        this.right = this.right.removeNot();
        return this;
    }

    public BooleanExpr computeSequence(BooleanExpr trueNext, BooleanExpr falseNext, Stack<BooleanExpr> tests) {
        BooleanExpr rightPath = right.computeSequence(trueNext, falseNext, tests);
        return left.computeSequence(rightPath, falseNext, tests);
    }
}
