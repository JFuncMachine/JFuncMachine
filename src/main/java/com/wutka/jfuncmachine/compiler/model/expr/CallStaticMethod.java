package com.wutka.jfuncmachine.compiler.model.expr;

import com.wutka.jfuncmachine.compiler.classgen.ClassGenerator;
import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.model.types.Type;

public class CallStaticMethod extends Expression {
    public final String className;
    public final String func;
    public final Expression[] arguments;
    public final Type[] parameterTypes;
    public final Type returnType;

    public CallStaticMethod(String className, String func, Type[] parameterTypes, Type returnType,
                            Expression[] arguments) {
        super(null, 0);
        this.className = className;
        this.func = func;
        this.arguments = arguments;
        this.parameterTypes = parameterTypes;
        this.returnType = returnType;
    }

    public CallStaticMethod(String className, String func, Type[] parameterTypes, Type returnType,
                            Expression[] arguments,
                            String filename, int lineNumber) {
        super(filename, lineNumber);
        this.className = className;
        this.func = func;
        this.arguments = arguments;
        this.parameterTypes = parameterTypes;
        this.returnType = returnType;
    }

    public CallStaticMethod(String func, Type[] parameterTypes, Type returnType,
                            Expression[] arguments) {
        super(null, 0);
        this.className = null;
        this.func = func;
        this.arguments = arguments;
        this.parameterTypes = parameterTypes;
        this.returnType = returnType;
    }

    public CallStaticMethod(String func, Type[] parameterTypes, Type returnType,
                            Expression[] arguments,
                            String filename, int lineNumber) {
        super(filename, lineNumber);
        this.className = null;
        this.func = func;
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
    }

    @Override
    public void generate(ClassGenerator generator, Environment env) {
        String invokeClassName = className;
        if (invokeClassName == null) {
            invokeClassName = generator.currentClass.getFullClassName();
        }
        for (Expression expr: arguments) {
            expr.generate(generator, env);
        }
        generator.instGen.invokestatic(
                generator.className(invokeClassName),
                func, generator.methodDescriptor(parameterTypes, returnType));
    }
}
