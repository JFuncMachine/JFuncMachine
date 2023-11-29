package org.jfuncmachine.jfuncmachine.examples.minilang.expr;

import org.jfuncmachine.jfuncmachine.examples.minilang.Environment;
import org.jfuncmachine.jfuncmachine.util.unification.TypeHolder;
import org.jfuncmachine.jfuncmachine.util.unification.UnificationException;

public class SymbolExpr extends Expr {
    public final String name;

    public SymbolExpr(String name, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.name = name;
    }

    @Override
    public void unify(TypeHolder other, Environment<TypeHolder> env) throws UnificationException {
        TypeHolder symbolLookup = env.lookup(name);
        if (symbolLookup == null) {
            throw createException(String.format("Invalid symbol %s", name));
        }
        symbolLookup.unify(other);
    }
}
