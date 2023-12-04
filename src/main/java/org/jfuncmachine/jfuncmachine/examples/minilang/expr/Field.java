package org.jfuncmachine.jfuncmachine.examples.minilang.expr;

import org.jfuncmachine.jfuncmachine.sexprlang.translate.ModelItem;
import org.jfuncmachine.jfuncmachine.util.unification.TypeHolder;

public class Field {
    public final String name;
    public final TypeHolder type;

    public Field(String name) {
        this.name = name;
        this.type = new TypeHolder();
    }
}
