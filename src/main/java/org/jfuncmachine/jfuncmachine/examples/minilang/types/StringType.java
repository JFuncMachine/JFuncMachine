package org.jfuncmachine.jfuncmachine.examples.minilang.types;

import org.jfuncmachine.jfuncmachine.compiler.model.types.SimpleTypes;
import org.jfuncmachine.jfuncmachine.util.unification.Unifiable;
import org.jfuncmachine.jfuncmachine.util.unification.UnificationException;

public class StringType extends Type {
    public StringType(String filename, int lineNumber) {
        super(filename, lineNumber);
    }

    @Override
    public org.jfuncmachine.jfuncmachine.compiler.model.types.Type toJFMType() {
        return SimpleTypes.STRING;
    }

    @Override
    public void unify(Unifiable other) throws UnificationException {
        if (!(other instanceof StringType)) {
            throw createException(other);
        }
    }
}
