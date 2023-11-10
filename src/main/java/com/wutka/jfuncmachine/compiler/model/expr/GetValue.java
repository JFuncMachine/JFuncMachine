package com.wutka.jfuncmachine.compiler.model.expr;

import com.wutka.jfuncmachine.compiler.classgen.ClassGenerator;
import com.wutka.jfuncmachine.compiler.classgen.EnvVar;
import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.model.types.*;
import org.objectweb.asm.Opcodes;

public class GetValue extends Expression {
    public String name;
    public Type type;

    public GetValue(String name, Type type) {
        super(null, 0);
        this.name = name;
        this.type = type;
    }

    public GetValue(String name, Type type, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.name = name;
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public void findCaptured(Environment env) {
        env.checkCaptured(name);
    }

    @Override
    public void generate(ClassGenerator generator, Environment env) {

        EnvVar envVar = env.getVar(name);

        int opcode = switch (type) {
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

        generator.instGen.rawIntOpcode(opcode, envVar.value);
    }
}
