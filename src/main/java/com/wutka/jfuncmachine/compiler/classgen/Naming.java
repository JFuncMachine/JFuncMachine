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

    public static String typeDescriptor(Type t) {
        return switch (t) {
            case ArrayType a -> "[" + typeDescriptor(a.containedType());
            case BooleanType b -> "Z";
            case ByteType b -> "B";
            case CharType c -> "C";
            case DoubleType d -> "D";
            case FloatType f -> "F";
            case IntType i -> "I";
            case ObjectType j -> "L"+j.className.replace('.', '/')+";";
            case LongType l -> "J";
            case ShortType s -> "S";
            case StringType s -> "Ljava/lang/String;";
            case UnitType u -> "V";
            case FunctionType f -> throw new JFuncMachineException("FunctionType isn't supported yet");
        };
    }
    public static String methodDescriptor(Method method) {
        StringBuilder builder = new StringBuilder("(");
        for (Field f: method.parameters) {
            builder.append(typeDescriptor(f.type()));
        }
        builder.append(")");
        builder.append(typeDescriptor(method.getReturnType()));
        return builder.toString();
    }

    public static String methodDescriptor(Expression[] arguments, Type returnType) {
        StringBuilder builder = new StringBuilder("(");
        for (Expression expr: arguments) {
            builder.append(typeDescriptor(expr.getType()));
        }
        builder.append(")");
        builder.append(typeDescriptor(returnType));
        return builder.toString();
    }

    public static String methodDescriptor(Type[] parameterTypes, Type returnType) {
        StringBuilder builder = new StringBuilder("(");
        for (Type type: parameterTypes) {
            builder.append(typeDescriptor(type));
        }
        builder.append(")");
        builder.append(typeDescriptor(returnType));
        return builder.toString();
    }
}
