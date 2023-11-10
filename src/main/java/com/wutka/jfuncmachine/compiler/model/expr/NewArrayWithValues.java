package com.wutka.jfuncmachine.compiler.model.expr;

import com.wutka.jfuncmachine.compiler.classgen.ClassGenerator;
import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.classgen.LambdaIntInfo;
import com.wutka.jfuncmachine.compiler.classgen.Naming;
import com.wutka.jfuncmachine.compiler.model.expr.constants.IntConstant;
import com.wutka.jfuncmachine.compiler.model.types.*;

public class NewArrayWithValues extends Expression {
    public final Type arrayType;
    public final Expression[] arrayValues;

    public NewArrayWithValues(Type arrayType, Expression[] arrayValues) {
        super(null, 0);
        this.arrayType = arrayType;
        this.arrayValues = arrayValues;
    }

    public NewArrayWithValues(Type arrayType, Expression[] arrayValues, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.arrayType = arrayType;
        this.arrayValues = arrayValues;
    }

    public Type getType() {
        return new ArrayType(arrayType);
    }

    public void findCaptured(Environment env) {
        for (Expression expr: arrayValues) {
            expr.findCaptured(env);
        }
    }

    @Override
    public void generate(ClassGenerator generator, Environment env) {
        new IntConstant(arrayValues.length, filename, lineNumber).generate(generator, env);
        switch (arrayType) {
            case ObjectType o -> generator.instGen.anewarray(Naming.className(o.className));
            case StringType s -> generator.instGen.anewarray("java/lang/String");
            case FunctionType f -> {
                LambdaIntInfo intInfo = generator.allocateLambdaInt(f);
                generator.instGen.anewarray(Naming.className(intInfo.packageName + "." + intInfo.name));
            }
            default -> generator.instGen.newarray(arrayType);
        }

        for (int i=0; i < arrayValues.length; i++) {
            generator.instGen.dup();
            new IntConstant(i, filename, lineNumber).generate(generator, env);
            arrayValues[i].generate(generator, env);
            switch (arrayType) {
                case BooleanType b -> generator.instGen.bastore();
                case ByteType b -> generator.instGen.bastore();
                case CharType c -> generator.instGen.castore();
                case DoubleType d -> generator.instGen.dastore();
                case FloatType f -> generator.instGen.fastore();
                case IntType it -> generator.instGen.iastore();
                case LongType l -> generator.instGen.lastore();
                case ShortType s -> generator.instGen.sastore();
                default -> generator.instGen.aastore();
            }
        }
    }
}
