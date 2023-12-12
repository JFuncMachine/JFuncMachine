package org.jfuncmachine.compiler.model.expr.javainterop;

import org.jfuncmachine.compiler.classgen.ClassGenerator;
import org.jfuncmachine.compiler.classgen.Environment;
import org.jfuncmachine.compiler.model.expr.Expression;
import org.jfuncmachine.compiler.model.expr.boxing.Autobox;
import org.jfuncmachine.compiler.model.types.Type;

/** An expression to call a Java method */
public class CallJavaSpecialMethod extends Expression {
    /** The name of the class that the method belongs to */
    public final String className;
    /** The name of the method */
    public final String methodName;
    /** The types of the method arguments */
    public Type[] parameterTypes;
    /** The object to invoke the method on */
    public final Expression target;
    /** The method arguments */
    public final Expression[] arguments;
    /** The return type of the method */
    public final Type returnType;

    /**
     * Create a Java method call
     *
     * @param className  The name of the class containing the method
     * @param methodName The name of the method
     * @param returnType The return type of the method
     * @param target     The object to invoke the method on
     * @param arguments  The method arguments
     */
    public CallJavaSpecialMethod(String className, String methodName, Type returnType, Expression target, Expression[] arguments) {
        super(null, 0);
        this.className = className;
        this.methodName = methodName;
        this.parameterTypes = new Type[arguments.length];
        for (int i=0; i < parameterTypes.length; i++) parameterTypes[i] = arguments[i].getType();
        this.target = target;
        this.arguments = arguments;
        this.returnType = returnType;
    }

    /**
     * Create a Java method call
     *
     * @param className  The name of the class containing the method
     * @param methodName The name of the method
     * @param returnType The return type of the method
     * @param target     The object to invoke the method on
     * @param arguments  The method arguments
     * @param filename The source filename this expression is associated with
     * @param lineNumber The source line number this expression is associated with
     */
    public CallJavaSpecialMethod(String className, String methodName, Type returnType, Expression target, Expression[] arguments,
                                 String filename, int lineNumber) {
        super(filename, lineNumber);
        this.className = className;
        this.methodName = methodName;
        this.parameterTypes = new Type[arguments.length];
        for (int i=0; i < parameterTypes.length; i++) parameterTypes[i] = arguments[i].getType();
        this.target = target;
        this.arguments = arguments;
        this.returnType = returnType;
    }

    /**
     * Create a Java method call
     *
     * @param className      The name of the class containing the method
     * @param methodName     The name of the method
     * @param parameterTypes The types of the method arguments
     * @param returnType     The return type of the method
     * @param target         The object to invoke the method on
     * @param arguments      The method arguments
     */
    public CallJavaSpecialMethod(String className, String methodName, Type[] parameterTypes,
                                 Type returnType, Expression target, Expression[] arguments) {
        super(null, 0);
        this.className = className;
        this.methodName = methodName;
        this.parameterTypes = parameterTypes;
        this.target = target;
        this.arguments = arguments;
        this.returnType = returnType;
    }

    /**
     * Create a Java method call
     *
     * @param className      The name of the class containing the method
     * @param methodName     The name of the method
     * @param parameterTypes The types of the method arguments
     * @param returnType     The return type of the method
     * @param target         The object to invoke the method on
     * @param arguments      The method arguments
     * @param filename       The source filename this expression is associated with
     * @param lineNumber     The source line number this expression is associated with
     */
    public CallJavaSpecialMethod(String className, String methodName, Type[] parameterTypes,
                                 Type returnType, Expression target, Expression[] arguments,
                                 String filename, int lineNumber) {
        super(filename, lineNumber);
        this.className = className;
        this.methodName = methodName;
        this.parameterTypes = parameterTypes;
        this.target = target;
        this.arguments = arguments;
        this.returnType = returnType;
    }

    public Type getType() {
        return returnType;
    }

    @Override
    public void reset() {
        for (Expression expr: arguments) {
            expr.reset();
        }
        target.reset();
    }

    public void findCaptured(Environment env) {
        for (Expression expr: arguments) {
            expr.findCaptured(env);
        }
        target.findCaptured(env);
    }

    @Override
    public void generate(ClassGenerator generator, Environment env, boolean inTailPosition) {
        target.generate(generator, env, false);
        for (int i=0; i < arguments.length; i++) {
            Expression expr = arguments[i];
            if (generator.options.autobox) {
                expr = Autobox.autobox(expr, parameterTypes[i]);
            }
            expr.generate(generator, env, false);
        }
        generator.instGen.invokespecial(
                generator.className(className),
                methodName, generator.methodDescriptor(parameterTypes, returnType));

        if (inTailPosition && generator.currentMethod.isTailCallable) {
            generator.instGen.generateBox(returnType);
        }
    }
}
