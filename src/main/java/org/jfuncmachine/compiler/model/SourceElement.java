package org.jfuncmachine.compiler.model;

import org.jfuncmachine.compiler.exceptions.JFuncMachineException;

/** Represents any item that is expected to have come from a particular source file */
public abstract class SourceElement {
    /** The name of the source file this element is defined in */
    public final String filename;
    /** The line number in the source file where the definition of this element begins */
    public final int lineNumber;

    /** Create a source element
     * @param filename The name of the source file this element is defined in
     * @param lineNumber The line number in the source file where the definition of this element begins
     */
    public SourceElement(String filename, int lineNumber) {
        this.filename = filename;
        this.lineNumber = lineNumber;
    }

    /** Creates an exception that contains this source element's filename and line number (if present)
     * @param message The message contained in the exception
     * @return A JFuncMachineException containing the filename and lineNumber if present
     */
    public JFuncMachineException generateException(String message) {
        if (filename == null) {
            return new JFuncMachineException(message);
        } else {
            return new JFuncMachineException(filename, lineNumber, message);
        }
    }
}
