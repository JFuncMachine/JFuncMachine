package com.wutka.jfuncmachine.compiler.model.expr;

import com.wutka.jfuncmachine.compiler.classgen.ClassGenerator;
import com.wutka.jfuncmachine.compiler.classgen.EnvVar;
import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.model.Access;
import com.wutka.jfuncmachine.compiler.model.ClassDef;
import com.wutka.jfuncmachine.compiler.model.MethodDef;
import com.wutka.jfuncmachine.compiler.model.expr.boxing.Autobox;
import com.wutka.jfuncmachine.compiler.model.types.Type;

/** An expression to invoke a method.
 * This method differs from the methods in the javainterop package in that it can apply certain optimizations.
 * For example, if this method call is a recursive call to the method currently being defined, and the
 * localTailCallsToLoops option is true, this expression updates the local variables representing the
 * method parameters and jumps back to the beginning of the method.
 * Likewise, if full tail recursion is enabled, this method should be used for any methods generated by
 * JFuncMachine so it can implement the looping necessary to handle full tail calls.
 */
public class CallMethod extends Expression {
    /** The name of the class containing the method */
    public final String className;
    /** The name of the method to call */
    public final String name;
    /** The object to call the method on */
    public final Expression target;
    /** The method argument values */
    public final Expression[] arguments;
    /** The types of the method arguments */
    public final Type[] parameterTypes;
    /** The return type of the method */
    public final Type returnType;

    /** Create a new method call expression
     * @param className The name of the class containing the method
     * @param name The name of the method
     * @param parameterTypes The types of the method parameters
     * @param returnType The return type of the method
     * @param target The object to invoke the method on
     * @param arguments The method argument values
     */
    public CallMethod(String className, String name, Type[] parameterTypes, Type returnType,
                      Expression target, Expression[] arguments) {
        super(null, 0);
        this.className = className;
        this.name = name;
        this.target = target;
        this.arguments = arguments;
        this.parameterTypes = parameterTypes;
        this.returnType = returnType;
    }

    /** Create a new method call expression
     * @param className The name of the class containing the method
     * @param name The name of the method
     * @param parameterTypes The types of the method parameters
     * @param returnType The return type of the method
     * @param target The object to invoke the method on
     * @param arguments The method argument values
     * @param filename The source filename this expression is associated with
     * @param lineNumber The source line number this expression is associated with
     */
    public CallMethod(String className, String name, Type[] parameterTypes, Type returnType,
                      Expression target, Expression[] arguments,
                      String filename, int lineNumber) {
        super(filename, lineNumber);
        this.className = className;
        this.name = name;
        this.target = target;
        this.arguments = arguments;
        this.parameterTypes = parameterTypes;
        this.returnType = returnType;
    }

    /** Create a new method call expression
     * @param name The name of the method
     * @param parameterTypes The types of the method parameters
     * @param returnType The return type of the method
     * @param target The object to invoke the method on
     * @param arguments The method argument values
     */
    public CallMethod(String name, Type[] parameterTypes, Type returnType,
                      Expression target, Expression[] arguments) {
        super(null, 0);
        this.className = null;
        this.name = name;
        this.target = target;
        this.arguments = arguments;
        this.parameterTypes = parameterTypes;
        this.returnType = returnType;
    }

    /** Create a new method call expression
     * @param name The name of the method
     * @param parameterTypes The types of the method parameters
     * @param returnType The return type of the method
     * @param target The object to invoke the method on
     * @param arguments The method argument values
     * @param filename The source filename this expression is associated with
     * @param lineNumber The source line number this expression is associated with
     */
    public CallMethod(String name, Type[] parameterTypes, Type returnType,
                      Expression target, Expression[] arguments,
                      String filename, int lineNumber) {
        super(filename, lineNumber);
        this.className = null;
        this.name = name;
        this.target = target;
        this.arguments = arguments;
        this.parameterTypes = parameterTypes;
        this.returnType = returnType;
    }

    public Type getType() {
        return returnType;
    }

    public void findCaptured(Environment env) {
        for (Expression expr: arguments) {
            expr.findCaptured(env);
        }
        target.findCaptured(env);
    }

    @Override
    public void generate(ClassGenerator generator, Environment env, boolean inTailPosition) {
        String invokeClassName = className;
        if (invokeClassName == null) {
            invokeClassName = generator.currentClass.getFullClassName();
        }
        target.generate(generator, env, false);
        for (int i=0; i < arguments.length; i++) {
            Expression expr = arguments[i];
            if (generator.options.autobox) {
                expr = Autobox.autobox(expr, parameterTypes[i]);
            }
            expr.generate(generator, env, false);
        }
        if (inTailPosition && generator.options.localTailCallsToLoops &&
                isCurrentFunc(generator.currentClass, generator.currentMethod)) {
            for (int i=0; i < arguments.length; i++) {
                generator.instGen.rawIntOpcode(EnvVar.setOpcode(arguments[i].getType()), i);
            }
            generator.instGen.gotolabel(generator.currentMethod.startLabel);
        } else{
            generator.instGen.invokevirtual(
                    generator.className(invokeClassName),
                    name, generator.methodDescriptor(parameterTypes, returnType));
        }
    }

    protected boolean isCurrentFunc(ClassDef currentClass, MethodDef currentMethod) {
        if ((currentMethod.access & Access.STATIC) != 0) return false;
        if (!className.equals(currentClass.getFullClassName())) return false;
        if (!name.equals(currentMethod.name)) return false;
        if (parameterTypes.length != currentMethod.parameters.length) return false;
        for (int i=0; i < parameterTypes.length; i++) {
            if (!parameterTypes[i].equals(currentMethod.parameters[i].type)) return false;
        }
        return true;
    }
}
