package com.wutka.jfuncmachine.compiler.model.expr.constants;

import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.classgen.InstructionGenerator;
import com.wutka.jfuncmachine.compiler.model.expr.Expression;
import com.wutka.jfuncmachine.compiler.model.types.SimpleTypes;
import com.wutka.jfuncmachine.compiler.model.types.Type;

public class ShortConstant extends Expression {
    public final short value;

    public ShortConstant(short value, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.value = value;
    }

    public Type getType() {
        return SimpleTypes.SHORT;
    }

    public void generate(InstructionGenerator gen, Environment env) {
        gen.ldc(value);
    }
}
