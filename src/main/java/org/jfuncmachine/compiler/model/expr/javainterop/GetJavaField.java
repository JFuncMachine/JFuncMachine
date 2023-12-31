package org.jfuncmachine.compiler.model.expr.javainterop;

import org.jfuncmachine.compiler.classgen.ClassGenerator;
import org.jfuncmachine.compiler.classgen.Environment;
import org.jfuncmachine.compiler.model.expr.Expression;
import org.jfuncmachine.compiler.model.expr.boxing.Box;
import org.jfuncmachine.compiler.model.types.Type;

/** An expression to retrieve a Java field value */
public class GetJavaField extends Expression {
    /** The class containing the field */
    public final String className;
    /** The name of the field */
    public final String fieldName;
    /** The type of the field */
    public final Type fieldType;
    /** The object containing the field */
    public final Expression target;

    /**
     * Create a Java field get expression
     *
     * @param className The name of the class containing the field
     * @param fieldName The name of the field
     * @param fieldType The type of the field
     * @param target    The object containing the field
     */
    public GetJavaField(String className, String fieldName, Type fieldType, Expression target) {
        super(null, 0);
        this.className = className;
        this.fieldName = fieldName;
        this.fieldType = fieldType;
        this.target = target;
    }

    /**
     * Create a Java field get expression
     *
     * @param className The name of the class containing the field
     * @param fieldName The name of the field
     * @param fieldType The type of the field
     * @param target    The object containing the field
     * @param filename The source filename this expression is associated with
     * @param lineNumber The source line number this expression is associated with
     */
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

    @Override
    public void reset() {
        target.reset();
    }

    public void findCaptured(Environment env) {
        target.findCaptured(env);
    }

    @Override
    public Expression convertToFullTailCalls(boolean inTailPosition) {
        if (inTailPosition && fieldType.getJVMTypeRepresentation() != 'A') {
            return new Box(this);
        }
        return this;
    }

    public void generate(ClassGenerator generator, Environment env, boolean inTailPosition) {
        target.generate(generator, env, false);

        generator.instGen.lineNumber(lineNumber);
        generator.instGen.getfield(generator.className(className),
                fieldName, generator.getTypeDescriptor(fieldType));
    }
}
