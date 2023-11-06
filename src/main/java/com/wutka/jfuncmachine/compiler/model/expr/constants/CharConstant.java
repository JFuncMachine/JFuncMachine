package com.wutka.jfuncmachine.compiler.model.expr.constants;

import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.classgen.InstructionGenerator;
import com.wutka.jfuncmachine.compiler.model.expr.Expression;
import com.wutka.jfuncmachine.compiler.model.types.SimpleTypes;
import com.wutka.jfuncmachine.compiler.model.types.Type;

public class CharConstant extends Expression {
    public final char value;

    public CharConstant(char value) {
        super(null, 0);
        this.value = value;
    }

    public CharConstant(char value, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.value = value;
    }

    public Type getType() {
        return SimpleTypes.CHAR;
    }

    public void generate(InstructionGenerator gen, Environment env) {
        gen.ldc(value);
    }
}
