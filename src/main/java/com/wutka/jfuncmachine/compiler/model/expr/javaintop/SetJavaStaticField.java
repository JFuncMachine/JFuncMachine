package com.wutka.jfuncmachine.compiler.model.expr.javaintop;

import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.classgen.InstructionGenerator;
import com.wutka.jfuncmachine.compiler.classgen.Naming;
import com.wutka.jfuncmachine.compiler.model.expr.Expression;
import com.wutka.jfuncmachine.compiler.model.types.SimpleTypes;
import com.wutka.jfuncmachine.compiler.model.types.Type;

public class SetJavaStaticField extends Expression {
    public final String className;
    public final String fieldName;
    public final Type fieldType;
    public final Expression expr;

    public SetJavaStaticField(String className, String fieldName, Type fieldType, Expression expr) {
        super(null, 0);
        this.className = className;
        this.fieldName = fieldName;
        this.fieldType = fieldType;
        this.expr = expr;
    }

    public SetJavaStaticField(String className, String fieldName, Type fieldType, Expression expr,
                              String filename, int lineNumber) {
        super(filename, lineNumber);
        this.className = className;
        this.fieldName = fieldName;
        this.fieldType = fieldType;
        this.expr = expr;
    }

    public Type getType() {
        return SimpleTypes.UNIT;
    }

    public void generate(InstructionGenerator instructionGenerator, Environment env) {
        expr.generate(instructionGenerator, env);

        instructionGenerator.putstatic(Naming.className(className),
                fieldName, fieldType.getTypeDescriptor());
    }
}
