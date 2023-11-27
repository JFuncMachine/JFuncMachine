package org.jfuncmachine.jfuncmachine.compiler.model.expr;

import org.jfuncmachine.jfuncmachine.compiler.classgen.ClassGenerator;
import org.jfuncmachine.jfuncmachine.compiler.classgen.Environment;
import org.jfuncmachine.jfuncmachine.compiler.classgen.LambdaIntInfo;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.boxing.Autobox;
import org.jfuncmachine.jfuncmachine.compiler.model.types.ArrayType;
import org.jfuncmachine.jfuncmachine.compiler.model.types.FunctionType;
import org.jfuncmachine.jfuncmachine.compiler.model.types.ObjectType;
import org.jfuncmachine.jfuncmachine.compiler.model.types.SimpleTypes;
import org.jfuncmachine.jfuncmachine.compiler.model.types.StringType;
import org.jfuncmachine.jfuncmachine.compiler.model.types.Type;

/** An expression to create a new array */
public class NewArray extends Expression {
    /** The type of the array elements */
    public final Type arrayType;
    /** The size of the array to create */
    public final Expression arraySize;

    /** Create a new array expression
     * @param arrayType The type of the array elements
     * @param arraySize The size of the array to create
     */
    public NewArray(Type arrayType, Expression arraySize) {
        super(null, 0);
        this.arrayType = arrayType;
        this.arraySize = arraySize;
    }

    /** Create a new array expression
     * @param arrayType The type of the array elements
     * @param arraySize The size of the array to create
     * @param filename The source filename this expression is associated with
     * @param lineNumber The source line number this expression is associated with
     */
    public NewArray(Type arrayType, Expression arraySize, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.arrayType = arrayType;
        this.arraySize = arraySize;
    }

    public Type getType() {
        return new ArrayType(arrayType);
    }

    public void findCaptured(Environment env) {
        arraySize.findCaptured(env);
    }

    @Override
    public void generate(ClassGenerator generator, Environment env, boolean inTailPosition) {
        if (generator.options.autobox) {
            Autobox.autobox(arraySize, SimpleTypes.INT).generate(generator, env, false);
        } else {
            arraySize.generate(generator, env, false);
        }
        switch (arrayType) {
            case ObjectType o -> generator.instGen.anewarray(generator.className(o.className));
            case StringType s -> generator.instGen.anewarray("java/lang/String");
            case FunctionType f -> {
                LambdaIntInfo intInfo = generator.allocateLambdaInt(f);
                generator.instGen.anewarray(generator.className(intInfo.packageName+"."+intInfo.name));
            }
            default -> generator.instGen.newarray(arrayType);
        }
    }
}
