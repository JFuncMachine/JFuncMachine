package com.wutka.jfuncmachine.compiler.model.expr;

import com.wutka.jfuncmachine.compiler.classgen.ClassGenerator;
import com.wutka.jfuncmachine.compiler.classgen.EnvVar;
import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.classgen.Label;
import com.wutka.jfuncmachine.compiler.model.types.ObjectType;
import com.wutka.jfuncmachine.compiler.model.types.Type;
import com.wutka.jfuncmachine.compiler.model.types.UnitType;

public class TryCatchFinally extends Expression {
    public final Expression tryBody;
    public final Catch[] catchExprs;
    public final Expression finallyBody;

    public TryCatchFinally(Expression tryBody, Catch[] catchExprs, Expression finallyBody) {
        super(null, 0);
        this.tryBody = tryBody;
        if (catchExprs == null) {
            this.catchExprs = new Catch[0];
        } else {
            this.catchExprs = catchExprs;
        }
        this.finallyBody = finallyBody;

    }

    public TryCatchFinally(Expression tryBody, Catch[] catchExprs, Expression finallyBody,
                           String filename, int lineNumber) {
        super(filename, lineNumber);
        this.tryBody = tryBody;
        if (catchExprs == null) {
            this.catchExprs = new Catch[0];
        } else {
            this.catchExprs = catchExprs;
        }
        this.finallyBody = finallyBody;
    }

    public Type getType() {
        return tryBody.getType();
    }

    public void findCaptured(Environment env) {
        tryBody.findCaptured(env);

        for (Catch catchExpr : catchExprs) {
            Environment newEnv = new Environment(env);
            newEnv.allocate(catchExpr.catchVariable, new ObjectType(catchExpr.catchClass));
            catchExpr.body.findCaptured(env);
        }

        if (finallyBody != null) {
            finallyBody.findCaptured(env);
        }
    }


    public void generate(ClassGenerator generator, Environment env) {
        Label blockEnd = new Label();

        Label finallyBlock = new Label();

        Label blockStart = new Label();
        generator.instGen.label(blockStart);


        Label bodyEnd = new Label();

        Environment newEnv = new Environment(env);

        tryBody.generate(generator, newEnv);

        if (finallyBody != null) {
            EnvVar saveVar = null;
            if (!(tryBody.getType() instanceof UnitType)) {
                saveVar = env.allocate(tryBody.getType());
                saveVar.generateSet(generator);
            }


            generator.instGen.label(bodyEnd);

            generator.instGen.trycatch(blockStart, bodyEnd, finallyBlock, null);

            finallyBody.generate(generator, env);

            if (!(tryBody.getType() instanceof UnitType)) {
                saveVar.generateGet(generator);
            }
        }
        generator.instGen.gotolabel(bodyEnd);

        for (Catch catchExpr: catchExprs) {
            Label catchStart = new Label();
            Label catchEnd = new Label();

            generator.instGen.trycatch(blockStart, bodyEnd, catchStart, catchExpr.catchClass);
            Environment catchEnv = new Environment(env);
            EnvVar excVar = catchEnv.allocate(catchExpr.catchVariable, new ObjectType(catchExpr.catchClass));

            generator.instGen.trycatch(catchStart, catchEnd, finallyBlock, null);

            generator.instGen.label(catchStart);

            excVar.generateSet(generator);

            catchExpr.body.generate(generator, newEnv);



        }
        if (!(tryBody.getType() instanceof UnitType) && (finallyBody != null)) {
        } else {
            tryBody.generate(generator, env);
        }

        if (finallyBody != null) {
            finallyBody.generate(generator, env);
            if ()
        }

        generator.instGen.gotolabel(blockEnd);


    }

}
