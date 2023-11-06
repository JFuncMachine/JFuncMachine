package com.wutka.jfuncmachine.compiler.model.expr.javaintop;

import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.classgen.InstructionGenerator;
import com.wutka.jfuncmachine.compiler.classgen.Naming;
import com.wutka.jfuncmachine.compiler.model.expr.Expression;
import com.wutka.jfuncmachine.compiler.model.types.Type;

public class GetJavaStaticField extends Expression {
    public final String className;
    public final String fieldName;
    public final Type fieldType;

    public GetJavaStaticField(String className, String fieldName, Type fieldType) {
        super(null, 0);
        this.className = className;
        this.fieldName = fieldName;
        this.fieldType = fieldType;
    }

    public GetJavaStaticField(String className, String fieldName, Type fieldType, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.className = className;
        this.fieldName = fieldName;
        this.fieldType = fieldType;
    }

    public Type getType() {
        return this.fieldType;
    }

    public void findCaptured(Environment env) {}

    public void generate(InstructionGenerator instructionGenerator, Environment env) {
        instructionGenerator.getstatic(Naming.className(className),
                fieldName, fieldType.getTypeDescriptor());
    }
}
