package com.wutka.jfuncmachine.compiler.model.expr.conv;

import com.wutka.jfuncmachine.compiler.classgen.ClassGenerator;
import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.model.expr.Expression;
import com.wutka.jfuncmachine.compiler.model.types.*;

public class ToLong extends Expression {
    protected Expression expr;

    public ToLong(Expression expr) {
        super(null, 0);
        this.expr = expr;
    }

    public ToLong(Expression expr, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.expr = expr;
    }

    public Type getType() { return SimpleTypes.LONG; }

    public void findCaptured(Environment env) {
        expr.findCaptured(env);
    }

    @Override
    public void generate(ClassGenerator generator, Environment env) {
        Type exprType = expr.getType();

        expr.generate(generator, env);

        switch (exprType) {
            case IntType i -> generator.instGen.i2l();
            case DoubleType d -> generator.instGen.d2l();
            case FloatType f -> generator.instGen.f2l();
            case LongType l -> {}
            default -> throw generateException(
                    String.format("Can't convert %s into long", exprType));
        }
    }
}
