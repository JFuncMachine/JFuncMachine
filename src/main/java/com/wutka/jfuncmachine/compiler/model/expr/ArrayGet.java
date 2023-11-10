package com.wutka.jfuncmachine.compiler.model.expr;

import com.wutka.jfuncmachine.compiler.classgen.ClassGenerator;
import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.model.types.*;
import org.objectweb.asm.Opcodes;

public class ArrayGet extends Expression {
    public final Expression array;
    public final Expression index;

    public ArrayGet(Expression array, Expression index) {
        super(null, 0);
        this.array = array;
        this.index = index;
        if (!(array.getType() instanceof ArrayType)) {
            throw generateException("Target of array reference must be an array");
        }
    }

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
    public void generate(ClassGenerator generator, Environment env) {
        Type arrayType = array.getType();

        if (arrayType instanceof ArrayType at) {
            Type containedType = at.containedType();

            array.generate(generator, env);
            index.generate(generator, env);

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
        } else {
            throw generateException(
                    String.format("Tried to do ArrayGet on type %s", arrayType));
        }

    }
}
