package org.jfuncmachine.compiler.model.expr;

import org.jfuncmachine.compiler.classgen.ClassGenerator;
import org.jfuncmachine.compiler.classgen.EnvVar;
import org.jfuncmachine.compiler.classgen.Environment;
import org.jfuncmachine.compiler.classgen.Label;
import org.jfuncmachine.compiler.model.types.ObjectType;
import org.jfuncmachine.compiler.model.types.Type;
import org.jfuncmachine.compiler.model.types.UnitType;

/** A try-catch-finally expression */
public class TryCatchFinally extends Expression {
    /** The body of the try block */
    public final Expression tryBody;
    /** The catch expressions, may be null or empty */
    public final Catch[] catchExprs;
    /** The finally body, may be null */
    public final Expression finallyBody;

    /** Create a try-catch-finally block
     * @param tryBody The body of the try block
     * @param catchExprs The catch exceptions
     * @param finallyBody The finally body
     */
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

    /** Create a try-catch-finally block
     * @param tryBody The body of the try block
     * @param catchExprs The catch exceptions
     * @param finallyBody The finally body
     * @param filename The source filename this expression is associated with
     * @param lineNumber The source line number this expression is associated with
     */
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

    @Override
    public void reset() {
        tryBody.reset();
        for (Catch catchExpr : catchExprs) {
            catchExpr.body.reset();
        }
        if (finallyBody != null) {
            finallyBody.reset();
        }
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

    @Override
    public Expression convertToFullTailCalls(boolean inTailPosition) {
        if (inTailPosition && finallyBody == null) {
            Catch[] newCatchExprs = new Catch[catchExprs.length];
            for (int i=0; i < newCatchExprs.length; i++) {
                newCatchExprs[i] = new Catch(catchExprs[i].catchClass,
                        catchExprs[i].catchVariable,
                        catchExprs[i].body.convertToFullTailCalls(true),
                        filename, lineNumber);
            }
            return new TryCatchFinally(tryBody.convertToFullTailCalls(true),
                    newCatchExprs, null, filename, lineNumber);
        }
        return this;
    }

    public void generate(ClassGenerator generator, Environment env, boolean inTailPosition) {
        Label blockEnd = new Label();

        Label finallyBlock = new Label();

        Label blockStart = new Label();
        generator.instGen.label(blockStart);

        Label bodyEnd = new Label();

        Environment newEnv = new Environment(env);

        tryBody.generate(generator, newEnv, inTailPosition && finallyBody == null);

        if (finallyBody != null) {
            Label finallyStart = new Label();
            Label finallyEnd = new Label();
            EnvVar saveVar = null;
            if (!(tryBody.getType() instanceof UnitType)) {
                generator.instGen.label(finallyStart);
                saveVar = env.allocate(tryBody.getType());
                generator.instGen.generateLocalVariable(saveVar.name, saveVar.type,
                        finallyStart, finallyEnd, saveVar.index);
                saveVar.generateSet(generator);
            }

            finallyBody.reset();
            finallyBody.generate(generator, env, false);

            if (!(tryBody.getType() instanceof UnitType)) {
                saveVar.generateGet(generator);
                env.free(saveVar.index);
                generator.instGen.label(finallyEnd);
            }
        }

        generator.instGen.gotolabel(blockEnd);
        generator.instGen.label(bodyEnd);

        for (Catch catchExpr: catchExprs) {
            Label catchStart = new Label();
            Label catchEnd = new Label();

            generator.instGen.trycatch(blockStart, bodyEnd, catchStart, catchExpr.catchClass);
            Environment catchEnv = new Environment(env);
            EnvVar excVar = catchEnv.allocate(catchExpr.catchVariable, new ObjectType(catchExpr.catchClass));

            generator.instGen.label(catchStart);

            generator.instGen.generateLocalVariable(excVar.name, excVar.type,
                    catchStart, catchEnd, excVar.index);

            excVar.generateSet(generator);
            catchEnv.free(excVar.index);

            catchExpr.body.generate(generator, catchEnv, inTailPosition && finallyBody == null);

            if (finallyBody != null) {
                Label finallyStart = new Label();
                Label finallyEnd = new Label();
                EnvVar saveVar = null;
                if (!(catchExpr.body.getType() instanceof UnitType)) {
                    generator.instGen.label(finallyStart);
                    saveVar = catchEnv.allocate(catchExpr.body.getType());
                    generator.instGen.generateLocalVariable(saveVar.name, saveVar.type,
                            finallyStart, finallyEnd, saveVar.index);
                    saveVar.generateSet(generator);
                }

                finallyBody.reset();
                finallyBody.generate(generator, env, false);


                generator.instGen.trycatch(catchStart, catchEnd, finallyBlock, null);

                if (!(catchExpr.body.getType() instanceof UnitType)) {
                    saveVar.generateGet(generator);
                    generator.instGen.label(finallyEnd);
                    catchEnv.free(saveVar.index);
                }
            }

            generator.instGen.label(catchEnd);
            generator.instGen.gotolabel(blockEnd);
        }

        if (finallyBody != null) {
            generator.instGen.trycatch(blockStart, bodyEnd, finallyBlock, null);

            generator.instGen.label(finallyBlock);
            Label finallyEnd = new Label();
            EnvVar var = env.allocate(new ObjectType(java.lang.Throwable.class));
            var.generateSet(generator);
            generator.instGen.generateLocalVariable(var.name, var.type,
                    finallyBlock, finallyEnd, var.index);

            finallyBody.reset();
            finallyBody.generate(generator, env, false);

            var.generateGet(generator);
            env.free(var.index);
            generator.instGen.label(finallyEnd);
            generator.instGen.athrow();
        }

        generator.instGen.label(blockEnd);
    }
}
