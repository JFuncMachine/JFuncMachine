package org.jfuncmachine.compiler.model;

import org.jfuncmachine.compiler.model.expr.*;
import org.jfuncmachine.compiler.model.expr.javainterop.CallJavaConstructor;
import org.jfuncmachine.compiler.model.expr.javainterop.CallJavaInterface;
import org.jfuncmachine.compiler.model.expr.javainterop.CallJavaMethod;
import org.jfuncmachine.compiler.model.expr.javainterop.CallJavaStaticMethod;
import org.jfuncmachine.compiler.model.types.Field;
import org.jfuncmachine.compiler.model.types.ObjectType;
import org.jfuncmachine.compiler.model.types.Type;

/** Utilities for creating pointers/references to methods, including constructors and interface methods */
public class MethodPtrs {
    /** Create a pointer to a Java constructor
     *
     * @param className The name of the class to create
     * @param parameterTypes The constructor parameters
     * @return A lambda that invokes the constructor
     */
    public static Expression makeJavaConstructorPtr(String className, Type[] parameterTypes) {
        return new Lambda(makeFields(parameterTypes), new ObjectType(className),
                new CallJavaConstructor(className, makeArgs(parameterTypes)));
    }

    /** Create a pointer to a Java constructor
     *
     * @param className The name of the class to create
     * @param parameterTypes The constructor parameters
     * @param filename The source file where this pointer is created
     * @param lineNumber The line number in the source file where this pointer is created
     * @return A lambda that invokes the constructor
     */
    public static Expression makeJavaConstructorPtr(String className, Type[] parameterTypes,
                                                    String filename, int lineNumber) {
        return new Lambda(makeFields(parameterTypes), new ObjectType(className),
                new CallJavaConstructor(className, makeArgs(parameterTypes)), filename, lineNumber);
    }

    /** Create a pointer to a Java interface method
     *
     * @param interfaceName The name of the Java interface containing the method
     * @param methodName The name of the method to point to
     * @param parameterTypes The types of the method parameters
     * @param returnType The method return type
     * @param target The object this method should be invoked on
     * @return A lambda that invokes the interface method
     */
    public static Expression makeJavaInterfaceMethodPtr(String interfaceName, String methodName,
                                                  Type[] parameterTypes, Type returnType,
                                                  Expression target) {
        return new Lambda(makeFields(parameterTypes), returnType,
                new CallJavaInterface(interfaceName, methodName, parameterTypes, returnType,
                        target, makeArgs(parameterTypes)));
    }

    /** Create a pointer to a Java interface method
     *
     * @param interfaceName The name of the Java interface containing the method
     * @param methodName The name of the method to point to
     * @param parameterTypes The types of the method parameters
     * @param returnType The method return type
     * @param target The object this method should be invoked on
     * @param filename The source file where this pointer is created
     * @param lineNumber The line number in the source file where this pointer is created
     * @return A lambda that invokes the interface method
     */
    public static Expression makeJavaInterfaceMethodPtr(String interfaceName, String methodName,
                                                  Type[] parameterTypes, Type returnType,
                                                  Expression target,
                                                  String filename, int lineNumber) {
        return new Lambda(makeFields(parameterTypes), returnType,
                new CallJavaInterface(interfaceName, methodName, parameterTypes, returnType,
                        target, makeArgs(parameterTypes)), filename, lineNumber);
    }

    /** Create a pointer to a Java method
     *
     * @param className The name of the Java class containing the method
     * @param methodName The name of the method to point to
     * @param parameterTypes The types of the method parameters
     * @param returnType The method return type
     * @param target The object this method should be invoked on
     * @return A lambda that invokes the interface method
     */
    public static Expression makeJavaMethodPtr(String className, String methodName,
                                                  Type[] parameterTypes, Type returnType,
                                                  Expression target) {
        return new Lambda(makeFields(parameterTypes), returnType,
                new CallJavaMethod(className, methodName, parameterTypes, returnType,
                        target, makeArgs(parameterTypes)));
    }

