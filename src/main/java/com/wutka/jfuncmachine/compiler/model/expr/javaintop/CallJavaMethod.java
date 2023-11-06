package com.wutka.jfuncmachine.compiler.model.expr.javaintop;

import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.classgen.InstructionGenerator;
import com.wutka.jfuncmachine.compiler.classgen.Naming;
import com.wutka.jfuncmachine.compiler.model.expr.Expression;
import com.wutka.jfuncmachine.compiler.model.types.SimpleTypes;
import com.wutka.jfuncmachine.compiler.model.types.Type;

public class CallJavaMethod extends Expression {
    public final String className;
    public final String methodName;
    public Type[] parameterTypes;
    public final Expression target;
    public final Expression[] arguments;
    public final Type returnType;

    public CallJavaMethod(String className, String methodName, Expression target, Expression[] arguments,
                          Type returnType) {
        super(null, 0);
        this.className = className;
        this.methodName = methodName;
        this.parameterTypes = new Type[arguments.length];
        for (int i=0; i < parameterTypes.length; i++) parameterTypes[i] = arguments[i].getType();
        this.target = target;
        this.arguments = arguments;
        this.returnType = returnType;
    }

    public CallJavaMethod(String className, String methodName, Expression target, Expression[] arguments,
                          Type returnType,
                          String filename, int lineNumber) {
        super(filename, lineNumber);
        this.className = className;
        this.methodName = methodName;
        this.parameterTypes = new Type[arguments.length];
        for (int i=0; i < parameterTypes.length; i++) parameterTypes[i] = arguments[i].getType();
        this.target = target;
        this.arguments = arguments;
        this.returnType = returnType;
    }

    public CallJavaMethod(String className, String methodName, Type[] parameterTypes,
                          Expression target, Expression[] arguments,
                          Type returnType) {
        super(null, 0);
        this.className = className;
        this.methodName = methodName;
        this.parameterTypes = parameterTypes;
        this.target = target;
        this.arguments = arguments;
        this.returnType = returnType;
    }

    public CallJavaMethod(String className, String methodName, Type[] parameterTypes,
                          Expression target, Expression[] arguments,
                          Type returnType,
                          String filename, int lineNumber) {
        super(filename, lineNumber);
        this.className = className;
        this.methodName = methodName;
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
    public void generate(InstructionGenerator generator, Environment env) {
        target.generate(generator, env);
        for (Expression expr: arguments) {
            expr.generate(generator, env);
        }
        generator.invokevirtual(
                Naming.className(className),
                methodName, Naming.methodDescriptor(parameterTypes, returnType));
    }
}
