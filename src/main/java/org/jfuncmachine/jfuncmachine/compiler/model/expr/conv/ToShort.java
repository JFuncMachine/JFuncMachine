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

/** Converts an expression to a short */
public class ToShort extends Expression {
    /** The expression to convert to a short */
    protected Expression expr;

    /** Create a short conversion expression
     *
     * @param expr The expression to convert to a short
     */
    public ToShort(Expression expr) {
        super(null, 0);
        this.expr = expr;
    }

    /** Create a short conversion expression
     *
     * @param expr The expression to convert to a short
     * @param filename The source filename this expression is associated with
     * @param lineNumber The source line number this expression is associated with
     */
    public ToShort(Expression expr, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.expr = expr;
    }

    public Type getType() { return SimpleTypes.SHORT; }

    public void findCaptured(Environment env) {
        expr.findCaptured(env);
    }

    @Override
    public void generate(ClassGenerator generator, Environment env, boolean inTailPosition) {
        Type exprType = expr.getType();

        expr.generate(generator, env, false);

        switch (exprType) {
            case BooleanType b -> generator.instGen.i2s();
            case ByteType b -> generator.instGen.i2s();
            case CharType c -> generator.instGen.i2s();
            case DoubleType d -> generator.instGen.d2i().i2s();
            case FloatType f -> generator.instGen.f2i().i2s();
            case IntType i -> generator.instGen.i2s();
            case LongType l -> generator.instGen.l2i().i2s();
            case ShortType s -> {}
            default -> throw generateException(
                    String.format("Can't convert %s into short", exprType));
        }

        if (inTailPosition && generator.currentMethod.isTailCallable) {
            generator.instGen.generateBox(SimpleTypes.SHORT.getBoxType());
        }
    }
}
