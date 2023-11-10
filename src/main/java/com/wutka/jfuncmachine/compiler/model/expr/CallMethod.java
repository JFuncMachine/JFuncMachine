package com.wutka.jfuncmachine.compiler.model.expr;

import com.wutka.jfuncmachine.compiler.classgen.ClassGenerator;
import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.classgen.Naming;
import com.wutka.jfuncmachine.compiler.model.types.Type;

public class CallMethod extends Expression {
    public final String className;
    public final String func;
    public final Expression target;
    public final Expression[] arguments;
    public final Type[] parameterTypes;
    public final Type returnType;

    public CallMethod(String className, String func, Type[] parameterTypes, Type returnType,
                      Expression target, Expression[] arguments) {
        super(null, 0);
        this.className = className;
        this.func = func;
        this.target = target;
        this.arguments = arguments;
        this.parameterTypes = parameterTypes;
        this.returnType = returnType;
    }

    public CallMethod(String className, String func, Type[] parameterTypes, Type returnType,
                      Expression target, Expression[] arguments,
                      String filename, int lineNumber) {
        super(filename, lineNumber);
        this.className = className;
        this.func = func;
        this.target = target;
        this.arguments = arguments;
        this.parameterTypes = parameterTypes;
        this.returnType = returnType;
    }

    public Type getType() {
        return returnType;
    }

    public void findCaptured(Environment env) {
        for (Expression expr: arguments) {
            expr.findCaptured(env);
        }
        target.findCaptured(env);
    }

    @Override
    public void generate(ClassGenerator generator, Environment env) {
        target.generate(generator, env);
        for (Expression expr: arguments) {
            expr.generate(generator, env);
        }
        generator.instGen.invokevirtual(
                Naming.className(className),
                func, Naming.methodDescriptor(parameterTypes, returnType));
    }
}
