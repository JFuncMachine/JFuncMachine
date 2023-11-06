package com.wutka.jfuncmachine.compiler.classgen;

import com.wutka.jfuncmachine.compiler.model.Access;
import com.wutka.jfuncmachine.compiler.model.Class;
import com.wutka.jfuncmachine.compiler.model.ClassField;
import com.wutka.jfuncmachine.compiler.model.Method;
import com.wutka.jfuncmachine.compiler.model.expr.Expression;
import com.wutka.jfuncmachine.compiler.model.expr.constants.StringConstant;
import com.wutka.jfuncmachine.compiler.model.expr.javaintop.CallJavaMethod;
import com.wutka.jfuncmachine.compiler.model.expr.javaintop.GetJavaStaticField;
import com.wutka.jfuncmachine.compiler.model.types.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;

public class TestBasicClassGeneration {

    @Test
    public void testEmptyClass()
        throws IOException, ClassNotFoundException {
        Class newClass = new Class("com.wutka.test", "EmptyClass",
                Access.PUBLIC,
                new Method[0], new ClassField[0],
                "empty.test", 1);


        ClassGenerator gen = new ClassGenerator();

        gen.generate(newClass, "testclasspath");
        try (var loader = new URLClassLoader(new URL[] {
                new File("testclasspath").toURI().toURL()
        })) {
            var loadedClass = loader.loadClass(newClass.packageName + "." + newClass.name);
            Assertions.assertEquals(newClass.packageName, loadedClass.getPackageName());
            Assertions.assertEquals(newClass.name, loadedClass.getSimpleName());
            Assertions.assertEquals(newClass.packageName + "." + newClass.name, loadedClass.getName());
        }
    }

    @Test
    public void testHelloWorld()
            throws IOException, ClassNotFoundException {

        Method main = new Method("main", Access.PUBLIC + Access.STATIC,
                new Field[] { new Field("args", new ArrayType(SimpleTypes.STRING, 0)) },
                new CallJavaMethod("java.io.PrintStream", "println",
                        new GetJavaStaticField("java.lang.System", "out",
                                new ObjectType("java.io.PrintStream"), "helloworld.test", 0),
                        new Expression[] { new StringConstant("Hello World!", "helloworld.test", 0) },
                        SimpleTypes.UNIT, "helloworld.test", 0),
                SimpleTypes.UNIT, "helloworld.test", 0);

        Class newClass = new Class("com.wutka.test", "HelloWorld",
                Access.PUBLIC,
                new Method[] { main }, new ClassField[0],
                "helloworld.test", 1);


        ClassGenerator gen = new ClassGenerator();

        gen.generate(newClass, "testclasspath");
        try (var loader = new URLClassLoader(new URL[] {
                new File("testclasspath").toURI().toURL()
        })) {
            var loadedClass = loader.loadClass(newClass.packageName + "." + newClass.name);
            Assertions.assertEquals(newClass.packageName, loadedClass.getPackageName());
            Assertions.assertEquals(newClass.name, loadedClass.getSimpleName());
            Assertions.assertEquals(newClass.packageName + "." + newClass.name, loadedClass.getName());
        }
    }
}
