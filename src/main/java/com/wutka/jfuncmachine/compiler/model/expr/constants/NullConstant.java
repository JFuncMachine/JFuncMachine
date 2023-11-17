package com.wutka.jfuncmachine.compiler.model.expr.constants;

import com.wutka.jfuncmachine.compiler.classgen.ClassGenerator;
import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.model.expr.Expression;
import com.wutka.jfuncmachine.compiler.model.types.ObjectType;
import com.wutka.jfuncmachine.compiler.model.types.SimpleTypes;
import com.wutka.jfuncmachine.compiler.model.types.Type;

public class NullConstant extends Expression {

    public final ObjectType type;

    public NullConstant() {
        super(null, 0);
        this.type = new ObjectType();
    }

    public NullConstant(ObjectType type) {
        super(null, 0);
        this.type = type;
    }

    public NullConstant(String filename, int lineNumber) {
        super(filename, lineNumber);
        this.type = new ObjectType();
    }

    public NullConstant(ObjectType type, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.type = type;
    }

    public void findCaptured(Environment env) {}

    public Type getType() {
        return type;
    }

    @Override
    public void generate(ClassGenerator gen, Environment env) {
        gen.instGen.aconst_null();
    }
}
