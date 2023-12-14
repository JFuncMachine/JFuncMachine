package org.jfuncmachine.compiler.model;

import org.jfuncmachine.compiler.model.types.Type;

/** Defines a class field */
public class ClassField extends SourceElement {
    /** The name of the field */
    public final String name;
    /** The type of the field */
    public final Type type;

    /** Create a new ClassField
     * @param name The name of the field
     * @param type The type of the field
     */
    public ClassField(String name, Type type) {
        super(null, 0);
        this.name = name;
        this.type = type;
    }

    /** Create a new ClassField
     * @param name The name of the field
     * @param type The type of the field
     * @param filename The name of the file where this field is defined
     * @param lineNumber The line number in the source file where this field is defined
     */
    public ClassField(String name, Type type, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.name = name;
        this.type = type;
    }
}
