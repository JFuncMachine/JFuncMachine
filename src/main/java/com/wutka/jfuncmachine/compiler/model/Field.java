package com.wutka.jfuncmachine.compiler.model;

import com.wutka.jfuncmachine.compiler.model.types.Type;

public class Field extends SourceElement {
    public final String name;
    public final Type type;

    public Field(String name, Type type, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.name = name;
        this.type = type;
    }
}
