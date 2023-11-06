package com.wutka.jfuncmachine.compiler.model.expr;

import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.classgen.InstructionGenerator;
import com.wutka.jfuncmachine.compiler.model.types.SimpleTypes;
import com.wutka.jfuncmachine.compiler.model.types.Type;

public class Block extends Expression {
    public final Expression[] expressions;

    public Block (Expression[] expressions) {
        super(null, 0);
        this.expressions = expressions;
    }

    public Block (Expression[] expressions, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.expressions = expressions;
    }

    public Type getType() {
        if (expressions.length > 0) {
            return expressions[expressions.length - 1].getType();
        } else {
            return SimpleTypes.UNIT;
        }
    }

    public void findCaptured(Environment env) {
        for (Expression expr: expressions) {
            expr.findCaptured(env);
        }
    }

    @Override
    public void generate(InstructionGenerator generator, Environment env) {
        for (int i=0; i < expressions.length; i++) {
            expressions[i].generate(generator, env);
            if (i < expressions.length-1) {
                if (expressions[i].getType().getStackSize() == 2) {
                    generator.pop2();
                } else {
                    generator.pop();
                }
            }
        }
    }
}
