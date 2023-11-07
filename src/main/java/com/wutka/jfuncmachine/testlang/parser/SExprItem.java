package com.wutka.jfuncmachine.testlang.parser;

public sealed abstract class SExprItem
    permits SExprString, SExprInt, SExprFloat, SExprSymbol, SExprList
{
    public final String filename;
    public final int lineNumber;

    public SExprItem(String filename, int lineNumber) {
        this.filename = filename;
        this.lineNumber = lineNumber;
    }
}
