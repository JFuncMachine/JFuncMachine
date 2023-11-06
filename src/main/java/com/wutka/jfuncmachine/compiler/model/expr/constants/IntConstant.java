package com.wutka.jfuncmachine.compiler.model.expr.constants;

import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.classgen.InstructionGenerator;
import com.wutka.jfuncmachine.compiler.model.expr.Expression;
import com.wutka.jfuncmachine.compiler.model.types.SimpleTypes;
import com.wutka.jfuncmachine.compiler.model.types.Type;

public class IntConstant extends Expression {
    public final int value;

    public IntConstant(int value) {
        super(null, 0);
        this.value = value;
    }

    public IntConstant(int value, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.value = value;
    }

    public Type getType() {
        return SimpleTypes.INT;
    }

    public void findCaptured(Environment env) {}

    public void generate(InstructionGenerator gen, Environment env) {
        gen.ldc(value);
    }
}