package com.wutka.jfuncmachine.compiler.model.expr.constants;

import com.wutka.jfuncmachine.compiler.classgen.ClassGenerator;
import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.model.expr.Expression;
import com.wutka.jfuncmachine.compiler.model.types.ObjectType;
import com.wutka.jfuncmachine.compiler.model.types.SimpleTypes;
import com.wutka.jfuncmachine.compiler.model.types.Type;

/** A null constant value.
 * The expression can have an optional object type, because null can represent
 * any object type, and if this expression type is needed to derive the type
 * of some parent expression, the type may need to be explicitly specified.
 */
public class NullConstant extends Expression {

    /** The type of the null, which may be needed to determine expression types */
    public final ObjectType type;

    /** Create a null constant with a type of java.lang.Object */
    public NullConstant() {
        super(null, 0);
        this.type = new ObjectType();
    }

    /** Create a null constant with a specific type
     *
     * @param type The type of this expression
     */
    public NullConstant(ObjectType type) {
        super(null, 0);
        this.type = type;
    }

    /** Create a null constant with a type of java.lang.Object
     * @param filename The source filename this expression is associated with
     * @param lineNumber The source line number this expression is associated with
     */
    public NullConstant(String filename, int lineNumber) {
        super(filename, lineNumber);
        this.type = new ObjectType();
    }

    /** Create a null constant with a specific type
     *
     * @param type The type of this expression
     * @param filename The source filename this expression is associated with
     * @param lineNumber The source line number this expression is associated with
     */
    public NullConstant(ObjectType type, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.type = type;
    }

    public void findCaptured(Environment env) {}

    public Type getType() {
        return type;
    }

    @Override
    public void generate(ClassGenerator gen, Environment env) {
        gen.instGen.aconst_null();
    }
}
