package com.wutka.jfuncmachine.compiler.model;

public class Class extends SourceElement {
    public final String packageName;
    public final String name;
    public final String superPackageName;
    public final String superName;
    public final int access;
    public final Method[] methods;
    public final ClassField[] fields;

    public Class(String packageName, String name,
                 int access,
                 Method[] methods, ClassField[] fields,
                 String filename, int lineNumber) {
        super(filename, lineNumber);
        this.packageName = packageName;
        this.name = name;
        this.superPackageName = "java.lang";
        this.superName = "Object";
        this.access = access;
        this.methods = methods;
        this.fields = fields;
    }

    public Class(String packageName, String name,
                 Class superClass,
                 int access,
                 Method[] methods, ClassField[] fields,
                 String filename, int lineNumber) {
        super(filename, lineNumber);
        this.packageName = packageName;
        this.name = name;
        this.superPackageName = superClass.packageName;
        this.superName = superClass.name;
        this.access = access;
        this.methods = methods;
        this.fields = fields;
    }

    public Class(String packageName, String name,
                 String superPackageName, String superName,
                 int access,
                 Method[] methods, ClassField[] fields,
                 String filename, int lineNumber) {
        super(filename, lineNumber);
        this.packageName = packageName;
        this.name = name;
        this.superPackageName = superPackageName;
        this.superName = superName;
        this.access = access;
        this.methods = methods;
        this.fields = fields;
    }
}
