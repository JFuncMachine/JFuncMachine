package org.jfuncmachine.compiler.model.expr;

import org.jfuncmachine.compiler.classgen.ClassGenerator;
import org.jfuncmachine.compiler.classgen.EnvVar;
import org.jfuncmachine.compiler.classgen.Environment;
import org.jfuncmachine.compiler.classgen.Handle;
import org.jfuncmachine.compiler.classgen.Label;
import org.jfuncmachine.compiler.model.Access;
import org.jfuncmachine.compiler.model.MethodDef;
import org.jfuncmachine.compiler.model.expr.boxing.Autobox;
import org.jfuncmachine.compiler.model.types.BooleanType;
import org.jfuncmachine.compiler.model.types.ByteType;
import org.jfuncmachine.compiler.model.types.CharType;
import org.jfuncmachine.compiler.model.types.DoubleType;
import org.jfuncmachine.compiler.model.types.FloatType;
import org.jfuncmachine.compiler.model.types.IntType;
import org.jfuncmachine.compiler.model.types.LongType;
import org.jfuncmachine.compiler.model.types.ObjectType;
import org.jfuncmachine.compiler.model.types.ShortType;
import org.jfuncmachine.compiler.model.types.Type;
import org.jfuncmachine.runtime.TailCall;
import org.objectweb.asm.Opcodes;

/** An expression to invoke the current method recursively via a local jump
 * back to the beginning of the method after updating the method parameters.
 */
public class LocalRecurse extends Expression {
    /** The method argument values */
    public final Expression[] arguments;

    /** The return type of the method */
    public final Type returnType;

    /** Create a new method call expression
     * @param returnType The return type of the method
     * @param arguments The method argument values
     */
    public LocalRecurse(Type returnType, Expression[] arguments) {
        super(null, 0);
        this.arguments = arguments;
        this.returnType = returnType;
    }

    /** Create a new method call expression
     * @param returnType The return type of the method
     * @param arguments The method argument values
     * @param filename The source filename this expression is associated with
     * @param lineNumber The source line number this expression is associated with
     */
    public LocalRecurse(Type returnType, Expression[] arguments, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.arguments = arguments;
        this.returnType = returnType;
    }

    public Type getType() {
        return returnType;
    }

    @Override
    public void reset() {
        for (Expression expr: arguments) {
            expr.reset();
        }
    }

    public void findCaptured(Environment env) {
        for (Expression expr: arguments) {
            expr.findCaptured(env);
        }
    }

    @Override
    public Expression convertToFullTailCalls(boolean inTailPosition) {
        if (inTailPosition) {
            return new LocalRecurse(new ObjectType(), arguments, filename, lineNumber);
        }
        return this;
    }

    @Override
    public void generate(ClassGenerator generator, Environment env, boolean inTailPosition) {
        int[] argumentLocations = new int[arguments.length];
        int argPos = 1;

        MethodDef currentMethod = generator.currentMethod;
        if ((currentMethod.access & Access.STATIC) != 0) {
            argPos = 0;
        }

        for (int i=0; i < currentMethod.numCapturedParameters; i++) {
            argPos += currentMethod.parameters[i].type.getStackSize();
        }

        Type[] parameterTypes = new Type[currentMethod.parameters.length];
        for (int i=0; i < parameterTypes.length; i++) {
            parameterTypes[i] = currentMethod.parameters[i+currentMethod.numCapturedParameters].type;
        }

        for (int i = 0; i < arguments.length; i++) {
            Expression expr = arguments[i];
            if (generator.options.autobox) {
                expr = Autobox.autobox(expr, parameterTypes[i+currentMethod.numCapturedParameters]);
            }
            expr.generate(generator, env, false);
            argumentLocations[i] = argPos;
            argPos += parameterTypes[i].getStackSize();
        }

        for (int i = arguments.length-1; i >= 0; i--) {
            generator.instGen.rawIntOpcode(EnvVar.setOpcode(arguments[i].getType()), argumentLocations[i]);
        }
        generator.instGen.lineNumber(lineNumber);
        generator.instGen.gotolabel(currentMethod.startLabel);
    }
}
