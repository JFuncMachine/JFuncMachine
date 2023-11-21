package com.wutka.jfuncmachine.compiler.model.expr;

import com.wutka.jfuncmachine.compiler.classgen.ClassGenerator;
import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.classgen.LambdaIntInfo;
import com.wutka.jfuncmachine.compiler.model.expr.boxing.Autobox;
import com.wutka.jfuncmachine.compiler.model.expr.constants.IntConstant;
import com.wutka.jfuncmachine.compiler.model.types.*;

/** Create a new array from initializer values */
public class NewArrayWithValues extends Expression {
    /** The type of the array elements */
    public final Type arrayType;
    /** The values to initialize the array with */
    public final Expression[] arrayValues;

    /** Create a new array with values expression
     * @param arrayType The type of the array elements
     * @param arrayValues The values to initialize the array with
     */
    public NewArrayWithValues(Type arrayType, Expression[] arrayValues) {
        super(null, 0);
        this.arrayType = arrayType;
        this.arrayValues = arrayValues;
    }

    /** Create a new array with values expression
     * @param arrayType The type of the array elements
     * @param arrayValues The values to initialize the array with
     * @param filename The source filename this expression is associated with
     * @param lineNumber The source line number this expression is associated with
     */
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
    public void generate(ClassGenerator generator, Environment env, boolean inTailPosition) {
        new IntConstant(arrayValues.length, filename, lineNumber).generate(generator, env, false);
        switch (arrayType) {
            case ObjectType o -> generator.instGen.anewarray(generator.className(o.className));
            case StringType s -> generator.instGen.anewarray("java/lang/String");
            case FunctionType f -> {
                LambdaIntInfo intInfo = generator.allocateLambdaInt(f);
                generator.instGen.anewarray(generator.className(intInfo.packageName + "." + intInfo.name));
            }
            default -> generator.instGen.newarray(arrayType);
        }

        for (int i=0; i < arrayValues.length; i++) {
            generator.instGen.dup();
            new IntConstant(i, filename, lineNumber).generate(generator, env, false);
            if (generator.options.autobox) {
                Autobox.autobox(arrayValues[i], arrayType).generate(generator, env, false);
            } else {
                arrayValues[i].generate(generator, env, false);
            }
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
