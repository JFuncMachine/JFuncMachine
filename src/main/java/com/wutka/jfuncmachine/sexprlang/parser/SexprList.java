package com.wutka.jfuncmachine.sexprlang.parser;

import java.util.ArrayList;

public final class SexprList extends SexprItem {
    public final ArrayList<SexprItem> value;

    public SexprList(ArrayList<SexprItem> value, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.value = value;
    }
}
