package com.wutka.jfuncmachine.compiler.model.expr.javainterop;

import com.wutka.jfuncmachine.compiler.classgen.ClassGenerator;
import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.model.expr.Expression;
import com.wutka.jfuncmachine.compiler.model.expr.boxing.Autobox;
import com.wutka.jfuncmachine.compiler.model.types.SimpleTypes;
import com.wutka.jfuncmachine.compiler.model.types.Type;

/** An expression to call a superclass constructor */
public class CallJavaSuperMethod extends Expression {
    /** The class containing the superclass constructor */
    public final String className;
    /** The types of the constructor arguments */
    public Type[] parameterTypes;
    /** The object to invoke the constructor on */
    public final Expression target;
    /** The arguments for the constructor */
    public final Expression[] arguments;

    /** Create a superclass constructor invocation
     *
     * @param className The class containing the constructor
     * @param target The object to invoke the constructor on
     * @param arguments The arguments for the constructor
     */
    public CallJavaSuperMethod(String className, Expression target, Expression[] arguments) {
        super(null, 0);
        this.className = className;
        this.parameterTypes = new Type[arguments.length];
        for (int i=0; i < parameterTypes.length; i++) parameterTypes[i] = arguments[i].getType();
        this.target = target;
        this.arguments = arguments;
    }

    /** Create a superclass constructor invocation
     *
     * @param className The class containing the constructor
     * @param target The object to invoke the constructor on
     * @param arguments The arguments for the constructor
     * @param filename The source filename this expression is associated with
     * @param lineNumber The source line number this expression is associated with
     */
    public CallJavaSuperMethod(String className, Expression target, Expression[] arguments,
                               String filename, int lineNumber) {
        super(filename, lineNumber);
        this.className = className;
        this.parameterTypes = new Type[arguments.length];
        for (int i=0; i < parameterTypes.length; i++) parameterTypes[i] = arguments[i].getType();
        this.target = target;
        this.arguments = arguments;
    }

    /** Create a superclass constructor invocation
     *
     * @param className The class containing the constructor
     * @param parameterTypes The types of the constructor arguments
     * @param target The object to invoke the constructor on
     * @param arguments The arguments for the constructor
     */
    public CallJavaSuperMethod(String className, Type[] parameterTypes,
                               Expression target, Expression[] arguments) {
        super(null, 0);
        this.className = className;
        this.parameterTypes = parameterTypes;
        this.target = target;
        this.arguments = arguments;
    }

    /** Create a superclass constructor invocation
     *
     * @param className The class containing the constructor
     * @param parameterTypes The types of the constructor arguments
     * @param target The object to invoke the constructor on
     * @param arguments The arguments for the constructor
     * @param filename The source filename this expression is associated with
     * @param lineNumber The source line number this expression is associated with
     */
    public CallJavaSuperMethod(String className, Type[] parameterTypes,
                               Expression target, Expression[] arguments,
                               String filename, int lineNumber) {
        super(filename, lineNumber);
        this.className = className;
        this.parameterTypes = parameterTypes;
        this.target = target;
        this.arguments = arguments;
    }

    public Type getType() {
        return SimpleTypes.UNIT;
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
        generator.instGen.dup();
        for (int i=0; i < arguments.length; i++) {
            Expression expr = arguments[i];
            if (generator.options.autobox) {
                expr = Autobox.autobox(expr, parameterTypes[i]);
            }
            expr.generate(generator, env, false);
        }
        generator.instGen.invokespecial(
                generator.className(className),
                "super", generator.methodDescriptor(parameterTypes, SimpleTypes.UNIT));
    }
}
