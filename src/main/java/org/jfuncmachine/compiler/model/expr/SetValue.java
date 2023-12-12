package org.jfuncmachine.compiler.model.expr;

import org.jfuncmachine.compiler.classgen.ClassGenerator;
import org.jfuncmachine.compiler.classgen.EnvVar;
import org.jfuncmachine.compiler.classgen.Environment;
import org.jfuncmachine.compiler.model.types.*;
import org.objectweb.asm.Opcodes;

/** An expression to set the value of a local variable */
public class SetValue extends Expression {
    /** The name of the variable to set */
    public String name;
    /** The value to store in the variable */
    public Expression expression;

    /** Create a set value expression
     * @param name The name of the variable to set
     * @param expression The value to store in a variable
     */
    public SetValue(String name, Expression expression) {
        super(null, 0);
        this.name = name;
        this.expression = expression;
    }

    /** Create a set value expression
     * @param name The name of the variable to set
     * @param expression The value to store in a variable
     * @param filename The source filename this expression is associated with
     * @param lineNumber The source line number this expression is associated with
     */
    public SetValue(String name, Expression expression, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.name = name;
        this.expression = expression;
    }

    public Type getType() {
        return SimpleTypes.UNIT;
    }

    @Override
    public void reset() {
        expression.reset();
    }

    public void findCaptured(Environment env) {
        expression.findCaptured(env);
    }

    @Override
    public void generate(ClassGenerator generator, Environment env, boolean inTailPosition) {
        EnvVar envVar = env.getVar(name);

        expression.generate(generator, env, false);

        int opcode = switch (expression.getType()) {
            case BooleanType b -> Opcodes.ISTORE;
            case ByteType b -> Opcodes.ISTORE;
            case CharType c -> Opcodes.ISTORE;
            case DoubleType d -> Opcodes.DSTORE;
            case FloatType f -> Opcodes.FSTORE;
            case IntType i -> Opcodes.ISTORE;
            case LongType l -> Opcodes.LSTORE;
            case ShortType s -> Opcodes.ISTORE;
            default -> Opcodes.ASTORE;
        };

        generator.instGen.rawIntOpcode(opcode, envVar.index);
        if (inTailPosition && generator.currentMethod.isTailCallable) {
            generator.instGen.aconst_null();
        }
    }
}
