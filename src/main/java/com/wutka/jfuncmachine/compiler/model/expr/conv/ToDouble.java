package com.wutka.jfuncmachine.compiler.model.expr.conv;

import com.wutka.jfuncmachine.compiler.classgen.ClassGenerator;
import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.model.expr.Expression;
import com.wutka.jfuncmachine.compiler.model.types.*;

public class ToDouble extends Expression {
    protected Expression expr;

    public ToDouble(Expression expr) {
        super(null, 0);
        this.expr = expr;
    }

    public ToDouble(Expression expr, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.expr = expr;
    }

    public Type getType() { return SimpleTypes.DOUBLE; }

    public void findCaptured(Environment env) {
        expr.findCaptured(env);
    }

    @Override
    public void generate(ClassGenerator generator, Environment env) {
        Type exprType = expr.getType();

        expr.generate(generator, env);

        switch (exprType) {
            case IntType i -> generator.instGen.i2d();
            case LongType l -> generator.instGen.l2d();
            case FloatType c -> generator.instGen.f2d();
            case DoubleType c -> {}
            default -> throw generateException(
                    String.format("Can't convert %s into double", exprType));
        }
    }
}
