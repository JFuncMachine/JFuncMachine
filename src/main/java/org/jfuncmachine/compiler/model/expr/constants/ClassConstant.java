package org.jfuncmachine.compiler.model.expr.constants;

import org.jfuncmachine.compiler.classgen.ClassGenerator;
import org.jfuncmachine.compiler.classgen.Environment;
import org.jfuncmachine.compiler.model.expr.Expression;
import org.jfuncmachine.compiler.model.types.ObjectType;
import org.jfuncmachine.compiler.model.types.Type;

/** An object constant value */
public class ClassConstant extends Expression {
    /** The constant value */
    public final Type value;

    /** Create a class constant
     *
     * @param value The constant value
     */
    public ClassConstant(Type value) {
        super(null, 0);
        this.value = value;
    }

    /** Create a class constant
     *
     * @param value The constant value
     * @param filename The source filename this expression is associated with
     * @param lineNumber The source line number this expression is associated with
     */
    public ClassConstant(Type value, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.value = value;
    }

    public Type getType() {
        return new ObjectType("java.lang.Class");
    }

    public void findCaptured(Environment env) {}

    @Override
    public void generate(ClassGenerator gen, Environment env, boolean inTailPosition) {
        gen.instGen.lineNumber(lineNumber);
        gen.instGen.ldc(org.objectweb.asm.Type.getType(gen.getTypeDescriptor(value)));
    }
}
