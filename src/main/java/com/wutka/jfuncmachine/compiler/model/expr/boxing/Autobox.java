package com.wutka.jfuncmachine.compiler.model.expr.boxing;

import com.wutka.jfuncmachine.compiler.model.expr.Expression;
import com.wutka.jfuncmachine.compiler.model.types.ObjectType;
import com.wutka.jfuncmachine.compiler.model.types.Type;

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
        if (expr.getType() instanceof ObjectType exprObj && !(desiredType instanceof ObjectType)) {
            if (expr.getType().isBoxType() && exprObj.isUnboxableTo(desiredType)) {
                return new Unbox(expr);
            } else {
                return expr;
            }
        } else if (!(expr.getType() instanceof ObjectType) && (desiredType instanceof ObjectType desiredObj)
                && desiredType.isBoxType()) {
            if (desiredObj.isBoxableFrom(expr.getType())) {
                return new Box(expr, desiredType);
            } else {
                return expr;
            }
        } else {
            return expr;
        }
    }
}
