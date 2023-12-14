package org.jfuncmachine.compiler.model;

/**
 * A definition for a class, containing methods, fields, and the names of interfaces that are implemented
 */
public class ClassDef extends SourceElement {
    /** The package name of the class (may be null) */
    public final String packageName;
    /** The name of the class */
    public final String name;
    /** The package name of the superclass */
    public final String superPackageName;
    /** The class name of the superclass */
    public final String superName;
    /** Any access flags for the class (can be a sum of values from the Access class) */
    public final int access;
    /** The methods defined for this class */
    public final MethodDef[] methodDefs;
    /** The fields defined for this class */
    public final ClassField[] fields;
    /** The interfaces this class implements */
    public final String[] interfaces;

    /**
     * @param packageName The package name of the class (may be null)
     * @param name The name of the class
     * @param access Any access flags for the class (can be a sum of values from the Access class)
     * @param methodDefs The methods defined for this class
     * @param fields The fields defined for this class
     * @param interfaces The interfaces this class implements
     */
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

    /**
     * @param packageName The package name of the class (may be null)
     * @param name The name of the class
     * @param superClass The ClassDef of the superclass
     * @param access Any access flags for the class (can be a sum of values from the Access class)
     * @param methodDefs The methods defined for this class
     * @param fields The fields defined for this class
     * @param interfaces The interfaces this class implements
     */
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

    /**
     * @param packageName The package name of the class (may be null)
     * @param name The name of the class
     * @param superPackageName The package name of the superclass
     * @param superName The name of the superclass
     * @param access Any access flags for the class (can be a sum of values from the Access class)
     * @param methodDefs The methods defined for this class
     * @param fields The fields defined for this class
     * @param interfaces The interfaces this class implements
     */
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

    /**
     * @param packageName The package name of the class (may be null)
     * @param name The name of the class
     * @param access Any access flags for the class (can be a sum of values from the Access class)
     * @param methodDefs The methods defined for this class
     * @param fields The fields defined for this class
     * @param interfaces The interfaces this class implements
     * @param filename The source code filename this class was generated from
     * @param lineNumber The starting line number in the source code where this class is defined
     */
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

    /**
     * @param packageName The package name of the class (may be null)
     * @param name The name of the class
     * @param superClass The ClassDef of the superclass
     * @param access Any access flags for the class (can be a sum of values from the Access class)
     * @param methodDefs The methods defined for this class
     * @param fields The fields defined for this class
     * @param interfaces The interfaces this class implements
     * @param filename The source code filename this class was generated from
     * @param lineNumber The starting line number in the source code where this class is defined
     */
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

    /**
     * @param packageName The package name of the class (may be null)
     * @param name The name of the class
     * @param superPackageName The package name of the superclass
     * @param superName The name of the superclass
     * @param access Any access flags for the class (can be a sum of values from the Access class)
     * @param methodDefs The methods defined for this class
     * @param fields The fields defined for this class
     * @param interfaces The interfaces this class implements
     * @param filename The source code filename this class was generated from
     * @param lineNumber The starting line number in the source code where this class is defined
     */
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

    /** Returns the fully-qualified name of the class - package (if not null) + '.' + class name */
    public String getFullClassName() {
        if (packageName == null || packageName.isEmpty()) {
            return name;
        } else {
            return packageName + "." + name;
        }
    }
}
