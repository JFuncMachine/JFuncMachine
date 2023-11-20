package com.wutka.jfuncmachine.compiler.model.expr;

import com.wutka.jfuncmachine.compiler.classgen.ClassGenerator;
import com.wutka.jfuncmachine.compiler.classgen.EnvVar;
import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.model.types.*;
import org.objectweb.asm.Opcodes;

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

    public void findCaptured(Environment env) {
        expr.findCaptured(env);
    }

    @Override
    public void generate(ClassGenerator generator, Environment env) {
        expr.generate(generator, env);
        generator.instGen.athrow();
    }
}
