package org.jfuncmachine.compiler.model.expr.javainterop;

import org.jfuncmachine.compiler.classgen.ClassGenerator;
import org.jfuncmachine.compiler.classgen.Environment;
import org.jfuncmachine.compiler.model.expr.Expression;
import org.jfuncmachine.compiler.model.expr.boxing.Autobox;
import org.jfuncmachine.compiler.model.expr.boxing.Box;
import org.jfuncmachine.compiler.model.types.Type;

/** An expression to invoke a static Java method */
public class CallJavaStaticMethod extends Expression {
    /** The name of the class containing the method */
    public final String className;
    /** The name of the method */
    public final String methodName;
    /** The types of the method arguments */
    public final Type[] parameterTypes;
    /** The method arguments */
    public final Expression[] arguments;
    /** The return type of the method */
    public final Type returnType;

    /**
     * Create a static method invocation
     *
     * @param className      The name of the class containing the method
     * @param methodName     The name of the method
     * @param parameterTypes The types of the method arguments
     * @param returnType     The return type of the method
     * @param arguments      The method arguments
     */
    public CallJavaStaticMethod(String className, String methodName, Type[] parameterTypes, Type returnType,
                                Expression[] arguments) {
        super(null, 0);
        this.className = className;
        this.methodName = methodName;
        this.parameterTypes = parameterTypes;
        this.arguments = arguments;
        this.returnType = returnType;
    }

    /**
     * Create a static method invocation
     *
     * @param className      The name of the class containing the method
     * @param methodName     The name of the method
     * @param parameterTypes The types of the method arguments
     * @param returnType     The return type of the method
     * @param arguments      The method arguments
     * @param filename The source filename this expression is associated with
     * @param lineNumber The source line number this expression is associated with
     */
    public CallJavaStaticMethod(String className, String methodName, Type[] parameterTypes, Expression[] arguments,
                                Type returnType, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.className = className;
        this.methodName = methodName;
        this.parameterTypes = parameterTypes;
        this.arguments = arguments;
        this.returnType = returnType;
    }

    /**
     * Create a static method invocation
     *
     * @param className  The name of the class containing the method
     * @param methodName The name of the method
     * @param returnType The return type of the method
     * @param arguments  The method arguments
     */
    public CallJavaStaticMethod(String className, String methodName, Type returnType, Expression[] arguments) {
        super(null, 0);
        this.className = className;
        this.methodName = methodName;
        this.parameterTypes = new Type[arguments.length];
        for (int i=0; i < arguments.length; i++) this.parameterTypes[i] = arguments[i].getType();
        this.arguments = arguments;
        this.returnType = returnType;
    }

    /**
     * Create a static method invocation
     *
     * @param className  The name of the class containing the method
     * @param methodName The name of the method
     * @param returnType The return type of the method
     * @param arguments  The method arguments
     * @param filename   The source filename this expression is associated with
     * @param lineNumber The source line number this expression is associated with
     */
    public CallJavaStaticMethod(String className, String methodName, Type returnType, Expression[] arguments,
                                String filename, int lineNumber) {
        super(filename, lineNumber);
        this.className = className;
        this.methodName = methodName;
        this.parameterTypes = new Type[arguments.length];
        for (int i=0; i < arguments.length; i++) this.parameterTypes[i] = arguments[i].getType();
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
    }

    public void findCaptured(Environment env) {
        for (Expression expr: arguments) {
            expr.findCaptured(env);
        }
    }

    @Override
    public Expression convertToFullTailCalls(boolean inTailPosition) {
        if (inTailPosition && returnType.getJVMTypeRepresentation() != 'A') {
            return new Box(this);
        }
        return this;
    }

    @Override
    public void generate(ClassGenerator generator, Environment env, boolean inTailPosition) {
        for (int i=0; i < arguments.length; i++) {
            Expression expr = arguments[i];
            if (generator.options.autobox) {
                expr = Autobox.autobox(expr, parameterTypes[i]);
            }
            expr.generate(generator, env, false);
        }
        generator.instGen.lineNumber(lineNumber);
        generator.instGen.invokestatic(
                generator.className(className),
                methodName, generator.methodDescriptor(parameterTypes, returnType));

        if (inTailPosition && generator.currentMethod.isTailCallable) {
            generator.instGen.generateBox(returnType);
        }
    }
}
