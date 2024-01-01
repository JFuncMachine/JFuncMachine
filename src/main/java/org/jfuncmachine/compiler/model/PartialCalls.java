package org.jfuncmachine.compiler.model;

import org.jfuncmachine.compiler.model.expr.*;
import org.jfuncmachine.compiler.model.expr.javainterop.CallJavaConstructor;
import org.jfuncmachine.compiler.model.expr.javainterop.CallJavaInterface;
import org.jfuncmachine.compiler.model.expr.javainterop.CallJavaMethod;
import org.jfuncmachine.compiler.model.expr.javainterop.CallJavaStaticMethod;
import org.jfuncmachine.compiler.model.types.Field;
import org.jfuncmachine.compiler.model.types.FunctionType;
import org.jfuncmachine.compiler.model.types.ObjectType;
import org.jfuncmachine.compiler.model.types.Type;

/** Utilities for creating pointers/references to methods, including constructors and interface methods */
public class PartialCalls {
    /** Create a partial call of a Java constructor
     *
     * @param className The name of the class to create
     * @param parameterTypes The constructor parameters
     * @param arguments The partial arguments to the constructor
     * @return A lambda that invokes the constructor
     */
    public static Expression makePartialJavaConstructorCall(String className, Type[] parameterTypes,
                                                            Expression[] arguments) {
        return new PartialCall(makeFields(parameterTypes), new ObjectType(className), arguments,
                new CallJavaConstructor(className, makeArgs(parameterTypes)));
    }

    /** Create a partial call of a Java constructor
     *
     * @param className The name of the class to create
     * @param parameterTypes The constructor parameters
     * @param arguments The partial arguments to the constructor
     * @param filename The source file where this call is created
     * @param lineNumber The line number in the source file where this call is created
     * @return A lambda that invokes the constructor
     */
    public static Expression makePartialJavaConstructorCall(String className, Type[] parameterTypes,
                                                    Expression[] arguments,
                                                    String filename, int lineNumber) {
        return new PartialCall(makeFields(parameterTypes), new ObjectType(className), arguments,
                new CallJavaConstructor(className, makeArgs(parameterTypes)), filename, lineNumber);
    }

    /** Create a partial call of a Java interface method
     *
     * @param interfaceName The name of the Java interface containing the method
     * @param methodName The name of the method to point to
     * @param parameterTypes The types of the method parameters
     * @param returnType The method return type
     * @param target The object this method should be invoked on
     * @param arguments The partial arguments to the method
     * @return A lambda that invokes the interface method
     */
    public static Expression makePartialJavaInterfaceMethodCall(String interfaceName, String methodName,
                                                  Type[] parameterTypes, Type returnType,
                                                  Expression target, Expression[] arguments) {
        return new PartialCall(makeFields(parameterTypes), returnType, arguments,
                new CallJavaInterface(interfaceName, methodName, parameterTypes, returnType,
                        target, makeArgs(parameterTypes)));
    }

    /** Create a partial call of a Java interface method
     *
     * @param interfaceName The name of the Java interface containing the method
     * @param methodName The name of the method to point to
     * @param parameterTypes The types of the method parameters
     * @param returnType The method return type
     * @param target The object this method should be invoked on
     * @param arguments The partial arguments to the method
     * @param filename The source file where this call is created
     * @param lineNumber The line number in the source file where this call is created
     * @return A lambda that invokes the interface method
     */
    public static Expression makePartialJavaInterfaceMethodCall(String interfaceName, String methodName,
                                                  Type[] parameterTypes, Type returnType,
                                                  Expression target, Expression[] arguments,
                                                  String filename, int lineNumber) {
        return new PartialCall(makeFields(parameterTypes), returnType, arguments,
                new CallJavaInterface(interfaceName, methodName, parameterTypes, returnType,
                    target, makeArgs(parameterTypes)), filename, lineNumber);
    }

