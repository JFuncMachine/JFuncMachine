package com.wutka.jfuncmachine.compiler.model.expr.javaintop;

import com.wutka.jfuncmachine.compiler.classgen.ClassGenerator;
import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.model.expr.Expression;
import com.wutka.jfuncmachine.compiler.model.expr.boxing.Autobox;
import com.wutka.jfuncmachine.compiler.model.types.SimpleTypes;
import com.wutka.jfuncmachine.compiler.model.types.Type;

/** An expression to set a Java field value */
public class SetJavaField extends Expression {
    /** The name of the class containing the field */
    public final String className;
    /** The name of the field */
    public final String fieldName;
    /** The object containing the field */
    public final Expression target;
    /** The value to store in the field */
    public final Expression expr;
    /** The type of the field */
    public final Type fieldType;

    /**
     * Create a Java field set expression
     *
     * @param className The name of the class containing the field
     * @param fieldName The name of the field
     * @param fieldType The type of the field
     * @param target    The object containing the field
     * @param expr      The value to store in the field
     */
    public SetJavaField(String className, String fieldName, Type fieldType, Expression target, Expression expr) {
        super(null, 0);
        this.className = className;
        this.fieldName = fieldName;
        this.target = target;
        this.fieldType = fieldType;
        this.expr = expr;
    }

    /**
     * Create a Java field set expression
     *
     * @param className  The name of the class containing the field
     * @param fieldName  The name of the field
     * @param fieldType  The type of the field
     * @param target     The object containing the field
     * @param expr       The value to store in the field
     * @param filename   The source filename this expression is associated with
     * @param lineNumber The source line number this expression is associated with
     */
    public SetJavaField(String className, String fieldName, Type fieldType, Expression target, Expression expr,
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

    public void findCaptured(Environment env) {
        target.findCaptured(env);
        expr.findCaptured(env);
    }

    public void generate(ClassGenerator generator, Environment env) {
        target.generate(generator, env);

        if (generator.options.autobox) {
            Autobox.autobox(expr, fieldType).generate(generator, env);
        } else {
            expr.generate(generator, env);
        }

        generator.instGen.putfield(generator.className(className),
                fieldName, generator.getTypeDescriptor(fieldType));
    }
}