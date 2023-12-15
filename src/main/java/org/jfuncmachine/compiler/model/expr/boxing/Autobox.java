package org.jfuncmachine.compiler.model.expr.boxing;

import org.jfuncmachine.compiler.model.expr.Expression;
import org.jfuncmachine.compiler.model.expr.If;
import org.jfuncmachine.compiler.model.types.ObjectType;
import org.jfuncmachine.compiler.model.types.Type;

/** A utility to add boxing or unboxing as necessary. */
public class Autobox {
    /** Autobox an expression if necessary
     *
     * @param expr The expression to autobox
     * @param desiredType The desired type of the expression
     * @return The expression wrapped with a Box or Unbox expression, or if no autoboxing is needed,
     * the expression as-is
     */
    public static Expression autobox(Expression expr, Type desiredType) {
        if (expr instanceof If ifExpr) {
            return new If(ifExpr.test, autobox(ifExpr.trueExpr, desiredType),
                    autobox(ifExpr.falseExpr, desiredType), ifExpr.filename, ifExpr.lineNumber);
        }
        if (expr.getType() instanceof ObjectType exprObj && !(desiredType instanceof ObjectType)) {
            if (expr.getType().isBoxType() && exprObj.isUnboxableTo(desiredType)) {
                return new Unbox(expr);
            } else {
                return expr;
            }
        } else if (!(expr.getType() instanceof ObjectType) && (desiredType instanceof ObjectType desiredObj)
                && (desiredType.isBoxType() || desiredType.equals(new ObjectType()))) {
            if (desiredObj.isBoxableFrom(expr.getType())) {
                return new Box(expr, desiredType);
            } else {
                return expr;
            }
        } else {
            return expr;
        }
    }

    /** Returns true if the expression needs to be autoboxed to be of the desired type
     *
     * @param expr The expression to be examined
     * @param desiredType The desired type of the expression
     * @return True if the expression needs autoboxing
     */
    public static boolean autoboxNeeded(Expression expr, Type desiredType) {
        if (expr.getType() instanceof ObjectType exprObj && !(desiredType instanceof ObjectType)) {
            if (expr.getType().isBoxType() && exprObj.isUnboxableTo(desiredType)) {
                return true;
            } else {
                return false;
            }
        } else if (!(expr.getType() instanceof ObjectType) && (desiredType instanceof ObjectType desiredObj)
                && (desiredType.isBoxType() || desiredType.equals(new ObjectType()))) {
            if (desiredObj.isBoxableFrom(expr.getType())) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

    /** Unused default constructor */
    private Autobox() {}
}
