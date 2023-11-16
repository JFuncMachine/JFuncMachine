package com.wutka.jfuncmachine.compiler.model.expr.javaintop;

import com.wutka.jfuncmachine.compiler.classgen.ClassGenerator;
import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.model.expr.Expression;
import com.wutka.jfuncmachine.compiler.model.expr.boxing.Autobox;
import com.wutka.jfuncmachine.compiler.model.types.Type;

public class CallJavaSuperMethod extends Expression {
    public final String className;
    public Type[] parameterTypes;
    public final Expression target;
    public final Expression[] arguments;
    public final Type returnType;

    public CallJavaSuperMethod(String className, Expression target, Expression[] arguments,
                               Type returnType) {
        super(null, 0);
        this.className = className;
        this.parameterTypes = new Type[arguments.length];
        for (int i=0; i < parameterTypes.length; i++) parameterTypes[i] = arguments[i].getType();
        this.target = target;
        this.arguments = arguments;
        this.returnType = returnType;
    }

    public CallJavaSuperMethod(String className, Expression target, Expression[] arguments,
                               Type returnType,
                               String filename, int lineNumber) {
        super(filename, lineNumber);
        this.className = className;
        this.parameterTypes = new Type[arguments.length];
        for (int i=0; i < parameterTypes.length; i++) parameterTypes[i] = arguments[i].getType();
        this.target = target;
        this.arguments = arguments;
        this.returnType = returnType;
    }

    public CallJavaSuperMethod(String className, Type[] parameterTypes,
                               Expression target, Expression[] arguments,
                               Type returnType) {
        super(null, 0);
        this.className = className;
        this.parameterTypes = parameterTypes;
        this.target = target;
        this.arguments = arguments;
        this.returnType = returnType;
    }

    public CallJavaSuperMethod(String className, Type[] parameterTypes,
                               Expression target, Expression[] arguments,
                               Type returnType,
                               String filename, int lineNumber) {
        super(filename, lineNumber);
        this.className = className;
        this.parameterTypes = parameterTypes;
        this.target = target;
        this.arguments = arguments;
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
        for (int i=0; i < arguments.length; i++) {
            Expression expr = arguments[i];
            if (generator.options.autobox) {
                expr = Autobox.autobox(expr, parameterTypes[i]);
            }
            expr.generate(generator, env);
        }
        generator.instGen.invokespecial(
                generator.className(className),
                "super", generator.methodDescriptor(parameterTypes, returnType));
    }
}
