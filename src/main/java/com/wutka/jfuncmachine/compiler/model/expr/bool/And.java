package com.wutka.jfuncmachine.compiler.model.expr.bool;

import com.wutka.jfuncmachine.compiler.classgen.ClassGenerator;
import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.classgen.Label;
import com.wutka.jfuncmachine.compiler.model.expr.Expression;

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

    @Override
    public void generate(ClassGenerator generator, Environment env, Expression truePath, Label falsePath) {
    }
}
