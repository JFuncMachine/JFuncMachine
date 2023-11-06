package com.wutka.jfuncmachine.compiler.model.expr;

import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.classgen.InstructionGenerator;
import com.wutka.jfuncmachine.compiler.classgen.Naming;
import com.wutka.jfuncmachine.compiler.model.Class;
import com.wutka.jfuncmachine.compiler.model.Method;
import com.wutka.jfuncmachine.compiler.model.types.Type;

public class CallStaticMethod extends Expression {
    public final Class clazz;
    public final Method func;
    public final Expression[] arguments;
    public final Type returnType;

    public CallStaticMethod(Class clazz, Method func, Expression[] arguments, Type returnType) {
        super(null, 0);
        this.clazz = clazz;
        this.func = func;
        this.arguments = arguments;
        this.returnType = returnType;
    }

    public CallStaticMethod(Class clazz, Method func, Expression[] arguments, Type returnType,
                            String filename, int lineNumber) {
        super(filename, lineNumber);
        this.clazz = clazz;
        this.func = func;
        this.arguments = arguments;
        this.returnType = returnType;
    }

    public Type getType() {
        return func.getReturnType();
    }

    @Override
    public void generate(InstructionGenerator generator, Environment env) {
        for (Expression expr: arguments) {
            expr.generate(generator, env);
        }
        Type[] parameterTypes = new Type[func.parameters.length];
        for (int i=0; i < parameterTypes.length; i++) parameterTypes[i] = func.parameters[i].type;
        generator.invokestatic(
                Naming.className(clazz),
                func.name, Naming.methodDescriptor(parameterTypes, returnType));
    }
}
