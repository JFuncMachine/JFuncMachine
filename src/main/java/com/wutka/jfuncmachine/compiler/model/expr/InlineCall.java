package com.wutka.jfuncmachine.compiler.model.expr;

import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.classgen.InstructionGenerator;
import com.wutka.jfuncmachine.compiler.model.InlineFunction;
import com.wutka.jfuncmachine.compiler.model.types.Type;

public class InlineCall extends Expression {
    public final InlineFunction func;
    public final Expression[] parameters;

    public InlineCall(InlineFunction func, Expression[] parameters) {
        super(null, 0);
        this.func = func;
        this.parameters = parameters;
    }

    public InlineCall(InlineFunction func, Expression[] parameters, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.func = func;
        this.parameters = parameters;
    }

    public Type getType() {
        return func.getReturnType();
    }

    @Override
    public void generate(InstructionGenerator generator, Environment env) {
        for (Expression expr: parameters) {
            expr.generate(generator, env);
        }
        func.generate(generator, env);
    }
}
