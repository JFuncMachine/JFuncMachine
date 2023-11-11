package com.wutka.jfuncmachine.compiler.model.expr;

import com.wutka.jfuncmachine.compiler.classgen.ClassGenerator;
import com.wutka.jfuncmachine.compiler.classgen.EnvVar;
import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.model.types.*;
import org.objectweb.asm.Opcodes;

public class Throw extends Expression {
    Expression expr;
    public Type type;

    public Throw(Type type, Expression expr) {
        super(null, 0);
        this.expr = expr;
        this.type = type;
    }

    public Throw(Type type, Expression expr, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.expr = expr;
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public void findCaptured(Environment env) {
        expr.findCaptured(env);
    }

    @Override
    public void generate(ClassGenerator generator, Environment env) {
        expr.generate(generator, env);
        generator.instGen.athrow();
    }
}
