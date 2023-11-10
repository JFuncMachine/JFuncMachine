package com.wutka.jfuncmachine.compiler.model.expr;

import com.wutka.jfuncmachine.compiler.classgen.ClassGenerator;
import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.model.MethodDef;
import com.wutka.jfuncmachine.compiler.model.types.*;
import org.objectweb.asm.Opcodes;

public class Recurse extends Expression {
    public final String name;
    public final Expression[] nextValues;

    public Recurse(String name, Expression[] nextValues) {
        super(null, 0);
        this.name = name;
        this.nextValues = nextValues;
    }

    public Recurse(String name, Expression[] nextValues,
                   String filename, int lineNumber) {
        super(filename, lineNumber);
        this.name = name;
        this.nextValues = nextValues;
    }

    public Type getType() {
        return SimpleTypes.UNIT;  // The recurse is not a function, so it shouldn't have a value
    }

    public void findCaptured(Environment env) {
        throw generateException(
                String.format("Can't call recurse to %s from a closure", name));
    }

    @Override
    public void generate(ClassGenerator generator, Environment env) {
        MethodDef methodDef = env.getCurrentMethod();
        if (nextValues.length != methodDef.parameters.length) {
            throw generateException(
                    String.format("Number of values (%d) does not match number of bindings (%d) for binding %s",
                            nextValues.length, methodDef.parameters.length, name));
        }
        for (int i=0; i < nextValues.length; i++) {
            if (!nextValues[i].getType().equals(methodDef.parameters[i].type)) {
                throw generateException(
                    String.format("In recurse to named binding %s, binding %s had initial type %s, but new value is type %s",
                            name, methodDef.parameters[i].name, methodDef.parameters[i].type,
                            nextValues[i].getType()));
            }
        }
        for (int i=0; i < nextValues.length; i++) {
            nextValues[i].generate(generator, env);
        }

        for (int i=nextValues.length-1; i >= 0; i--) {
            int opcode = switch (nextValues[i].getType()) {
                case BooleanType b -> Opcodes.ISTORE;
                case ByteType b -> Opcodes.ISTORE;
                case CharType c -> Opcodes.ISTORE;
                case DoubleType d -> Opcodes.DSTORE;
                case FloatType f -> Opcodes.FSTORE;
                case IntType it -> Opcodes.ISTORE;
                case LongType l -> Opcodes.LSTORE;
                case ShortType s -> Opcodes.ISTORE;
                default -> Opcodes.ASTORE;
            };
            generator.instGen.rawIntOpcode(opcode, i);
        }
        generator.instGen.gotolabel(methodDef.startLabel);
    }
}
