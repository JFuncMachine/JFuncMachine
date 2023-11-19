package com.wutka.jfuncmachine.compiler.model.expr.conv;

import com.wutka.jfuncmachine.compiler.classgen.ClassGenerator;
import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.model.expr.Expression;
import com.wutka.jfuncmachine.compiler.model.types.SimpleTypes;
import com.wutka.jfuncmachine.compiler.model.types.Type;
import com.wutka.jfuncmachine.compiler.model.types.UnitType;

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

    public void findCaptured(Environment env) {
        expr.findCaptured(env);
    }

    @Override
    public void generate(ClassGenerator generator, Environment env) {
        Type exprType = expr.getType();

        expr.generate(generator, env);

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
