package org.jfuncmachine.sexprlang.parser;

/** An S-Expression double value */
public final class SexprString extends SexprItem {
    /** The string value */
    public final String value;

    /** Create a new SexprString with the specified value and source location
     *
     * @param value The string value
     * @param filename The source file where this value was read from
     * @param lineNumber The line number in the source file where this value is located
     */
    public SexprString(String value, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.value = value;
    }

    public String toString() { return "\"" + value + "\""; }
}
