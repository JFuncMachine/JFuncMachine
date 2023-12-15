package org.jfuncmachine.runtime;

/** An interface to represent the invocation of a tail-call reference */
public interface TailCall {
    /** Invoke the tail call
     *
     * @return The result of the call (may be another TailCall object)
     */
    Object invoke();
}
