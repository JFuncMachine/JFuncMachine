package org.jfuncmachine.compiler.model.expr.javainterop;

import org.jfuncmachine.compiler.classgen.ClassGenerator;
import org.jfuncmachine.compiler.classgen.Environment;
import org.jfuncmachine.compiler.model.expr.Expression;
import org.jfuncmachine.compiler.model.expr.boxing.Autobox;
import org.jfuncmachine.compiler.model.expr.boxing.Box;
import org.jfuncmachine.compiler.model.types.ObjectType;
import org.jfuncmachine.compiler.model.types.Type;

/** An expression to call a Java interface method */
public class CallJavaInterface extends Expression {
    /** The name of the interface containing the method to invoke */
    public final String interfaceName;
    /** The name of the method to invoke */
    public final String methodName;
    /** The object to invoke the method on */
    public final Expression target;
    /** The arguments for the method */
    public final Expression[] arguments;
    /** The argument types for the method */
    public final Type[] parameterTypes;
    /** The return type of the method */
    public final Type returnType;

    /** Create a Java interface method call
     *
     * @param interfaceName The name of the interface containing the method
     * @param methodName The name of the method
     * @param returnType The return type of the method
     * @param target The object to invoke the method on
     * @param arguments The arguments for the method
     */
    public CallJavaInterface(String interfaceName, String methodName, Type returnType, Expression target,
                             Expression[] arguments) {
        super(null, 0);
        this.interfaceName = interfaceName;
        this.methodName = methodName;
        this.target = target;
        this.parameterTypes = new Type[arguments.length];
        for (int i=0; i < parameterTypes.length; i++) parameterTypes[i] = arguments[i].getType();
        this.returnType = returnType;
        this.arguments = arguments;
    }

    /** Create a Java interface method call
     *
     * @param interfaceName The name of the interface containing the method
     * @param methodName The name of the method
     * @param parameterTypes The arguments types of the method
     * @param returnType The return type of the method
     * @param target The object to invoke the method on
     * @param arguments The arguments for the method
     */
    public CallJavaInterface(String interfaceName, String methodName, Type[] parameterTypes, Type returnType,
                             Expression target, Expression[] arguments) {
        super(null, 0);
        this.interfaceName = interfaceName;
        this.methodName = methodName;
        this.target = target;
        this.parameterTypes = parameterTypes;
        this.returnType = returnType;
        this.arguments = arguments;
    }

    /** Create a Java interface method call
     *
     * @param interfaceName The name of the interface containing the method
     * @param methodName The name of the method
     * @param returnType The return type of the method
     * @param target The object to invoke the method on
     * @param arguments The arguments for the method
     * @param filename The source filename this expression is associated with
     * @param lineNumber The source line number this expression is associated with
     */
    public CallJavaInterface(String interfaceName, String methodName, Type returnType, Expression target,
                             Expression[] arguments, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.interfaceName = interfaceName;
        this.methodName = methodName;
        this.target = target;
        this.parameterTypes = new Type[arguments.length];
        for (int i=0; i < parameterTypes.length; i++) parameterTypes[i] = arguments[i].getType();
        this.returnType = returnType;
        this.arguments = arguments;
    }

    /** Create a Java interface method call
     *
     * @param interfaceName The name of the interface containing the method
     * @param methodName The name of the method
     * @param parameterTypes The arguments types of the method
     * @param returnType The return type of the method
     * @param target The object to invoke the method on
     * @param arguments The arguments for the method
     * @param filename The source filename this expression is associated with
     * @param lineNumber The source line number this expression is associated with
     */
    public CallJavaInterface(String interfaceName, String methodName, Type[] parameterTypes, Type returnType,
                             Expression target, Expression[] arguments, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.interfaceName = interfaceName;
        this.methodName = methodName;
        this.target = target;
        this.parameterTypes = parameterTypes;
        this.returnType = returnType;
        this.arguments = arguments;
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
    public Expression convertToFullTailCalls(boolean inTailPosition) {
        if (inTailPosition && returnType.getJVMTypeRepresentation() != 'A') {
            return new Box(this);
        }
        return this;
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
        generator.instGen.lineNumber(lineNumber);
        generator.instGen.invokeinterface(
                generator.className(interfaceName),
                methodName, generator.methodDescriptor(parameterTypes, returnType));
    }
}
