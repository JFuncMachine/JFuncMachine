package org.jfuncmachine.jfuncmachine.compiler.model.expr.constants;

import org.jfuncmachine.jfuncmachine.compiler.classgen.ClassGenerator;
import org.jfuncmachine.jfuncmachine.compiler.classgen.Environment;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.Expression;
import org.jfuncmachine.jfuncmachine.compiler.model.types.SimpleTypes;
import org.jfuncmachine.jfuncmachine.compiler.model.types.Type;

/** An int constant expression */
public class IntConstant extends Expression {
    /** The constant value */
    public final int value;

    /** Create an int constant
     *
     * @param value The constant value
     */
    public IntConstant(int value) {
        super(null, 0);
        this.value = value;
    }

    /** Create an int constant
     *
     * @param value The constant value
     * @param filename The source filename this expression is associated with
     * @param lineNumber The source line number this expression is associated with
     */
    public IntConstant(int value, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.value = value;
    }

    public Type getType() {
        return SimpleTypes.INT;
    }

    public void findCaptured(Environment env) {}

    @Override
    public void generate(ClassGenerator gen, Environment env, boolean inTailPosition) {
        gen.instGen.ldc(value);
        if (inTailPosition && gen.options.fullTailCalls) {
            gen.instGen.generateBox(SimpleTypes.INT.getBoxType());
        }
    }
}