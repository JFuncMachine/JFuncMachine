package org.jfuncmachine.jfuncmachine.compiler.model.expr;

import org.jfuncmachine.jfuncmachine.compiler.classgen.ClassGenerator;
import org.jfuncmachine.jfuncmachine.compiler.classgen.Environment;
import org.jfuncmachine.jfuncmachine.compiler.model.SourceElement;
import org.jfuncmachine.jfuncmachine.compiler.model.types.Type;

public abstract class Expression extends SourceElement {
    /**
     * Get the type returned by this expression
     * @return The type of this expression
     */
    public abstract Type getType();

    /**
     * Create an expression associated with a specific filename and line number
     * @param filename The source filename this expression occurs in, or null
     * @param lineNumber The line number this expression occurs on
     */
    public Expression(String filename, int lineNumber) {
        super(filename, lineNumber);
    }

    /**
     * Search for local variables that would be captured by a lambda.
     *
     * Each expression has its own implementation of this, because each subexpression must be searched.
     * @param env
     */
    public abstract void findCaptured(Environment env);

    /**
     * Resets any labels in expressions so that the method can be regenerated
     */
    public void resetLabels() {

    }

    /**
     * Generate the bytecode for this expression
     * @param generator The generator for generating instructions and additional declarations
     * @param env The environment containing the local variables currently visible to this expression
     * @param inTailPosition True if this expression is being generated from the tail position of the method
     */
    public abstract void generate(ClassGenerator generator, Environment env, boolean inTailPosition);

}
