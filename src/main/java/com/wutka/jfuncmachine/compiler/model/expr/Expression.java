package com.wutka.jfuncmachine.compiler.model.expr;

import com.wutka.jfuncmachine.compiler.classgen.ClassGenerator;
import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.model.SourceElement;
import com.wutka.jfuncmachine.compiler.model.types.Type;

public abstract class Expression extends SourceElement {
    public abstract Type getType();

    public Expression(String filename, int lineNumber) {
        super(filename, lineNumber);
    }

    public abstract void findCaptured(Environment env);

    // TODO: Should be abstract, but for now, allow it to be implemented case-by-case
    public void generate(ClassGenerator generator, Environment env) {

    }

}
