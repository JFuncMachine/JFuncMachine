package org.jfuncmachine.compiler.model;

import org.jfuncmachine.compiler.model.expr.*;
import org.jfuncmachine.compiler.model.expr.constants.ClassConstant;
import org.jfuncmachine.compiler.model.expr.constants.IntConstant;
import org.jfuncmachine.compiler.model.expr.constants.ObjectConstant;
import org.jfuncmachine.compiler.model.expr.constants.StringConstant;
import org.jfuncmachine.compiler.model.expr.javainterop.*;
import org.jfuncmachine.compiler.model.types.*;

import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * A definition for an num, containing methods, fields, the names of interfaces that are implemented,
 * and enum value constructors
 */
public class EnumDef extends ClassDef {
    /** Create a class definition
     * @param packageName The package name of the class (may be null)
     * @param name The name of the class
     * @param access Any access flags for the class (can be a sum of values from the Access class)
     * @param methodDefs The methods defined for this class
     * @param fields The fields defined for this class
     * @param initializers The initializers for each enum value
     * @param interfaces The interfaces this class implements
     */
    public EnumDef(String packageName, String name,
                   int access,
                   MethodDef[] methodDefs, ClassField[] fields, EnumInitializer[] initializers,
                   String[] interfaces) {
        super(packageName, name, "java.lang", "Enum", access,
                generateMethodDefs(packageName+"."+name, methodDefs, fields, initializers),
                generateEnumFields(packageName+"."+name, fields, initializers), interfaces);
    }

    /** Create a class definition
     * @param packageName The package name of the class (may be null)
     * @param name The name of the class
     * @param access Any access flags for the class (can be a sum of values from the Access class)
     * @param methodDefs The methods defined for this class
     * @param fields The fields defined for this class
     * @param initializers The initializers for each enum value
     * @param interfaces The interfaces this class implements
     * @param filename The source code filename this class was generated from
     * @param lineNumber The starting line number in the source code where this class is defined
     */
    public EnumDef(String packageName, String name,
                   int access,
                   MethodDef[] methodDefs, ClassField[] fields, EnumInitializer[] initializers,
                   String[] interfaces,
                   String filename, int lineNumber) {
        super(packageName, name, "java.lang", "Enum", access,
                generateMethodDefs(packageName+"."+name, methodDefs, fields, initializers),
                generateEnumFields(packageName+"."+name, fields, initializers),
                interfaces, filename, lineNumber);
    }

    /** Create a class definition
     * @param packageName The package name of the class (may be null)
     * @param name The name of the class
     * @param access Any access flags for the class (can be a sum of values from the Access class)
     * @param methodDefs The methods defined for this class
     * @param fields The fields defined for this class
     * @param names The names of each enum value
     * @param interfaces The interfaces this class implements
     */
    public EnumDef(String packageName, String name,
                   int access,
                   MethodDef[] methodDefs, ClassField[] fields, String[] names,
                   String[] interfaces) {
        super(packageName, name, "java.lang", "Enum", access,
                generateMethodDefs(packageName+"."+name, methodDefs, fields, generateInitializers(names)),
                generateEnumFields(packageName+"."+name, fields, generateInitializers(names)), interfaces);
    }

    /** Create a class definition
     * @param packageName The package name of the class (may be null)
     * @param name The name of the class
     * @param access Any access flags for the class (can be a sum of values from the Access class)
     * @param methodDefs The methods defined for this class
     * @param fields The fields defined for this class
     * @param names The names of each enum value
     * @param interfaces The interfaces this class implements
     * @param filename The source code filename this class was generated from
     * @param lineNumber The starting line number in the source code where this class is defined
     */
    public EnumDef(String packageName, String name,
                   int access,
                   MethodDef[] methodDefs, ClassField[] fields, String[] names,
                   String[] interfaces,
                   String filename, int lineNumber) {
        super(packageName, name, "java.lang", "Enum", access,
                generateMethodDefs(packageName+"."+name, methodDefs, fields, generateInitializers(names)),
                generateEnumFields(packageName+"."+name, fields, generateInitializers(names)),
                interfaces, filename, lineNumber);
    }

