package com.wutka.jfuncmachine.compiler.model.expr.constants;

import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.classgen.InstructionGenerator;
import com.wutka.jfuncmachine.compiler.model.expr.Expression;
import com.wutka.jfuncmachine.compiler.model.types.SimpleTypes;
import com.wutka.jfuncmachine.compiler.model.types.Type;

public class LongConstant extends Expression {
    public final long value;

    public LongConstant(long value) {
        super(null, 0);
        this.value = value;
    }

    public LongConstant(long value, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.value = value;
    }

    public Type getType() {
        return SimpleTypes.LONG;
    }

    public void generate(InstructionGenerator gen, Environment env) {
        gen.ldc(value);
    }
}