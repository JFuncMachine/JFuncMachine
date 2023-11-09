package com.wutka.jfuncmachine.compiler.model.expr;

import com.wutka.jfuncmachine.compiler.classgen.EnvVar;
import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.classgen.InstructionGenerator;
import com.wutka.jfuncmachine.compiler.classgen.Label;
import com.wutka.jfuncmachine.compiler.model.types.BooleanType;
import com.wutka.jfuncmachine.compiler.model.types.ByteType;
import com.wutka.jfuncmachine.compiler.model.types.CharType;
import com.wutka.jfuncmachine.compiler.model.types.DoubleType;
import com.wutka.jfuncmachine.compiler.model.types.FloatType;
import com.wutka.jfuncmachine.compiler.model.types.IntType;
import com.wutka.jfuncmachine.compiler.model.types.LongType;
import com.wutka.jfuncmachine.compiler.model.types.ShortType;
import com.wutka.jfuncmachine.compiler.model.types.Type;
import org.objectweb.asm.Opcodes;

public class Binding extends Expression {
    public enum Visibility {
        Separate,
        Next,
        Recursive
    }

    public final BindingPair[] bindings;
    public final Expression expr;
    public final Visibility visibility;
    public final String name;
    public final Label label;

    public Binding(BindingPair[] bindings, Visibility visibility, Expression expr) {
        super(null, 0);
        this.bindings = bindings;
        this.visibility = visibility;
        this.expr = expr;
        this.name = null;
        label = null;
    }

    public Binding(BindingPair[] bindings, Visibility visibility, Expression expr,
                    String filename, int lineNumber) {
        super(filename, lineNumber);
        this.bindings = bindings;
        this.visibility = visibility;
        this.expr = expr;
        this.name = null;
        label = null;
    }

    public Binding(String name, BindingPair[] bindings, Visibility visibility, Expression expr) {
        super(null, 0);
        this.bindings = bindings;
        this.visibility = visibility;
        this.expr = expr;
        this.name = name;
        this.label = new Label();
    }

    public Binding(String name, BindingPair[] bindings, Visibility visibility, Expression expr,
                   String filename, int lineNumber) {
        super(filename, lineNumber);
        this.bindings = bindings;
        this.visibility = visibility;
        this.expr = expr;
        this.name = name;
        this.label = new Label();
    }

    public Type getType() {
        return expr.getType();
    }

    public void findCaptured(Environment env) {
        Environment newEnv = new Environment(env);
        for (BindingPair pair: bindings) {
            if (visibility == Visibility.Recursive || visibility == Visibility.Next) {
                pair.value.findCaptured(newEnv);
            } else {
                pair.value.findCaptured(env);
            }
        }
        expr.findCaptured(newEnv);
    }

    @Override
    public void generate(InstructionGenerator generator, Environment env) {

        Environment newEnv = new Environment(env);

        Label label = null;

        Label bindingEnd = new Label();

        for (BindingPair pair: bindings) {
            EnvVar envVar = null;

            if (visibility == Visibility.Recursive) {
                envVar = newEnv.allocate(pair.name, pair.value.getType());
            }

            if (visibility == Visibility.Separate) {
                pair.value.generate(generator, env);
            } else {
                pair.value.generate(generator, newEnv);
            }

            if (visibility != Visibility.Recursive) {
                envVar = newEnv.allocate(pair.name, pair.value.getType());
            }

            Label bindingVarStart = new Label();

            int opcode = switch (pair.value.getType()) {
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
            generator.rawIntOpcode(opcode, envVar.value);
        }
        if (name != null) {
            generator.label(label);
        }
        expr.generate(generator, newEnv);
        generator.label(bindingEnd);
    }

    public static class BindingPair {
        public final String name;
        public final Expression value;

        public BindingPair (String name, Expression value) {
            this.name = name;
            this.value = value;
        }
    }
}