    /** Adds the required enum methods to the list of method definitions for this enum
     *
     * @param className The name of the enum class being generated
     * @param initialMethodDefs The method defs already defined for this enum
     * @param fields The fields for this enum
     * @param initializers The initializers for this enum
     * @return The original method defs plus the methods required for an enum
     */
    public static MethodDef[] generateMethodDefs(String className, MethodDef[] initialMethodDefs, ClassField[] fields,
                                                 EnumInitializer[] initializers) {
        List<ClassField> memberFields = new ArrayList<>();
        for (ClassField field: fields) {
            if ((field.access & Access.STATIC) == 0) {
                memberFields.add(field);
            }
        }

        for (EnumInitializer initializer: initializers) {
            if (initializer.initializers.length != memberFields.size()) {
                throw initializer.generateException(String.format(
                        "Initializer %s for enum %s has %d values, but there are %d non-static class fields",
                        initializer.name, className, initializer.initializers.length, memberFields.size()));
            }
        }

        MethodDef[] methods = new MethodDef[initialMethodDefs.length + 5];
        System.arraycopy(initialMethodDefs, 0, methods, 0, initialMethodDefs.length);

        int pos = initialMethodDefs.length;

       methods[pos++] = new MethodDef("values", Access.PUBLIC + Access.STATIC,
                new Field[0], new ArrayType(new ObjectType(className)),
                new Cast(new ArrayType(new ObjectType(className)),
                        new CallJavaMethod("java.lang.Object", "clone", new Type[0],
                                new ArrayType(new ObjectType()),
                                new GetJavaStaticField(className, "$VALUES",
                                        new ArrayType(new ObjectType(className))),
                                new Expression[0])));

       methods[pos++] = new MethodDef("valueOf", Access.PUBLIC + Access.STATIC,
               new Field[] { new Field("name", SimpleTypes.STRING)},
                new ObjectType(className),
               new Cast(new ObjectType(className),
                       new CallJavaStaticMethod("java.lang.Enum", "valueOf",
                               new Type[] { new ObjectType("java.lang.Class"), SimpleTypes.STRING},
                               new ObjectType("java.lang.Enum"),
                               new Expression[] {
                                       new ClassConstant(new ObjectType(className)),
                                       new GetValue("name", SimpleTypes.STRING)
                               })));

       Field[] consFields = new Field[2 + memberFields.size()];
       consFields[0] = new Field("$name", SimpleTypes.STRING);
       consFields[1] = new Field("$index", SimpleTypes.INT);
       int fieldPos = 2;
       for (ClassField field: memberFields) {
           consFields[fieldPos++] = new Field(field.name, field.type);
       }

       Expression[] blockExprs = new Expression[1 + memberFields.size()];
       blockExprs[0] = new CallJavaSuperConstructor(new GetValue("this", new ObjectType()),
               new Expression[] {
                       new GetValue("$name", SimpleTypes.STRING),
                       new GetValue("$index", SimpleTypes.INT)
               });

       for (int i=0; i < memberFields.size(); i++) {
           ClassField field = memberFields.get(i);
           blockExprs[i+1] = new SetJavaField(className, field.name, field.type,
                   new GetValue("this", new ObjectType()),
                   new GetValue(field.name, field.type));
       }

       methods[pos++] = new ConstructorDef(Access.PRIVATE,
               consFields, new Block(blockExprs));

       Expression[] arrayInitExprs = new Expression[initializers.length];
       for (int i=0; i < initializers.length; i++) {
           arrayInitExprs[i] = new GetJavaStaticField(className, initializers[i].name,
                   new ObjectType(className));
       }
       methods[pos++] = new MethodDef("$values", Access.PRIVATE + Access.STATIC,
               new Field[0], new ArrayType(new ObjectType(className)),
               new NewArrayWithValues(new ObjectType(className), arrayInitExprs));

       Expression[] staticInitExprs = new Expression[initializers.length];
       for (int i=0; i < initializers.length; i++) {
           Expression[] inits = new Expression[initializers[i].initializers.length+2];
           inits[0] = new StringConstant(initializers[i].name);
           inits[1] = new IntConstant(i);
           System.arraycopy(initializers[i].initializers, 0, inits,
                   2, initializers[i].initializers.length);
           staticInitExprs[i] = new SetJavaStaticField(className, initializers[i].name,
                   new ObjectType(className),
                   new CallJavaConstructor(className, inits));
       }
       methods[pos++] = new StaticInitializerDef(new Block(staticInitExprs));
       return methods;
    }

    /**
     * Adds the class fields for the enum to the existing class fields
     * @param className The class name being generated
     * @param fields The current class fields
     * @param initializers The enum initializers for this enum
     * @return The original class fields with the enum field values added
     */
    public static ClassField[] generateEnumFields(String className, ClassField[] fields,
                                                  EnumInitializer[] initializers) {
        ClassField[] newFields = new ClassField[fields.length + initializers.length];

        System.arraycopy(fields, 0, newFields, 0, fields.length);

        int pos = fields.length;
        for (EnumInitializer initializer: initializers) {
            newFields[pos++] = new ClassField(initializer.name,
                    new ObjectType(className),
                    Access.PUBLIC + Access.STATIC + Access.FINAL + Access.ENUM,
                    null);
        }

        return newFields;
    }

    /**
     * Creates a list of enum initializers for each enum value name
     * @param names The names of the initializers to generate
     * @return An array of the enum initializers
     */
    public static EnumInitializer[] generateInitializers(String[] names) {
        EnumInitializer[] initializers = new EnumInitializer[names.length];
        for (int i=0; i < names.length; i++) {
            initializers[i] = new EnumInitializer(names[i], new Expression[0]);
        }
        return initializers;
    }
}