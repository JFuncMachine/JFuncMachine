package com.wutka.jfuncmachine.compiler.model.expr.conv;

import com.wutka.jfuncmachine.compiler.classgen.ClassGenerator;
import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.model.expr.Expression;
import com.wutka.jfuncmachine.compiler.model.types.SimpleTypes;
import com.wutka.jfuncmachine.compiler.model.types.Type;
import com.wutka.jfuncmachine.compiler.model.types.UnitType;

public class ToUnit extends Expression {
    protected Expression expr;

    public ToUnit(Expression expr) {
        super(null, 0);
        this.expr = expr;
    }

    public ToUnit(Expression expr, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.expr = expr;
    }

    public Type getType() { return SimpleTypes.UNIT; }

    public void findCaptured(Environment env) {
        expr.findCaptured(env);
    }

    @Override
    public void generate(ClassGenerator generator, Environment env) {
        Type exprType = expr.getType();

        expr.generate(generator, env);

        switch (exprType) {
            case UnitType u -> {}
            default -> {
                if (exprType.getStackSize() == 2) {
                    generator.instGen.pop2();
                } else {
                    generator.instGen.pop();
                }
            }
        }
    }
}
