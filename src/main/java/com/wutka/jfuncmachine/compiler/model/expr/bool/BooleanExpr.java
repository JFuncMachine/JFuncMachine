package com.wutka.jfuncmachine.compiler.model.expr.bool;

import com.wutka.jfuncmachine.compiler.classgen.ClassGenerator;
import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.classgen.Label;
import com.wutka.jfuncmachine.compiler.model.SourceElement;
import com.wutka.jfuncmachine.compiler.model.expr.Expression;

public abstract class BooleanExpr extends SourceElement {
    public BooleanExpr(String filename, int lineNumber) {
        super(filename, lineNumber);
    }

    public abstract void generate(ClassGenerator generator, Environment env,
                                  Expression truePath, Label falsePath) {

    }
}
