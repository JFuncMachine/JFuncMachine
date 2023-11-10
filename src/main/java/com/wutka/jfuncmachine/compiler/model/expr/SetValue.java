package com.wutka.jfuncmachine.compiler.model.expr;

import com.wutka.jfuncmachine.compiler.classgen.ClassGenerator;
import com.wutka.jfuncmachine.compiler.classgen.EnvVar;
import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.model.types.*;
import org.objectweb.asm.Opcodes;

public class SetValue extends Expression {
    public String name;
    public Expression expression;

    public SetValue(String name, Expression expression) {
        super(null, 0);
        this.name = name;
        this.expression = expression;
    }

    public SetValue(String name, Expression expression, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.name = name;
        this.expression = expression;
    }

    public Type getType() {
        return SimpleTypes.UNIT;
    }

    public void findCaptured(Environment env) {
        expression.findCaptured(env);
    }

    @Override
    public void generate(ClassGenerator generator, Environment env) {
        EnvVar envVar = env.getVar(name);

        expression.generate(generator,env);

        int opcode = switch (expression.getType()) {
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

        generator.instGen.rawIntOpcode(opcode, envVar.value);
    }
}
