package com.wutka.jfuncmachine.compiler.model.expr;

import com.wutka.jfuncmachine.compiler.classgen.ClassGenerator;
import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.model.expr.boxing.Autobox;
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

    public CallMethod(String func, Type[] parameterTypes, Type returnType,
                      Expression target, Expression[] arguments) {
        super(null, 0);
        this.className = null;
        this.func = func;
        this.target = target;
        this.arguments = arguments;
        this.parameterTypes = parameterTypes;
        this.returnType = returnType;
    }

    public CallMethod(String func, Type[] parameterTypes, Type returnType,
                      Expression target, Expression[] arguments,
                      String filename, int lineNumber) {
        super(filename, lineNumber);
        this.className = null;
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
        String invokeClassName = className;
        if (invokeClassName == null) {
            invokeClassName = generator.currentClass.getFullClassName();
        }
        target.generate(generator, env);
        for (int i=0; i < arguments.length; i++) {
            Expression expr = arguments[i];
            if (generator.options.autobox) {
                expr = Autobox.autobox(expr, parameterTypes[i]);
            }
            expr.generate(generator, env);
        }
        generator.instGen.invokevirtual(
                generator.className(invokeClassName),
                func, generator.methodDescriptor(parameterTypes, returnType));
    }
}
