package org.jfuncmachine.compiler.model;

import org.jfuncmachine.compiler.classgen.Label;
import org.jfuncmachine.compiler.model.expr.Expression;
import org.jfuncmachine.compiler.model.types.Field;
import org.jfuncmachine.compiler.model.types.Type;

/** Defines a method, which has zero or more parameters, a return type and a single expression */
public class MethodDef extends SourceElement {
    /** The name of the method */
    public final String name;
    /** The access flags of the method (e.g. Access.PUBLIC + Access.STATIC) */
    public final int access;
    /** The name and type of each parameter */
    public final Field[] parameters;
    /** The expression that this method executes */
    public Expression body;
    /** The return type of the method */
    public final Type returnType;
    /** A label pointing to the start of the method */
    public final Label startLabel;
    /** If true, this method can be called using the tail call elimination mechanism implemented by JFuncMachine */
    public final boolean isTailCallable;

    /** Used by Lambda to indicate how many method parameters were captured from
     * the lambda environment. This allows the Recur method to know how many parameters
     * to expect and how to call the lambda function properly.
     */
    public int numCapturedParameters = 0;


    /** Create a method definition
     *
     * @param name The name of the method
     * @param access The access flags of the method
     * @param parameters The name and type of each parameter
     * @param returnType The return type of the method
     * @param body The expression that this method executes
     */
    public MethodDef(String name, int access, Field[] parameters, Type returnType, Expression body) {
        super(null, 0);
        this.name = name;
        this.access = access;
        this.parameters = parameters;
        this.body = body;
        this.returnType = returnType;
        this.startLabel = new Label();
        this.isTailCallable = false;
    }

    /** Create a method definition
     *
     * @param name The name of the method
     * @param access The access flags of the method
     * @param parameters The name and type of each parameter
     * @param returnType The return type of the method
     * @param body The expression that this method executes
     * @param filename The name of the source file this method is defined in
     * @param lineNumber The line number in the source file where this method definition starts
     */
    public MethodDef(String name, int access, Field[] parameters, Type returnType, Expression body,
                     String filename, int lineNumber) {
        super(filename, lineNumber);
        this.name = name;
        this.access = access;
        this.parameters = parameters;
        this.body = body;
        this.returnType = returnType;
        this.startLabel = new Label();
        this.isTailCallable = false;
    }

    /** Create a method definition
     *
     * @param name The name of the method
     * @param access The access flags of the method
     * @param parameters The name and type of each parameter
     * @param returnType The return type of the method
     * @param isTailCallable If true, this method can be called using JFuncMachine's tail call optimization
     * @param body The expression that this method executes
     */
    public MethodDef(String name, int access, Field[] parameters, Type returnType, boolean isTailCallable,
                     Expression body) {
        super(null, 0);
        this.name = name;
        this.access = access;
        this.parameters = parameters;
        this.body = body;
        this.returnType = returnType;
        this.startLabel = new Label();
        this.isTailCallable = isTailCallable;
    }

    /** Create a method definition
     *
     * @param name The name of the method
     * @param access The access flags of the method
     * @param parameters The name and type of each parameter
     * @param returnType The return type of the method
     * @param isTailCallable If true, this method can be called using JFuncMachine's tail call optimization
     * @param body The expression that this method executes
     * @param filename The name of the source file this method is defined in
     * @param lineNumber The line number in the source file where this method definition starts
     */
    public MethodDef(String name, int access, Field[] parameters, Type returnType, boolean isTailCallable,
                    Expression body, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.name = name;
        this.access = access;
        this.parameters = parameters;
        this.body = body;
        this.returnType = returnType;
        this.startLabel = new Label();
        this.isTailCallable = isTailCallable;
    }

    /** Returns a version of this method that can be called from the JFuncMachines tail call optimization
     *
     * @return A tail-callable version of this method
     */
    public MethodDef getTailCallVersion() {
        return new MethodDef(name+"$$TC$$", access, parameters, returnType, true,
                body, filename, lineNumber);
    }

    /** Gets the return type of this method
     *
     * @return The return type of this method
     */
    public Type getReturnType() {
        return returnType;
    }

    /** Resets this method back to its original state so it can be regenerated (clears data that
     * was created during code generation)
     */
    public void reset() {
        startLabel.reset();
        if (body != null) {
            body.reset();
        }
    }
}
