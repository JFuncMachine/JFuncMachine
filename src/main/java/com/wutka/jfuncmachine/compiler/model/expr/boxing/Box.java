package com.wutka.jfuncmachine.compiler.model.expr.boxing;

import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.classgen.InstructionGenerator;
import com.wutka.jfuncmachine.compiler.model.expr.Expression;
import com.wutka.jfuncmachine.compiler.model.types.*;

import java.lang.String;

public class Box extends Expression {
    public Expression expr;

    public Box(Expression expr, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.expr = expr;
    }

    public Type getType() {
        Type exprType = expr.getType();

        return switch (exprType) {
            case BooleanType b -> new ObjectType("java.lang.Boolean");
            case ByteType b -> new ObjectType("java.lang.Byte");
            case CharType c -> new ObjectType("java.lang.Character");
            case DoubleType d -> new ObjectType("java.lang.Double");
            case FloatType f -> new ObjectType("java.lang.Float");
            case IntType i -> new ObjectType("java.lang.Integer");
            case LongType l -> new ObjectType("java.lang.Long");
            case ShortType s -> new ObjectType("java.lang.Short");
            case StringType s -> new ObjectType("java.lang.String");
            default -> exprType;
        };
    }
}
