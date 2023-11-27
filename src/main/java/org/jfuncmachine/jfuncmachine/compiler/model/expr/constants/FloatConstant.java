package org.jfuncmachine.jfuncmachine.compiler.model.expr.constants;

import org.jfuncmachine.jfuncmachine.compiler.classgen.ClassGenerator;
import org.jfuncmachine.jfuncmachine.compiler.classgen.Environment;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.Expression;
import org.jfuncmachine.jfuncmachine.compiler.model.types.SimpleTypes;
import org.jfuncmachine.jfuncmachine.compiler.model.types.Type;

/** A float constant expression */
public class FloatConstant extends Expression {
    /** The constant value */
    public final float value;

    /** Create a float constant
     *
     * @param value The constant value
     */
    public FloatConstant(float value) {
        super(null, 0);
        this.value = value;
    }

    /** Create a float constant
     *
     * @param value The constant value
     * @param filename The source filename this expression is associated with
     * @param lineNumber The source line number this expression is associated with
     */
    public FloatConstant(float value, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.value = value;
    }

    public Type getType() {
        return SimpleTypes.FLOAT;
    }

    public void findCaptured(Environment env) {}

    @Override
    public void generate(ClassGenerator gen, Environment env, boolean inTailPosition) {
        gen.instGen.ldc(value);
    }
}
