package org.jfuncmachine.compiler.model.expr.javainterop;

import org.jfuncmachine.compiler.classgen.ClassGenerator;
import org.jfuncmachine.compiler.classgen.Environment;
import org.jfuncmachine.compiler.model.expr.Expression;
import org.jfuncmachine.compiler.model.types.Type;

/** An expression to retrieve a static Java field value */
public class GetJavaStaticField extends Expression {
    /** The name of the class containing the field */
    public final String className;
    /** The name of the field */
    public final String fieldName;
    /** The type of the field */
    public final Type fieldType;

    /** Create a Java static field get expression
     *
     * @param className The name of the class containing the field
     * @param fieldName The name of the field
     * @param fieldType The type of the field
     */
    public GetJavaStaticField(String className, String fieldName, Type fieldType) {
        super(null, 0);
        this.className = className;
        this.fieldName = fieldName;
        this.fieldType = fieldType;
    }

    /** Create a Java static field get expression
     *
     * @param className The name of the class containing the field
     * @param fieldName The name of the field
     * @param fieldType The type of the field
     * @param filename The source filename this expression is associated with
     * @param lineNumber The source line number this expression is associated with
     */
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

    public void generate(ClassGenerator generator, Environment env, boolean inTailPosition) {
        generator.instGen.getstatic(generator.className(className),
                fieldName, generator.getTypeDescriptor(fieldType));

        if (inTailPosition && generator.currentMethod.isTailCallable) {
            generator.instGen.generateBox(fieldType);
        }
    }
}
