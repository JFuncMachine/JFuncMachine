package org.jfuncmachine.sexprlang.parser;

/** An S-Expression double value */
public final class SexprDouble extends SexprItem {
    /** The double value */
    public final double value;

    /** Create a new SexprDouble with the specified value and source location
     *
     * @param value The double value
     * @param filename The source file where this value was read from
     * @param lineNumber The line number in the source file where this value is located
     */
    public SexprDouble(double value, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.value = value;
    }

    public String toString() { return Double.toString(value); }
}
