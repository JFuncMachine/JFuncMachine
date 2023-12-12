package org.jfuncmachine.compiler.model.expr;

import org.jfuncmachine.compiler.classgen.ClassGenerator;
import org.jfuncmachine.compiler.classgen.Environment;
import org.jfuncmachine.compiler.model.types.Type;

/** An expression to throw an exception*/
public class Throw extends Expression {
    /** The expression to throw */
    Expression expr;
    /** The type of the expression */
    public Type type;

    /** Create a new throw expression
     * @param type The type of throwable to throw
     * @param expr The expression to throw
     */
    public Throw(Type type, Expression expr) {
        super(null, 0);
        this.expr = expr;
        this.type = type;
    }

    /** Create a new throw expression
     * @param type The type of throwable to throw
     * @param expr The expression to throw
     * @param filename The source filename this expression is associated with
     * @param lineNumber The source line number this expression is associated with
     */
    public Throw(Type type, Expression expr, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.expr = expr;
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    @Override
    public void reset() {
        expr.reset();
    }

    public void findCaptured(Environment env) {
        expr.findCaptured(env);
    }

    @Override
    public void generate(ClassGenerator generator, Environment env, boolean inTailPosition) {
        expr.generate(generator, env, false);
        generator.instGen.athrow();
    }
}
