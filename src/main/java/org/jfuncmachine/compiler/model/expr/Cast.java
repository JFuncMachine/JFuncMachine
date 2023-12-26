package org.jfuncmachine.compiler.model.expr;

import org.jfuncmachine.compiler.classgen.ClassGenerator;
import org.jfuncmachine.compiler.classgen.Environment;
import org.jfuncmachine.compiler.model.types.ArrayType;
import org.jfuncmachine.compiler.model.types.ObjectType;
import org.jfuncmachine.compiler.model.types.StringType;
import org.jfuncmachine.compiler.model.types.Type;

/** Define a cast to a particular class type */
public class Cast extends Expression {
    /** The class to cast to*/
    public final Type castClass;

    /** The expression to cast */
    public final Expression body;

    /** Create a new cast
     * @param castClass The class of exception to catch
     * @param body The body of the catch block
     */
    public Cast(Type castClass, Expression body) {
        super(null, 0);
        this.castClass = castClass;
        this.body = body;
    }

    /** Create a new cast
     * @param castClass The class of exception to catch
     * @param body The body of the catch block
     * @param filename The source filename this expression is associated with
     * @param lineNumber The source line number this expression is associated with
     */
    public Cast(Type castClass, Expression body, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.castClass = castClass;
        this.body = body;
    }

    @Override
    public Type getType() {
        return castClass;
    }

    @Override
    public void reset() {
        body.reset();
    }

    @Override
    public void findCaptured(Environment env) {
        body.findCaptured(env);
    }

    @Override
    public Expression convertToFullTailCalls(boolean inTailPosition) {
        return this;
    }

    @Override
    public void generate(ClassGenerator generator, Environment env, boolean inTailPosition) {
        body.generate(generator, env, false);
        generator.instGen.lineNumber(lineNumber);
        if (castClass instanceof ObjectType objectType) {
            generator.instGen.checkcast(generator.className(objectType.className));
        } else if (castClass instanceof ArrayType arrayType) {
            generator.instGen.checkcast(generator.getTypeDescriptor(arrayType));
        } else if (castClass instanceof StringType) {
            generator.instGen.checkcast(generator.className("java.lang.String"));
        } else {
            throw generateException("Cannot use cast on type "+castClass+". Use conv package to convert numbers.");

        }
    }
}
