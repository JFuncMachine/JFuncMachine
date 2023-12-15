package org.jfuncmachine.sexprlang.parser;

import java.util.ArrayList;

/** A list of S-Expression items */
public final class SexprList extends SexprItem {
    /** The item list */
    public final ArrayList<SexprItem> value;

    /** Create a new SexprList with the specified values and source location
     *
     * @param value The list of values
     * @param filename The source file where this list was read from
     * @param lineNumber The line number in the source file where this list starts
     */
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