    /** Create a partial call of a Java interface method that requires the target
     * object to be passed in as the first parameter
     *
     * @param interfaceName The name of the Java interface containing the method
     * @param methodName The name of the method to point to
     * @param parameterTypes The types of the method parameters
     * @param returnType The method return type
     * @param arguments The partial arguments to the method
     * @return A lambda that invokes the interface method
     */
    public static Expression makePartialJavaInterfaceMethodCall(String interfaceName, String methodName,
                                                                Type[] parameterTypes, Type returnType,
                                                                Expression[] arguments) {
        return new PartialCall(makeFields(new ObjectType(interfaceName), parameterTypes, arguments.length),
                returnType, arguments,
                new CallJavaInterface(interfaceName, methodName, parameterTypes, returnType,
                        new GetValue("$target", new ObjectType(interfaceName)),
                        makeArgs(parameterTypes)));
    }

    /** Create a partial call of a Java interface method
     *
     * @param interfaceName The name of the Java interface containing the method
     * @param methodName The name of the method to point to
     * @param parameterTypes The types of the method parameters
     * @param returnType The method return type
     * @param arguments The partial arguments to the method
     * @param filename The source file where this call is created
     * @param lineNumber The line number in the source file where this call is created
     * @return A lambda that invokes the interface method
     */
    public static Expression makePartialJavaInterfaceMethodCall(String interfaceName, String methodName,
                                                                Type[] parameterTypes, Type returnType,
                                                                Expression[] arguments,
                                                                String filename, int lineNumber) {
        return new PartialCall(makeFields(new ObjectType(interfaceName), parameterTypes, arguments.length),
                returnType, arguments,
                new CallJavaInterface(interfaceName, methodName, parameterTypes, returnType,
                        new GetValue("$target", new ObjectType(interfaceName)),
                        makeArgs(parameterTypes)), filename, lineNumber);
    }

    /** Create a partial call of a Java method
     *
     * @param className The name of the Java class containing the method
     * @param methodName The name of the method to point to
     * @param parameterTypes The types of the method parameters
     * @param returnType The method return type
     * @param target The object this method should be invoked on
     * @param arguments The partial arguments to the method
     * @return A lambda that invokes the interface method
     */
    public static Expression makePartialJavaMethodCall(String className, String methodName,
                                               Type[] parameterTypes, Type returnType,
                                               Expression target, Expression[] arguments) {
        return new PartialCall(makeFields(parameterTypes), returnType, arguments,
                new CallJavaMethod(className, methodName, parameterTypes, returnType,
                        target, makeArgs(parameterTypes)));
    }

    /** Create a partial call of a Java method
     *
     * @param className The name of the Java class containing the method
     * @param methodName The name of the method to point to
     * @param parameterTypes The types of the method parameters
     * @param returnType The method return type
     * @param target The object this method should be invoked on
     * @param arguments The partial arguments to the method
     * @param filename The source file where this call is created
     * @param lineNumber The line number in the source file where this call is created
     * @return A lambda that invokes the interface method
     */
    public static Expression makePartialJavaMethodCall(String className, String methodName,
                                               Type[] parameterTypes, Type returnType,
                                               Expression target, Expression[] arguments,
                                               String filename, int lineNumber) {
        return new PartialCall(makeFields(parameterTypes), returnType, arguments,
                new CallJavaMethod(className, methodName, parameterTypes, returnType,
                        target, makeArgs(parameterTypes)), filename, lineNumber);
    }

    /** Create a partial call of a Java method that requires the object to be passed as the first argument
     *
     * @param className The name of the Java class containing the method
     * @param methodName The name of the method to point to
     * @param parameterTypes The types of the method parameters
     * @param returnType The method return type
     * @param arguments The partial arguments to the method
     * @return A lambda that invokes the interface method
     */
    public static Expression makePartialJavaMethodCall(String className, String methodName,
                                                       Type[] parameterTypes, Type returnType,
                                                       Expression[] arguments) {
        return new PartialCall(makeFields(new ObjectType(className), parameterTypes, arguments.length),
                returnType, arguments,
                new CallJavaMethod(className, methodName, parameterTypes, returnType,
                        new GetValue("$target", new ObjectType(className)), makeArgs(parameterTypes)));
    }

