package com.wutka.jfuncmachine.compiler.model.expr.javaintop;

import com.wutka.jfuncmachine.compiler.classgen.ClassGenerator;
import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.classgen.Naming;
import com.wutka.jfuncmachine.compiler.model.expr.Expression;
import com.wutka.jfuncmachine.compiler.model.types.FunctionType;
import com.wutka.jfuncmachine.compiler.model.types.Type;

public class CallJavaStaticMethod extends Expression {
    public final String className;
    public final String methodName;
    public final Type[] parameterTypes;
    public final Expression[] arguments;
    public final Type returnType;

    public CallJavaStaticMethod(String className, String methodName, Type[] parameterTypes, Expression[] arguments, Type returnType) {
        super(null, 0);
        this.className = className;
        this.methodName = methodName;
        this.parameterTypes = parameterTypes;
        this.arguments = arguments;
        this.returnType = returnType;
    }

    public CallJavaStaticMethod(String className, String methodName, Type[] parameterTypes, Expression[] arguments, Type returnType,
                                String filename, int lineNumber) {
        super(filename, lineNumber);
        this.className = className;
        this.methodName = methodName;
        this.parameterTypes = parameterTypes;
        this.arguments = arguments;
        this.returnType = returnType;
    }

    public CallJavaStaticMethod(String className, String methodName, Expression[] arguments, Type returnType) {
        super(null, 0);
        this.className = className;
        this.methodName = methodName;
        this.parameterTypes = new Type[arguments.length];
        for (int i=0; i < arguments.length; i++) this.parameterTypes[i] = arguments[i].getType();
        this.arguments = arguments;
        this.returnType = returnType;
    }

    public CallJavaStaticMethod(String className, String methodName, Expression[] arguments, Type returnType,
                                String filename, int lineNumber) {
        super(filename, lineNumber);
        this.className = className;
        this.methodName = methodName;
        this.parameterTypes = new Type[arguments.length];
        for (int i=0; i < arguments.length; i++) this.parameterTypes[i] = arguments[i].getType();
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
    }

    @Override
    public void generate(ClassGenerator generator, Environment env) {
        for (Expression expr: arguments) {
            expr.generate(generator, env);
        }
        generator.instGen.invokestatic(
                Naming.className(className),
                methodName, generator.methodDescriptor(parameterTypes, returnType));
    }
}
