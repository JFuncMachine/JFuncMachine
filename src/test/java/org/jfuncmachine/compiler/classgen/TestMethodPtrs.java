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
import java.util.function.Supplier;

public class TestMethodPtrs {
    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(ClassGeneratorProvider.class)
    @Retention(RetentionPolicy.RUNTIME)
    private @interface TestAllImplementations {}

    @TestAllImplementations
    public void testDefaultJavaConstructorPtr(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("methodptrtest", Access.PUBLIC, new Field[] {
                 },
                new ObjectType(ToyClass.class),
                new Invoke(new FunctionType(new Type[0], new ObjectType(ToyClass.class)),
                        MethodPtrs.makeJavaConstructorPtr(ToyClass.class.getName(),
                                new Type[0]),
                        new Expression[0]));


        Object result = generator.invokeMethod("TestMethodPtrs",method);
        Assertions.assertInstanceOf(ToyClass.class, result);
    }

    @TestAllImplementations
    public void testJavaConstructorPtr(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("methodptrtest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.INT),
        },
                new ObjectType(ToyClass.class),
                new Invoke(new FunctionType(new Type[] { SimpleTypes.INT }, new ObjectType(ToyClass.class)),
                        MethodPtrs.makeJavaConstructorPtr(ToyClass.class.getName(),
                                new Type[] { SimpleTypes.INT }),
                        new Expression[] { new GetValue("x", SimpleTypes.INT)}));


