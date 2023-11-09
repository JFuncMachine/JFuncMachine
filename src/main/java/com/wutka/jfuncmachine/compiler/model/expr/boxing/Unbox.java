package com.wutka.jfuncmachine.compiler.model.expr.boxing;

import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.classgen.InstructionGenerator;
import com.wutka.jfuncmachine.compiler.model.expr.Expression;
import com.wutka.jfuncmachine.compiler.model.expr.javaintop.CallJavaMethod;
import com.wutka.jfuncmachine.compiler.model.types.BooleanType;
import com.wutka.jfuncmachine.compiler.model.types.ByteType;
import com.wutka.jfuncmachine.compiler.model.types.CharType;
import com.wutka.jfuncmachine.compiler.model.types.DoubleType;
import com.wutka.jfuncmachine.compiler.model.types.FloatType;
import com.wutka.jfuncmachine.compiler.model.types.IntType;
import com.wutka.jfuncmachine.compiler.model.types.LongType;
import com.wutka.jfuncmachine.compiler.model.types.ObjectType;
import com.wutka.jfuncmachine.compiler.model.types.ShortType;
import com.wutka.jfuncmachine.compiler.model.types.SimpleTypes;
import com.wutka.jfuncmachine.compiler.model.types.Type;

public class Unbox extends Expression {
    public final Expression expr;
    public final Type unboxedType;

    public Unbox(Expression expr) {
        super(null, 0);
        this.expr = expr;
        this.unboxedType = expr.getType().getUnboxedType();
    }

    public Unbox(Expression expr, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.expr = expr;
        this.unboxedType = expr.getType().getUnboxedType();
    }

    public Unbox(Expression expr, Type unboxedType) {
        super(null, 0);
        this.expr = expr;
        this.unboxedType = unboxedType;
    }
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
    public void generate(InstructionGenerator generator, Environment env) {
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

        CallJavaMethod method = switch (unboxedType) {
            case BooleanType b -> new CallJavaMethod(className, "booleanValue",
                    new Type[0], expr, new Expression[0], SimpleTypes.BOOLEAN, filename, lineNumber);
            case ByteType b -> new CallJavaMethod(className, "byteValue",
                    new Type[0], expr, new Expression[0], SimpleTypes.BYTE, filename, lineNumber);
            case CharType c -> new CallJavaMethod(className, "charValue",
                    new Type[0], expr, new Expression[0], SimpleTypes.CHAR, filename, lineNumber);
            case DoubleType d -> new CallJavaMethod(className, "doubleValue",
                    new Type[0], expr, new Expression[0], SimpleTypes.DOUBLE, filename, lineNumber);
            case FloatType f -> new CallJavaMethod(className, "floatValue",
                    new Type[0], expr, new Expression[0], SimpleTypes.FLOAT, filename, lineNumber);
            case IntType i -> new CallJavaMethod(className, "intValue",
                    new Type[0], expr, new Expression[0], SimpleTypes.INT, filename, lineNumber);
            case LongType l -> new CallJavaMethod(className, "longValue",
                    new Type[0], expr, new Expression[0], SimpleTypes.LONG, filename, lineNumber);
            case ShortType s -> new CallJavaMethod(className, "shortValue",
                    new Type[0], expr, new Expression[0], SimpleTypes.SHORT, filename, lineNumber);
            default -> null;
        };

        if (method == null) {
            // Not an unboxable type
            return;
        }

        method.generate(generator, env);
    }
}
