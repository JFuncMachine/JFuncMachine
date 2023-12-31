package org.jfuncmachine.compiler.model.expr;

import org.jfuncmachine.compiler.classgen.ClassGenerator;
import org.jfuncmachine.compiler.classgen.Environment;
import org.jfuncmachine.compiler.model.types.SimpleTypes;
import org.jfuncmachine.compiler.model.types.Type;
import org.jfuncmachine.compiler.model.types.UnitType;

/** A sequence of expressions. The return value of the block is the value of the last expression.
 * If any of the expressions before the last one leave a value on the stack, that value is popped off
 * the stack.
 */
public class Block extends Expression {
    /** The expressions to execute */
    public final Expression[] expressions;

    /** Create a new block of expressions
     *
     * @param expressions The expressions to execute
     */
    public Block (Expression[] expressions) {
        super(null, 0);
        this.expressions = expressions;
    }

    /** Create a new block of expressions
     *
     * @param expressions The expressions to execute
     * @param filename The source filename this expression is associated with
     * @param lineNumber The source line number this expression is associated with
     */
    public Block (Expression[] expressions, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.expressions = expressions;
    }

    public Type getType() {
        if (expressions.length > 0) {
            return expressions[expressions.length - 1].getType();
        } else {
            return SimpleTypes.UNIT;
        }
    }

    @Override
    public void reset() {
        for (Expression expr: expressions) {
            expr.reset();
        }
    }

    public void findCaptured(Environment env) {
        for (Expression expr: expressions) {
            expr.findCaptured(env);
        }
    }

    @Override
    public Expression convertToFullTailCalls(boolean inTailPosition) {
        if (inTailPosition && getType().getJVMTypeRepresentation() != 'A') {
            Expression[] newExpressions = new Expression[expressions.length];
            for (int i = 0; i < expressions.length - 1; i++) {
                newExpressions[i] = expressions[i];
            }
            newExpressions[expressions.length-1] =
                    expressions[expressions.length-1].convertToFullTailCalls(true);
            return new Block(newExpressions);
        }
        return this;
    }

    @Override
    public void generate(ClassGenerator generator, Environment env, boolean inTailPosition) {
        for (int i=0; i < expressions.length; i++) {
            expressions[i].generate(generator, env, i == expressions.length-1);
            if (i < expressions.length-1 && !(expressions[i].getType() instanceof UnitType)) {
                if (expressions[i].getType().getStackSize() == 2) {
                    generator.instGen.pop2();
                } else {
                    generator.instGen.pop();
                }
            }
        }
    }
}