    /** Create a partial call of a Java method that requires the object to be passed as the first argument
     *
     * @param className The name of the Java class containing the method
     * @param methodName The name of the method to point to
     * @param parameterTypes The types of the method parameters
     * @param returnType The method return type
     * @param arguments The partial arguments to the method
     * @param filename The source file where this call is created
     * @param lineNumber The line number in the source file where this call is created
     * @return A lambda that invokes the interface method
     */
    public static Expression makePartialJavaMethodCall(String className, String methodName,
                                                       Type[] parameterTypes, Type returnType,
                                                       Expression[] arguments,
                                                       String filename, int lineNumber) {
        return new PartialCall(makeFields(new ObjectType(className), parameterTypes, arguments.length),
                returnType, arguments,
                new CallJavaMethod(className, methodName, parameterTypes, returnType,
                        new GetValue("$target", new ObjectType(className)),
                        makeArgs(parameterTypes)), filename, lineNumber);
    }

    /** Create a partial call of a static Java method
     *
     * @param className The name of the Java class containing the method
     * @param methodName The name of the method to point to
     * @param parameterTypes The types of the method parameters
     * @param returnType The method return type
     * @param arguments The partial arguments to the method
     * @return A lambda that invokes the interface method
     */
    public static Expression makePartialJavaStaticMethodCall(String className, String methodName,
                                                     Type[] parameterTypes, Type returnType,
                                                     Expression[] arguments) {
        return new PartialCall(makeFields(parameterTypes), returnType, arguments,
                new CallJavaStaticMethod(className, methodName, parameterTypes, returnType,
                        makeArgs(parameterTypes)));
    }

    /** Create a partial call of a static Java method
     *
     * @param className The name of the Java class containing the method
     * @param methodName The name of the method to point to
     * @param parameterTypes The types of the method parameters
     * @param returnType The method return type
     * @param arguments The partial arguments to the method
     * @param filename The source file where this call is created
     * @param lineNumber The line number in the source file where this call is created
     * @return A lambda that invokes the interface method
     */
    public static Expression makePartialJavaStaticMethodCall(String className, String methodName,
                                               Type[] parameterTypes, Type returnType,
                                               Expression[] arguments,
                                               String filename, int lineNumber) {
        return new PartialCall(makeFields(parameterTypes), returnType, arguments,
                new CallJavaStaticMethod(className, methodName, parameterTypes, returnType,
                        makeArgs(parameterTypes)), filename, lineNumber);
    }

    /** Create a partial call of a method that may or may not do tail call optimization
     *
     * @param className The name of the class containing the method
     * @param methodName The name of the method to point to
     * @param parameterTypes The types of the method parameters
     * @param returnType The method return type
     * @param target The object this method should be invoked on
     * @param arguments The partial arguments to the method
     * @return A lambda that invokes the interface method
     */
    public static Expression makePartialMethodCall(String className, String methodName,
                                               Type[] parameterTypes, Type returnType,
                                               Expression target, Expression[] arguments) {
        return new PartialCall(makeFields(parameterTypes), returnType, arguments,
                new CallMethod(className, methodName, parameterTypes, returnType,
                        target, makeArgs(parameterTypes)));
    }

