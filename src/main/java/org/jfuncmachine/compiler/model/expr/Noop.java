package org.jfuncmachine.compiler.model.expr;

import org.jfuncmachine.compiler.classgen.ClassGenerator;
import org.jfuncmachine.compiler.classgen.Environment;
import org.jfuncmachine.compiler.model.types.SimpleTypes;
import org.jfuncmachine.compiler.model.types.Type;

/** An expression to retrieve a local variable value */
public class Noop extends Expression {

    /** Create a No-op
     */
    public Noop() {
        super(null, 0);
    }

    /** Create a No-op
     * @param filename The source filename this expression is associated with
     * @param lineNumber The source line number this expression is associated with
     */
    public Noop(String filename, int lineNumber) {
        super(filename, lineNumber);
    }

    public Type getType() {
        return SimpleTypes.UNIT;
    }

    public void findCaptured(Environment env) {
    }

    @Override
    public Expression convertToFullTailCalls(boolean inTailPosition) {
        return this;
    }

    @Override
    public void generate(ClassGenerator generator, Environment env, boolean inTailPosition) {

    }
}
