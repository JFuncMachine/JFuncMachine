package org.jfuncmachine.jfuncmachine.compiler.model.expr.conv;

import org.jfuncmachine.jfuncmachine.compiler.classgen.ClassGenerator;
import org.jfuncmachine.jfuncmachine.compiler.classgen.Environment;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.Expression;
import org.jfuncmachine.jfuncmachine.compiler.model.types.BooleanType;
import org.jfuncmachine.jfuncmachine.compiler.model.types.ByteType;
import org.jfuncmachine.jfuncmachine.compiler.model.types.CharType;
import org.jfuncmachine.jfuncmachine.compiler.model.types.DoubleType;
import org.jfuncmachine.jfuncmachine.compiler.model.types.FloatType;
import org.jfuncmachine.jfuncmachine.compiler.model.types.IntType;
import org.jfuncmachine.jfuncmachine.compiler.model.types.LongType;
import org.jfuncmachine.jfuncmachine.compiler.model.types.ShortType;
import org.jfuncmachine.jfuncmachine.compiler.model.types.SimpleTypes;
import org.jfuncmachine.jfuncmachine.compiler.model.types.Type;

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
    public void resetLabels() {
        expr.resetLabels();
    }

    public void findCaptured(Environment env) {
        expr.findCaptured(env);
    }

    @Override
    public void generate(ClassGenerator generator, Environment env, boolean inTailPosition) {
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
