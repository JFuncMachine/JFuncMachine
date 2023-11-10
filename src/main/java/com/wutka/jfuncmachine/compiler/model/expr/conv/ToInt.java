package com.wutka.jfuncmachine.compiler.model.expr.conv;

import com.wutka.jfuncmachine.compiler.classgen.ClassGenerator;
import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.model.expr.Expression;
import com.wutka.jfuncmachine.compiler.model.types.*;

public class ToInt extends Expression {
    protected Expression expr;

    public ToInt(Expression expr) {
        super(null, 0);
        this.expr = expr;
    }

    public ToInt(Expression expr, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.expr = expr;
    }

    public Type getType() { return SimpleTypes.INT; }

    public void findCaptured(Environment env) {
        expr.findCaptured(env);
    }

    @Override
    public void generate(ClassGenerator generator, Environment env) {
        Type exprType = expr.getType();

        expr.generate(generator, env);

        switch (exprType) {
            case DoubleType d -> generator.instGen.d2i();
            case FloatType f -> generator.instGen.f2i();
            case LongType l -> generator.instGen.l2i();
            case IntType i -> {}
            default -> throw generateException(
                    String.format("Can't convert %s into int", exprType));
        }
    }
}
