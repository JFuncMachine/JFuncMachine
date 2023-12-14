package org.jfuncmachine.compiler.model.expr.constants;

import org.jfuncmachine.compiler.classgen.ClassGenerator;
import org.jfuncmachine.compiler.classgen.Environment;
import org.jfuncmachine.compiler.model.expr.Expression;
import org.jfuncmachine.compiler.model.types.SimpleTypes;
import org.jfuncmachine.compiler.model.types.Type;

/** A boolean constant expression */
public class BooleanConstant extends Expression {
    /** The constant value */
    public final boolean value;

    /** Create a boolean constant
     *
     * @param value The value of the constant
     */
    public BooleanConstant(boolean value) {
        super(null, 0);
        this.value = value;
    }

    /** Create a boolean constant for a particular byte value
     *
     * @param value The value of the constant
     * @param filename The source filename this expression is associated with
     * @param lineNumber The source line number this expression is associated with
     */
    public BooleanConstant(boolean value, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.value = value;
    }

    public void findCaptured(Environment env) {}

    public Type getType() {
        return SimpleTypes.BOOLEAN;
    }

    @Override
    public void generate(ClassGenerator gen, Environment env, boolean inTailPosition) {
        gen.instGen.lineNumber(lineNumber);
        if (value) {
            gen.instGen.iconst_1();
        } else {
            gen.instGen.iconst_0();
        }
        if (inTailPosition && gen.currentMethod.isTailCallable) {
            gen.instGen.generateBox(SimpleTypes.BOOLEAN.getBoxType());
        }
    }
}
