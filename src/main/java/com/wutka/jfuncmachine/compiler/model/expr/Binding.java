package com.wutka.jfuncmachine.compiler.model.expr;

import com.wutka.jfuncmachine.compiler.model.types.Type;

public class Binding extends Expression {
    public final BindingPair[] bindings;
    public final Expression expr;

    public Binding( BindingPair[] bindings, Expression expr) {
        super(null, 0);
        this.bindings = bindings;
        this.expr = expr;
    }

    public Binding( BindingPair[] bindings, Expression expr, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.bindings = bindings;
        this.expr = expr;
    }

    public Type getType() {
        return expr.getType();
    }

    public static class BindingPair {
        public final String name;
        public final Expression value;

        public BindingPair (String name, Expression value) {
            this.name = name;
            this.value = value;
        }
    }
}
