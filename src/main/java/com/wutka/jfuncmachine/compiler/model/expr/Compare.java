package com.wutka.jfuncmachine.compiler.model.expr;

import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.classgen.InstructionGenerator;
import com.wutka.jfuncmachine.compiler.model.types.SimpleTypes;
import com.wutka.jfuncmachine.compiler.model.types.Type;

public abstract class Compare extends Expression {
    public final Expression expr1;
    public final Expression expr2;

    public Compare(Expression expr1, Expression expr2) {
        super(null, 0);
        this.expr1 = expr1;
        this.expr2 = expr2;
    }

    public Compare(Expression expr1, Expression expr2, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.expr1 = expr1;
        this.expr2 = expr2;
    }

    public Type getType() { return SimpleTypes.INT; }

    public abstract int getOpcode();

    @Override
    public void generate(InstructionGenerator generator, Environment env) {
        expr1.generate(generator, env);
        expr2.generate(generator, env);
        generator.rawOpcode(getOpcode());
    }
}