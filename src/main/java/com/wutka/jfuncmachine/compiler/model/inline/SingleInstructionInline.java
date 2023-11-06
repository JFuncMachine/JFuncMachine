package com.wutka.jfuncmachine.compiler.model.inline;

import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.classgen.InstructionGenerator;
import com.wutka.jfuncmachine.compiler.model.InlineFunction;
import com.wutka.jfuncmachine.compiler.model.types.Type;

public class SingleInstructionInline extends InlineFunction {
    public final int opcode;

    public SingleInstructionInline(Type[] parameterTypes, Type returnType, int opcode) {
        super(parameterTypes, returnType);
        this.opcode = opcode;
    }

    @Override
    public void generate(InstructionGenerator generator, Environment env) {
        generator.rawOpcode(opcode);
    }
}
