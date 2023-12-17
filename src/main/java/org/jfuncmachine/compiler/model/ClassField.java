package org.jfuncmachine.compiler.model;

import org.jfuncmachine.compiler.model.types.Type;

/** Defines a class field */
public class ClassField extends SourceElement {
    /** The name of the field */
    public final String name;
    /** The type of the field */
    public final Type type;
    /** The access flags of the field */
    public final int access;
    /** The default value of the field. This value may be null, but if not, it must be an Object,
     * Integer, Long, Float, or Double. If the field is Boolean, Byte, Char, or Short, use an Integer
     * to initialize it.
     */
    public final Object defaultValue;

    /** Create a new ClassField
     * @param name The name of the field
     * @param type The type of the field
     * @param access The access flags for the field
     * @param defaultValue The default value for the field (may be null)
     */
    public ClassField(String name, Type type, int access, Object defaultValue) {
        super(null, 0);
        this.name = name;
        this.type = type;
        this.access = access;
        this.defaultValue = defaultValue;
    }

    /** Create a new ClassField
     * @param name The name of the field
     * @param type The type of the field
     * @param access The access flags for the field
     * @param defaultValue The default value for the field (may be null)
     * @param filename The name of the file where this field is defined
     * @param lineNumber The line number in the source file where this field is defined
     */
    public ClassField(String name, Type type, int access, Object defaultValue,
                      String filename, int lineNumber) {
        super(filename, lineNumber);
        this.name = name;
        this.type = type;
        this.access = access;
        this.defaultValue = defaultValue;
    }
}
