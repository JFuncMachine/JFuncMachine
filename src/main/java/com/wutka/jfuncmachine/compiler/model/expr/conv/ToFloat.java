package com.wutka.jfuncmachine.compiler.model.expr.conv;

import com.wutka.jfuncmachine.compiler.classgen.ClassGenerator;
import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.model.expr.Expression;
import com.wutka.jfuncmachine.compiler.model.types.*;

public class ToFloat extends Expression {
    protected Expression expr;

    public ToFloat(Expression expr) {
        super(null, 0);
        this.expr = expr;
    }

    public ToFloat(Expression expr, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.expr = expr;
    }

    public Type getType() { return SimpleTypes.FLOAT; }

    public void findCaptured(Environment env) {
        expr.findCaptured(env);
    }

    @Override
    public void generate(ClassGenerator generator, Environment env) {
        Type exprType = expr.getType();

        expr.generate(generator, env);

        switch (exprType) {
            case IntType i -> generator.instGen.i2f();
            case LongType l -> generator.instGen.l2f();
            case DoubleType d -> generator.instGen.d2f();
            case FloatType c -> {}
            default -> throw generateException(
                    String.format("Can't convert %s into float", exprType));
        }
    }
}
