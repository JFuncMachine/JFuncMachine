package com.wutka.jfuncmachine.compiler.model.expr.javaintop;

import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.classgen.InstructionGenerator;
import com.wutka.jfuncmachine.compiler.classgen.Naming;
import com.wutka.jfuncmachine.compiler.model.expr.Expression;
import com.wutka.jfuncmachine.compiler.model.types.Type;

public class GetJavaField extends Expression {
    public final String className;
    public final String fieldName;
    public final Type fieldType;
    public final Expression target;

    public GetJavaField(String className, String fieldName, Expression target, Type fieldType) {
        super(null, 0);
        this.className = className;
        this.fieldName = fieldName;
        this.fieldType = fieldType;
        this.target = target;
    }

    public GetJavaField(String className, String fieldName, Expression target, Type fieldType,
                        String filename, int lineNumber) {
        super(filename, lineNumber);
        this.className = className;
        this.fieldName = fieldName;
        this.fieldType = fieldType;
        this.target = target;
    }

    public Type getType() {
        return this.fieldType;
    }

    public void findCaptured(Environment env) {
        target.findCaptured(env);
    }

    public void generate(InstructionGenerator instructionGenerator, Environment env) {
        target.generate(instructionGenerator, env);

        instructionGenerator.getfield(Naming.className(className),
                fieldName, fieldType.getTypeDescriptor());
    }
}
