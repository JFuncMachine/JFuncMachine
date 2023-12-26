package org.jfuncmachine.compiler.model.expr;

import org.jfuncmachine.compiler.classgen.ClassGenerator;
import org.jfuncmachine.compiler.classgen.Environment;
import org.jfuncmachine.compiler.model.InlineFunction;
import org.jfuncmachine.compiler.model.expr.boxing.Autobox;
import org.jfuncmachine.compiler.model.expr.boxing.Box;
import org.jfuncmachine.compiler.model.types.ObjectType;
import org.jfuncmachine.compiler.model.types.Type;

/** Perform an inline function call.
 * Inline functions are generated as inline instructions, and often just represent a single
 * instruction, like an integer add.
 */
public class InlineCall extends Expression {
    /** The function to invoke */
    public final InlineFunction func;
    /** The function arguments */
    public final Expression[] arguments;

    private Type returnType;

    /** Create an inline function call
     * @param func The inline function to invoke
     * @param arguments The function arguments
     */
    public InlineCall(InlineFunction func, Expression[] arguments) {
        super(null, 0);
        this.func = func;
        this.arguments = arguments;
        this.returnType = func.returnType;
    }

    /** Create an inline function call
     * @param func The inline function to invoke
     * @param arguments The function arguments
     * @param filename The source filename this expression is associated with
     * @param lineNumber The source line number this expression is associated with
     */
    public InlineCall(InlineFunction func, Expression[] arguments, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.func = func;
        this.arguments = arguments;
        this.returnType = func.returnType;
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
        if (inTailPosition && func.getReturnType().getJVMTypeRepresentation() != 'A') {
            InlineCall newCall = new InlineCall(func, arguments, filename, lineNumber);
            newCall.returnType = new ObjectType();
            return new Box(newCall);
        }
        return this;
    }

    @Override
    public void generate(ClassGenerator generator, Environment env, boolean inTailPosition) {
        for (int i=0; i < arguments.length; i++) {
            Expression expr = arguments[i];
            if (generator.options.autobox) {
                expr = Autobox.autobox(expr, func.parameterTypes[i]);
            }
            expr.generate(generator, env, false);
        }
        generator.instGen.lineNumber(lineNumber);
        func.generate(generator, env);
    }
}
