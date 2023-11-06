package com.wutka.jfuncmachine.compiler.model;

import com.wutka.jfuncmachine.compiler.model.types.Type;

public class ClassField extends SourceElement {
    public final String name;
    public final Type type;

    public ClassField(String name, Type type) {
        super(null, 0);
        this.name = name;
        this.type = type;
    }

    public ClassField(String name, Type type, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.name = name;
        this.type = type;
    }
}