    /** Create a pointer to a Java method
     *
     * @param className The name of the Java class containing the method
     * @param methodName The name of the method to point to
     * @param parameterTypes The types of the method parameters
     * @param returnType The method return type
     * @param target The object this method should be invoked on
     * @param filename The source file where this pointer is created
     * @param lineNumber The line number in the source file where this pointer is created
     * @return A lambda that invokes the interface method
     */
    public static Expression makeJavaMethodPtr(String className, String methodName,
                                                  Type[] parameterTypes, Type returnType,
                                                  Expression target,
                                                  String filename, int lineNumber) {
        return new Lambda(makeFields(parameterTypes), returnType,
                new CallJavaMethod(className, methodName, parameterTypes, returnType,
                        target, makeArgs(parameterTypes)), filename, lineNumber);
    }

    /** Create a pointer to a static Java method
     *
     * @param className The name of the Java class containing the method
     * @param methodName The name of the method to point to
     * @param parameterTypes The types of the method parameters
     * @param returnType The method return type
     * @return A lambda that invokes the interface method
     */
    public static Expression makeJavaStaticMethodPtr(String className, String methodName,
                                               Type[] parameterTypes, Type returnType) {
        return new Lambda(makeFields(parameterTypes), returnType,
                new CallJavaStaticMethod(className, methodName, parameterTypes, returnType,
                        makeArgs(parameterTypes)));
    }

    /** Create a pointer to a static Java method
     *
     * @param className The name of the Java class containing the method
     * @param methodName The name of the method to point to
     * @param parameterTypes The types of the method parameters
     * @param returnType The method return type
     * @param filename The source file where this pointer is created
     * @param lineNumber The line number in the source file where this pointer is created
     * @return A lambda that invokes the interface method
     */
    public static Expression makeJavaStaticMethodPtr(String className, String methodName,
                                               Type[] parameterTypes, Type returnType,
                                               String filename, int lineNumber) {
        return new Lambda(makeFields(parameterTypes), returnType,
                new CallJavaStaticMethod(className, methodName, parameterTypes, returnType,
                        makeArgs(parameterTypes)), filename, lineNumber);
    }

    /** Create a pointer to a method that may or may not do tail call optimization
     *
     * @param className The name of the class containing the method
     * @param methodName The name of the method to point to
     * @param parameterTypes The types of the method parameters
     * @param returnType The method return type
     * @param target The object this method should be invoked on
     * @return A lambda that invokes the interface method
     */
    public static Expression makeMethodPtr(String className, String methodName,
                                               Type[] parameterTypes, Type returnType,
                                               Expression target) {
        return new Lambda(makeFields(parameterTypes), returnType,
                new CallMethod(className, methodName, parameterTypes, returnType,
                        target, makeArgs(parameterTypes)));
    }

    /** Create a pointer to a method that may or may not do tail call optimization
     *
     * @param className The name of the class containing the method
     * @param methodName The name of the method to point to
     * @param parameterTypes The types of the method parameters
     * @param returnType The method return type
     * @param target The object this method should be invoked on
     * @param filename The source file where this pointer is created
     * @param lineNumber The line number in the source file where this pointer is created
     * @return A lambda that invokes the interface method
     */
    public static Expression makeMethodPtr(String className, String methodName,
                                               Type[] parameterTypes, Type returnType,
                                               Expression target,
                                               String filename, int lineNumber) {
        return new Lambda(makeFields(parameterTypes), returnType,
                new CallMethod(className, methodName, parameterTypes, returnType,
                        target, makeArgs(parameterTypes)), filename, lineNumber);
    }

    /** Create a pointer to a static method that may or may not do tail call optimization
     *
     * @param className The name of the class containing the method
     * @param methodName The name of the method to point to
     * @param parameterTypes The types of the method parameters
     * @param returnType The method return type
     * @return A lambda that invokes the interface method
     */
    public static Expression makeStaticMethodPtr(String className, String methodName,
                                                     Type[] parameterTypes, Type returnType) {
        return new Lambda(makeFields(parameterTypes), returnType,
                new CallStaticMethod(className, methodName, parameterTypes, returnType,
                        makeArgs(parameterTypes)));
    }

    /** Create a pointer to a static method that may or may not do tail call optimization
     *
     * @param className The name of the class containing the method
     * @param methodName The name of the method to point to
     * @param parameterTypes The types of the method parameters
     * @param returnType The method return type
     * @param filename The source file where this pointer is created
     * @param lineNumber The line number in the source file where this pointer is created
     * @return A lambda that invokes the interface method
     */
    public static Expression makeStaticMethodPtr(String className, String methodName,
                                                     Type[] parameterTypes, Type returnType,
                                                     String filename, int lineNumber) {
        return new Lambda(makeFields(parameterTypes), returnType,
                new CallStaticMethod(className, methodName, parameterTypes, returnType,
                        makeArgs(parameterTypes)), filename, lineNumber);
    }

