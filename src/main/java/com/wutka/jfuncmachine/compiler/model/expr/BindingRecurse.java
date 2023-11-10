package com.wutka.jfuncmachine.compiler.model.expr;

import com.wutka.jfuncmachine.compiler.classgen.ClassGenerator;
import com.wutka.jfuncmachine.compiler.classgen.EnvVar;
import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.model.types.*;
import org.objectweb.asm.Opcodes;

public class BindingRecurse extends Expression {
    public final String name;
    public final Expression[] nextValues;

    public BindingRecurse(String name, Expression[] nextValues) {
        super(null, 0);
        this.name = name;
        this.nextValues = nextValues;
    }

    public BindingRecurse(String name, Expression[] nextValues,
                          String filename, int lineNumber) {
        super(filename, lineNumber);
        this.name = name;
        this.nextValues = nextValues;
    }

    public Type getType() {
        return SimpleTypes.UNIT;  // The recurse is not a function, so it shouldn't have a value
    }

    public void findCaptured(Environment env) {
        for (Expression expr: nextValues) {
            expr.findCaptured(env);
        }
    }

    @Override
    public void generate(ClassGenerator generator, Environment env) {
        Binding binding = env.getBinding(name);
        if (nextValues.length != binding.bindings.length) {
            throw generateException(
                    String.format("Number of values (%d) does not match number of bindings (%d) for binding %s",
                            nextValues.length, binding.bindings.length, name));
        }
        for (int i=0; i < nextValues.length; i++) {
            if (!nextValues[i].getType().equals(binding.bindings[i].value.getType())) {
                throw generateException(
                    String.format("In recurse to named binding %s, binding %s had initial type %s, but new value is type %s",
                            name, binding.bindings[i].name, binding.bindings[i].value.getType(),
                            nextValues[i].getType()));
            }
        }
        for (int i=0; i < nextValues.length; i++) {
            nextValues[i].generate(generator, env);
        }

        for (int i=nextValues.length-1; i >= 0; i--) {
            EnvVar envVar = env.getVar(binding.bindings[i].name);

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
            generator.instGen.rawIntOpcode(opcode, envVar.value);
        }
        generator.instGen.gotolabel(binding.label);
    }
}
