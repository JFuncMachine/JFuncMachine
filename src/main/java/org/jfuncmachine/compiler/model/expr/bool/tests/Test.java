package org.jfuncmachine.compiler.model.expr.bool.tests;

/** A base class for various boolean-valued tests used to compare values for if statements. */
public abstract class Test {

    /** Inverts the test, such that equal becomes not-equal or less-than becomes greater-or-equal. */
    public abstract Test invert();

    public boolean equals(Object other) {
        return other.getClass().equals(getClass());
    }
}
