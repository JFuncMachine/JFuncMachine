package com.wutka.jfuncmachine.compiler.model;

public class Class extends SourceElement {
    public String packageName;
    public String name;
    public final Method[] methods;
    public final Field[] fields;

    public Class(String packageName, String name, Method[] methods, Field[] fields,
                 String filename, int lineNumber) {
        super(filename, lineNumber);
        this.packageName = packageName;
        this.name = name;
        this.methods = methods;
        this.fields = fields;
    }
}
