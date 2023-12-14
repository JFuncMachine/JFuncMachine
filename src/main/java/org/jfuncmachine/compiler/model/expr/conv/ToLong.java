package org.jfuncmachine.compiler.model.expr.conv;

import org.jfuncmachine.compiler.classgen.ClassGenerator;
import org.jfuncmachine.compiler.classgen.Environment;
import org.jfuncmachine.compiler.model.expr.Expression;
import org.jfuncmachine.compiler.model.types.*;

/** Converts an expression to a long */
public class ToLong extends Expression {
    /** The expression to convert to a long */
    protected Expression expr;

    /** Create a long conversion expression
     *
     * @param expr The expression to convert to a long
     */
    public ToLong(Expression expr) {
        super(null, 0);
        this.expr = expr;
    }

    /** Create a long conversion expression
     *
     * @param expr The expression to convert to a long
     * @param filename The source filename this expression is associated with
     * @param lineNumber The source line number this expression is associated with
     */
    public ToLong(Expression expr, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.expr = expr;
    }

    public Type getType() { return SimpleTypes.LONG; }

    @Override
    public void reset() {
        expr.reset();
    }

    public void findCaptured(Environment env) {
        expr.findCaptured(env);
    }

    @Override
    public void generate(ClassGenerator generator, Environment env, boolean inTailPosition) {
        generator.instGen.lineNumber(lineNumber);
        Type exprType = expr.getType();

        expr.generate(generator, env, false);

        switch (exprType) {
            case BooleanType b -> generator.instGen.i2l();
            case ByteType b -> generator.instGen.i2l();
            case CharType c -> generator.instGen.i2l();
            case DoubleType d -> generator.instGen.d2l();
            case FloatType f -> generator.instGen.f2l();
            case IntType i -> generator.instGen.i2l();
            case LongType l -> {}
            case ShortType s -> generator.instGen.i2l();
            default -> throw generateException(
                    String.format("Can't convert %s into long", exprType));
        }
        if (inTailPosition && generator.currentMethod.isTailCallable) {
            generator.instGen.generateBox(SimpleTypes.LONG.getBoxType());
        }
    }
}
