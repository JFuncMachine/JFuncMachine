package org.jfuncmachine.compiler.model.expr.conv;

import org.jfuncmachine.compiler.classgen.ClassGenerator;
import org.jfuncmachine.compiler.classgen.Environment;
import org.jfuncmachine.compiler.model.expr.Expression;
import org.jfuncmachine.compiler.model.expr.boxing.Box;
import org.jfuncmachine.compiler.model.expr.boxing.Unbox;
import org.jfuncmachine.compiler.model.types.*;

/** Converts an expression to a double */
public class ToDouble extends Expression {
    /** The expression to convert to a double */
    protected Expression expr;

    /** Create a double conversion expression
     *
     * @param expr The expression to convert to a double
     */
    public ToDouble(Expression expr) {
        super(null, 0);
        this.expr = expr;
    }

    /** Create a double conversion expression
     *
     * @param expr The expression to convert to a double
     * @param filename The source filename this expression is associated with
     * @param lineNumber The source line number this expression is associated with
     */
    public ToDouble(Expression expr, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.expr = expr;
    }

    public Type getType() { return SimpleTypes.DOUBLE; }

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
            return new Box(this);
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
            case BooleanType b -> generator.instGen.i2d();
            case ByteType b -> generator.instGen.i2d();
            case CharType c -> generator.instGen.i2d();
            case DoubleType c -> {}
            case FloatType c -> generator.instGen.f2d();
            case IntType i -> generator.instGen.i2d();
            case LongType l -> generator.instGen.l2d();
            case ShortType s -> generator.instGen.i2d();
            default -> throw generateException(
                    String.format("Can't convert %s into double", exprType));
        }
    }
}
