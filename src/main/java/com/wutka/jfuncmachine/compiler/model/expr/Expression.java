package com.wutka.jfuncmachine.compiler.model.expr;

import com.wutka.jfuncmachine.compiler.classgen.ClassGenerator;
import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.model.SourceElement;
import com.wutka.jfuncmachine.compiler.model.types.Type;

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
     * Generate the bytecode for this expression
     * @param generator The generator for generating instructions and additional declarations
     * @param env The environment containing the local variables currently visible to this expression
     */
    public abstract void generate(ClassGenerator generator, Environment env);

}
