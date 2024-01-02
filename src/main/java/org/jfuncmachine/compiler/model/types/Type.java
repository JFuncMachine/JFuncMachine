package org.jfuncmachine.compiler.model.types;

/** The interface representing all JFuncMachine types */
public sealed interface Type
    permits BooleanType, ByteType, CharType, ShortType, IntType, LongType, FloatType, DoubleType,
        ArrayType, ObjectType, UnitType, StringType, FunctionType {

    /** Returns the name of this type's box class, if any
     *
     * For example, SimpleTypes.INT has a box class name of "java.lang.Integer"
     *
     * @return The name of the box class, or null if there isn't one
     */
    default String getBoxTypeName() { return null; }

    /** Returns the type of this type's box class, if any.
     *
     * For example, SimpleTypes.INT has a box type of new ObjectType("java.lang.Integer")
     *
     * @return The type of this type's box class, or null if there isn't one
     */
    default Type getBoxType() {
        String boxTypeName = getBoxTypeName();
        if (boxTypeName != null) {
            return new ObjectType(boxTypeName);
        }
        return null;
    }

    /** Returns the unboxed type of this type, if any
     *
     * @return The unboxed type of this type, or null if there isn't one
     */
    default Type getUnboxedType() { return null; }

    /** Test whether this type is a box type
     *
     * @return True if this type is a box type
     */
    default boolean isBoxType() { return false; }


    /** Test whether this type can be unboxed from the given class name
     *
     * @param className The name of a possible box type
     * @return True if this type can be unboxed from the given class name
     */
    default boolean isUnboxableFrom(String className) { return false; }

    /** The number of stack slots that this type consumes on the JVM stack.
     * A long or double type consumes two slots, all others consume one.
     *
     * @return The number of stack slots this type consumes
     */
    default int getStackSize() { return 1; }

    /** Tests whether this type is represented as an int in the
     * JVM. This is true for boolean, byte, char, int, and short.
     *
     * @return True if this type is represented as an int by the JVM
     */
    default boolean hasIntRepresentation() { return false; }

    /** Returns the character that the JVM uses to represent this type
     *
     * @return I for types represented by an int, J for Long, F for Float, D for double, A for object
     */
    default char getJVMTypeRepresentation() {
        return switch (this) {
            case BooleanType b -> 'I';
            case ByteType b -> 'I';
            case CharType c -> 'I';
            case DoubleType d -> 'D';
            case FloatType f -> 'F';
            case IntType i -> 'I';
            case LongType l -> 'J';
            case ShortType c -> 'I';
            default -> 'A';
        };
    }

    /** Tests whether this type has the same JVM representation as another type
     *
     * @param other The other type to compare against
     * @return True if the types have the same representation in the JVM
     */
    default boolean sameJavaType(Type other) {
        return getJVMTypeRepresentation() == other.getJVMTypeRepresentation();
    }

    /** Tests whether this type is an enum type
     *
     * @return True if this type represents an enum
     */
    default boolean isEnum() { return false; }
}
