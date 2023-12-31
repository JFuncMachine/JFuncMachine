package org.jfuncmachine.compiler.model.expr.boxing;

import org.jfuncmachine.compiler.classgen.ClassGenerator;
import org.jfuncmachine.compiler.classgen.Environment;
import org.jfuncmachine.compiler.model.expr.*;
import org.jfuncmachine.compiler.model.expr.constants.NullConstant;
import org.jfuncmachine.compiler.model.expr.javainterop.CallJavaStaticMethod;
import org.jfuncmachine.compiler.model.types.ObjectType;
import org.jfuncmachine.compiler.model.types.SimpleTypes;
import org.jfuncmachine.compiler.model.types.Type;

/** Boxes an expression - converts an expression returning a native type to the Object equivalent
 * of that type (e.g. byte becomes java.lang.Byte, double becomes java.lang.Double).
 */
public class Box extends Expression {
    /** The expression to box */
    public final Expression expr;
    /** The type of the expression */
    public final Type boxType;
    /** An optional desired box type, such as boxing a byte as a java.lang.Integer instead of java.lang.Byte */
    public final Type desiredBoxType;

    /** Create a box for an expression
     *
     * @param expr The expression to box, the box type is derived from the expression type
     */
    public Box(Expression expr) {
        super(null, 0);
        this.expr = expr;
        this.boxType = expr.getType();
        this.desiredBoxType = null;
    }

    /** Create a box for an expression
     *
     * @param expr The expression to box, the box type is derived from the expression type
     * @param filename The source filename this expression is associated with
     * @param lineNumber The source line number this expression is associated with
     */
    public Box(Expression expr, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.expr = expr;
        this.boxType = expr.getType();
        this.desiredBoxType = null;
    }

    /** Create a box for an expression with a specific box type
     *
     * @param expr The expression to box, the box type is derived from the expression type
     * @param desiredBoxType The type of the box to use, in case it is different than what would
     *                       otherwise be derived from the expression type
     */
    public Box(Expression expr, Type desiredBoxType) {
        super(null, 0);
        this.expr = expr;
        this.boxType = expr.getType();
        this.desiredBoxType = desiredBoxType;
    }

    /** Create a box for an expression with a specific box type
     *
     * @param expr The expression to box, the box type is derived from the expression type
     * @param desiredBoxType The type of the box to use, in case it is different than what would
     *                       otherwise be derived from the expression type
     * @param filename The source filename this expression is associated with
     * @param lineNumber The source line number this expression is associated with
     */
    public Box(Expression expr, Type desiredBoxType, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.expr = expr;
        this.boxType = expr.getType();
        this.desiredBoxType = desiredBoxType;
    }

    /** Create a box for an expression with a specific box type
     *
     * @param expr The expression to box, the box type is derived from the expression type
     * @param unboxedType The type of the unboxed expression (if it is different from that of expr)
     * @param desiredBoxType The type of the box to use, in case it is different than what would
     *                       otherwise be derived from the expression type
     */
    public Box(Expression expr, Type unboxedType, Type desiredBoxType) {
        super(null, 0);
        this.expr = expr;
        this.boxType = unboxedType;
        this.desiredBoxType = desiredBoxType;
    }

    /** Create a box for an expression with a specific box type
     *
     * @param expr The expression to box, the box type is derived from the expression type
     * @param unboxedType The type of the unboxed expression (if it is different from that of expr)
     * @param desiredBoxType The type of the box to use, in case it is different than what would
     *                       otherwise be derived from the expression type
     * @param filename The source filename this expression is associated with
     * @param lineNumber The source line number this expression is associated with
     */
    public Box(Expression expr, Type unboxedType, Type desiredBoxType, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.expr = expr;
        this.boxType = unboxedType;
        this.desiredBoxType = desiredBoxType;
    }

    public Type getType() {
        Type exprType = boxType;

        String boxTypeName = exprType.getBoxTypeName();
        if (boxTypeName != null) {
            return new ObjectType(boxTypeName);
        } else {
            return exprType;
        }
    }

    @Override
    public void findCaptured(Environment env) {
        expr.findCaptured(env);
    }

    @Override
    public void reset() {
        expr.reset();
    }

    @Override
    public Expression convertToFullTailCalls(boolean inTailPosition) {
        if (inTailPosition) {
            Expression convertedExpression = expr.convertToFullTailCalls(true);
            if (convertedExpression.getType().equals(desiredBoxType)) {
                return convertedExpression;
            }
            return new Box(expr.convertToFullTailCalls(false), boxType, desiredBoxType,
                    filename, lineNumber);
        }
        return this;
    }

    @Override
    public void generate(ClassGenerator generator, Environment env, boolean inTailPosition) {
        String boxName;

        if (desiredBoxType == null || desiredBoxType.equals(new ObjectType())) {
            boxName = boxType.getBoxTypeName();
        } else {
            boxName = ((ObjectType) desiredBoxType).className;
        }

        if (expr.getType().equals(SimpleTypes.UNIT) && (desiredBoxType == null ||
                desiredBoxType.equals(new ObjectType()))) {
            expr.generate(generator, env, inTailPosition);
            new NullConstant().generate(generator, env, inTailPosition);
            return;
        }

        if (generator.options.fullTailCalls) {
            switch (expr) {
                case CallStaticMethod csm -> { expr.generate(generator, env, inTailPosition); return; }
                case CallMethod cm -> { expr.generate(generator, env, inTailPosition); return; }
                case LocalRecurse r -> { expr.generate(generator, env, inTailPosition); return; }
                default -> {}
            }
        } else {
            switch (expr) {
                case CallTailCallMethod ctcm -> { expr.generate(generator, env, inTailPosition); return; }
                case CallTailCallStaticMethod ctcsm -> { expr.generate(generator, env, inTailPosition); return; }
                default -> {}
            }
        }

        if (boxName == null) {
            expr.generate(generator, env, inTailPosition);
            return;
        }

        Type boxArgumentType = new ObjectType(boxName).getUnboxedType();

        CallJavaStaticMethod method = new CallJavaStaticMethod(boxName, "valueOf",
                new Type[] { boxArgumentType }, new Expression[] { expr },
                new ObjectType(boxName), filename, lineNumber);
        method.generate(generator, env, inTailPosition);
    }
}
