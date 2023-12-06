package org.jfuncmachine.jfuncmachine.examples.minilang.expr;

import org.jfuncmachine.jfuncmachine.compiler.model.expr.Expression;
import org.jfuncmachine.jfuncmachine.examples.minilang.Environment;
import org.jfuncmachine.jfuncmachine.examples.minilang.MinilangException;
import org.jfuncmachine.jfuncmachine.examples.minilang.types.Type;
import org.jfuncmachine.jfuncmachine.util.unification.TypeHolder;
import org.jfuncmachine.jfuncmachine.util.unification.UnificationException;

public abstract class Expr {
    public final String filename;
    public final int lineNumber;
    public final TypeHolder<Type> type;

    public Expr(String filename, int lineNumber) {
        this.filename = filename;
        this.lineNumber = lineNumber;
        this.type = new TypeHolder<>();
    }

    public void unify(TypeHolder<Type> other, Environment<TypeHolder<Type>> env) throws UnificationException {
        this.type.unify(other);
    }

    public MinilangException createException(String message) {
        return new MinilangException(String.format("%s line %d: %s", filename, lineNumber, message));
    }

    public abstract Expression generate();
}
