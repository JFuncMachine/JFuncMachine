package org.jfuncmachine.compiler.model.expr.conv;

import org.jfuncmachine.compiler.classgen.ClassGenerator;
import org.jfuncmachine.compiler.classgen.Environment;
import org.jfuncmachine.compiler.model.expr.Block;
import org.jfuncmachine.compiler.model.expr.Expression;
import org.jfuncmachine.compiler.model.expr.boxing.Box;
import org.jfuncmachine.compiler.model.expr.boxing.Unbox;
import org.jfuncmachine.compiler.model.expr.constants.NullConstant;
import org.jfuncmachine.compiler.model.types.ObjectType;
import org.jfuncmachine.compiler.model.types.SimpleTypes;
import org.jfuncmachine.compiler.model.types.Type;
import org.jfuncmachine.compiler.model.types.UnitType;

/** Converts an expression to unit (void) type.
 * This has the effect of just popping a value off the stack unless the expression is already of type Unit.
 */
public class ToUnit extends Expression {
    /** The expression to convert to unit */
    protected Expression expr;

    /** Create a unit conversion expression
     *
     * @param expr The expression to convert to unit
     */
    public ToUnit(Expression expr) {
        super(null, 0);
        this.expr = expr;
    }

    /** Create a unit conversion expression
     *
     * @param expr The expression to convert to unit
     * @param filename The source filename this expression is associated with
     * @param lineNumber The source line number this expression is associated with
     */
    public ToUnit(Expression expr, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.expr = expr;
    }

    public Type getType() { return SimpleTypes.UNIT; }

    @Override
    public void reset() {
        expr.reset();
    }

    public void findCaptured(Environment env) {
        expr.findCaptured(env);
    }

    @Override
    public Expression convertToFullTailCalls(boolean inTailPosition) {
        if (inTailPosition) {
            return new Block(new Expression[] { expr, new NullConstant(filename, lineNumber)});
        } else {
            return this;
        }
    }

    @Override
    public void generate(ClassGenerator generator, Environment env, boolean inTailPosition) {
        generator.instGen.lineNumber(lineNumber);
        Type exprType = expr.getType();

        expr.generate(generator, env, false);

        switch (exprType) {
            case UnitType u -> {}
            default -> {
                // double and long types take up to slots on the stack, so use pop2 to pop them
                if (exprType.getStackSize() == 2) {
                    generator.instGen.pop2();
                } else {
                    generator.instGen.pop();
                }
            }
        }
    }
}
