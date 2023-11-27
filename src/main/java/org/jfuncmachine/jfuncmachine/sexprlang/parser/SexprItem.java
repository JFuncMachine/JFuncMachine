package org.jfuncmachine.jfuncmachine.sexprlang.parser;

public sealed abstract class SexprItem
    permits SexprString, SexprInt, SexprFloat, SexprSymbol, SexprList
{
    public final String filename;
    public final int lineNumber;

    public SexprItem(String filename, int lineNumber) {
        this.filename = filename;
        this.lineNumber = lineNumber;
    }
}
