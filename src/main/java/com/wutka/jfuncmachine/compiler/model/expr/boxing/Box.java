package com.wutka.jfuncmachine.compiler.model.expr.boxing;

import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.classgen.InstructionGenerator;
import com.wutka.jfuncmachine.compiler.model.expr.Expression;
import com.wutka.jfuncmachine.compiler.model.expr.javaintop.CallJavaConstructor;
import com.wutka.jfuncmachine.compiler.model.types.*;

import java.lang.String;

public class Box extends Expression {
    public Expression expr;

    public Box(Expression expr) {
        super(null, 0);
        this.expr = expr;
    }

    public Box(Expression expr, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.expr = expr;
    }

    public Type getType() {
        Type exprType = expr.getType();

        String boxType = exprType.getBoxType();
        if (boxType != null) {
            return new ObjectType(boxType);
        } else {
            return exprType;
        }
    }

    @Override
    public void generate(InstructionGenerator generator, Environment env) {
        Type exprType = expr.getType();

        String boxName = exprType.getBoxType();

        if (boxName == null) return;

        CallJavaConstructor cons = new CallJavaConstructor(boxName, new Expression[] { expr }, filename, lineNumber);
        cons.generate(generator, env);
    }
}
