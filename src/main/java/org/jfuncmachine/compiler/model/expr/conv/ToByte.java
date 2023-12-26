package org.jfuncmachine.compiler.model.expr.conv;

import org.jfuncmachine.compiler.classgen.ClassGenerator;
import org.jfuncmachine.compiler.classgen.Environment;
import org.jfuncmachine.compiler.model.expr.Expression;
import org.jfuncmachine.compiler.model.expr.boxing.Box;
import org.jfuncmachine.compiler.model.expr.boxing.Unbox;
import org.jfuncmachine.compiler.model.types.*;

/** Converts an expression to a byte. */
public class ToByte extends Expression {
    /** The expression to convert */
    protected Expression expr;

    /** Create a byte conversion expression
     *
     * @param expr The expression to convert to byte
     */
    public ToByte(Expression expr) {
        super(null, 0);
        this.expr = expr;
    }

    /** Create a byte conversion expression
     *
     * @param expr The expression to convert to byte
     * @param filename The source filename this expression is associated with
     * @param lineNumber The source line number this expression is associated with
     */
    public ToByte(Expression expr, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.expr = expr;
    }

    public Type getType() { return SimpleTypes.BYTE; }

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
            case BooleanType b -> generator.instGen.i2b();
            case ByteType b -> { }
            case CharType c -> generator.instGen.i2b();
            case DoubleType d -> generator.instGen.d2i().i2b();
            case FloatType f -> generator.instGen.f2i().i2b();
            case IntType i -> generator.instGen.i2b();
            case LongType l -> generator.instGen.l2i().i2b();
            case ShortType s -> generator.instGen.i2b();
            default -> throw generateException(
                    String.format("Can't convert %s into byte", exprType));
            
        }
    }
}
