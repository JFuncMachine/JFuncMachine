package com.wutka.jfuncmachine.compiler.model.expr.javaintop;

import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.classgen.InstructionGenerator;
import com.wutka.jfuncmachine.compiler.classgen.Naming;
import com.wutka.jfuncmachine.compiler.model.expr.Expression;
import com.wutka.jfuncmachine.compiler.model.types.SimpleTypes;
import com.wutka.jfuncmachine.compiler.model.types.Type;

public class SetJavaField extends Expression {
    public final String className;
    public final String fieldName;
    public final Expression target;
    public final Expression expr;
    public final Type fieldType;

    public SetJavaField(String className, String fieldName, Expression target, Type fieldType, Expression expr) {
        super(null, 0);
        this.className = className;
        this.fieldName = fieldName;
        this.target = target;
        this.fieldType = fieldType;
        this.expr = expr;
    }

    public SetJavaField(String className, String fieldName, Expression target, Type fieldType, Expression expr,
                        String filename, int lineNumber) {
        super(filename, lineNumber);
        this.className = className;
        this.fieldName = fieldName;
        this.fieldType = fieldType;
        this.target = target;
        this.expr = expr;
    }

    public Type getType() {
        return SimpleTypes.UNIT;
    }

    public void generate(InstructionGenerator instructionGenerator, Environment env) {
        target.generate(instructionGenerator, env);
        expr.generate(instructionGenerator, env);

        instructionGenerator.putfield(Naming.className(className),
                fieldName, fieldType.getTypeDescriptor());
    }
}