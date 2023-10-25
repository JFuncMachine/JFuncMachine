package com.wutka.jfuncmachine.compiler.model.expr;

import com.wutka.jfuncmachine.compiler.model.types.Type;

public class Switch extends Expression {
    public final Expression value;
    public final SwitchCase[] cases;

    public Switch(Expression value, SwitchCase[] cases, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.value = value;
        this.cases = cases;

        if (cases.length == 0) {
            throw generateException("Switch must contain at least one case");
        }

        Type switchType = cases[0].expr().getType();
        for (int i=1; i < cases.length; i++) {
            if (!switchType.equals(cases[i].expr().getType())) {
                throw cases[i].generateException("Switch expressions must all be the same type");
            }
        }
    }

    public Type getType() {
        return cases[0].expr().getType();
    }
}
