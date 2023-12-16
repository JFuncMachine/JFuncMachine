package org.jfuncmachine.compiler.classgen;

/** Represents some location in the generated instructions.
 * This may be used as the target for a jump instruction, or
 * to specify the bounds of a try-catch block or the area of
 * code where a local variable is valid.
 */
public class Label {
    protected org.objectweb.asm.Label label;

    /** Create a new label */
    public Label() {
        this.label = new org.objectweb.asm.Label();
    }

    /** Resets this label so it can be regenerated */
    public void reset() {
        this.label = new org.objectweb.asm.Label();
    }
}
