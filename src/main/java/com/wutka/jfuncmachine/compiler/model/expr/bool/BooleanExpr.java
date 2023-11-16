package com.wutka.jfuncmachine.compiler.model.expr.bool;

import com.wutka.jfuncmachine.compiler.classgen.Label;
import com.wutka.jfuncmachine.compiler.model.SourceElement;
import com.wutka.jfuncmachine.compiler.model.expr.bool.tests.Tests;
import com.wutka.jfuncmachine.compiler.model.expr.constants.IntConstant;

import java.util.List;
import java.util.Stack;

public abstract class BooleanExpr extends SourceElement {
    public Label label;

    public BooleanExpr(String filename, int lineNumber) {
        super(filename, lineNumber);
    }

    public abstract BooleanExpr invert();

    public abstract BooleanExpr removeNot();

    public BooleanExpr computeSequence(BooleanExpr trueNext, BooleanExpr falseNext, List<BooleanExpr> tests) {
        return this;
    }

    public static void main(String[] args) {
        BooleanExpr expr =
                new Or(
                        new And(
                                new Or(
                                    new BinaryComparison(Tests.LT, new IntConstant(1), new IntConstant(2)),
                                    new BinaryComparison(Tests.LT, new IntConstant(3), new IntConstant(4))),
                                new Or(
                                        new BinaryComparison(Tests.LT, new IntConstant(5), new IntConstant(6)),
                                        new BinaryComparison(Tests.LT, new IntConstant(7), new IntConstant(8)))),
                        new And(
                                new Or(
                                        new BinaryComparison(Tests.LT, new IntConstant(9), new IntConstant(10)),
                                        new BinaryComparison(Tests.LT, new IntConstant(11), new IntConstant(12))),
                                new Or(
                                        new BinaryComparison(Tests.LT, new IntConstant(13), new IntConstant(14)),
                                        new BinaryComparison(Tests.LT, new IntConstant(15), new IntConstant(16)))));

        Result trueExpr = new Result(new IntConstant(1));
        Result falseExpr = new Result(new IntConstant(0));
        Stack<BooleanExpr> tests = new Stack<>();
        BooleanExpr seq = expr.computeSequence(trueExpr, falseExpr, tests);
        System.out.println();
    }
}
