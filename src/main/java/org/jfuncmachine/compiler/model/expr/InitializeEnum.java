package org.jfuncmachine.compiler.model.expr;

import org.jfuncmachine.compiler.classgen.*;
import org.jfuncmachine.compiler.model.*;
import org.jfuncmachine.compiler.model.expr.boxing.Autobox;
import org.jfuncmachine.compiler.model.expr.javainterop.CallJavaStaticMethod;
import org.jfuncmachine.compiler.model.types.*;
import org.jfuncmachine.runtime.TailCall;
import org.objectweb.asm.Opcodes;

/** An expression that is the static initializer for an enum */
public class InitializeEnum extends Expression {
    public final String enumClass;
    public final ClassField[] enumInstanceFields;
    public final EnumInitializer[] initializers;

    /** Create a static method invocation
     * @param enumClass The class name of the enum being initialized
     * @param enumInstanceFields The non-static fields of the enum class
     * @param initializers The initializers for each enum value
     */
    public InitializeEnum(String enumClass, ClassField[] enumInstanceFields,
        EnumInitializer[] initializers) {
        super(null, 0);
        this.enumClass = enumClass;
        this.enumInstanceFields = enumInstanceFields;
        this.initializers = initializers;
    }

    /** Create a static method invocation
     * @param enumClass The class name of the enum being initialized
     * @param enumInstanceFields The non-static fields of the enum class
     * @param initializers The initializers for each enum value
     * @param filename The source file where this initializer occurs
     * @param lineNumber The line number in the source file where this initializer occurs
     */
    public InitializeEnum(String enumClass, ClassField[] enumInstanceFields,
                          EnumInitializer[] initializers, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.enumClass = enumClass;
        this.enumInstanceFields = enumInstanceFields;
        this.initializers = initializers;
    }
    public Type getType() {
        return SimpleTypes.UNIT;
    }

    @Override
    public void reset() {
        for (EnumInitializer initializer: initializers) {
            for (Expression expr : initializer.initializers) {
                expr.reset();
            }
        }
    }

    public void findCaptured(Environment env) {
        for (EnumInitializer initializer: initializers) {
            for (Expression expr : initializer.initializers) {
                expr.findCaptured(env);
            }
        }
    }

    @Override
    public Expression convertToFullTailCalls(boolean inTailPosition) {
        return this;
    }

    @Override
    public void generate(ClassGenerator generator, Environment env, boolean inTailPosition) {
    }
}
