package com.wutka.jfuncmachine.compiler.model.expr.constants;

import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.classgen.InstructionGenerator;
import com.wutka.jfuncmachine.compiler.model.expr.Expression;
import com.wutka.jfuncmachine.compiler.model.types.SimpleTypes;
import com.wutka.jfuncmachine.compiler.model.types.Type;

public class ByteConstant extends Expression {
    public final byte value;

    public ByteConstant(byte value) {
        super(null, 0);
        this.value = value;
    }

    public ByteConstant(byte value, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.value = value;
    }

    public void findCaptured(Environment env) {}

    public Type getType() {
        return SimpleTypes.BYTE;
    }

    public void generate(InstructionGenerator gen, Environment env) {
        gen.ldc(value);
    }
}
