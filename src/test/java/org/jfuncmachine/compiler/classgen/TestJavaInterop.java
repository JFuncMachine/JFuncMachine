package org.jfuncmachine.compiler.classgen;

import org.jfuncmachine.compiler.model.*;
import org.jfuncmachine.compiler.model.expr.Expression;
import org.jfuncmachine.compiler.model.expr.GetValue;
import org.jfuncmachine.compiler.model.expr.constants.IntConstant;
import org.jfuncmachine.compiler.model.expr.javainterop.*;
import org.jfuncmachine.compiler.model.types.Field;
import org.jfuncmachine.compiler.model.types.ObjectType;
import org.jfuncmachine.compiler.model.types.SimpleTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class TestJavaInterop {
    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(ClassGeneratorProvider.class)
    @Retention(RetentionPolicy.RUNTIME)
    private @interface TestAllImplementations {}

    @TestAllImplementations
    public void testCallConstructor(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("testjava", Access.PUBLIC, new Field[]{},
                new ObjectType(ToyClass.class.getName()),
                new CallJavaConstructor(ToyClass.class.getName(), new Expression[0]));

        Object result = generator.invokeMethod("TestJava", method);
        Assertions.assertInstanceOf(ToyClass.class, result, "Constructor should return ToyClass");
    }

    @TestAllImplementations
    public void testCallInterface(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("testjava", Access.PUBLIC, new Field[]{
                new Field("obj", new ObjectType(ToyInterface.class.getName()))
        }, SimpleTypes.INT,
                new CallJavaInterface(ToyInterface.class.getName(), "addMember",
                        SimpleTypes.INT, new GetValue("obj", new ObjectType(ToyInterface.class.getName())),
                        new Expression[] { new IntConstant(22) }));

        Object result = generator.invokeMethod("TestJava", method, new ToyClass(20));
        Assertions.assertEquals(result, 42);
    }

    @TestAllImplementations
    public void testCallMethod(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("testjava", Access.PUBLIC, new Field[]{
                new Field("obj", new ObjectType(ToyClass.class.getName()))
        }, SimpleTypes.INT,
                new CallJavaMethod(ToyClass.class.getName(), "addMember",
                        SimpleTypes.INT, new GetValue("obj", new ObjectType(ToyClass.class.getName())),
                        new Expression[] { new IntConstant(22) }));

        Object result = generator.invokeMethod("TestJava", method, new ToyClass(20));
        Assertions.assertEquals(result, 42);
    }

    @TestAllImplementations
    public void testCallSpecial(String generatorType, ClassGenerator generator) {
        MethodDef privateMethod = new MethodDef("privateMethod", Access.PRIVATE,
                new Field[] { new Field("x", SimpleTypes.STRING)},
                SimpleTypes.STRING, new GetValue("x", SimpleTypes.STRING));

        MethodDef callPrivateMethod = new MethodDef("callPrivateMethod", Access.PUBLIC,
                new Field[] { new Field("x", SimpleTypes.STRING)},
                SimpleTypes.STRING,
                new CallJavaSpecialMethod("org.jfuncmachine.test.TestJava",
                        "privateMethod", SimpleTypes.STRING,
                        new GetValue("this",
                                new ObjectType("org.jfuncmachine.test.TestJava")),
                        new Expression[] { new GetValue("x", SimpleTypes.STRING) }));

        MethodDef[] methods = {
                ConstructorDef.generateWith(new Field[0]),
                privateMethod, callPrivateMethod
        };

        ClassDef classDef = new ClassDef("org.jfuncmachine.test", "TestJava",
                Access.PUBLIC, methods, new ClassField[0], new String[0]);

        Object result = generator.invokeMethod(classDef, "callPrivateMethod", "Foo");
        Assertions.assertEquals(result, "Foo");
    }

    @TestAllImplementations
    public void testCallStaticMethod(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("testjava", Access.PUBLIC, new Field[]{
                new Field("str", SimpleTypes.STRING) },
                SimpleTypes.STRING,
                new CallJavaStaticMethod(ToyClass.class.getName(), "addStatic",
                        SimpleTypes.STRING,
                        new Expression[] { new GetValue("str", SimpleTypes.STRING) }));

        Object result = generator.invokeMethod("TestJava", method, "Bar");
        Assertions.assertEquals(result, "BarFoo");
    }

    @TestAllImplementations
    public void testCallSuperconstructor(String generatorType, ClassGenerator generator) {
        ConstructorDef constructor = new ConstructorDef(Access.PUBLIC,
                new Field[] { },
                new CallJavaSuperConstructor(new GetValue("this",
                        new ObjectType("org.jfuncmachine.test.TestJava")),
                        new Expression[] { new IntConstant(20)}));

        MethodDef[] methods = {
                constructor
        };

        ClassDef classDef = new ClassDef("org.jfuncmachine.test", "TestJava",
                ToyClass.class.getPackageName(), ToyClass.class.getSimpleName(),
                Access.PUBLIC, methods, new ClassField[0], new String[0]);

        Object result = generator.invokeMethod(classDef, "addMember", 22);
        Assertions.assertEquals(result, 42);
    }

    @TestAllImplementations
    public void testGetJavaField(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("testjava", Access.PUBLIC, new Field[]{
                new Field("obj", new ObjectType(ToyClass.class.getName()))
        }, SimpleTypes.INT,
                new GetJavaField(ToyClass.class.getName(), "memberInt", SimpleTypes.INT,
                        new GetValue("obj", new ObjectType(ToyClass.class.getName()))));

        Object result = generator.invokeMethod("TestJava", method, new ToyClass(42));
        Assertions.assertEquals(result, 42);
    }

    @TestAllImplementations
    public void testGetStaticJavaField(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("testjava", Access.PUBLIC, new Field[]{
        }, SimpleTypes.STRING,
                new GetJavaStaticField(ToyClass.class.getName(), "staticString", SimpleTypes.STRING));

        Object result = generator.invokeMethod("TestJava", method);
        Assertions.assertEquals(result, "Foo");
    }

    @TestAllImplementations
    public void testSetJavaField(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("testjava", Access.PUBLIC, new Field[]{
                new Field("obj", new ObjectType(ToyClass.class.getName())),
                new Field("i", SimpleTypes.INT)
        }, SimpleTypes.UNIT,
                new SetJavaField(ToyClass.class.getName(), "memberInt", SimpleTypes.INT,
                        new GetValue("obj", new ObjectType(ToyClass.class.getName())),
                         new GetValue("i", SimpleTypes.INT)));

        ToyClass toy = new ToyClass(37);
        Object result = generator.invokeMethod("TestJava", method, toy, 42);
        Assertions.assertEquals(toy.memberInt, 42);
    }

    @TestAllImplementations
    public void testSetStaticJavaField(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("testjava", Access.PUBLIC, new Field[]{
                new Field("str", SimpleTypes.STRING)
        }, SimpleTypes.UNIT,
                new SetJavaStaticField(ToyClass.class.getName(), "staticString", SimpleTypes.STRING,
                        new GetValue("str", SimpleTypes.STRING)));

        Object result = generator.invokeMethod("TestJava", method, "Quux");
        Assertions.assertEquals(ToyClass.staticString, "Quux");
        ToyClass.staticString = "Foo";
    }
}
