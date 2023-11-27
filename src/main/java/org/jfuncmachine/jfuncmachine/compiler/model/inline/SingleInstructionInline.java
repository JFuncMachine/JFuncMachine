package org.jfuncmachine.jfuncmachine.compiler.model.inline;

import org.jfuncmachine.jfuncmachine.compiler.classgen.ClassGenerator;
import org.jfuncmachine.jfuncmachine.compiler.classgen.Environment;
import org.jfuncmachine.jfuncmachine.compiler.model.InlineFunction;
import org.jfuncmachine.jfuncmachine.compiler.model.types.Type;

public class SingleInstructionInline extends InlineFunction {
    public final int opcode;

    public SingleInstructionInline(Type[] parameterTypes, Type returnType, int opcode) {
        super(parameterTypes, returnType);
        this.opcode = opcode;
    }

    @Override
    public void generate(ClassGenerator generator, Environment env) {
        generator.instGen.rawOpcode(opcode);
    }
}
