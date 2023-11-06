package com.wutka.jfuncmachine.compiler.model.expr.boxing;

import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.classgen.InstructionGenerator;
import com.wutka.jfuncmachine.compiler.model.expr.Expression;
import com.wutka.jfuncmachine.compiler.model.expr.javaintop.CallJavaConstructor;
import com.wutka.jfuncmachine.compiler.model.expr.javaintop.CallJavaStaticMethod;
import com.wutka.jfuncmachine.compiler.model.types.*;

import java.lang.String;

public class Box extends Expression {
    public final Expression expr;
    public final Type boxType;

    public Box(Expression expr) {
        super(null, 0);
        this.expr = expr;
        this.boxType = expr.getType();
    }

    public Box(Expression expr, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.expr = expr;
        this.boxType = expr.getType();
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
    public void generate(InstructionGenerator generator, Environment env) {
        String boxName = boxType.getBoxType();

        if (boxName == null) return;

        CallJavaStaticMethod method = new CallJavaStaticMethod(boxName, "valueOf",
                new Type[] { boxType }, new Expression[] { expr },
                new ObjectType(boxName), filename, lineNumber);
        method.generate(generator, env);
    }
}
