package com.wutka.jfuncmachine.compiler.model.expr.boxing;

import com.wutka.jfuncmachine.compiler.classgen.ClassGenerator;
import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.model.expr.Expression;
import com.wutka.jfuncmachine.compiler.model.expr.javaintop.CallJavaStaticMethod;
import com.wutka.jfuncmachine.compiler.model.types.ObjectType;
import com.wutka.jfuncmachine.compiler.model.types.Type;

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

    public Type getType() {
        Type exprType = boxType;

        String boxTypeName = exprType.getBoxType();
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
    public void generate(ClassGenerator generator, Environment env) {
        String boxName;

        if (desiredBoxType == null) {
            boxName = boxType.getBoxType();
        } else {
            boxName = ((ObjectType) desiredBoxType).className;
        }

        if (boxName == null) return;

        CallJavaStaticMethod method = new CallJavaStaticMethod(boxName, "valueOf",
                new Type[] { boxType }, new Expression[] { expr },
                new ObjectType(boxName), filename, lineNumber);
        method.generate(generator, env);
    }
}