    /** Create a pointer to a method that uses tail call optimization
     *
     * @param className The name of the class containing the method
     * @param methodName The name of the method to point to
     * @param parameterTypes The types of the method parameters
     * @param returnType The method return type
     * @param target The object this method should be invoked on
     * @return A lambda that invokes the interface method
     */
    public static Expression makeTailCallMethodPtr(String className, String methodName,
                                           Type[] parameterTypes, Type returnType,
                                           Expression target) {
        return new Lambda(makeFields(parameterTypes), returnType,
                new CallTailCallMethod(className, methodName, parameterTypes, returnType,
                        target, makeArgs(parameterTypes)));
    }

    /** Create a pointer to a method that uses tail call optimization
     *
     * @param className The name of the class containing the method
     * @param methodName The name of the method to point to
     * @param parameterTypes The types of the method parameters
     * @param returnType The method return type
     * @param target The object this method should be invoked on
     * @param filename The source file where this pointer is created
     * @param lineNumber The line number in the source file where this pointer is created
     * @return A lambda that invokes the interface method
     */
    public static Expression makeTailCallMethodPtr(String className, String methodName,
                                           Type[] parameterTypes, Type returnType,
                                           Expression target,
                                           String filename, int lineNumber) {
        return new Lambda(makeFields(parameterTypes), returnType,
                new CallTailCallMethod(className, methodName, parameterTypes, returnType,
                        target, makeArgs(parameterTypes)), filename, lineNumber);
    }

    /** Create a pointer to a static method that uses tail call optimization
     *
     * @param className The name of the class containing the method
     * @param methodName The name of the method to point to
     * @param parameterTypes The types of the method parameters
     * @param returnType The method return type
     * @return A lambda that invokes the interface method
     */
    public static Expression makeTailCallStaticMethodPtr(String className, String methodName,
                                                 Type[] parameterTypes, Type returnType) {
        return new Lambda(makeFields(parameterTypes), returnType,
                new CallTailCallStaticMethod(className, methodName, parameterTypes, returnType,
                        makeArgs(parameterTypes)));
    }

    /** Create a pointer to a static method that uses tail call optimization
     *
     * @param className The name of the class containing the method
     * @param methodName The name of the method to point to
     * @param parameterTypes The types of the method parameters
     * @param returnType The method return type
     * @param filename The source file where this pointer is created
     * @param lineNumber The line number in the source file where this pointer is created
     * @return A lambda that invokes the interface method
     */
    public static Expression makeTailCallStaticMethodPtr(String className, String methodName,
                                                 Type[] parameterTypes, Type returnType,
                                                 String filename, int lineNumber) {
        return new Lambda(makeFields(parameterTypes), returnType,
                new CallTailCallStaticMethod(className, methodName, parameterTypes, returnType,
                        makeArgs(parameterTypes)), filename, lineNumber);
    }

    /** Create an array of fields for a lambda from a given list of parameter types.
     * The field names are named arg0, arg1, ...
     * @param parameterTypes The types of the parameters
     * @return A list of fields containing a name and a type
     */
    public static Field[] makeFields(Type[] parameterTypes) {
        Field[] fields = new Field[parameterTypes.length];
        for (int i=0; i < parameterTypes.length; i++) {
            fields[i] = new Field("arg"+i, parameterTypes[i]);
        }
        return fields;
    }

    /** Create an array of GetValue expressions to fetch lambda arguments for a
     * call to a function. This assumes that the lambda parameters are named
     * arg0, arg1, ...
     * @param parameterTypes The types of the arguments
     * @return An array of GetValue expressions to fetch the function call arguments
     */
    public static Expression[] makeArgs(Type[] parameterTypes) {
        Expression[] getArgs = new Expression[parameterTypes.length];
        for (int i=0; i < parameterTypes.length; i++) {
            getArgs[i] = new GetValue("arg"+i, parameterTypes[i]);
        }
        return getArgs;
    }
}
