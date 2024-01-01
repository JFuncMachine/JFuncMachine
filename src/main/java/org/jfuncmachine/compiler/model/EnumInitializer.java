package org.jfuncmachine.compiler.model;

import org.jfuncmachine.compiler.model.expr.Expression;

/** An expression to initialize an enum value */
public class EnumInitializer extends SourceElement {
    /** The name of the enum value */
    public final String name;

    /** The expressions to initialize the enum value fields */
    public final Expression[] initializers;

    /** Create a new EnumInitializer
     *
     * @param name The name of the enum value
     * @param initializers The expressions to initialize the enum value fields
     */
    public EnumInitializer(String name, Expression[] initializers) {
        super(null, 0);
        this.name = name;
        this.initializers = initializers;
    }

    /** Create a new EnumInitializer
     *
     * @param name The name of the enum value
     * @param initializers The expressions to initialize the enum value fields
     * @param filename The source file where this initializer occurs
     * @param lineNumber The line number in the source file where this initializer occurs
     */
    public EnumInitializer(String name, Expression[] initializers, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.name = name;
        this.initializers = initializers;
    }
}
