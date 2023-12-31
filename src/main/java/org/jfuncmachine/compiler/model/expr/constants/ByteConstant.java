package org.jfuncmachine.compiler.model.expr.constants;

import org.jfuncmachine.compiler.classgen.ClassGenerator;
import org.jfuncmachine.compiler.classgen.Environment;
import org.jfuncmachine.compiler.model.expr.Expression;
import org.jfuncmachine.compiler.model.types.SimpleTypes;
import org.jfuncmachine.compiler.model.types.Type;

/** A byte constant expression */
public class ByteConstant extends Expression {
    /** The constant value */
    public final byte value;

    /** Create a byte constant
     *
     * @param value The value of the constant
     */
    public ByteConstant(byte value) {
        super(null, 0);
        this.value = value;
    }

    /** Create a byte constant for a particular byte value
     *
     * @param value The value of the constant
     * @param filename The source filename this expression is associated with
     * @param lineNumber The source line number this expression is associated with
     */
    public ByteConstant(byte value, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.value = value;
    }

    public void findCaptured(Environment env) {}

    public Type getType() {
        return SimpleTypes.BYTE;
    }

    @Override
    public Expression convertToFullTailCalls(boolean inTailPosition) {
        return this;
    }

    @Override
    public void generate(ClassGenerator gen, Environment env, boolean inTailPosition) {
        gen.instGen.lineNumber(lineNumber);
        gen.instGen.bipush(value);
        if (inTailPosition && gen.currentMethod.isTailCallable) {
            gen.instGen.generateBox(SimpleTypes.BYTE.getBoxType());
        }
    }
}
