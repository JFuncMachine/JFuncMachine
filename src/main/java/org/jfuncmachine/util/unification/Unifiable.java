package org.jfuncmachine.util.unification;

/** An interface for types that can be unified with each other */
public interface Unifiable {
    /** Performs a unification of types
     *
     * @param other The type to unify with
     * @throws UnificationException If the types can't be unified
     */
    void unify(Unifiable other) throws UnificationException;
}
