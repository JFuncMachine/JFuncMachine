package com.wutka.jfuncmachine.compiler.model.expr.bool;

import com.wutka.jfuncmachine.compiler.classgen.ClassGenerator;
import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.classgen.Label;
import com.wutka.jfuncmachine.compiler.model.SourceElement;
import com.wutka.jfuncmachine.compiler.model.expr.Expression;
import com.wutka.jfuncmachine.compiler.model.expr.bool.tests.Tests;
import com.wutka.jfuncmachine.compiler.model.expr.constants.IntConstant;

public abstract class BooleanExpr extends SourceElement {
    public BooleanExpr next = null;
    public BooleanExpr shortCircuit = null;
    public int numTargeted = 0;

    public BooleanExpr(String filename, int lineNumber) {
        super(filename, lineNumber);
    }

    public abstract BooleanExpr invert();

    public abstract BooleanExpr removeNot();

    public abstract BooleanExpr computeSequence(BooleanExpr trueNext, BooleanExpr falseNext);

    public static void main(String[] args) {
        BooleanExpr expr =
                new Or(
                        new And(
                                new Or(
                                    new Comparison(Tests.LT, new IntConstant(1), new IntConstant(2)),
                                    new Comparison(Tests.LT, new IntConstant(3), new IntConstant(4))),
                                new Or(
                                        new Comparison(Tests.LT, new IntConstant(5), new IntConstant(6)),
                                        new Comparison(Tests.LT, new IntConstant(7), new IntConstant(8)))),
                        new And(
                                new Or(
                                        new Comparison(Tests.LT, new IntConstant(9), new IntConstant(10)),
                                        new Comparison(Tests.LT, new IntConstant(11), new IntConstant(12))),
                                new Or(
                                        new Comparison(Tests.LT, new IntConstant(13), new IntConstant(14)),
                                        new Comparison(Tests.LT, new IntConstant(15), new IntConstant(16)))));

        Result trueExpr = new Result(new IntConstant(1));
        Result falseExpr = new Result(new IntConstant(0));
        BooleanExpr seq = expr.computeSequence(trueExpr, falseExpr);
        System.out.println();
    }
}
