package com.wutka.jfuncmachine.compiler.classgen;

import com.wutka.jfuncmachine.compiler.exceptions.JFuncMachineException;
import com.wutka.jfuncmachine.compiler.model.Class;
import com.wutka.jfuncmachine.compiler.model.Method;
import com.wutka.jfuncmachine.compiler.model.expr.Expression;
import com.wutka.jfuncmachine.compiler.model.types.*;

public class Naming {
    public static String className(Class clazz) {
        return className(clazz.packageName, clazz.name);
    }

    public static String className(String packageName, String name) {
        return packageName.replace('.', '/')+"/"+name;
    }

    public static String className(String name) {
        return name.replace('.', '/');
    }

    public static String classSignature(Class clazz) {
        return "L"+className(clazz)+";";
    }

    public static String methodDescriptor(Method method) {
        StringBuilder builder = new StringBuilder("(");
        for (Field f: method.parameters) {
            builder.append(f.type.getTypeDescriptor());
        }
        builder.append(")");
        builder.append(method.getReturnType().getTypeDescriptor());
        return builder.toString();
    }

    public static String methodDescriptor(Expression[] arguments, Type returnType) {
        StringBuilder builder = new StringBuilder("(");
        for (Expression expr: arguments) {
            builder.append(expr.getType().getTypeDescriptor());
        }
        builder.append(")");
        builder.append(returnType.getTypeDescriptor());
        return builder.toString();
    }

    public static String methodDescriptor(Type[] parameterTypes, Type returnType) {
        StringBuilder builder = new StringBuilder("(");
        for (Type type: parameterTypes) {
            builder.append(type.getTypeDescriptor());
        }
        builder.append(")");
        builder.append(returnType.getTypeDescriptor());
        return builder.toString();
    }

    public static String lambdaMethodDescriptor(Type[] capturedParameterTypes, Type[] parameterTypes,
                                                Type returnType) {
        StringBuilder builder = new StringBuilder("(");
        for (Type type: capturedParameterTypes) {
            builder.append(type.getTypeDescriptor());
        }
        for (Type type: parameterTypes) {
            builder.append(type.getTypeDescriptor());
        }
        builder.append(")");
        builder.append(returnType.getTypeDescriptor());
        return builder.toString();
    }
}
