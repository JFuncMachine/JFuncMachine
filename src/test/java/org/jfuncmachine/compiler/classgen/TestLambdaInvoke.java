package org.jfuncmachine.compiler.classgen;

import org.jfuncmachine.compiler.model.*;
import org.jfuncmachine.compiler.model.expr.*;
import org.jfuncmachine.compiler.model.expr.constants.IntConstant;
import org.jfuncmachine.compiler.model.expr.constants.StringConstant;
import org.jfuncmachine.compiler.model.expr.javainterop.SetJavaField;
import org.jfuncmachine.compiler.model.expr.javainterop.SetJavaStaticField;
import org.jfuncmachine.compiler.model.inline.Inlines;
import org.jfuncmachine.compiler.model.types.*;
import org.junit.jupiter.api.Assertions;
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
import java.util.function.Function;
import java.util.function.Supplier;

public class TestLambdaInvoke {
    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(ClassGeneratorProvider.class)
    @Retention(RetentionPolicy.RUNTIME)
    private @interface TestAllImplementations {}

    @TestAllImplementations
    public void testLambdaInvokeInBinding(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("lambainvoketest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.INT) },
                SimpleTypes.INT,
                new Binding(new Binding.BindingPair[]{
                        new Binding.BindingPair("double",
                                new Lambda(new Field[]{
                                        new Field("n", SimpleTypes.INT)
                                }, SimpleTypes.INT,
                                        new InlineCall(Inlines.IntAdd, new Expression[] {
                                                new GetValue("n", SimpleTypes.INT),
                                                new GetValue("n", SimpleTypes.INT)
                                        })))
                }, Binding.Visibility.Previous,
                        new Invoke(new FunctionType(new Type[] { SimpleTypes.INT }, SimpleTypes.INT),
                        new GetValue("double", new ObjectType()), new Expression[] {
                                new GetValue("x", SimpleTypes.INT)
                })));


        Object result = generator.invokeMethod("TestLambda",method, 21);
        Assertions.assertEquals(42, result);
    }

    @TestAllImplementations
    public void testLambdaInvokeInBindingWithCapture(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("lambainvoketest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.INT) },
                SimpleTypes.INT,
                new Binding(new Binding.BindingPair[]{
                        new Binding.BindingPair("q", new IntConstant(5)),
                        new Binding.BindingPair("addq",
                                new Lambda(new Field[]{
                                        new Field("n", SimpleTypes.INT)
                                }, SimpleTypes.INT,
                                        new InlineCall(Inlines.IntAdd, new Expression[] {
                                                new GetValue("q", SimpleTypes.INT),
                                                new GetValue("n", SimpleTypes.INT)
                                        })))
                }, Binding.Visibility.Previous,
                        new Invoke(new FunctionType(new Type[] { SimpleTypes.INT }, SimpleTypes.INT),
                                new GetValue("addq", new ObjectType()), new Expression[] {
                                new GetValue("x", SimpleTypes.INT)
                        })));


        Object result = generator.invokeMethod("TestLambda",method, 37);
        Assertions.assertEquals(42, result);
    }
    @TestAllImplementations
    public void testLambdaInvokeStaticSet(String generatorType, ClassGenerator generator) throws IOException {
        MethodDef method = new MethodDef("lambdainvoketest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.INT) },
                new ObjectType("java.util.function.Supplier"),
                new Lambda(new ObjectType("java.util.function.Supplier"), "get",
                        new Field[0], SimpleTypes.INT.getBoxType(), true,
                        new Block(new Expression[] {
                                new SetJavaStaticField("org.jfuncmachine.test.LambdaTest", "setme",
                                        SimpleTypes.STRING,
                                        new StringConstant("foo")),
                                new GetValue("x", SimpleTypes.INT)
                        })));

        ClassField field = new ClassField("setme", SimpleTypes.STRING, Access.PUBLIC + Access.STATIC, "bar");

        ClassDef newClass = new ClassDef("org.jfuncmachine.test", "LambdaTest",
                // Make it a public class
                Access.PUBLIC,
                // Containing one method, the main method, and no fields
                new MethodDef[] { method, ConstructorDef.generateWith(new Field[0])},
                new ClassField[] { field }, new String[0]);

        ClassGenerator gen = new ClassGenerator();

        gen.generate(newClass, "testclasspath");
        try (var loader = new URLClassLoader(new URL[] {
                new File("testclasspath").toURI().toURL()
        })) {
            var loadedClass = loader.loadClass(newClass.getFullClassName());
            Object instance = loadedClass.getConstructors()[0].newInstance();
            java.lang.reflect.Field fieldRef = loadedClass.getField("setme");
            Object initialFieldVal = fieldRef.get(instance);
            Assertions.assertEquals("bar", initialFieldVal);
            Method methodRef = loadedClass.getMethod("lambdainvoketest", int.class);
            Object lambdaRef = methodRef.invoke(instance, 5);
            Object lambdaResult = ((Supplier)lambdaRef).get();
            Assertions.assertEquals(Integer.valueOf(5), lambdaResult);
            Object fieldVal = fieldRef.get(instance);
            Assertions.assertEquals("foo", fieldVal);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    @TestAllImplementations
    public void testLambdaInvokeWithCaptureThis(String generatorType, ClassGenerator generator) throws IOException {
        MethodDef method = new MethodDef("lambdainvoketest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.INT) },
                new ObjectType("java.util.function.Supplier"),
                new Lambda(new ObjectType("java.util.function.Supplier"), "get",
                        new Field[0], SimpleTypes.INT.getBoxType(), true,
                        new Block(new Expression[] {
                                new SetJavaField("org.jfuncmachine.test.LambdaTest", "setme",
                                        SimpleTypes.STRING,
                                        new GetValue("this", new ObjectType("org.jfuncmachine.test.LambdaTest")),
                                        new StringConstant("foo")),
                                new GetValue("x", SimpleTypes.INT)
                        })));

        ClassField field = new ClassField("setme", SimpleTypes.STRING, Access.PUBLIC, "bar");

        ClassDef newClass = new ClassDef("org.jfuncmachine.test", "LambdaTest",
                // Make it a public class
                Access.PUBLIC,
                // Containing one method, the main method, and no fields
                new MethodDef[] { method, ConstructorDef.generateWith(new Field[0])},
                new ClassField[] { field }, new String[0]);

        ClassGenerator gen = new ClassGenerator();

        gen.generate(newClass, "testclasspath");
        try (var loader = new URLClassLoader(new URL[] {
                new File("testclasspath").toURI().toURL()
        })) {
            var loadedClass = loader.loadClass(newClass.getFullClassName());
            Object instance = loadedClass.getConstructors()[0].newInstance();
            java.lang.reflect.Field fieldRef = loadedClass.getField("setme");
            Object initialFieldVal = fieldRef.get(instance);
            Assertions.assertEquals("bar", initialFieldVal);
            Method methodRef = loadedClass.getMethod("lambdainvoketest", int.class);
            Object lambdaRef = methodRef.invoke(instance, 5);
            Object lambdaResult = ((Supplier)lambdaRef).get();
            Assertions.assertEquals(Integer.valueOf(5), lambdaResult);
            Object fieldVal = fieldRef.get(instance);
            Assertions.assertEquals("foo", fieldVal);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }
}
