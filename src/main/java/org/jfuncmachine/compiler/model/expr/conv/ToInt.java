package org.jfuncmachine.compiler.model.expr.conv;

import org.jfuncmachine.compiler.classgen.ClassGenerator;
import org.jfuncmachine.compiler.classgen.Environment;
import org.jfuncmachine.compiler.model.expr.Expression;
import org.jfuncmachine.compiler.model.types.*;

/** Converts an expression to an int */
public class ToInt extends Expression {
    /** The expression to convert to an int */
    protected Expression expr;

    /** Create an int conversion expression
     *
     * @param expr The expression to convert to an int
     */
    public ToInt(Expression expr) {
        super(null, 0);
        this.expr = expr;
    }

    /** Create an int conversion expression
     *
     * @param expr The expression to convert to an int
     * @param filename The source filename this expression is associated with
     * @param lineNumber The source line number this expression is associated with
     */
    public ToInt(Expression expr, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.expr = expr;
    }

    public Type getType() { return SimpleTypes.INT; }

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
            case BooleanType b -> {}
            case ByteType b -> {}
            case CharType c -> {}
            case DoubleType d -> generator.instGen.d2i();
            case FloatType f -> generator.instGen.f2i();
            case IntType i -> {}
            case LongType l -> generator.instGen.l2i();
            case ShortType i -> {}
            default -> throw generateException(
                    String.format("Can't convert %s into int", exprType));
        }

        if (inTailPosition && generator.currentMethod.isTailCallable) {
            generator.instGen.generateBox(SimpleTypes.INT.getBoxType());
        }
    }
}
