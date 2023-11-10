package com.wutka.jfuncmachine.compiler.model.expr.conv;

import com.wutka.jfuncmachine.compiler.classgen.ClassGenerator;
import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.model.expr.Expression;
import com.wutka.jfuncmachine.compiler.model.types.IntType;
import com.wutka.jfuncmachine.compiler.model.types.ShortType;
import com.wutka.jfuncmachine.compiler.model.types.SimpleTypes;
import com.wutka.jfuncmachine.compiler.model.types.Type;

public class ToShort extends Expression {
    protected Expression expr;

    public ToShort(Expression expr) {
        super(null, 0);
        this.expr = expr;
    }

    public ToShort(Expression expr, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.expr = expr;
    }

    public Type getType() { return SimpleTypes.SHORT; }

    public void findCaptured(Environment env) {
        expr.findCaptured(env);
    }

    @Override
    public void generate(ClassGenerator generator, Environment env) {
        Type exprType = expr.getType();

        expr.generate(generator, env);

        switch (exprType) {
            case IntType i -> generator.instGen.i2s();
            case ShortType s -> {}
            default -> throw generateException(
                    String.format("Can't convert %s into short", exprType));
        }
    }
}
