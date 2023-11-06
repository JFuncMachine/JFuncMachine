package com.wutka.jfuncmachine.compiler.model.expr;

import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.classgen.InstructionGenerator;
import com.wutka.jfuncmachine.compiler.model.types.ArrayType;
import com.wutka.jfuncmachine.compiler.model.types.BooleanType;
import com.wutka.jfuncmachine.compiler.model.types.ByteType;
import com.wutka.jfuncmachine.compiler.model.types.CharType;
import com.wutka.jfuncmachine.compiler.model.types.DoubleType;
import com.wutka.jfuncmachine.compiler.model.types.FloatType;
import com.wutka.jfuncmachine.compiler.model.types.IntType;
import com.wutka.jfuncmachine.compiler.model.types.LongType;
import com.wutka.jfuncmachine.compiler.model.types.ShortType;
import com.wutka.jfuncmachine.compiler.model.types.SimpleTypes;
import com.wutka.jfuncmachine.compiler.model.types.Type;
import org.objectweb.asm.Opcodes;

public class ArraySet extends Expression {
    public final Expression array;
    public final Expression index;
    public final Expression value;

    public ArraySet(Expression array, Expression index, Expression value) {
        super(null, 0);
        this.array = array;
        this.index = index;
        this.value = value;
        if (!(array.getType() instanceof ArrayType)) {
            throw generateException("Target of array reference must be an array");
        }
    }

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
    public void generate(InstructionGenerator generator, Environment env) {
        Type arrayType = array.getType();

        if (arrayType instanceof ArrayType at) {
            Type containedType = at.containedType();

            array.generate(generator, env);
            index.generate(generator, env);
            value.generate(generator, env);

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

            generator.rawOpcode(opcode);
        } else {
            throw generateException(
                    String.format("Tried to do ArrayGet on type %s", arrayType));
        }
    }
}
