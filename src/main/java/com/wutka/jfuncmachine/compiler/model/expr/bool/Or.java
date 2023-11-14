package com.wutka.jfuncmachine.compiler.model.expr.bool;

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

    public BooleanExpr computeSequence(BooleanExpr trueNext, BooleanExpr falseNext) {
        BooleanExpr rightPath = right.computeSequence(trueNext, falseNext);
        return left.computeSequence(trueNext, rightPath);
    }
}
