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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class TestEnum {

    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(ClassGeneratorProvider.class)
    @Retention(RetentionPolicy.RUNTIME)
    private @interface TestAllImplementations {}

    @TestAllImplementations
    public void testSimpleEnum()
            throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        EnumDef newEnum = new EnumDef("org.jfuncmachine.test", "TestEnum",
                Access.PUBLIC,
                new MethodDef[0], new ClassField[0], new String[]{"Foo", "Bar", "Baz"},
                new String[0]);


        MethodDef getEnum = new MethodDef("getEnum", Access.PUBLIC + Access.STATIC,
                new Field[0],
                new ObjectType(newEnum.getFullClassName(), true),
                new GetJavaStaticField(newEnum.getFullClassName(), "Foo",
                        new ObjectType(newEnum.getFullClassName(), true)));

        ClassDef newClass = new ClassDef("org.jfuncmachine.test", "EnumTest",
                // Make it a public class
                Access.PUBLIC,
                // Containing one method, the main method, and no fields
                new MethodDef[]{getEnum, ConstructorDef.generateWith(new Field[0])},
                new ClassField[0], new String[0]);

        ClassGenerator gen = new ClassGenerator();

        gen.generate(new ClassDef[]{newEnum, newClass}, "test");
        try (var loader = new URLClassLoader(new URL[]{
                new File("test").toURI().toURL()
        })) {
            var loadedClass = loader.loadClass(newClass.getFullClassName());
            var enumClass = loader.loadClass(newEnum.getFullClassName());
            var getEnumMethod = loadedClass.getMethod("getEnum");
            var result = getEnumMethod.invoke(null);
            Assertions.assertInstanceOf(enumClass, result);
            Assertions.assertEquals("Foo", ((Enum)result).name());
        }
    }

    @TestAllImplementations
    public void testEnumWithParams()
            throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        EnumDef newEnum = new EnumDef("org.jfuncmachine.test", "TestEnum",
                Access.PUBLIC,
                new MethodDef[0], new ClassField[] {
                        new ClassField("thing", SimpleTypes.STRING, Access.PUBLIC, null),
                        new ClassField("amount", SimpleTypes.INT, Access.PUBLIC, null)
                },
                new EnumInitializer[] {
                        new EnumInitializer("Foo", new Expression[] { new StringConstant("fooThing"), new IntConstant(100) }),
                        new EnumInitializer("Bar", new Expression[] { new StringConstant("barThing"), new IntConstant(3000) }),
                        new EnumInitializer("Baz", new Expression[] { new StringConstant("bazThing"), new IntConstant(200) }),
                },
                new String[0]);


        MethodDef getEnum = new MethodDef("getEnum", Access.PUBLIC + Access.STATIC,
                new Field[0],
                new ObjectType(newEnum.getFullClassName(), true),
                new GetJavaStaticField(newEnum.getFullClassName(), "Bar",
                        new ObjectType(newEnum.getFullClassName(), true)));

        ClassDef newClass = new ClassDef("org.jfuncmachine.test", "EnumTest",
                // Make it a public class
                Access.PUBLIC,
                // Containing one method, the main method, and no fields
                new MethodDef[]{getEnum, ConstructorDef.generateWith(new Field[0])},
                new ClassField[0], new String[0]);

        ClassGenerator gen = new ClassGenerator();

        gen.generate(new ClassDef[]{newEnum, newClass}, "test");
        try (var loader = new URLClassLoader(new URL[]{
                new File("test").toURI().toURL()
        })) {
            var loadedClass = loader.loadClass(newClass.getFullClassName());
            var enumClass = loader.loadClass(newEnum.getFullClassName());
            var getEnumMethod = loadedClass.getMethod("getEnum");
            var result = getEnumMethod.invoke(null);
            Assertions.assertInstanceOf(enumClass, result);
            Assertions.assertEquals("Bar", ((Enum)result).name());
            var thingField = enumClass.getField("thing");
            Assertions.assertEquals("barThing", thingField.get(result));
            var amountField = enumClass.getField("amount");
            Assertions.assertEquals(3000, amountField.get(result));
        }
    }
}
