package org.jfuncmachine.jfuncmachine.compiler.model.expr.boxing;

import org.jfuncmachine.jfuncmachine.compiler.classgen.ClassGenerator;
import org.jfuncmachine.jfuncmachine.compiler.classgen.Environment;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.Expression;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.javainterop.CallJavaMethod;
import org.jfuncmachine.jfuncmachine.compiler.model.types.BooleanType;
import org.jfuncmachine.jfuncmachine.compiler.model.types.ByteType;
import org.jfuncmachine.jfuncmachine.compiler.model.types.CharType;
import org.jfuncmachine.jfuncmachine.compiler.model.types.DoubleType;
import org.jfuncmachine.jfuncmachine.compiler.model.types.FloatType;
import org.jfuncmachine.jfuncmachine.compiler.model.types.IntType;
import org.jfuncmachine.jfuncmachine.compiler.model.types.LongType;
import org.jfuncmachine.jfuncmachine.compiler.model.types.ObjectType;
import org.jfuncmachine.jfuncmachine.compiler.model.types.ShortType;
import org.jfuncmachine.jfuncmachine.compiler.model.types.SimpleTypes;
import org.jfuncmachine.jfuncmachine.compiler.model.types.Type;

/** Unboxes an expression - converts a box type to a native type (e.g. java.lang.Short to short) */
public class Unbox extends Expression {
    /** The expression to unbox */
    public final Expression expr;
    /** The type that the expression should be unboxed to */
    public final Type unboxedType;

    /** Create an Unbox for an expression
     *
     * @param expr The expression to unbox, the unboxed type is derived from the expression type
     */
    public Unbox(Expression expr) {
        super(null, 0);
        this.expr = expr;
        this.unboxedType = expr.getType().getUnboxedType();
    }

    /** Create an Unbox for an expression
     *
     * @param expr The expression to unbox, the unboxed type is derived from the expression type
     * @param filename The source filename this expression is associated with
     * @param lineNumber The source line number this expression is associated with
     */
    public Unbox(Expression expr, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.expr = expr;
        this.unboxedType = expr.getType().getUnboxedType();
    }

    /** Create an Unbox for an expression
     *
     * @param expr The expression to unbox, the unboxed type is derived from the expression type
     * @param unboxedType The type that the expression should be unboxed to
     */
    public Unbox(Expression expr, Type unboxedType) {
        super(null, 0);
        this.expr = expr;
        this.unboxedType = unboxedType;
    }

    /** Create an Unbox for an expression
     *
     * @param expr The expression to unbox, the unboxed type is derived from the expression type
     * @param unboxedType The type that the expression should be unboxed to
     * @param filename The source filename this expression is associated with
     * @param lineNumber The source line number this expression is associated with
     */
    public Unbox(Expression expr, Type unboxedType, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.expr = expr;
        this.unboxedType = unboxedType;
    }

    public Type getType() { return expr.getType().getUnboxedType(); }

    @Override
    public void findCaptured(Environment env) {
        expr.findCaptured(env);
    }

    @Override
    public void generate(ClassGenerator generator, Environment env, boolean inTailPosition) {
        Type exprType = expr.getType();
        String className = (exprType instanceof ObjectType ot) ? ot.className : null;

        if (className == null) {
            // Don't unbox a non-object
            return;
        }

        if (!unboxedType.isUnboxableFrom(className)) {
            throw generateException(String.format("Value of type %s is not unboxable from type %s",
                    unboxedType, className));
        }

        // Don't unbox if this value is in the tail position and full-tail calls are enabled,
        // because the value must remain an object
        if (inTailPosition && generator.options.fullTailCalls) {
            return;
        }

        CallJavaMethod method = switch (unboxedType) {
            case BooleanType b -> new CallJavaMethod(className, "booleanValue",
                    new Type[0], SimpleTypes.BOOLEAN, expr, new Expression[0], filename, lineNumber);
            case ByteType b -> new CallJavaMethod(className, "byteValue",
                    new Type[0], SimpleTypes.BYTE, expr, new Expression[0], filename, lineNumber);
            case CharType c -> new CallJavaMethod(className, "charValue",
                    new Type[0], SimpleTypes.CHAR, expr, new Expression[0], filename, lineNumber);
            case DoubleType d -> new CallJavaMethod(className, "doubleValue",
                    new Type[0], SimpleTypes.DOUBLE, expr, new Expression[0], filename, lineNumber);
            case FloatType f -> new CallJavaMethod(className, "floatValue",
                    new Type[0], SimpleTypes.FLOAT, expr, new Expression[0], filename, lineNumber);
            case IntType i -> new CallJavaMethod(className, "intValue",
                    new Type[0], SimpleTypes.INT, expr, new Expression[0], filename, lineNumber);
            case LongType l -> new CallJavaMethod(className, "longValue",
                    new Type[0], SimpleTypes.LONG, expr, new Expression[0], filename, lineNumber);
            case ShortType s -> new CallJavaMethod(className, "shortValue",
                    new Type[0], SimpleTypes.SHORT, expr, new Expression[0], filename, lineNumber);
            default -> null;
        };

        if (method == null) {
            // Not an unboxable type
            return;
        }

        method.generate(generator, env, inTailPosition);
    }
}
