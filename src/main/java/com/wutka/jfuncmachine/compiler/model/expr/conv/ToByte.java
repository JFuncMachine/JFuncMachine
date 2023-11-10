package com.wutka.jfuncmachine.compiler.model.expr.conv;

import com.wutka.jfuncmachine.compiler.classgen.ClassGenerator;
import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.model.expr.Expression;
import com.wutka.jfuncmachine.compiler.model.types.ByteType;
import com.wutka.jfuncmachine.compiler.model.types.IntType;
import com.wutka.jfuncmachine.compiler.model.types.SimpleTypes;
import com.wutka.jfuncmachine.compiler.model.types.Type;

public class ToByte extends Expression {
    protected Expression expr;

    public ToByte(Expression expr) {
        super(null, 0);
        this.expr = expr;
    }

    public ToByte(Expression expr, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.expr = expr;
    }

    public Type getType() { return SimpleTypes.BYTE; }

    public void findCaptured(Environment env) {
        expr.findCaptured(env);
    }

    @Override
    public void generate(ClassGenerator generator, Environment env) {
        Type exprType = expr.getType();

        expr.generate(generator, env);

        switch (exprType) {
            case IntType i -> generator.instGen.i2b();
            case ByteType b -> { }
            default -> throw generateException(
                    String.format("Can't convert %s into byte", exprType));
            
        }
    }
}
