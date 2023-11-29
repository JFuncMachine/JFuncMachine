package org.jfuncmachine.jfuncmachine.compiler.model;

import org.jfuncmachine.jfuncmachine.compiler.model.expr.Expression;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.GetValue;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.javainterop.CallJavaSuperConstructor;
import org.jfuncmachine.jfuncmachine.compiler.model.types.Field;
import org.jfuncmachine.jfuncmachine.compiler.model.types.ObjectType;
import org.jfuncmachine.jfuncmachine.compiler.model.types.SimpleTypes;

public class ConstructorDef extends MethodDef {
    public ConstructorDef(int access, Field[] parameters, Expression body) {
        super("<init>", access, parameters, SimpleTypes.UNIT, body);
    }

    public ConstructorDef(int access, Field[] parameters, Expression body,
                          String filename, int lineNumber) {
        super("<init>", access, parameters, SimpleTypes.UNIT, body, filename, lineNumber);
    }

    public static ConstructorDef generateWith(Field[] parameters) {
        Expression target = new GetValue("this", new ObjectType());
        Expression[] arguments = new Expression[parameters.length];
        for (int i=0; i < arguments.length; i++) {
            arguments[i] = new GetValue(parameters[i].name, parameters[i].type);
        }
        return new ConstructorDef(Access.PUBLIC, parameters,
                new CallJavaSuperConstructor(target, arguments));
    }

    public static ConstructorDef generateWith(Field[] parameters,
                                              String filename, int lineNumber) {
        Expression target = new GetValue("this", new ObjectType());
        Expression[] arguments = new Expression[parameters.length];
        for (int i=0; i < arguments.length; i++) {
            arguments[i] = new GetValue(parameters[i].name, parameters[i].type);
        }
        return new ConstructorDef(Access.PUBLIC, parameters,
                new CallJavaSuperConstructor(target, arguments),
                filename, lineNumber);
    }
}