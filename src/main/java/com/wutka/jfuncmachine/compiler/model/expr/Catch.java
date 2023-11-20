package com.wutka.jfuncmachine.compiler.model.expr;

import com.wutka.jfuncmachine.compiler.model.SourceElement;

/** Define a catch clause for a try-catch block */
public class Catch extends SourceElement {
    /** The class of exception to catch */
    public final String catchClass;
    /** The variable that the exception is stored in */
    public final String catchVariable;
    /** The body of the catch block */
    public final Expression body;

    /** Create a new catch clause
     * @param catchClass The class of exception to catch
     * @param catchVariable The variable that the exception is stored in
     * @param body The body of the catch block
     */
    public Catch(String catchClass, String catchVariable, Expression body) {
        super(null, 0);
        this.catchClass = catchClass;
        this.catchVariable = catchVariable;
        this.body = body;
    }

    /** Create a new catch clause
     * @param catchClass The class of exception to catch
     * @param catchVariable The variable that the exception is stored in
     * @param body The body of the catch block
     * @param filename The source filename this expression is associated with
     * @param lineNumber The source line number this expression is associated with
     */
    public Catch(String catchClass, String catchVariable, Expression body, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.catchClass = catchClass;
        this.catchVariable = catchVariable;
        this.body = body;
    }
}
