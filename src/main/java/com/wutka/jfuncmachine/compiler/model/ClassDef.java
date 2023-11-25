package com.wutka.jfuncmachine.compiler.model;

public class ClassDef extends SourceElement {
    public final String packageName;
    public final String name;
    public final String superPackageName;
    public final String superName;
    public final int access;
    public final MethodDef[] methodDefs;
    public final ClassField[] fields;
    public final String[] interfaces;

    public ClassDef(String packageName, String name,
                    int access,
                    MethodDef[] methodDefs, ClassField[] fields, String[] interfaces) {
        super(null, 0);
        this.packageName = packageName;
        this.name = name;
        this.superPackageName = "java.lang";
        this.superName = "Object";
        this.access = access;
        this.methodDefs = methodDefs;
        this.fields = fields;
        this.interfaces = interfaces;
    }

    public ClassDef(String packageName, String name,
                    ClassDef superClass,
                    int access,
                    MethodDef[] methodDefs, ClassField[] fields, String[] interfaces) {
        super(null, 0);
        this.packageName = packageName;
        this.name = name;
        this.superPackageName = superClass.packageName;
        this.superName = superClass.name;
        this.access = access;
        this.methodDefs = methodDefs;
        this.fields = fields;
        this.interfaces = interfaces;
    }

    public ClassDef(String packageName, String name,
                    String superPackageName, String superName,
                    int access,
                    MethodDef[] methodDefs, ClassField[] fields, String[] interfaces) {
        super(null, 0);
        this.packageName = packageName;
        this.name = name;
        this.superPackageName = superPackageName;
        this.superName = superName;
        this.access = access;
        this.methodDefs = methodDefs;
        this.fields = fields;
        this.interfaces = interfaces;
    }

    public ClassDef(String packageName, String name,
                    int access,
                    MethodDef[] methodDefs, ClassField[] fields, String[] interfaces,
                    String filename, int lineNumber) {
        super(filename, lineNumber);
        this.packageName = packageName;
        this.name = name;
        this.superPackageName = "java.lang";
        this.superName = "Object";
        this.access = access;
        this.methodDefs = methodDefs;
        this.fields = fields;
        this.interfaces = interfaces;
    }

    public ClassDef(String packageName, String name,
                    ClassDef superClass,
                    int access,
                    MethodDef[] methodDefs, ClassField[] fields, String[] interfaces,
                    String filename, int lineNumber) {
        super(filename, lineNumber);
        this.packageName = packageName;
        this.name = name;
        this.superPackageName = superClass.packageName;
        this.superName = superClass.name;
        this.access = access;
        this.methodDefs = methodDefs;
        this.fields = fields;
        this.interfaces = interfaces;
    }

    public ClassDef(String packageName, String name,
                    String superPackageName, String superName,
                    int access,
                    MethodDef[] methodDefs, ClassField[] fields, String[] interfaces,
                    String filename, int lineNumber) {
        super(filename, lineNumber);
        this.packageName = packageName;
        this.name = name;
        this.superPackageName = superPackageName;
        this.superName = superName;
        this.access = access;
        this.methodDefs = methodDefs;
        this.fields = fields;
        this.interfaces = interfaces;
    }

    public String getFullClassName() {
        return packageName + "." + name;
    }
}
