package org.jfuncmachine.compiler.model.expr;

import org.jfuncmachine.compiler.classgen.ClassGenerator;
import org.jfuncmachine.compiler.classgen.Environment;
import org.jfuncmachine.compiler.model.expr.boxing.Autobox;
import org.jfuncmachine.compiler.model.types.*;
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

    @Override
    public void reset() {
        array.reset();
        index.reset();
        value.reset();
    }

    public void findCaptured(Environment env) {
        array.findCaptured(env);
        index.findCaptured(env);
        value.findCaptured(env);
    }

    @Override
    public void generate(ClassGenerator generator, Environment env, boolean inTailPosition) {
        Type arrayType = array.getType();

        if (arrayType instanceof ArrayType at) {
            Type containedType = at.containedType();

            array.generate(generator, env, false);

            if (generator.options.autobox) {
                Autobox.autobox(index, SimpleTypes.INT).generate(generator, env, false);
                Autobox.autobox(value, containedType).generate(generator, env, false);

            } else {
                index.generate(generator, env, false);
                value.generate(generator, env, false);
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

            if (inTailPosition && generator.currentMethod.isTailCallable) {
                generator.instGen.aconst_null();
            }
        } else {
            throw generateException(
                    String.format("Tried to do ArrayGet on type %s", arrayType));
        }
    }
}
