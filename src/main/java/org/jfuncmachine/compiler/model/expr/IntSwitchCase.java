package org.jfuncmachine.compiler.model.expr;

import org.jfuncmachine.compiler.model.SourceElement;

/** Defines a case value for a switch statement containing a target value and the
 * expression to be executed if the switch value matches the case value
 */
public class IntSwitchCase extends SourceElement {
    /** The value of this case */
    public final int value;
    /** The expression to execute if the switch value equals this case value */
    public final Expression expr;

    /** Create a switch case
     * @param value The value of this case
     * @param expr The expression to execute if the switch value equals this case value
     */
    public IntSwitchCase(int value, Expression expr) {
        super(null, 0);
        this.value = value;
        this.expr = expr;

    }

    /** Create a switch case
     * @param value The value of this case
     * @param expr The expression to execute if the switch value equals this case value
     * @param filename The source filename containing this case
     * @param lineNumber The line number in the source filename where this case starts
     */
    public IntSwitchCase(int value, Expression expr, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.value = value;
        this.expr = expr;

    }
}
