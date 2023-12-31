package org.jfuncmachine.compiler.classgen;

import org.jfuncmachine.compiler.model.*;
import org.jfuncmachine.compiler.model.expr.Expression;
import org.jfuncmachine.compiler.model.expr.GetValue;
import org.jfuncmachine.compiler.model.expr.InlineCall;
import org.jfuncmachine.compiler.model.expr.NewArrayWithValues;
import org.jfuncmachine.compiler.model.expr.boxing.Box;
import org.jfuncmachine.compiler.model.expr.constants.IntConstant;
import org.jfuncmachine.compiler.model.expr.constants.StringConstant;
import org.jfuncmachine.compiler.model.expr.conv.ToByte;
import org.jfuncmachine.compiler.model.expr.javainterop.CallJavaMethod;
import org.jfuncmachine.compiler.model.expr.javainterop.CallJavaStaticMethod;
import org.jfuncmachine.compiler.model.expr.javainterop.GetJavaStaticField;
import org.jfuncmachine.compiler.model.expr.javainterop.SetJavaStaticField;
import org.jfuncmachine.compiler.model.inline.Inlines;
import org.jfuncmachine.compiler.model.types.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class TestBasicClassGeneration {

    @Test
    public void testEmptyClass()
        throws IOException, ClassNotFoundException {
        ClassDef newClass = new ClassDef("org.jfuncmachine.test", "EmptyClass",
                Access.PUBLIC,
                new MethodDef[0], new ClassField[0], new String[0],
                "empty.test", 1);


        ClassGenerator gen = new ClassGenerator();

        gen.generate(newClass, "testclasspath");
        try (var loader = new URLClassLoader(new URL[] {
                new File("testclasspath").toURI().toURL()
        })) {
            var loadedClass = loader.loadClass(newClass.getFullClassName());
            Assertions.assertEquals(newClass.packageName, loadedClass.getPackageName());
            Assertions.assertEquals(newClass.name, loadedClass.getSimpleName());
            Assertions.assertEquals(newClass.getFullClassName(), loadedClass.getName());
        }
    }

    @Test
    public void testHelloWorld()
            throws IOException, ClassNotFoundException {

        // Create a public static method named "main"
        MethodDef main = new MethodDef("main", Access.PUBLIC + Access.STATIC,
                // That takes one argument called "args" that is an array of String
                new Field[] { new Field("args", new ArrayType(SimpleTypes.STRING)) },
                // The main method returns type Unit (aka void)
                SimpleTypes.UNIT,
                // The only thing the function should do is call System.out.println, which means
                // call the println method on the static PrintStream field named out in the System class
                // So create an expression that calls the println method
                new CallJavaMethod("java.io.PrintStream", "println",
                        // Get the PrintStream object from System.out, that is the object
                        // that we will be calling println on
                        SimpleTypes.UNIT, new GetJavaStaticField("java.lang.System", "out",
                                new ObjectType("java.io.PrintStream")),
                        // Load up the arguments to println, which is just one, that is a string constant
                        new Expression[] { new StringConstant("Hello World!") }
                        // the function returns void (which in functional languages is called Unit)
                ));

        // Create a org.jfuncmachine.test.HelloWorld class
        ClassDef newClass = new ClassDef("org.jfuncmachine.test", "HelloWorld",
                // Make it a public class
                Access.PUBLIC,
                // Containing one method, the main method, and no fields
                new MethodDef[] { main }, new ClassField[0], new String[0]);


        ClassGenerator gen = new ClassGenerator();

        gen.generate(newClass, "testclasspath");
        try (var loader = new URLClassLoader(new URL[] {
                new File("testclasspath").toURI().toURL()
        })) {
            var loadedClass = loader.loadClass(newClass.getFullClassName());
            Assertions.assertEquals(newClass.packageName, loadedClass.getPackageName());
            Assertions.assertEquals(newClass.name, loadedClass.getSimpleName());
            Assertions.assertEquals(newClass.getFullClassName(), loadedClass.getName());
        }
    }

    @Test
    public void testInlineAdd()
            throws IOException, ClassNotFoundException {

        // Create a public static method named "main"
        MethodDef main = new MethodDef("main", Access.PUBLIC + Access.STATIC,
                // That takes one argument called "args" that is an array of String
                new Field[] { new Field("args", new ArrayType(SimpleTypes.STRING)) },
                // The only thing the function should do is call System.out.println
                // So create an expression that calls the println method
                SimpleTypes.UNIT, new CallJavaMethod("java.io.PrintStream", "println",
                        // Get the PrintStream object from System.out, that is the object
                        // that we will be calling println on
                SimpleTypes.UNIT, new GetJavaStaticField("java.lang.System", "out",
                                new ObjectType("java.io.PrintStream"), "inlineadd.test", 0),
                        // Load up the arguments to println, which is just one, that is a string constant
                        new Expression[]{
                                new CallJavaStaticMethod("java.lang.String", "format",
                                        new Type[] { SimpleTypes.STRING, new ArrayType(new ObjectType("java.lang.Object"))},
                                        new Expression[]{
                                                new StringConstant("The sum of 12 and 30 is %d", "inlineadd", 0),
                                                new NewArrayWithValues(new ObjectType(), new Expression[] {
                                                    new Box(new InlineCall(Inlines.IntAdd,
                                                        new Expression[]{new IntConstant(12, "inlineadd", 0),
                                                                new IntConstant(30, "inlineadd", 0)},
                                                        "inlineadd", 0), "inlineadd", 0) },
                                                   "inlineadd", 0)
                                        }, SimpleTypes.STRING, "inlineadd", 0)
                        },
                        // the function returns void (which in functional languages is called Unit)
                "inlineadd.test", 0),
                // The main method returns void (Unit)
                "inlineadd.test", 0);

        // Create a org.jfuncmachine.test.HelloWorld class
        ClassDef newClass = new ClassDef("org.jfuncmachine.test", "InlineAdd",
                // Make it a public class
                Access.PUBLIC,
                // Containing one method, the main method, and no fields
                new MethodDef[] { main }, new ClassField[0], new String[0],
                "helloworld.test", 1);


        ClassGenerator gen = new ClassGenerator();

        gen.generate(newClass, "testclasspath");
        try (var loader = new URLClassLoader(new URL[] {
                new File("testclasspath").toURI().toURL()
        })) {
            var loadedClass = loader.loadClass(newClass.getFullClassName());
            Assertions.assertEquals(newClass.packageName, loadedClass.getPackageName());
            Assertions.assertEquals(newClass.name, loadedClass.getSimpleName());
            Assertions.assertEquals(newClass.getFullClassName(), loadedClass.getName());
        }
    }

    @Test
    public void testInlineAdd2()
            throws IOException, ClassNotFoundException {

        // Create a public static method named "main"
        MethodDef main = new MethodDef("main", Access.PUBLIC + Access.STATIC,
                // That takes one argument called "args" that is an array of String
                new Field[] { new Field("args", new ArrayType(SimpleTypes.STRING)) },
                // The only thing the function should do is call System.out.println
                // So create an expression that calls the println method
                SimpleTypes.UNIT, new CallJavaMethod("java.io.PrintStream", "println",
                        // Get the PrintStream object from System.out, that is the object
                        // that we will be calling println on
                SimpleTypes.UNIT, new GetJavaStaticField("java.lang.System", "out",
                                new ObjectType("java.io.PrintStream"), "inlineadd.test", 0),
                        // Load up the arguments to println, which is just one, that is a string constant
                        new Expression[]{
                                new CallJavaStaticMethod("java.lang.String", "format",
                                        new Type[] { SimpleTypes.STRING, new ArrayType(new ObjectType("java.lang.Object"))},
                                        new Expression[]{
                                                new StringConstant("The sum of 120 and 30 as a byte is %d", "inlineadd", 0),
                                                new NewArrayWithValues(new ObjectType(), new Expression[] {
                                                        new Box(new ToByte(new InlineCall(Inlines.IntAdd,
                                                                new Expression[]{new IntConstant(120, "inlineadd", 0),
                                                                        new IntConstant(30, "inlineadd", 0)},
                                                                "inlineadd", 0)), "inlineadd", 0) },
                                                        "inlineadd", 0)
                                        }, SimpleTypes.STRING, "inlineadd", 0)
                        },
                        // the function returns void (which in functional languages is called Unit)
                "inlineadd.test", 0),
                // The main method returns void (Unit)
                "inlineadd.test", 0);

        // Create a org.jfuncmachine.test.HelloWorld class
        ClassDef newClass = new ClassDef("org.jfuncmachine.test", "InlineAdd2",
                // Make it a public class
                Access.PUBLIC,
                // Containing one method, the main method, and no fields
                new MethodDef[] { main }, new ClassField[0], new String[0],
                "helloworld.test", 1);


        ClassGenerator gen = new ClassGenerator();

        gen.generate(newClass, "testclasspath");
        try (var loader = new URLClassLoader(new URL[] {
                new File("testclasspath").toURI().toURL()
        })) {
            var loadedClass = loader.loadClass(newClass.getFullClassName());
            Assertions.assertEquals(newClass.packageName, loadedClass.getPackageName());
            Assertions.assertEquals(newClass.name, loadedClass.getSimpleName());
            Assertions.assertEquals(newClass.getFullClassName(), loadedClass.getName());
        }
    }

    @Test
    public void testField()
            throws IOException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {

        ClassField field = new ClassField("aField", SimpleTypes.STRING,
                Access.PUBLIC + Access.STATIC, "foo");

        // Create a public static method named "main"
        MethodDef getField = new MethodDef("getField", Access.PUBLIC + Access.STATIC,
                new Field[] { },
                SimpleTypes.STRING,
                new GetJavaStaticField("org.jfuncmachine.test.FieldTest", "aField",
                        SimpleTypes.STRING));

        MethodDef setField = new MethodDef("setField", Access.PUBLIC + Access.STATIC,
                new Field[] { new Field("x", SimpleTypes.STRING )},
                SimpleTypes.UNIT,
                new SetJavaStaticField("org.jfuncmachine.test.FieldTest", "aField",
                        SimpleTypes.STRING, new GetValue("x", SimpleTypes.STRING)));

        ClassDef newClass = new ClassDef("org.jfuncmachine.test", "FieldTest",
                // Make it a public class
                Access.PUBLIC,
                // Containing one method, the main method, and no fields
                new MethodDef[] { getField, setField, ConstructorDef.generateWith(new Field[0])},
                new ClassField[] { field }, new String[0]);

        ClassGenerator gen = new ClassGenerator();

        gen.generate(newClass, "testclasspath");
        try (var loader = new URLClassLoader(new URL[] {
                new File("testclasspath").toURI().toURL()
        })) {
            var loadedClass = loader.loadClass(newClass.getFullClassName());
            Object instance = loadedClass.getConstructors()[0].newInstance();
            Method getFieldMethod = loadedClass.getMethod("getField");
            Method setFieldMethod = loadedClass.getMethod("setField", String.class);

            Assertions.assertEquals("foo", getFieldMethod.invoke(instance),
                    "Initial value should be foo");
            setFieldMethod.invoke(instance, "bar");
            Assertions.assertEquals("bar", getFieldMethod.invoke(instance),
                    "Changed value should be bar");

        }
    }
}
