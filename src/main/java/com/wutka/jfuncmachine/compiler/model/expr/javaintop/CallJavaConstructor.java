package com.wutka.jfuncmachine.compiler.model.expr.javaintop;

import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.classgen.InstructionGenerator;
import com.wutka.jfuncmachine.compiler.classgen.Naming;
import com.wutka.jfuncmachine.compiler.model.expr.Expression;
import com.wutka.jfuncmachine.compiler.model.types.ObjectType;
import com.wutka.jfuncmachine.compiler.model.types.SimpleTypes;
import com.wutka.jfuncmachine.compiler.model.types.Type;

public class CallJavaConstructor extends Expression {
    public final String className;
    public final Type[] parameterTypes;
    public final Expression[] arguments;

    public CallJavaConstructor(String className, Expression[] arguments) {
        super(null, 0);
        this.className = className;
        this.parameterTypes = new Type[arguments.length];
        for (int i=0; i < parameterTypes.length; i++) parameterTypes[i] = arguments[i].getType();
        this.arguments = arguments;
    }

    public CallJavaConstructor(String className, Expression[] arguments, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.className = className;
        this.parameterTypes = new Type[arguments.length];
        for (int i=0; i < parameterTypes.length; i++) parameterTypes[i] = arguments[i].getType();
        this.arguments = arguments;
    }

    public CallJavaConstructor(String className, Type[] parameterTypes, Expression[] arguments) {
        super(null, 0);
        this.className = className;
        if (parameterTypes.length != arguments.length) {
            throw generateException(String.format("Number of parameter types (%d) does not match number of arguments (%d",
                    parameterTypes.length, arguments.length));
        }
        this.parameterTypes = parameterTypes;
        this.arguments = arguments;
    }

    public CallJavaConstructor(String className, Type[] parameterTypes, Expression[] arguments, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.className = className;
        if (parameterTypes.length != arguments.length) {
            throw generateException(String.format("Number of parameter types (%d) does not match number of arguments (%d",
                    parameterTypes.length, arguments.length));
        }
        this.parameterTypes = parameterTypes;
        this.arguments = arguments;
    }

    public Type getType() {
        return new ObjectType(className);
    }

    public void findCaptured(Environment env) {
        for (Expression expr: arguments) {
            expr.findCaptured(env);
        }
    }

    @Override
    public void generate(InstructionGenerator generator, Environment env) {
        generator.new_object(Naming.className(className));
        generator.dup();
        for (Expression expr: arguments) {
            expr.generate(generator, env);
        }
        generator.invokespecial(Naming.className(className),
                "<init>", Naming.methodDescriptor(parameterTypes, SimpleTypes.UNIT));
    }
}
