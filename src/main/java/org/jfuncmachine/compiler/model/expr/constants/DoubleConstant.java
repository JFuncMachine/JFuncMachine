package org.jfuncmachine.compiler.model.expr.constants;

import org.jfuncmachine.compiler.classgen.ClassGenerator;
import org.jfuncmachine.compiler.classgen.Environment;
import org.jfuncmachine.compiler.model.expr.Expression;
import org.jfuncmachine.compiler.model.types.SimpleTypes;
import org.jfuncmachine.compiler.model.types.Type;

/** A double constant expression */
public class DoubleConstant extends Expression {
    /** The constant value */
    public final double value;

    /** Create a double constant
     *
     * @param value The constant value
     */
    public DoubleConstant(double value) {
        super(null, 0);
        this.value = value;
    }

    /** Create a double constant
     *
     * @param value The constant value
     * @param filename The source filename this expression is associated with
     * @param lineNumber The source line number this expression is associated with
     */
    public DoubleConstant(double value, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.value = value;
    }

    public Type getType() {
        return SimpleTypes.DOUBLE;
    }

    public void findCaptured(Environment env) {}

    @Override
    public Expression convertToFullTailCalls(boolean inTailPosition) {
        return this;
    }

    @Override
    public void generate(ClassGenerator gen, Environment env, boolean inTailPosition) {
        gen.instGen.lineNumber(lineNumber);
        gen.instGen.ldc(value);
        if (inTailPosition && gen.currentMethod.isTailCallable) {
            gen.instGen.generateBox(SimpleTypes.DOUBLE.getBoxType());
        }
    }
}
