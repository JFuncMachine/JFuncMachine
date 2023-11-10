package com.wutka.jfuncmachine.compiler.model.expr;

import com.wutka.jfuncmachine.compiler.classgen.ClassGenerator;
import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.model.InlineFunction;
import com.wutka.jfuncmachine.compiler.model.types.Type;

public class InlineCall extends Expression {
    public final InlineFunction func;
    public final Expression[] arguments;

    public InlineCall(InlineFunction func, Expression[] arguments) {
        super(null, 0);
        this.func = func;
        this.arguments = arguments;
    }

    public InlineCall(InlineFunction func, Expression[] arguments, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.func = func;
        this.arguments = arguments;
    }

    public Type getType() {
        return func.getReturnType();
    }

    public void findCaptured(Environment env) {
        for (Expression expr: arguments) {
            expr.findCaptured(env);
        }
    }

    @Override
    public void generate(ClassGenerator generator, Environment env) {
        for (Expression expr: arguments) {
            expr.generate(generator, env);
        }
        func.generate(generator, env);
    }
}
