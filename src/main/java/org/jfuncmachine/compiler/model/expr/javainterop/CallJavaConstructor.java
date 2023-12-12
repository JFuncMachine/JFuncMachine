package org.jfuncmachine.compiler.model.expr.javainterop;

import org.jfuncmachine.compiler.classgen.ClassGenerator;
import org.jfuncmachine.compiler.classgen.Environment;
import org.jfuncmachine.compiler.model.expr.Expression;
import org.jfuncmachine.compiler.model.expr.boxing.Autobox;
import org.jfuncmachine.compiler.model.types.ObjectType;
import org.jfuncmachine.compiler.model.types.SimpleTypes;
import org.jfuncmachine.compiler.model.types.Type;

/** An expression to call a Java constructor.
 * In the Java Virtual Machine, object creation is a two-step process that requires the creation
 * of an object via the new_object instruction, and then executing the constructor method to initialize
 * the newly-created object. This class takes care of both of those operations.
 */
public class CallJavaConstructor extends Expression {
    /** The name of the class to create */
    public final String className;
    /** The parameter types of the constructor */
    public final Type[] parameterTypes;
    /** The arguments to pass to the constructor */
    public final Expression[] arguments;

    /** Create a Java constructor expression
     *
     * @param className The name of the class to create
     * @param arguments The arguments to pass to the constructor
     */
    public CallJavaConstructor(String className, Expression[] arguments) {
        super(null, 0);
        this.className = className;
        this.parameterTypes = new Type[arguments.length];
        for (int i=0; i < parameterTypes.length; i++) parameterTypes[i] = arguments[i].getType();
        this.arguments = arguments;
    }

    /** Create a Java constructor expression
     *
     * @param className The name of the class to create
     * @param arguments The arguments to pass to the constructor
     * @param filename The source filename this expression is associated with
     * @param lineNumber The source line number this expression is associated with
     */
    public CallJavaConstructor(String className, Expression[] arguments, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.className = className;
        this.parameterTypes = new Type[arguments.length];
        for (int i=0; i < parameterTypes.length; i++) parameterTypes[i] = arguments[i].getType();
        this.arguments = arguments;
    }

    /** Create a Java constructor expression
     *
     * @param className The name of the class to create
     * @param parameterTypes The parameter types of the constructor, in case they differ from the
     *                       types of the argument expressions
     * @param arguments The arguments to pass to the constructor
     */
    public CallJavaConstructor(String className, Type[] parameterTypes, Expression[] arguments) {
        super(null, 0);
        this.className = className;
        if (parameterTypes.length != arguments.length) {
            throw generateException(String.format("Number of parameter types (%d) does not match number of arguments (%d",
                    parameterTypes.length, arguments.length));
        }
        this.parameterTypes = parameterTypes;
        this.arguments = arguments;
    }

    /** Create a Java constructor expression
     *
     * @param className The name of the class to create
     * @param parameterTypes The parameter types of the constructor, in case they differ from the
     *                       types of the argument expressions
     * @param arguments The arguments to pass to the constructor
     * @param filename The source filename this expression is associated with
     * @param lineNumber The source line number this expression is associated with
     */
    public CallJavaConstructor(String className, Type[] parameterTypes, Expression[] arguments, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.className = className;
        if (parameterTypes.length != arguments.length) {
            throw generateException(String.format("Number of parameter types (%d) does not match number of arguments (%d",
                    parameterTypes.length, arguments.length));
        }
        this.parameterTypes = parameterTypes;
        this.arguments = arguments;
    }

    public Type getType() {

        return new ObjectType(className);
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
    public void generate(ClassGenerator generator, Environment env, boolean inTailPosition) {
        String currClassName = className;
        if (currClassName == null) {
            currClassName = generator.currentClass.getFullClassName();
        }
        if (className == null) {
            generator.instGen.new_object(generator.className(currClassName));
        } else {
            generator.instGen.new_object(generator.className(className));

        }
        generator.instGen.dup();
        for (int i=0; i < arguments.length; i++) {
            Expression expr = arguments[i];
            if (generator.options.autobox) {
                expr = Autobox.autobox(expr, parameterTypes[i]);
            }
            expr.generate(generator, env, false);
        }
        generator.instGen.invokespecial(generator.className(currClassName),
                "<init>", generator.methodDescriptor(parameterTypes, SimpleTypes.UNIT));
    }
}
