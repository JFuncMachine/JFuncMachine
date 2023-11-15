package com.wutka.jfuncmachine.compiler.model.expr.bool.tests;

public abstract class Test {

    public abstract Test invert();

    public boolean equals(Object other) {
        return other.getClass().equals(getClass());
    }
}
