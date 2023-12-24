package org.jfuncmachine.compiler.model.expr;

import org.jfuncmachine.compiler.model.SourceElement;

/** Defines a case value for a class switch statement containing a target value, an optional
 * extra comparison, and an expression to be executed if the switch value matches the case value.
 */
public class TypeSwitchCase extends SourceElement {
    /** The class that this case matches, or a string, or an int */
    public final Object target;

    /** An additional expression to execute to check whether target matches this
     * case value. @see "org.jfuncmachine.compiler.expr.ClassSwitch"
     *
     * This value can be null.
     */
    public final Expression additionalComparison;

    /** The expression to execute if the switch value equals this case value */
    public final Expression expr;

    /** Create a switch case
     * @param target The class that this case matches
     * @param expr The expression to execute if the switch value equals this case value
     */
    public TypeSwitchCase(Object target, Expression expr) {
        super(null, 0);
        this.target = target;
        this.additionalComparison = null;
        this.expr = expr;

    }

    /** Create a switch case
     * @param target The class that this case matches
     * @param expr The expression to execute if the switch value equals this case value
     * @param filename The source filename containing this case
     * @param lineNumber The line number in the source filename where this case starts
     */
    public TypeSwitchCase(Object target, Expression expr, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.target = target;
        this.additionalComparison = null;
        this.expr = expr;
    }

    /** Create a switch case
     * @param target The class that this case matches
     * @param additionalComparison An additional comparison to execute to make sure the target matches this case
     * @param expr The expression to execute if the switch value equals this case value
     */
    public TypeSwitchCase(Object target, Expression additionalComparison, Expression expr) {
        super(null, 0);
        this.target = target;
        this.additionalComparison = additionalComparison;
        this.expr = expr;

    }

    /** Create a switch case
     * @param target The class that this case matches
     * @param additionalComparison An additional comparison to execute to make sure the target matches this case
     * @param expr The expression to execute if the switch value equals this case value
     * @param filename The source filename containing this case
     * @param lineNumber The line number in the source filename where this case starts
     */
    public TypeSwitchCase(Object target, Expression additionalComparison, Expression expr,
                          String filename, int lineNumber) {
        super(filename, lineNumber);
        this.target = target;
        this.additionalComparison = additionalComparison;
        this.expr = expr;
    }
}