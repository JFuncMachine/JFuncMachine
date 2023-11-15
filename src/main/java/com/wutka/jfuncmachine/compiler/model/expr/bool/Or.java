package com.wutka.jfuncmachine.compiler.model.expr.bool;

import java.util.List;
import java.util.Stack;

public class Or extends BooleanExpr {
    public BooleanExpr left;
    public BooleanExpr right;

    public Or(BooleanExpr left, BooleanExpr right) {
        super(null, 0);
        this.left = left;
        this.right = right;
    }

    public Or(BooleanExpr left, BooleanExpr right, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.left = left;
        this.right = right;
    }

    public BooleanExpr invert() {
        return new And(left.invert(), right.invert());
    }

    public BooleanExpr removeNot() {
        this.left = this.left.removeNot();
        this.right = this.right.removeNot();
        return this;
    }

    public BooleanExpr computeSequence(BooleanExpr trueNext, BooleanExpr falseNext, List<BooleanExpr> tests) {
        BooleanExpr rightPath = right.computeSequence(trueNext, falseNext, tests);
        return left.computeSequence(trueNext, rightPath, tests);
    }
}
