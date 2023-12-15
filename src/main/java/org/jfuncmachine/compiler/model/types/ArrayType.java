package org.jfuncmachine.compiler.model.types;

/** The type of an array of the containedType element
 *
 * @param containedType The type of element contained in the array
 */
public record ArrayType(Type containedType) implements Type {

    public String toString() {
        return "ArrayType["+containedType.toString()+"]";
    }
}