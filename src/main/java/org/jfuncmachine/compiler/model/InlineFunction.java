package org.jfuncmachine.compiler.model;

import org.jfuncmachine.compiler.classgen.ClassGenerator;
import org.jfuncmachine.compiler.classgen.Environment;
import org.jfuncmachine.compiler.model.types.Type;

/** The base class for inline functions (functions where the instructions are directly inserted
 * into the byte code rather than generating a function call)
 */
public abstract class InlineFunction {
    /** The types of each function parameter */
    public final Type[] parameterTypes;
    /** The return type of the function */
    public final Type returnType;


    /** Create an inline function
     * @param parameterTypes The types of each function parameter
     * @param returnType The return type of the function
     */
    public InlineFunction(Type[] parameterTypes, Type returnType) {
        this.parameterTypes = parameterTypes;
        this.returnType = returnType;
    }

    /** Get the return type of the function
     *
     * @return The return type of this function
     */
    public Type getReturnType() {
        return returnType;
    }

    /** Generate the byte code for the function
     * @param generator The class generator current being used to generate the class
     * @param env The symbol environment available to this function
     */
    public abstract void generate(ClassGenerator generator, Environment env);
}
