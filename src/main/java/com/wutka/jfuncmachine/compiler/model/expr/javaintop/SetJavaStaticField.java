package com.wutka.jfuncmachine.compiler.model.expr.javaintop;

import com.wutka.jfuncmachine.compiler.classgen.ClassGenerator;
import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.model.expr.Expression;
import com.wutka.jfuncmachine.compiler.model.expr.boxing.Autobox;
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

    public void findCaptured(Environment env) {
        expr.findCaptured(env);
    }

    public void generate(ClassGenerator generator, Environment env) {
        if (generator.options.autobox) {
            Autobox.autobox(expr, fieldType).generate(generator, env);
        } else {
            expr.generate(generator, env);
        }

        generator.instGen.putstatic(generator.className(className),
                fieldName, generator.getTypeDescriptor(fieldType));
    }
}
