package org.jfuncmachine.sexprlang.parser;

import java.util.ArrayList;

public final class SexprList extends SexprItem {
    public final ArrayList<SexprItem> value;

    public SexprList(ArrayList<SexprItem> value, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.value = value;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append('(');
        for (int i=0; i < value.size(); i++) {
            if (i > 0) builder.append(' ');
            builder.append(value.get(i).toString());
        }
        builder.append(')');
        return builder.toString();
    }
}
