package com.wutka.jfuncmachine.compiler.model;

import org.objectweb.asm.Opcodes;

public class Class extends SourceElement {
    public enum Access {
        Public,
        Private,
        Protected
    }

    public final String packageName;
    public final String name;
    public final String superPackageName;
    public final String superName;
    public final Access access;
    public final Method[] methods;
    public final Field[] fields;

    public Class(String packageName, String name,
                 Access access,
                 Method[] methods, Field[] fields,
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
                 Access access,
                 Method[] methods, Field[] fields,
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
                 Access access,
                 Method[] methods, Field[] fields,
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
