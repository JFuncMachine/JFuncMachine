package org.jfuncmachine.jfuncmachine.util.unification;

public interface Unifiable {
    void unify(Unifiable other) throws UnificationException;
}
