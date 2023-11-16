package com.wutka.jfuncmachine.compiler.model.expr.javaintop;

import com.wutka.jfuncmachine.compiler.classgen.ClassGenerator;
import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.model.expr.Expression;
import com.wutka.jfuncmachine.compiler.model.expr.boxing.Autobox;
import com.wutka.jfuncmachine.compiler.model.types.Type;

public class CallJavaInterface extends Expression {
    public final String interfaceName;
    public final String methodName;
    public final Expression target;
    public final Expression[] arguments;
    public final Type[] parameterTypes;
    public final Type returnType;

    public CallJavaInterface(String interfaceName, String methodName, Expression target, Expression[] arguments,
                             Type returnType) {
        super(null, 0);
        this.interfaceName = interfaceName;
        this.methodName = methodName;
        this.target = target;
        this.parameterTypes = new Type[arguments.length];
        for (int i=0; i < parameterTypes.length; i++) parameterTypes[i] = arguments[i].getType();
        this.returnType = returnType;
        this.arguments = arguments;
    }

    public CallJavaInterface(String interfaceName, String methodName, Expression target, Type[] parameterTypes,
                             Expression[] arguments, Type returnType) {
        super(null, 0);
        this.interfaceName = interfaceName;
        this.methodName = methodName;
        this.target = target;
        this.parameterTypes = parameterTypes;
        this.returnType = returnType;
        this.arguments = arguments;
    }

    public CallJavaInterface(String interfaceName, String methodName, Expression target, Expression[] arguments,
                             Type returnType, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.interfaceName = interfaceName;
        this.methodName = methodName;
        this.target = target;
        this.parameterTypes = new Type[arguments.length];
        for (int i=0; i < parameterTypes.length; i++) parameterTypes[i] = arguments[i].getType();
        this.returnType = returnType;
        this.arguments = arguments;
    }

    public CallJavaInterface(String interfaceName, String methodName, Expression target, Type[] parameterTypes,
                             Expression[] arguments, Type returnType, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.interfaceName = interfaceName;
        this.methodName = methodName;
        this.target = target;
        this.parameterTypes = parameterTypes;
        this.returnType = returnType;
        this.arguments = arguments;
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
        generator.instGen.invokeinterface(
                generator.className(interfaceName),
                methodName, generator.methodDescriptor(parameterTypes, returnType));
    }
}
