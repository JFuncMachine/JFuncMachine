package org.jfuncmachine.jfuncmachine.compiler.model.expr.javainterop;

import org.jfuncmachine.jfuncmachine.compiler.classgen.ClassGenerator;
import org.jfuncmachine.jfuncmachine.compiler.classgen.Environment;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.Expression;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.boxing.Autobox;
import org.jfuncmachine.jfuncmachine.compiler.model.types.SimpleTypes;
import org.jfuncmachine.jfuncmachine.compiler.model.types.Type;

/** An expression to call a Java method */
public class CallJavaSuperConstructor extends Expression {
    /** The types of the method arguments */
    public Type[] parameterTypes;
    /** The object to invoke the method on */
    public final Expression target;
    /** The method arguments */
    public final Expression[] arguments;

    /**
     * Create a Java super constructor call
     *
     * @param target     The object to invoke the method on
     * @param arguments  The method arguments
     */
    public CallJavaSuperConstructor(Expression target, Expression[] arguments) {
        super(null, 0);
        this.parameterTypes = new Type[arguments.length];
        for (int i=0; i < parameterTypes.length; i++) parameterTypes[i] = arguments[i].getType();
        this.target = target;
        this.arguments = arguments;
    }

    /**
     * Create a Java super constructor call
     *
     * @param target     The object to invoke the method on
     * @param arguments  The method arguments
     * @param filename The source filename this expression is associated with
     * @param lineNumber The source line number this expression is associated with
     */
    public CallJavaSuperConstructor(Expression target, Expression[] arguments,
                                    String filename, int lineNumber) {
        super(filename, lineNumber);
        this.parameterTypes = new Type[arguments.length];
        for (int i=0; i < parameterTypes.length; i++) parameterTypes[i] = arguments[i].getType();
        this.target = target;
        this.arguments = arguments;
    }
    /**
     * Create a Java super constructor call
     *
     * @param parameterTypes The types of the method arguments
     * @param target         The object to invoke the method on
     * @param arguments      The method arguments
     */
    public CallJavaSuperConstructor(Type[] parameterTypes,
                                    Expression target, Expression[] arguments) {
        super(null, 0);
        this.parameterTypes = parameterTypes;
        this.target = target;
        this.arguments = arguments;
    }

    /**
     * Create a Java super constructor call
     *
     * @param parameterTypes The types of the method arguments
     * @param target         The object to invoke the method on
     * @param arguments      The method arguments
     * @param filename       The source filename this expression is associated with
     * @param lineNumber     The source line number this expression is associated with
     */
    public CallJavaSuperConstructor(Type[] parameterTypes,
                                    Type returnType, Expression target, Expression[] arguments,
                                    String filename, int lineNumber) {
        super(filename, lineNumber);
        this.parameterTypes = parameterTypes;
        this.target = target;
        this.arguments = arguments;
    }

    public Type getType() {
        return SimpleTypes.UNIT;
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
                generator.className(generator.currentClass.superPackageName+"."+generator.currentClass.superName),
                "<init>", generator.methodDescriptor(parameterTypes, SimpleTypes.UNIT));
    }
}
