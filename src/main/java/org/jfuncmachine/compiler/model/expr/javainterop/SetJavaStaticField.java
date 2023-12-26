package org.jfuncmachine.compiler.model.expr.javainterop;

import org.jfuncmachine.compiler.classgen.ClassGenerator;
import org.jfuncmachine.compiler.classgen.Environment;
import org.jfuncmachine.compiler.model.expr.Expression;
import org.jfuncmachine.compiler.model.expr.boxing.Autobox;
import org.jfuncmachine.compiler.model.expr.conv.ToUnit;
import org.jfuncmachine.compiler.model.types.SimpleTypes;
import org.jfuncmachine.compiler.model.types.Type;

/** An expression to set a static Java field value */
public class SetJavaStaticField extends Expression {
    /** The name of the class containing the field */
    public final String className;
    /** The name of the field */
    public final String fieldName;
    /** The type of the field */
    public final Type fieldType;
    /** The value to store in the field */
    public final Expression expr;

    /** Create a static Java field set expression
     *
     * @param className The name of the class containing the field
     * @param fieldName The name of the field
     * @param fieldType The type of the field
     * @param expr The value to store in the field
     */
    public SetJavaStaticField(String className, String fieldName, Type fieldType, Expression expr) {
        super(null, 0);
        this.className = className;
        this.fieldName = fieldName;
        this.fieldType = fieldType;
        this.expr = expr;
    }

    /** Create a static Java field set expression
     *
     * @param className The name of the class containing the field
     * @param fieldName The name of the field
     * @param fieldType The type of the field
     * @param expr The value to store in the field
     * @param filename The source filename this expression is associated with
     * @param lineNumber The source line number this expression is associated with
     */
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

    @Override
    public Expression convertToFullTailCalls(boolean inTailPosition) {
        if (inTailPosition) {
            return (new ToUnit(this, filename, lineNumber)).convertToFullTailCalls(true);
        }
        return this;
    }

    public void generate(ClassGenerator generator, Environment env, boolean inTailPosition) {
        if (generator.options.autobox) {
            Autobox.autobox(expr, fieldType).generate(generator, env, false);
        } else {
            expr.generate(generator, env, false);
        }

        generator.instGen.lineNumber(lineNumber);
        generator.instGen.putstatic(generator.className(className),
                fieldName, generator.getTypeDescriptor(fieldType));

        if (inTailPosition && generator.currentMethod.isTailCallable) {
            generator.instGen.aconst_null();
        }
    }
}
