package org.jfuncmachine.compiler.model.inline;

import org.jfuncmachine.compiler.classgen.ClassGenerator;
import org.jfuncmachine.compiler.classgen.Environment;
import org.jfuncmachine.compiler.model.InlineFunction;
import org.jfuncmachine.compiler.model.types.Type;

/** Represents an inline function that generates a single instruction */
public class SingleInstructionInline extends InlineFunction {
    /** The opcode that this function executes */
    public final int opcode;

    /** Create a new single instruction inline
     *
     * @param parameterTypes The parameter types of the function
     * @param returnType The return type of the function
     * @param opcode The opcode that this function executes
     */
    public SingleInstructionInline(Type[] parameterTypes, Type returnType, int opcode) {
        super(parameterTypes, returnType);
        this.opcode = opcode;
    }

    @Override
    public void generate(ClassGenerator generator, Environment env) {
        generator.instGen.rawOpcode(opcode);
    }
}
