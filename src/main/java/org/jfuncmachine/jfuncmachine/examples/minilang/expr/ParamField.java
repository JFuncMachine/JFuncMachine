package org.jfuncmachine.jfuncmachine.examples.minilang.expr;

import org.jfuncmachine.jfuncmachine.examples.minilang.types.Type;
import org.jfuncmachine.jfuncmachine.sexprlang.translate.ModelItem;
import org.jfuncmachine.jfuncmachine.util.unification.TypeHolder;

@ModelItem
public class ParamField {
    public final String name;
    public final TypeHolder<Type> type;

    public ParamField(String name) {
        this.name = name;
        this.type = new TypeHolder<>();
    }
}