    /** Create a partial call of a method that may or may not do tail call optimization
     *
     * @param className The name of the class containing the method
     * @param methodName The name of the method to point to
     * @param parameterTypes The types of the method parameters
     * @param returnType The method return type
     * @param target The object this method should be invoked on
     * @param arguments The partial arguments to the method
     * @param filename The source file where this call is created
     * @param lineNumber The line number in the source file where this call is created
     * @return A lambda that invokes the interface method
     */
    public static Expression makePartialMethodCall(String className, String methodName,
                                               Type[] parameterTypes, Type returnType,
                                               Expression target, Expression[] arguments,
                                               String filename, int lineNumber) {
        return new PartialCall(makeFields(parameterTypes), returnType, arguments,
                new CallMethod(className, methodName, parameterTypes, returnType,
                        target, makeArgs(parameterTypes)), filename, lineNumber);
    }

    /** Create a partial call of a method that may or may not do tail call optimization
     *
     * @param className The name of the class containing the method
     * @param methodName The name of the method to point to
     * @param parameterTypes The types of the method parameters
     * @param returnType The method return type
     * @param arguments The partial arguments to the method
     * @return A lambda that invokes the interface method
     */
    public static Expression makePartialMethodCall(String className, String methodName,
                                                   Type[] parameterTypes, Type returnType,
                                                   Expression[] arguments) {
        return new PartialCall(makeFields(new ObjectType(className), parameterTypes, arguments.length),
                returnType, arguments,
                new CallMethod(className, methodName, parameterTypes, returnType,
                        new GetValue("$target", new ObjectType(className)), makeArgs(parameterTypes)));
    }

    /** Create a partial call of a method that may or may not do tail call optimization
     *
     * @param className The name of the class containing the method
     * @param methodName The name of the method to point to
     * @param parameterTypes The types of the method parameters
     * @param returnType The method return type
     * @param arguments The partial arguments to the method
     * @param filename The source file where this call is created
     * @param lineNumber The line number in the source file where this call is created
     * @return A lambda that invokes the interface method
     */
    public static Expression makePartialMethodCall(String className, String methodName,
                                                   Type[] parameterTypes, Type returnType,
                                                   Expression[] arguments,
                                                   String filename, int lineNumber) {
        return new PartialCall(makeFields(new ObjectType(className), parameterTypes, arguments.length),
                returnType, arguments,
                new CallMethod(className, methodName, parameterTypes, returnType,
                        new GetValue("$target", new ObjectType(className)),
                        makeArgs(parameterTypes)), filename, lineNumber);
    }

    /** Create a partial call of a static method that may or may not do tail call optimization
     *
     * @param className The name of the class containing the method
     * @param methodName The name of the method to point to
     * @param parameterTypes The types of the method parameters
     * @param returnType The method return type
     * @param arguments The partial arguments to the method
     * @return A lambda that invokes the interface method
     */
    public static Expression makePartialStaticMethodCall(String className, String methodName,
                                                     Type[] parameterTypes, Type returnType,
                                                 Expression[] arguments) {
        return new PartialCall(makeFields(parameterTypes), returnType, arguments,
                new CallStaticMethod(className, methodName, parameterTypes, returnType,
                        makeArgs(parameterTypes)));
    }

    /** Create a partial call of a static method that may or may not do tail call optimization
     *
     * @param className The name of the class containing the method
     * @param methodName The name of the method to point to
     * @param parameterTypes The types of the method parameters
     * @param returnType The method return type
     * @param arguments The partial arguments to the method
     * @param filename The source file where this call is created
     * @param lineNumber The line number in the source file where this call is created
     * @return A lambda that invokes the interface method
     */
    public static Expression makePartialStaticMethodCall(String className, String methodName,
                                                     Type[] parameterTypes, Type returnType,
                                                     Expression[] arguments,
                                                     String filename, int lineNumber) {
        return new PartialCall(makeFields(parameterTypes), returnType, arguments,
                new CallStaticMethod(className, methodName, parameterTypes, returnType,
                        makeArgs(parameterTypes)), filename, lineNumber);
    }

