package com.wutka.jfuncmachine.compiler.model.expr.boxing;

import com.wutka.jfuncmachine.compiler.classgen.ClassGenerator;
import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.model.expr.Expression;
import com.wutka.jfuncmachine.compiler.model.expr.javaintop.CallJavaStaticMethod;
import com.wutka.jfuncmachine.compiler.model.types.ObjectType;
import com.wutka.jfuncmachine.compiler.model.types.Type;

public class Box extends Expression {
    public final Expression expr;
    public final Type boxType;
    public final Type desiredBoxType;

    public Box(Expression expr) {
        super(null, 0);
        this.expr = expr;
        this.boxType = expr.getType();
        this.desiredBoxType = null;
    }

    public Box(Expression expr, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.expr = expr;
        this.boxType = expr.getType();
        this.desiredBoxType = null;
    }

    public Box(Expression expr, Type desiredBoxType) {
        super(null, 0);
        this.expr = expr;
        this.boxType = expr.getType();
        this.desiredBoxType = desiredBoxType;
    }

    public Box(Expression expr, Type desiredBoxType, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.expr = expr;
        this.boxType = expr.getType();
        this.desiredBoxType = desiredBoxType;
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
    public void generate(ClassGenerator generator, Environment env) {
        String boxName;

        if (desiredBoxType == null) {
            boxName = boxType.getBoxType();
        } else {
            boxName = ((ObjectType) desiredBoxType).className;
        }

        if (boxName == null) return;

        CallJavaStaticMethod method = new CallJavaStaticMethod(boxName, "valueOf",
                new Type[] { boxType }, new Expression[] { expr },
                new ObjectType(boxName), filename, lineNumber);
        method.generate(generator, env);
    }
}
