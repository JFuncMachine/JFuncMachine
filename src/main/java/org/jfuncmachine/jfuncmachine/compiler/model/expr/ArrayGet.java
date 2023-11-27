package org.jfuncmachine.jfuncmachine.compiler.model.expr;

import org.jfuncmachine.jfuncmachine.compiler.classgen.ClassGenerator;
import org.jfuncmachine.jfuncmachine.compiler.classgen.Environment;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.boxing.Autobox;
import org.jfuncmachine.jfuncmachine.compiler.model.types.ArrayType;
import org.jfuncmachine.jfuncmachine.compiler.model.types.BooleanType;
import org.jfuncmachine.jfuncmachine.compiler.model.types.ByteType;
import org.jfuncmachine.jfuncmachine.compiler.model.types.CharType;
import org.jfuncmachine.jfuncmachine.compiler.model.types.DoubleType;
import org.jfuncmachine.jfuncmachine.compiler.model.types.FloatType;
import org.jfuncmachine.jfuncmachine.compiler.model.types.IntType;
import org.jfuncmachine.jfuncmachine.compiler.model.types.LongType;
import org.jfuncmachine.jfuncmachine.compiler.model.types.ShortType;
import org.jfuncmachine.jfuncmachine.compiler.model.types.SimpleTypes;
import org.jfuncmachine.jfuncmachine.compiler.model.types.Type;
import org.objectweb.asm.Opcodes;

/** An expression to retrieve a value from an array */
public class ArrayGet extends Expression {
    /** The array to fetch the value from */
    public final Expression array;
    /** The index of the value in the array */
    public final Expression index;

    /** Create an array get expression
     *
     * @param array The array to fetch the value from
     * @param index The index of the value in the array
     */
    public ArrayGet(Expression array, Expression index) {
        super(null, 0);
        this.array = array;
        this.index = index;
        if (!(array.getType() instanceof ArrayType)) {
            throw generateException("Target of array reference must be an array");
        }
    }

    /** Create an array get expression
     *
     * @param array The array to fetch the value from
     * @param index The index of the value in the array
     * @param filename The source filename this expression is associated with
     * @param lineNumber The source line number this expression is associated with
     */
    public ArrayGet(Expression array, Expression index, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.array = array;
        this.index = index;
        if (!(array.getType() instanceof ArrayType)) {
            throw generateException("Target of array reference must be an array");
        }
    }

    public Type getType() {
        return array.getType();
    }

    public void findCaptured(Environment env) {
        array.findCaptured(env);
        index.findCaptured(env);
    }

    @Override
    public void generate(ClassGenerator generator, Environment env, boolean inTailPosition) {
        Type arrayType = array.getType();

        if (arrayType instanceof ArrayType at) {
            Type containedType = at.containedType();

            array.generate(generator, env, false);

            if (generator.options.autobox) {
                Autobox.autobox(index, SimpleTypes.INT).generate(generator, env, false);
            } else {
                index.generate(generator, env, false);
            }

            int opcode = switch (containedType) {
                case BooleanType b -> Opcodes.BALOAD;
                case ByteType b -> Opcodes.BALOAD;
                case CharType c -> Opcodes.CALOAD;
                case DoubleType d -> Opcodes.DALOAD;
                case FloatType f -> Opcodes.FALOAD;
                case IntType i -> Opcodes.IALOAD;
                case LongType l -> Opcodes.LALOAD;
                case ShortType s -> Opcodes.SALOAD;
                default -> Opcodes.AALOAD;
            };

            generator.instGen.rawOpcode(opcode);

            if (inTailPosition && generator.options.fullTailCalls) {
                generator.instGen.generateBox(containedType);
            }
        } else {
            throw generateException(
                    String.format("Tried to do ArrayGet on type %s", arrayType));
        }

    }
}
