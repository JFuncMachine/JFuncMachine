package org.jfuncmachine.compiler.model.expr;

import org.jfuncmachine.compiler.model.SourceElement;
import org.jfuncmachine.compiler.model.expr.bool.BooleanExpr;
import org.jfuncmachine.compiler.model.types.ObjectType;

/** Defines a case value for an enum switch statement containing a target value
 * and an expression to be executed if the switch value matches the case value.
 */
public class EnumSwitchCase extends SourceElement {
    /** The string name of an enum value of the switch target's enum class */
    public final String target;

    /** The expression to execute if the switch value equals this case value */
    public final Expression expr;

    /** Create a switch case that matches an enum field
     * @param target The enum name that this case matches
     * @param expr The expression to execute if the switch value equals this case value
     */
    public EnumSwitchCase(String target, Expression expr) {
        super(null, 0);
        this.target = target;
        this.expr = expr;

    }

    /** Create a switch case that matches an enum field
     * @param target The enum name that this case matches
     * @param expr The expression to execute if the switch value equals this case value
     * @param filename The source filename containing this case
     * @param lineNumber The line number in the source filename where this case starts
     */
    public EnumSwitchCase(String target, Expression expr, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.target = target;
        this.expr = expr;
    }
}