package org.jfuncmachine.compiler.classgen;

import org.jfuncmachine.compiler.model.types.*;
import org.objectweb.asm.Opcodes;

import java.util.Objects;

/** Associates a name and type with the index of a local variable.
 * In Java instructions, local variables are referred to by a simple
 * index number. The parameters to the method have the lowest index numbers.
 */
public class EnvVar {
    /** The name of the variable */
    public final String name;
    /** The type of the variable */
    public Type type;
    /** The index of the variable */
    public final int index;

    /** Creates a new EnvVar with a name, type, and index value
     *
     * @param name The name of the variable
     * @param type The type of the variable
     * @param index The index of the variable
     */
    public EnvVar(String name, Type type, int index) {
        this.name = name;
        this.type = type;
        this.index = index;
    }

    /** Generates an instruction to set this variable
     *
     * @param generator The class generator currently generating the class
     */
    public void generateSet(ClassGenerator generator) {
        int opcode = setOpcode(type);
        generator.instGen.rawIntOpcode(opcode, index);
    }

    /** Returns the opcode used to set variables of a particular type
     *
     * @param t The type of variable to return the opcode for
     * @return The opcode used to store variables of the given type
     */
    public static int setOpcode(Type t) {
        return switch (t) {
            case BooleanType b -> Opcodes.ISTORE;
            case ByteType b -> Opcodes.ISTORE;
            case CharType c -> Opcodes.ISTORE;
            case DoubleType d -> Opcodes.DSTORE;
            case FloatType f -> Opcodes.FSTORE;
            case IntType i -> Opcodes.ISTORE;
            case LongType l -> Opcodes.LSTORE;
            case ShortType s -> Opcodes.ISTORE;
            default -> Opcodes.ASTORE;
        };
    }

    /** Generates an instruction to get this variable
     *
     * @param generator The class generator currently generating the class
     */
    public void generateGet(ClassGenerator generator) {
        int opcode = getOpcode(type);
        generator.instGen.rawIntOpcode(opcode, index);
    }

    /** Returns the opcode used to get variables of a particular type
     *
     * @param t The type of variable to return the opcode for
     * @return The opcode used to fetch variables of the given type
     */
    public static int getOpcode(Type t) {
        return switch (t) {
            case BooleanType b -> Opcodes.ILOAD;
            case ByteType b -> Opcodes.ILOAD;
            case CharType c -> Opcodes.ILOAD;
            case DoubleType d -> Opcodes.DLOAD;
            case FloatType f -> Opcodes.FLOAD;
            case IntType i -> Opcodes.ILOAD;
            case LongType l -> Opcodes.LLOAD;
            case ShortType s -> Opcodes.ILOAD;
            default -> Opcodes.ALOAD;
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EnvVar envVar = (EnvVar) o;
        return index == envVar.index && Objects.equals(name, envVar.name) && Objects.equals(type, envVar.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, index);
    }
}
