package org.jfuncmachine.compiler.model.expr.conv;

import org.jfuncmachine.compiler.classgen.ClassGenerator;
import org.jfuncmachine.compiler.classgen.Environment;
import org.jfuncmachine.compiler.model.expr.Expression;
import org.jfuncmachine.compiler.model.types.*;

/** Converts an expression to a float */
public class ToFloat extends Expression {
    /** The expression to convert to a float */
    protected Expression expr;

    /** Create a float conversion expression
     *
     * @param expr The expression to convert to a float
     */
    public ToFloat(Expression expr) {
        super(null, 0);
        this.expr = expr;
    }

    /** Create a float conversion expression
     *
     * @param expr The expression to convert to a float
     * @param filename The source filename this expression is associated with
     * @param lineNumber The source line number this expression is associated with
     */
    public ToFloat(Expression expr, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.expr = expr;
    }

    public Type getType() { return SimpleTypes.FLOAT; }

    @Override
    public void reset() {
        expr.reset();
    }

    public void findCaptured(Environment env) {
        expr.findCaptured(env);
    }

    @Override
    public void generate(ClassGenerator generator, Environment env, boolean inTailPosition) {
        Type exprType = expr.getType();

        expr.generate(generator, env, false);

        switch (exprType) {
            case BooleanType b -> generator.instGen.i2f();
            case ByteType b -> generator.instGen.i2f();
            case CharType b -> generator.instGen.i2f();
            case DoubleType d -> generator.instGen.d2f();
            case FloatType c -> {}
            case IntType i -> generator.instGen.i2f();
            case LongType l -> generator.instGen.l2f();
            case ShortType s -> generator.instGen.i2f();
            default -> throw generateException(
                    String.format("Can't convert %s into float", exprType));
        }

        if (inTailPosition && generator.currentMethod.isTailCallable) {
            generator.instGen.generateBox(SimpleTypes.FLOAT.getBoxType());
        }
    }
}
