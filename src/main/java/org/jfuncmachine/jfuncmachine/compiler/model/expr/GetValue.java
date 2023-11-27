package org.jfuncmachine.jfuncmachine.compiler.model.expr;

import org.jfuncmachine.jfuncmachine.compiler.classgen.ClassGenerator;
import org.jfuncmachine.jfuncmachine.compiler.classgen.EnvVar;
import org.jfuncmachine.jfuncmachine.compiler.classgen.Environment;
import org.jfuncmachine.jfuncmachine.compiler.model.types.Type;

/** An expression to retrieve a local variable value */
public class GetValue extends Expression {
    /** The name of the local variable */
    public String name;
    /** The type of the local variable */
    public Type type;

    /** Create a get value expression
     * @param name The name of the local variable to get
     * @param type The type of the local variable
     */
    public GetValue(String name, Type type) {
        super(null, 0);
        this.name = name;
        this.type = type;
    }

    /** Create a get value expression
     * @param name The name of the local variable to get
     * @param type The type of the local variable
     * @param filename The source filename this expression is associated with
     * @param lineNumber The source line number this expression is associated with
     */
    public GetValue(String name, Type type, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.name = name;
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public void findCaptured(Environment env) {
        env.checkCaptured(name);
    }

    @Override
    public void generate(ClassGenerator generator, Environment env, boolean inTailPosition) {

        EnvVar envVar = env.getVar(name);
        envVar.generateGet(generator);
    }
}
