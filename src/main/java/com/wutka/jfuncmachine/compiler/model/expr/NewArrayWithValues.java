package com.wutka.jfuncmachine.compiler.model.expr;

import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.classgen.InstructionGenerator;
import com.wutka.jfuncmachine.compiler.classgen.Naming;
import com.wutka.jfuncmachine.compiler.exceptions.JFuncMachineException;
import com.wutka.jfuncmachine.compiler.model.expr.constants.IntConstant;
import com.wutka.jfuncmachine.compiler.model.types.ArrayType;
import com.wutka.jfuncmachine.compiler.model.types.BooleanType;
import com.wutka.jfuncmachine.compiler.model.types.ByteType;
import com.wutka.jfuncmachine.compiler.model.types.CharType;
import com.wutka.jfuncmachine.compiler.model.types.DoubleType;
import com.wutka.jfuncmachine.compiler.model.types.FloatType;
import com.wutka.jfuncmachine.compiler.model.types.FunctionType;
import com.wutka.jfuncmachine.compiler.model.types.IntType;
import com.wutka.jfuncmachine.compiler.model.types.LongType;
import com.wutka.jfuncmachine.compiler.model.types.ObjectType;
import com.wutka.jfuncmachine.compiler.model.types.ShortType;
import com.wutka.jfuncmachine.compiler.model.types.StringType;
import com.wutka.jfuncmachine.compiler.model.types.Type;

public class NewArrayWithValues extends Expression {
    public final Type arrayType;
    public final Expression[] arrayValues;

    public NewArrayWithValues(Type arrayType, Expression[] arrayValues, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.arrayType = arrayType;
        this.arrayValues = arrayValues;
    }

    public Type getType() {
        return new ArrayType(arrayType);
    }

    @Override
    public void generate(InstructionGenerator generator, Environment env) {
        new IntConstant(arrayValues.length, filename, lineNumber).generate(generator, env);
        switch (arrayType) {
            case ObjectType o -> generator.anewarray(Naming.className(o.className));
            case StringType s -> generator.anewarray("java/lang/String");
            case FunctionType s -> throw new JFuncMachineException("FunctionType not implemented");
            default -> generator.newarray(arrayType);
        }

        for (int i=0; i < arrayValues.length; i++) {
            generator.dup();
            new IntConstant(i, filename, lineNumber).generate(generator, env);
            arrayValues[i].generate(generator, env);
            switch (arrayType) {
                case BooleanType b -> generator.bastore();
                case ByteType b -> generator.bastore();
                case CharType c -> generator.castore();
                case DoubleType d -> generator.dastore();
                case FloatType f -> generator.fastore();
                case IntType it -> generator.iastore();
                case LongType l -> generator.lastore();
                case ShortType s -> generator.sastore();
                default -> generator.aastore();
            }
        }
    }
}
