package com.wutka.jfuncmachine.compiler.model.expr;

import com.wutka.jfuncmachine.compiler.classgen.ClassGenerator;
import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.classgen.LambdaIntInfo;
import com.wutka.jfuncmachine.compiler.model.types.*;

public class NewArray extends Expression {
    public final Type arrayType;
    public final Expression arraySize;

    public NewArray(Type arrayType, Expression arraySize) {
        super(null, 0);
        this.arrayType = arrayType;
        this.arraySize = arraySize;
    }

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
    public void generate(ClassGenerator generator, Environment env) {
        arraySize.generate(generator, env);
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
