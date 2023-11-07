package com.wutka.jfuncmachine.testlang.parser;

import java.util.ArrayList;

public final class SExprList extends SExprItem {
    public final ArrayList<SExprItem> value;

    public SExprList(ArrayList<SExprItem> value, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.value = value;
    }
}
