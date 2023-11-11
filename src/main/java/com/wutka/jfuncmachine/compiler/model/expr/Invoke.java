package com.wutka.jfuncmachine.compiler.model.expr;

import com.wutka.jfuncmachine.compiler.classgen.ClassGenerator;
import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.classgen.LambdaIntInfo;
import com.wutka.jfuncmachine.compiler.model.types.FunctionType;
import com.wutka.jfuncmachine.compiler.model.types.ObjectType;
import com.wutka.jfuncmachine.compiler.model.types.Type;

public class Invoke extends Expression {
    public final Type targetType;
    public final String intMethod;
    public final Expression target;
    public final Expression[] arguments;
    public final Type[] parameterTypes;
    public final Type returnType;

    public Invoke(FunctionType targetType, Expression target, Expression[] arguments) {
        super(null, 0);
        this.target = target;
        this.arguments = arguments;
        this.targetType = targetType;
        this.parameterTypes = targetType.parameterTypes;
        this.returnType = targetType.returnType;
        this.intMethod = ClassGenerator.lambdaIntMethodName;
    }

    public Invoke(FunctionType targetType, Expression target, Expression[] arguments,
                  String filename, int lineNumber) {
        super(filename, lineNumber);
        this.target = target;
        this.arguments = arguments;
        this.targetType = targetType;
        this.parameterTypes = targetType.parameterTypes;
        this.returnType = targetType.returnType;
        this.intMethod = ClassGenerator.lambdaIntMethodName;
    }

    public Invoke(Expression target, Expression[] arguments) {
        super(null, 0);
        this.target = target;
        this.arguments = arguments;
        if (!(target.getType() instanceof FunctionType)) {
            throw generateException(String.format(
                    "Can't determine Invoke type because target type (%s) is not type FunctionType",
                    target.getType()));
        }
        this.targetType = target.getType();
        this.parameterTypes = ((FunctionType) targetType).parameterTypes;
        this.returnType = ((FunctionType) targetType).returnType;
        this.intMethod = ClassGenerator.lambdaIntMethodName;
    }

    public Invoke(Expression target, Expression[] arguments,
                  String filename, int lineNumber) {
        super(filename, lineNumber);
        this.target = target;
        this.arguments = arguments;
        if (!(target.getType() instanceof FunctionType)) {
            throw generateException(String.format(
                    "Can't determine Invoke type because target type (%s) is not type FunctionType",
                    target.getType()));
        }
        this.targetType = target.getType();
        this.parameterTypes = ((FunctionType) targetType).parameterTypes;
        this.returnType = ((FunctionType) targetType).returnType;
        this.intMethod = ClassGenerator.lambdaIntMethodName;
    }

    public Invoke(String intMethod, Type targetType, Type[] parameterTypes, Type returnType,
                  Expression target, Expression[] arguments) {
        super(null, 0);
        this.target = target;
        this.arguments = arguments;
        this.targetType = targetType;
        this.parameterTypes = parameterTypes;
        this.returnType = returnType;
        this.intMethod = intMethod;
    }

    public Invoke(String intMethod, Type targetType, Type[] parameterTypes, Type returnType,
                  Expression target, Expression[] arguments,
                  String filename, int lineNumber) {
        super(filename, lineNumber);
        this.target = target;
        this.arguments = arguments;
        this.targetType = targetType;
        this.parameterTypes = parameterTypes;
        this.returnType = returnType;
        this.intMethod = intMethod;
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

        String className;

        if (targetType instanceof FunctionType) {
            LambdaIntInfo intInfo = generator.allocateLambdaInt((FunctionType) targetType);
            className = intInfo.packageName + "." + intInfo.name;
        } else if (targetType instanceof ObjectType) {
            className = ((ObjectType) targetType).className;
        } else {
            throw generateException(String.format("Invalid target type for invoke: %s", targetType));
        }

        generator.instGen.invokeinterface(
                generator.className(className),
                intMethod, generator.methodDescriptor(parameterTypes, returnType));
    }
}
