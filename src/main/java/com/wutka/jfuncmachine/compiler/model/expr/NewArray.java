package com.wutka.jfuncmachine.compiler.model.expr;

import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.classgen.InstructionGenerator;
import com.wutka.jfuncmachine.compiler.classgen.Naming;
import com.wutka.jfuncmachine.compiler.exceptions.JFuncMachineException;
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
    public void generate(InstructionGenerator generator, Environment env) {
        arraySize.generate(generator, env);
        switch (arrayType) {
            case ObjectType o -> generator.anewarray(Naming.className(o.className));
            case StringType s -> generator.anewarray("java/lang/String");
            case FunctionType s -> throw new JFuncMachineException("FunctionType not implemented");
            default -> generator.newarray(arrayType);
        }
    }
}
