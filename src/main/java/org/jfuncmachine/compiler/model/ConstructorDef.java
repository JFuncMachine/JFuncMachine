package org.jfuncmachine.compiler.model;

import org.jfuncmachine.compiler.model.expr.Expression;
import org.jfuncmachine.compiler.model.expr.GetValue;
import org.jfuncmachine.compiler.model.expr.javainterop.CallJavaSuperConstructor;
import org.jfuncmachine.compiler.model.types.Field;
import org.jfuncmachine.compiler.model.types.ObjectType;
import org.jfuncmachine.compiler.model.types.SimpleTypes;

/** A special version of MethodDef for defining constructors */
public class ConstructorDef extends MethodDef {
    /** Create a constructor definition
     *
     * @param access The access flags for the constructor
     * @param parameters The name and type for each parameter
     * @param body The code to execute for this constructor
     */
    public ConstructorDef(int access, Field[] parameters, Expression body) {
        super("<init>", access, parameters, SimpleTypes.UNIT, body);
    }

    /** Create a constructor definition
     *
     * @param access The access flags for the constructor
     * @param parameters The name and type for each parameter
     * @param body The code to execute for this constructor
     * @param filename The name of the file where this constructor is defined
     * @param lineNumber The line number in the source file where this constructor is defined
     */
    public ConstructorDef(int access, Field[] parameters, Expression body,
                          String filename, int lineNumber) {
        super("<init>", access, parameters, SimpleTypes.UNIT, body, filename, lineNumber);
    }

    /** Generate a default constructor that just passes its arguments to the superclass constructor
     * @param parameters The name and type for each parameter
     * @return A default constructor that only calls the superclass constructor
     */
    public static ConstructorDef generateWith(Field[] parameters) {
        Expression target = new GetValue("this", new ObjectType());
        Expression[] arguments = new Expression[parameters.length];
        for (int i=0; i < arguments.length; i++) {
            arguments[i] = new GetValue(parameters[i].name, parameters[i].type);
        }
        return new ConstructorDef(Access.PUBLIC, parameters,
                new CallJavaSuperConstructor(target, arguments));
    }

    /** Generate a default constructor that just passes its arguments to the superclass constructor
     * @param parameters The name and type for each parameter
     * @param filename The name of the file where this constructor is defined
     * @param lineNumber The line number in the source file where this constructor is defined
     * @return A default constructor that only calls the superclass constructor
     */
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