        Object result = generator.invokeMethod("TestMethodPtrs",method, 42);
        Assertions.assertInstanceOf(ToyClass.class, result);
        Assertions.assertEquals(42, ((ToyClass)result).memberInt);
    }

    @TestAllImplementations
    public void testJavaInterfacePtr(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("methodptrtest", Access.PUBLIC, new Field[] {
                new Field("obj", new ObjectType(ToyClass.class)),
                new Field("x", SimpleTypes.INT),
        },
                SimpleTypes.INT,
                new Invoke(new FunctionType(new Type[] { SimpleTypes.INT }, SimpleTypes.INT),
                        MethodPtrs.makeJavaInterfaceMethodPtr(ToyInterface.class.getName(), "addMember",
                                new Type[] { SimpleTypes.INT }, SimpleTypes.INT,
                                new GetValue("obj", new ObjectType(ToyClass.class))),
                        new Expression[] { new GetValue("x", SimpleTypes.INT)}));


        ToyClass toy = new ToyClass(20);
        Object result = generator.invokeMethod("TestMethodPtrs",method, toy, 22);
        Assertions.assertEquals(42, result);
    }

    @TestAllImplementations
    public void testJavaInterfacePtrWithoutObject(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("methodptrtest", Access.PUBLIC, new Field[] {
                new Field("obj", new ObjectType(ToyClass.class)),
                new Field("x", SimpleTypes.INT),
        },
                SimpleTypes.INT,
                new Invoke(new FunctionType(new Type[] { new ObjectType(ToyInterface.class.getName()),
                        SimpleTypes.INT }, SimpleTypes.INT),
                        MethodPtrs.makeJavaInterfaceMethodPtr(ToyInterface.class.getName(), "addMember",
                                new Type[] { SimpleTypes.INT }, SimpleTypes.INT),
                        new Expression[] { new GetValue("obj", new ObjectType(ToyInterface.class.getName())),
                                new GetValue("x", SimpleTypes.INT)}));


        ToyClass toy = new ToyClass(20);
        Object result = generator.invokeMethod("TestMethodPtrs",method, toy, 22);
        Assertions.assertEquals(42, result);
    }

    @TestAllImplementations
    public void testJavaMethodPtr(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("methodptrtest", Access.PUBLIC, new Field[] {
                new Field("obj", new ObjectType(ToyClass.class)),
                new Field("x", SimpleTypes.INT),
        },
                SimpleTypes.INT,
                new Invoke(new FunctionType(new Type[] { SimpleTypes.INT }, SimpleTypes.INT),
                        MethodPtrs.makeJavaMethodPtr(ToyClass.class.getName(), "addMember",
                                new Type[] { SimpleTypes.INT }, SimpleTypes.INT,
                                new GetValue("obj", new ObjectType(ToyClass.class))),
                        new Expression[] { new GetValue("x", SimpleTypes.INT)}));


        ToyClass toy = new ToyClass(20);
        Object result = generator.invokeMethod("TestMethodPtrs",method, toy, 22);
        Assertions.assertEquals(42, result);
    }

    @TestAllImplementations
    public void testJavaMethodPtrWithoutObject(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("methodptrtest", Access.PUBLIC, new Field[] {
                new Field("obj", new ObjectType(ToyClass.class)),
                new Field("x", SimpleTypes.INT),
        },
                SimpleTypes.INT,
                new Invoke(new FunctionType(new Type[] { new ObjectType(ToyClass.class),
                        SimpleTypes.INT }, SimpleTypes.INT),
                        MethodPtrs.makeJavaMethodPtr(ToyClass.class.getName(), "addMember",
                                new Type[] { SimpleTypes.INT }, SimpleTypes.INT),
                        new Expression[] { new GetValue("obj", new ObjectType(ToyClass.class)),
                                new GetValue("x", SimpleTypes.INT)}));


        ToyClass toy = new ToyClass(20);
        Object result = generator.invokeMethod("TestMethodPtrs",method, toy, 22);
        Assertions.assertEquals(42, result);
    }

    @TestAllImplementations
    public void testJavaStaticMethodPtr(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("methodptrtest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.STRING),
        },
                SimpleTypes.STRING,
                new Invoke(new FunctionType(new Type[] { SimpleTypes.STRING }, SimpleTypes.STRING),
                        MethodPtrs.makeJavaStaticMethodPtr(ToyClass.class.getName(), "addStatic",
                                new Type[] { SimpleTypes.STRING }, SimpleTypes.STRING),
                        new Expression[] { new GetValue("x", SimpleTypes.STRING)}));


        Object result = generator.invokeMethod("TestMethodPtrs",method, "Bar");
        Assertions.assertEquals("BarFoo", result);
    }

    @TestAllImplementations
    public void testMethodPtr(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("methodptrtest", Access.PUBLIC, new Field[] {
                new Field("obj", new ObjectType(ToyClass.class)),
                new Field("x", SimpleTypes.INT),
        },
                SimpleTypes.INT,
                new Invoke(new FunctionType(new Type[] { SimpleTypes.INT }, SimpleTypes.INT),
                        MethodPtrs.makeMethodPtr(ToyClass.class.getName(), "addMember",
                                new Type[] { SimpleTypes.INT }, SimpleTypes.INT,
                                new GetValue("obj", new ObjectType(ToyClass.class))),
                        new Expression[] { new GetValue("x", SimpleTypes.INT)}));


        ToyClass toy = new ToyClass(20);
        Object result = generator.invokeMethod("TestMethodPtrs",method, toy, 22);
        Assertions.assertEquals(42, result);
    }

    @TestAllImplementations
    public void testMethodPtrWithoutObject(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("methodptrtest", Access.PUBLIC, new Field[] {
                new Field("obj", new ObjectType(ToyClass.class)),
                new Field("x", SimpleTypes.INT),
        },
                SimpleTypes.INT,
                new Invoke(new FunctionType(new Type[] { new ObjectType(ToyClass.class), SimpleTypes.INT },
                        SimpleTypes.INT),
                        MethodPtrs.makeMethodPtr(ToyClass.class.getName(), "addMember",
                                new Type[] { SimpleTypes.INT }, SimpleTypes.INT),
                        new Expression[] { new GetValue("obj", new ObjectType(ToyClass.class)),
                                new GetValue("x", SimpleTypes.INT)}));


        ToyClass toy = new ToyClass(20);
        Object result = generator.invokeMethod("TestMethodPtrs",method, toy, 22);
        Assertions.assertEquals(42, result);
    }

    @TestAllImplementations
    public void testStaticMethodPtr(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("methodptrtest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.STRING),
        },
                SimpleTypes.STRING,
                new Invoke(new FunctionType(new Type[] { SimpleTypes.STRING }, SimpleTypes.STRING),
                        MethodPtrs.makeStaticMethodPtr(ToyClass.class.getName(), "addStatic",
                                new Type[] { SimpleTypes.STRING }, SimpleTypes.STRING),
                        new Expression[] { new GetValue("x", SimpleTypes.STRING)}));


        Object result = generator.invokeMethod("TestMethodPtrs",method, "Bar");
        Assertions.assertEquals("BarFoo", result);
    }

    @TestAllImplementations
    public void testTailCallMethodPtr(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("methodptrtest", Access.PUBLIC, new Field[] {
                new Field("obj", new ObjectType(ToyClass.class)),
                new Field("x", SimpleTypes.INT),
        },
                SimpleTypes.INT,
                new Invoke(new FunctionType(new Type[] { SimpleTypes.INT }, SimpleTypes.INT),
                        MethodPtrs.makeTailCallMethodPtr(ToyClass.class.getName(), "addMember$$TC$$",
                                new Type[] { SimpleTypes.INT }, SimpleTypes.INT,
                                new GetValue("obj", new ObjectType(ToyClass.class))),
                        new Expression[] { new GetValue("x", SimpleTypes.INT)}));


        ToyClass toy = new ToyClass(20);
        Object result = generator.invokeMethod("TestMethodPtrs",method, toy, 22);
        Assertions.assertEquals(42, result);
    }

    @TestAllImplementations
    public void testTailCallMethodPtrWithoutObject(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("methodptrtest", Access.PUBLIC, new Field[] {
                new Field("obj", new ObjectType(ToyClass.class)),
                new Field("x", SimpleTypes.INT),
        },
                SimpleTypes.INT,
                new Invoke(new FunctionType(new Type[] { new ObjectType(ToyClass.class), SimpleTypes.INT },
                        SimpleTypes.INT),
                        MethodPtrs.makeTailCallMethodPtr(ToyClass.class.getName(), "addMember$$TC$$",
                                new Type[] { SimpleTypes.INT }, SimpleTypes.INT),
                        new Expression[] { new GetValue("obj", new ObjectType(ToyClass.class)),
                                new GetValue("x", SimpleTypes.INT)}));


        ToyClass toy = new ToyClass(20);
        Object result = generator.invokeMethod("TestMethodPtrs",method, toy, 22);
        Assertions.assertEquals(42, result);
    }

    @TestAllImplementations
    public void testTailCallStaticMethodPtr(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("methodptrtest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.STRING),
        },
                SimpleTypes.STRING,
                new Invoke(new FunctionType(new Type[] { SimpleTypes.STRING }, SimpleTypes.STRING),
                        MethodPtrs.makeTailCallStaticMethodPtr(ToyClass.class.getName(), "addStatic$$TC$$",
                                new Type[] { SimpleTypes.STRING }, SimpleTypes.STRING),
                        new Expression[] { new GetValue("x", SimpleTypes.STRING)}));


        Object result = generator.invokeMethod("TestMethodPtrs",method, "Bar");
        Assertions.assertEquals("BarFoo", result);
    }
}
