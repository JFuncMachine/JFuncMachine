package org.jfuncmachine.sexprlang.parser;

/** An S-Expression double value */
public final class SexprInt extends SexprItem {
    /** The int value */
    public final int value;

    /** Create a new SexprInt with the specified value and source location
     *
     * @param value The int value
     * @param filename The source file where this value was read from
     * @param lineNumber The line number in the source file where this value is located
     */
    public SexprInt(int value, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.value = value;
    }

    public String toString() { return Integer.toString(value); }
}
