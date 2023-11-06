package com.wutka.jfuncmachine.compiler.model.expr.constants;

import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.classgen.InstructionGenerator;
import com.wutka.jfuncmachine.compiler.model.expr.Expression;
import com.wutka.jfuncmachine.compiler.model.types.SimpleTypes;
import com.wutka.jfuncmachine.compiler.model.types.Type;

public class FloatConstant extends Expression {
    public final float value;

    public FloatConstant(float value) {
        super(null, 0);
        this.value = value;
    }

    public FloatConstant(float value, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.value = value;
    }

    public Type getType() {
        return SimpleTypes.FLOAT;
    }

    public void generate(InstructionGenerator gen, Environment env) {
        gen.ldc(value);
    }
}
