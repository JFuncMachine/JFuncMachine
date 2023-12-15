package org.jfuncmachine.sexprlang.parser;

/** An item from an S-expression */
public sealed abstract class SexprItem
    permits SexprString, SexprInt, SexprDouble, SexprSymbol, SexprList
{
    /** The name of the source filename that the item was read from */
    public final String filename;
    /** The line number in the source file where this item started */
    public final int lineNumber;

    /** Create a new SexprItem
     *
     * @param filename The name of the source filename that the item was read from
     * @param lineNumber The line number in the source file where this item started
     */
    public SexprItem(String filename, int lineNumber) {
        this.filename = filename;
        this.lineNumber = lineNumber;
    }
}
