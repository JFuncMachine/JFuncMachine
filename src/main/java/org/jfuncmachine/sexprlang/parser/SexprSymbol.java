package org.jfuncmachine.sexprlang.parser;

/** An S-Expression symbol value */
public final class SexprSymbol extends SexprItem {
    /** The symbol value */
    public final String value;

    /** Create a new SexprSymbol with the specified value and source location
     *
     * @param value The symbol value
     * @param filename The source file where this value was read from
     * @param lineNumber The line number in the source file where this value is located
     */
    public SexprSymbol(String value, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.value = value;
    }

    public String toString() { return value; }
}
