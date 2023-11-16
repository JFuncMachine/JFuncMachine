package com.wutka.jfuncmachine.compiler.model.expr.boxing;

import com.wutka.jfuncmachine.compiler.classgen.ClassGenerator;
import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.model.expr.Expression;
import com.wutka.jfuncmachine.compiler.model.types.ObjectType;
import com.wutka.jfuncmachine.compiler.model.types.Type;

public class Autobox {
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
