package org.jfuncmachine.compiler.model.types;

import java.util.Objects;

/** A method field */
public class Field {
    /** The name of the field */
    public final String name;
    /** The type of the field */
    public final Type type;

    /** Create a new field
     *
     * @param name The name of the field
     * @param type The type of the field
     */
    public Field(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Field field = (Field) o;
        return Objects.equals(name, field.name) && Objects.equals(type, field.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type);
    }
}