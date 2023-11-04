package com.wutka.jfuncmachine.compiler.classgen;

import com.wutka.jfuncmachine.compiler.model.Class;
import com.wutka.jfuncmachine.compiler.model.Field;
import com.wutka.jfuncmachine.compiler.model.Method;
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
                Class.Access.Public,
                new Method[0], new Field[0],
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
}
