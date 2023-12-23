package org.jfuncmachine.compiler.model;

import org.jfuncmachine.compiler.model.expr.Expression;
import org.jfuncmachine.compiler.model.expr.GetValue;
import org.jfuncmachine.compiler.model.expr.javainterop.CallJavaSuperConstructor;
import org.jfuncmachine.compiler.model.types.Field;
import org.jfuncmachine.compiler.model.types.ObjectType;
import org.jfuncmachine.compiler.model.types.SimpleTypes;

/** A special version of MethodDef for defining a static initializer */
public class StaticInitializerDef extends MethodDef {
    /** Create a constructor definition
     *
     * @param body The code to execute for this constructor
     */
    public StaticInitializerDef(Expression body) {
        super("<clinit>", Access.STATIC, new Field[0], SimpleTypes.UNIT, body);
    }

    /** Create a constructor definition
     *
     * @param body The code to execute for this constructor
     * @param filename The name of the file where this constructor is defined
     * @param lineNumber The line number in the source file where this constructor is defined
     */
    public StaticInitializerDef(Expression body,
                                String filename, int lineNumber) {
        super("<clinit>", Access.STATIC, new Field[0], SimpleTypes.UNIT, body, filename, lineNumber);
    }
}