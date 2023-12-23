package org.jfuncmachine.compiler.model.expr.constants;

import org.jfuncmachine.compiler.classgen.ClassGenerator;
import org.jfuncmachine.compiler.classgen.Environment;
import org.jfuncmachine.compiler.model.expr.Expression;
import org.jfuncmachine.compiler.model.types.SimpleTypes;
import org.jfuncmachine.compiler.model.types.Type;

/** An object constant value */
public class ObjectConstant extends Expression {
    /** The constant value */
    public final Object value;

    /** The object type */
    public final Type objectType;

    /** Create an object constant
     *
     * @param value The constant value
     * @param type The object type
     */
    public ObjectConstant(Object value, Type type) {
        super(null, 0);
        this.value = value;
        this.objectType = type;
    }

    /** Create an object constant
     *
     * @param value The constant value
     * @param type The object type
     * @param filename The source filename this expression is associated with
     * @param lineNumber The source line number this expression is associated with
     */
    public ObjectConstant(Object value, Type type, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.value = value;
        this.objectType = type;
    }

    public Type getType() {
        return objectType;
    }

    public void findCaptured(Environment env) {}

    @Override
    public void generate(ClassGenerator gen, Environment env, boolean inTailPosition) {
        gen.instGen.lineNumber(lineNumber);
        gen.instGen.ldc(value);
    }
}
