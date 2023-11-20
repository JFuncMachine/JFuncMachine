package com.wutka.jfuncmachine.compiler.model.expr;

import com.wutka.jfuncmachine.compiler.classgen.ClassGenerator;
import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.model.expr.boxing.Autobox;
import com.wutka.jfuncmachine.compiler.model.types.*;
import org.objectweb.asm.Opcodes;

/** An expression to store a value in an array */
public class ArraySet extends Expression {
    /** The array to store the value in */
    public final Expression array;
    /** The array index where the value should be stored */
    public final Expression index;
    /** The value to store */
    public final Expression value;

    /** Create an array set expression
     *
     * @param array The array to store the value in
     * @param index The array index where the value should be stored
     * @param value The value to be stored
     */
    public ArraySet(Expression array, Expression index, Expression value) {
        super(null, 0);
        this.array = array;
        this.index = index;
        this.value = value;
        if (!(array.getType() instanceof ArrayType)) {
            throw generateException("Target of array reference must be an array");
        }
    }

    /** Create an array set expression
     *
     * @param array The array to store the value in
     * @param index The array index where the value should be stored
     * @param value The value to be stored
     * @param filename The source filename this expression is associated with
     * @param lineNumber The source line number this expression is associated with
     */
    public ArraySet(Expression array, Expression index, Expression value,
                    String filename, int lineNumber) {
        super(filename, lineNumber);
        this.array = array;
        this.index = index;
        this.value = value;
        if (!(array.getType() instanceof ArrayType)) {
            throw generateException("Target of array reference must be an array");
        }
    }

    public Type getType() {
        return SimpleTypes.UNIT;
    }

    public void findCaptured(Environment env) {
        array.findCaptured(env);
        index.findCaptured(env);
        value.findCaptured(env);
    }

    @Override
    public void generate(ClassGenerator generator, Environment env) {
        Type arrayType = array.getType();

        if (arrayType instanceof ArrayType at) {
            Type containedType = at.containedType();

            array.generate(generator, env);

            if (generator.options.autobox) {
                Autobox.autobox(index, SimpleTypes.INT).generate(generator, env);
                Autobox.autobox(value, containedType).generate(generator, env);

            } else {
                index.generate(generator, env);
                value.generate(generator, env);
            }

            int opcode = switch (containedType) {
                case BooleanType b -> Opcodes.BASTORE;
                case ByteType b -> Opcodes.BASTORE;
                case CharType c -> Opcodes.CASTORE;
                case DoubleType d -> Opcodes.DASTORE;
                case FloatType f -> Opcodes.FASTORE;
                case IntType i -> Opcodes.IASTORE;
                case LongType l -> Opcodes.LASTORE;
                case ShortType s -> Opcodes.SASTORE;
                default -> Opcodes.AASTORE;
            };

            generator.instGen.rawOpcode(opcode);
        } else {
            throw generateException(
                    String.format("Tried to do ArrayGet on type %s", arrayType));
        }
    }
}