    /** Create a partial call of a method that uses tail call optimization
     *
     * @param className The name of the class containing the method
     * @param methodName The name of the method to point to
     * @param parameterTypes The types of the method parameters
     * @param returnType The method return type
     * @param target The object this method should be invoked on
     * @param arguments The partial arguments to the method
     * @return A lambda that invokes the interface method
     */
    public static Expression makePartialTailCallMethodCall(String className, String methodName,
                                           Type[] parameterTypes, Type returnType,
                                           Expression target, Expression[] arguments) {
        return new PartialCall(makeFields(parameterTypes), returnType, arguments,
                new CallTailCallMethod(className, methodName, parameterTypes, returnType,
                        target, makeArgs(parameterTypes)));
    }

    /** Create a partial call of a method that uses tail call optimization
     *
     * @param className The name of the class containing the method
     * @param methodName The name of the method to point to
     * @param parameterTypes The types of the method parameters
     * @param returnType The method return type
     * @param target The object this method should be invoked on
     * @param arguments The partial arguments to the method
     * @param filename The source file where this call is created
     * @param lineNumber The line number in the source file where this call is created
     * @return A lambda that invokes the interface method
     */
    public static Expression makePartialTailCallMethodCall(String className, String methodName,
                                           Type[] parameterTypes, Type returnType,
                                           Expression target, Expression[] arguments,
                                           String filename, int lineNumber) {
        return new PartialCall(makeFields(parameterTypes), returnType, arguments,
                new CallTailCallMethod(className, methodName, parameterTypes, returnType,
                        target, makeArgs(parameterTypes)), filename, lineNumber);
    }


    /** Create a partial call of a method that uses tail call optimization that requires the object
     * to be passed in as the first parameter
     *
     * @param className The name of the class containing the method
     * @param methodName The name of the method to point to
     * @param parameterTypes The types of the method parameters
     * @param returnType The method return type
     * @param arguments The partial arguments to the method
     * @return A lambda that invokes the interface method
     */
    public static Expression makePartialTailCallMethodCall(String className, String methodName,
                                                           Type[] parameterTypes, Type returnType,
                                                           Expression[] arguments) {
        return new PartialCall(makeFields(new ObjectType(className), parameterTypes, arguments.length),
                returnType, arguments,
                new CallTailCallMethod(className, methodName, parameterTypes, returnType,
                        new GetValue("$target", new ObjectType(className)), makeArgs(parameterTypes)));
    }

    /** Create a partial call of a method that uses tail call optimization
     *
     * @param className The name of the class containing the method
     * @param methodName The name of the method to point to
     * @param parameterTypes The types of the method parameters
     * @param returnType The method return type
     * @param arguments The partial arguments to the method
     * @param filename The source file where this call is created
     * @param lineNumber The line number in the source file where this call is created
     * @return A lambda that invokes the interface method
     */
    public static Expression makePartialTailCallMethodCall(String className, String methodName,
                                                           Type[] parameterTypes, Type returnType,
                                                           Expression[] arguments,
                                                           String filename, int lineNumber) {
        return new PartialCall(makeFields(new ObjectType(className), parameterTypes, arguments.length),
                returnType, arguments,
                new CallTailCallMethod(className, methodName, parameterTypes, returnType,
                        new GetValue("$target", new ObjectType(className)),
                        makeArgs(parameterTypes)), filename, lineNumber);
    }

    /** Create a partial call of a static method that uses tail call optimization
     *
     * @param className The name of the class containing the method
     * @param methodName The name of the method to point to
     * @param parameterTypes The types of the method parameters
     * @param returnType The method return type
     * @param arguments The partial arguments to the method
     * @return A lambda that invokes the interface method
     */
    public static Expression makePartialTailCallStaticMethodCall(String className, String methodName,
                                                 Type[] parameterTypes, Type returnType,
                                                         Expression[] arguments) {
        return new PartialCall(makeFields(parameterTypes), returnType, arguments,
                new CallTailCallStaticMethod(className, methodName, parameterTypes, returnType,
                        makeArgs(parameterTypes)));
    }

    /** Create a partial call of a static method that uses tail call optimization
     *
     * @param className The name of the class containing the method
     * @param methodName The name of the method to point to
     * @param parameterTypes The types of the method parameters
     * @param returnType The method return type
     * @param arguments The partial arguments to the method
     * @param filename The source file where this call is created
     * @param lineNumber The line number in the source file where this call is created
     * @return A lambda that invokes the interface method
     */
    public static Expression makePartialTailCallStaticMethodCall(String className, String methodName,
                                                 Type[] parameterTypes, Type returnType,
                                                 Expression[] arguments,
                                                 String filename, int lineNumber) {
        return new PartialCall(makeFields(parameterTypes), returnType, arguments,
                new CallTailCallStaticMethod(className, methodName, parameterTypes, returnType,
                        makeArgs(parameterTypes)), filename, lineNumber);
    }

    /** Create a partial call of a Java interface method
     *
     * @param funcType The type of the function pointer to invoke
     * @param target The function pointer to invoke
     * @param arguments The partial arguments to the method
     * @return A lambda that invokes the interface method
     */
    public static Expression makePartialInvoke(FunctionType funcType, Expression target, Expression[] arguments) {
            return new PartialCall(makeFields(funcType.parameterTypes), funcType.returnType, arguments,
                    new Invoke(funcType, target, makeArgs(funcType.parameterTypes)));
    }

    /** Create a partial call of a Java interface method
     *
     * @param funcType The type of the function pointer to invoke
     * @param target The function pointer to invoke
     * @param arguments The partial arguments to the method
     * @param filename The source file where this call is created
     * @param lineNumber The line number in the source file where this call is created
     * @return A lambda that invokes the interface method
     */
    public static Expression makePartialInvoke(FunctionType funcType, Expression target, Expression[] arguments,
                                               String filename, int lineNumber) {
        return new PartialCall(makeFields(funcType.parameterTypes), funcType.returnType, arguments,
                new Invoke(funcType, target, makeArgs(funcType.parameterTypes)), filename, lineNumber);
    }

    /** Create an array of fields for a lambda from a given list of parameter types.
     * The field names are named arg0, arg1, ...
     * @param parameterTypes The types of the parameters
     * @return A list of fields containing a name and a type
     */
    private static Field[] makeFields(Type[] parameterTypes) {
        Field[] fields = new Field[parameterTypes.length];
        for (int i=0; i < parameterTypes.length; i++) {
            fields[i] = new Field("arg"+i, parameterTypes[i]);
        }
        return fields;
    }

    /** Create an array of fields for a lambda from a given list of parameter types.
     * The field names are named arg0, arg1, ...
     * @param objectType the type of object containing the method
     * @param parameterTypes The types of the parameters
     * @return A list of fields containing a name and a type
     */
    private static Field[] makeFields(Type objectType, Type[] parameterTypes, int numPartials) {
        Field[] fields = new Field[parameterTypes.length+1];
        for (int i=0; i < numPartials; i++) {
            fields[i] = new Field("arg"+i, parameterTypes[i]);
        }
        fields[numPartials] = new Field("$target", objectType);
        for (int i=numPartials; i < parameterTypes.length; i++) {
            fields[i+1] = new Field("arg"+i, parameterTypes[i]);
        }
        return fields;
    }
    /** Create an array of GetValue expressions to fetch lambda arguments for a
     * call to a function. This assumes that the lambda parameters are named
     * arg0, arg1, ...
     * @param parameterTypes The types of the arguments
     * @return An array of GetValue expressions to fetch the function call arguments
     */
    private static Expression[] makeArgs(Type[] parameterTypes) {
        Expression[] getArgs = new Expression[parameterTypes.length];
        for (int i=0; i < parameterTypes.length; i++) {
            getArgs[i] = new GetValue("arg"+i, parameterTypes[i]);
        }
        return getArgs;
    }

    private PartialCalls() {}
}
