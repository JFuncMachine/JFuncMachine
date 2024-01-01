package org.jfuncmachine.compiler.classgen;

import org.jfuncmachine.compiler.model.Access;
import org.jfuncmachine.compiler.model.MethodDef;
import org.jfuncmachine.compiler.model.MethodPtrs;
import org.jfuncmachine.compiler.model.PartialCalls;
import org.jfuncmachine.compiler.model.expr.Expression;
import org.jfuncmachine.compiler.model.expr.GetValue;
import org.jfuncmachine.compiler.model.expr.Invoke;
import org.jfuncmachine.compiler.model.types.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class TestPartialCalls {
    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(ClassGeneratorProvider.class)
    @Retention(RetentionPolicy.RUNTIME)
    private @interface TestAllImplementations {}

    @TestAllImplementations
    public void testPartialJavaConstructor(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("partialcalltest", Access.PUBLIC, new Field[] {
                new Field("foo", SimpleTypes.INT),
                new Field("bar", SimpleTypes.STRING),
                new Field("baz", SimpleTypes.DOUBLE),
        },
                new ObjectType(ToyForPartials.class),
                new Invoke(new FunctionType(new Type[] { SimpleTypes.STRING, SimpleTypes.DOUBLE},
                        new ObjectType(ToyForPartials.class)),
                        PartialCalls.makePartialJavaConstructorCall(ToyForPartials.class.getName(),
                                new Type[]{SimpleTypes.INT, SimpleTypes.STRING, SimpleTypes.DOUBLE},
                                new Expression[] { new GetValue("foo", SimpleTypes.INT)}),
                        new Expression[] { new GetValue("bar", SimpleTypes.STRING),
                                new GetValue("baz", SimpleTypes.DOUBLE)}));


        Object result = generator.invokeMethod("TestPartialCalls",method, 42, "Moe", 3.14);
        Assertions.assertInstanceOf(ToyForPartials.class, result);
        Assertions.assertEquals(42, ((ToyForPartials)result).foo);
        Assertions.assertEquals("Moe", ((ToyForPartials)result).bar);
        Assertions.assertEquals(3.14, ((ToyForPartials)result).baz);
    }

    @TestAllImplementations
    public void testPartialJavaConstructor2(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("partialcalltest", Access.PUBLIC, new Field[] {
                new Field("foo", SimpleTypes.INT),
                new Field("bar", SimpleTypes.STRING),
                new Field("baz", SimpleTypes.DOUBLE),
        },
                new ObjectType(ToyForPartials.class),
                new Invoke(new FunctionType(new Type[] { SimpleTypes.DOUBLE},
                        new ObjectType(ToyForPartials.class)),
                        PartialCalls.makePartialJavaConstructorCall(ToyForPartials.class.getName(),
                                new Type[]{SimpleTypes.INT, SimpleTypes.STRING, SimpleTypes.DOUBLE},
                                new Expression[] { new GetValue("foo", SimpleTypes.INT),
                                    new GetValue("bar", SimpleTypes.STRING)}),
                        new Expression[] { new GetValue("baz", SimpleTypes.DOUBLE)}));


        Object result = generator.invokeMethod("TestPartialCalls",method, 42, "Moe", 3.14);
        Assertions.assertInstanceOf(ToyForPartials.class, result);
        Assertions.assertEquals(42, ((ToyForPartials)result).foo);
        Assertions.assertEquals("Moe", ((ToyForPartials)result).bar);
        Assertions.assertEquals(3.14, ((ToyForPartials)result).baz);
    }

    @TestAllImplementations
    public void testPartialJavaInterfaceCall(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("partialcalltest", Access.PUBLIC, new Field[] {
                new Field("obj", new ObjectType(ToyPartialsInterface.class)),
                new Field("a", SimpleTypes.INT),
                new Field("b", SimpleTypes.INT),
                new Field("c", SimpleTypes.INT),
        },
                SimpleTypes.INT,
                new Invoke(new FunctionType(new Type[] { SimpleTypes.INT, SimpleTypes.INT }, SimpleTypes.INT),
                        PartialCalls.makePartialJavaInterfaceMethodCall(ToyPartialsInterface.class.getName(), "addThree",
                                new Type[] { SimpleTypes.INT, SimpleTypes.INT, SimpleTypes.INT }, SimpleTypes.INT,
                                new GetValue("obj", new ObjectType(ToyPartialsInterface.class)),
                                new Expression[] { new GetValue("a", SimpleTypes.INT)}),
                        new Expression[] { new GetValue("b", SimpleTypes.INT),
                            new GetValue("c", SimpleTypes.INT)}));


        ToyForPartials toy = new ToyForPartials();
        Object result = generator.invokeMethod("TestPartialCalls",method, toy, 10, 12, 20);
        Assertions.assertEquals(42, result);
    }


    @TestAllImplementations
    public void testPartialJavaInterfaceCall2(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("partialcalltest", Access.PUBLIC, new Field[] {
                new Field("obj", new ObjectType(ToyPartialsInterface.class)),
                new Field("a", SimpleTypes.INT),
                new Field("b", SimpleTypes.INT),
                new Field("c", SimpleTypes.INT),
        },
                SimpleTypes.INT,
                new Invoke(new FunctionType(new Type[] { SimpleTypes.INT }, SimpleTypes.INT),
                        PartialCalls.makePartialJavaInterfaceMethodCall(ToyPartialsInterface.class.getName(), "addThree",
                                new Type[] { SimpleTypes.INT, SimpleTypes.INT, SimpleTypes.INT }, SimpleTypes.INT,
                                new GetValue("obj", new ObjectType(ToyPartialsInterface.class)),
                                new Expression[] { new GetValue("a", SimpleTypes.INT),
                                    new GetValue("b", SimpleTypes.INT)}),
                        new Expression[] { new GetValue("c", SimpleTypes.INT) }));


        ToyForPartials toy = new ToyForPartials();
        Object result = generator.invokeMethod("TestPartialCalls",method, toy, 10, 12, 20);
        Assertions.assertEquals(42, result);
    }

    @TestAllImplementations
    public void testPartialJavaInterfaceCallWithoutObject(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("partialcalltest", Access.PUBLIC, new Field[] {
                new Field("obj", new ObjectType(ToyPartialsInterface.class)),
                new Field("a", SimpleTypes.INT),
                new Field("b", SimpleTypes.INT),
                new Field("c", SimpleTypes.INT),
        },
                SimpleTypes.INT,
                new Invoke(new FunctionType(new Type[] { new ObjectType(ToyPartialsInterface.class),
                        SimpleTypes.INT, SimpleTypes.INT }, SimpleTypes.INT),
                        PartialCalls.makePartialJavaInterfaceMethodCall(ToyPartialsInterface.class.getName(), "addThree",
                                new Type[] { SimpleTypes.INT, SimpleTypes.INT, SimpleTypes.INT }, SimpleTypes.INT,
                                new Expression[] { new GetValue("a", SimpleTypes.INT)}),
                        new Expression[] { new GetValue("obj", new ObjectType(ToyPartialsInterface.class)),
                                new GetValue("b", SimpleTypes.INT),
                                new GetValue("c", SimpleTypes.INT)}));


        ToyForPartials toy = new ToyForPartials();
        Object result = generator.invokeMethod("TestPartialCalls",method, toy, 10, 12, 20);
        Assertions.assertEquals(42, result);
    }


    @TestAllImplementations
    public void testPartialJavaInterfaceCallWithoutObject2(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("partialcalltest", Access.PUBLIC, new Field[] {
                new Field("obj", new ObjectType(ToyPartialsInterface.class)),
                new Field("a", SimpleTypes.INT),
                new Field("b", SimpleTypes.INT),
                new Field("c", SimpleTypes.INT),
        },
                SimpleTypes.INT,
                new Invoke(new FunctionType(new Type[] { new ObjectType(ToyPartialsInterface.class),
                        SimpleTypes.INT }, SimpleTypes.INT),
                        PartialCalls.makePartialJavaInterfaceMethodCall(ToyPartialsInterface.class.getName(), "addThree",
                                new Type[] { SimpleTypes.INT, SimpleTypes.INT, SimpleTypes.INT }, SimpleTypes.INT,
                                new Expression[] { new GetValue("a", SimpleTypes.INT),
                                        new GetValue("b", SimpleTypes.INT)}),
                        new Expression[] { new GetValue("obj", new ObjectType(ToyPartialsInterface.class)),
                                new GetValue("c", SimpleTypes.INT) }));


        ToyForPartials toy = new ToyForPartials();
        Object result = generator.invokeMethod("TestPartialCalls",method, toy, 10, 12, 20);
        Assertions.assertEquals(42, result);
    }

    @TestAllImplementations
    public void testPartialJavaMethodCall(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("partialcalltest", Access.PUBLIC, new Field[] {
                new Field("obj", new ObjectType(ToyForPartials.class)),
                new Field("a", SimpleTypes.INT),
                new Field("b", SimpleTypes.INT),
                new Field("c", SimpleTypes.INT),
        },
                SimpleTypes.INT,
                new Invoke(new FunctionType(new Type[] { SimpleTypes.INT, SimpleTypes.INT }, SimpleTypes.INT),
                        PartialCalls.makePartialJavaMethodCall(ToyForPartials.class.getName(), "addThree",
                                new Type[] { SimpleTypes.INT, SimpleTypes.INT, SimpleTypes.INT }, SimpleTypes.INT,
                                new GetValue("obj", new ObjectType(ToyForPartials.class)),
                                new Expression[] { new GetValue("a", SimpleTypes.INT)}),
                        new Expression[] { new GetValue("b", SimpleTypes.INT),
                            new GetValue("c", SimpleTypes.INT)}));


        ToyForPartials toy = new ToyForPartials();
        Object result = generator.invokeMethod("TestPartialCalls",method, toy, 10, 12, 20);
        Assertions.assertEquals(42, result);
    }

    @TestAllImplementations
    public void testPartialJavaMethodCall2(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("partialcalltest", Access.PUBLIC, new Field[] {
                new Field("obj", new ObjectType(ToyForPartials.class)),
                new Field("a", SimpleTypes.INT),
                new Field("b", SimpleTypes.INT),
                new Field("c", SimpleTypes.INT),
        },
                SimpleTypes.INT,
                new Invoke(new FunctionType(new Type[] { SimpleTypes.INT }, SimpleTypes.INT),
                        PartialCalls.makePartialJavaMethodCall(ToyForPartials.class.getName(), "addThree",
                                new Type[] { SimpleTypes.INT, SimpleTypes.INT, SimpleTypes.INT }, SimpleTypes.INT,
                                new GetValue("obj", new ObjectType(ToyForPartials.class)),
                                new Expression[] { new GetValue("a", SimpleTypes.INT),
                                    new GetValue("b", SimpleTypes.INT)}),
                        new Expression[] { new GetValue("c", SimpleTypes.INT)}));


        ToyForPartials toy = new ToyForPartials();
        Object result = generator.invokeMethod("TestPartialCalls",method, toy, 10, 12, 20);
        Assertions.assertEquals(42, result);
    }

    @TestAllImplementations
    public void testPartialJavaMethodCallWithoutObject(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("partialcalltest", Access.PUBLIC, new Field[] {
                new Field("obj", new ObjectType(ToyForPartials.class)),
                new Field("a", SimpleTypes.INT),
                new Field("b", SimpleTypes.INT),
                new Field("c", SimpleTypes.INT),
        },
                SimpleTypes.INT,
                new Invoke(new FunctionType(new Type[] { new ObjectType(ToyForPartials.class),
                        SimpleTypes.INT, SimpleTypes.INT }, SimpleTypes.INT),
                        PartialCalls.makePartialJavaMethodCall(ToyForPartials.class.getName(), "addThree",
                                new Type[] { SimpleTypes.INT, SimpleTypes.INT, SimpleTypes.INT }, SimpleTypes.INT,
                                new Expression[] { new GetValue("a", SimpleTypes.INT)}),
                        new Expression[] { new GetValue("obj", new ObjectType(ToyForPartials.class)),
                                new GetValue("b", SimpleTypes.INT),
                                new GetValue("c", SimpleTypes.INT)}));


        ToyForPartials toy = new ToyForPartials();
        Object result = generator.invokeMethod("TestPartialCalls",method, toy, 10, 12, 20);
        Assertions.assertEquals(42, result);
    }

    @TestAllImplementations
    public void testPartialJavaMethodCallWithoutObject2(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("partialcalltest", Access.PUBLIC, new Field[] {
                new Field("obj", new ObjectType(ToyForPartials.class)),
                new Field("a", SimpleTypes.INT),
                new Field("b", SimpleTypes.INT),
                new Field("c", SimpleTypes.INT),
        },
                SimpleTypes.INT,
                new Invoke(new FunctionType(new Type[] { new ObjectType(ToyForPartials.class),
                        SimpleTypes.INT }, SimpleTypes.INT),
                        PartialCalls.makePartialJavaMethodCall(ToyForPartials.class.getName(), "addThree",
                                new Type[] { SimpleTypes.INT, SimpleTypes.INT, SimpleTypes.INT }, SimpleTypes.INT,
                                new Expression[] { new GetValue("a", SimpleTypes.INT),
                                        new GetValue("b", SimpleTypes.INT)}),
                        new Expression[] { new GetValue("obj", new ObjectType(ToyForPartials.class)),
                                new GetValue("c", SimpleTypes.INT)}));


        ToyForPartials toy = new ToyForPartials();
        Object result = generator.invokeMethod("TestPartialCalls",method, toy, 10, 12, 20);
        Assertions.assertEquals(42, result);
    }


    @TestAllImplementations
    public void testPartialStaticJavaMethodCall(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("partialcalltest", Access.PUBLIC, new Field[] {
                new Field("a", SimpleTypes.INT),
                new Field("b", SimpleTypes.INT),
                new Field("c", SimpleTypes.INT),
        },
                SimpleTypes.INT,
                new Invoke(new FunctionType(new Type[] { SimpleTypes.INT, SimpleTypes.INT }, SimpleTypes.INT),
                        PartialCalls.makePartialJavaStaticMethodCall(ToyForPartials.class.getName(),
                                "addThreeStatic",
                                new Type[] { SimpleTypes.INT, SimpleTypes.INT, SimpleTypes.INT }, SimpleTypes.INT,
                                new Expression[] { new GetValue("a", SimpleTypes.INT)}),
                        new Expression[] { new GetValue("b", SimpleTypes.INT),
                                new GetValue("c", SimpleTypes.INT)}));


        Object result = generator.invokeMethod("TestPartialCalls",method, 10, 12, 20);
        Assertions.assertEquals(42, result);
    }

    @TestAllImplementations
    public void testPartialStaticJavaMethodCall2(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("partialcalltest", Access.PUBLIC, new Field[] {
                new Field("a", SimpleTypes.INT),
                new Field("b", SimpleTypes.INT),
                new Field("c", SimpleTypes.INT),
        },
                SimpleTypes.INT,
                new Invoke(new FunctionType(new Type[] { SimpleTypes.INT }, SimpleTypes.INT),
                        PartialCalls.makePartialJavaStaticMethodCall(ToyForPartials.class.getName(),
                                "addThreeStatic",
                                new Type[] { SimpleTypes.INT, SimpleTypes.INT, SimpleTypes.INT }, SimpleTypes.INT,
                                new Expression[] { new GetValue("a", SimpleTypes.INT),
                                        new GetValue("b", SimpleTypes.INT)}),
                        new Expression[] { new GetValue("c", SimpleTypes.INT)}));


        Object result = generator.invokeMethod("TestPartialCalls",method, 10, 12, 20);
        Assertions.assertEquals(42, result);
    }

    @TestAllImplementations
    public void testPartialMethodCall(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("partialcalltest", Access.PUBLIC, new Field[] {
                new Field("obj", new ObjectType(ToyForPartials.class)),
                new Field("a", SimpleTypes.INT),
                new Field("b", SimpleTypes.INT),
                new Field("c", SimpleTypes.INT),
        },
                SimpleTypes.INT,
                new Invoke(new FunctionType(new Type[] { SimpleTypes.INT, SimpleTypes.INT }, SimpleTypes.INT),
                        PartialCalls.makePartialMethodCall(ToyForPartials.class.getName(), "addThree",
                                new Type[] { SimpleTypes.INT, SimpleTypes.INT, SimpleTypes.INT }, SimpleTypes.INT,
                                new GetValue("obj", new ObjectType(ToyForPartials.class)),
                                new Expression[] { new GetValue("a", SimpleTypes.INT)}),
                        new Expression[] { new GetValue("b", SimpleTypes.INT),
                                new GetValue("c", SimpleTypes.INT)}));


        ToyForPartials toy = new ToyForPartials();
        Object result = generator.invokeMethod("TestPartialCalls",method, toy, 10, 12, 20);
        Assertions.assertEquals(42, result);
    }

    @TestAllImplementations
    public void testPartialMethodCall2(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("partialcalltest", Access.PUBLIC, new Field[] {
                new Field("obj", new ObjectType(ToyForPartials.class)),
                new Field("a", SimpleTypes.INT),
                new Field("b", SimpleTypes.INT),
                new Field("c", SimpleTypes.INT),
        },
                SimpleTypes.INT,
                new Invoke(new FunctionType(new Type[] { SimpleTypes.INT }, SimpleTypes.INT),
                        PartialCalls.makePartialMethodCall(ToyForPartials.class.getName(), "addThree",
                                new Type[] { SimpleTypes.INT, SimpleTypes.INT, SimpleTypes.INT }, SimpleTypes.INT,
                                new GetValue("obj", new ObjectType(ToyForPartials.class)),
                                new Expression[] { new GetValue("a", SimpleTypes.INT),
                                        new GetValue("b", SimpleTypes.INT)}),
                        new Expression[] { new GetValue("c", SimpleTypes.INT)}));


        ToyForPartials toy = new ToyForPartials();
        Object result = generator.invokeMethod("TestPartialCalls",method, toy, 10, 12, 20);
        Assertions.assertEquals(42, result);
    }

    @TestAllImplementations
    public void testPartialMethodCallWithoutObject(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("partialcalltest", Access.PUBLIC, new Field[] {
                new Field("obj", new ObjectType(ToyForPartials.class)),
                new Field("a", SimpleTypes.INT),
                new Field("b", SimpleTypes.INT),
                new Field("c", SimpleTypes.INT),
        },
                SimpleTypes.INT,
                new Invoke(new FunctionType(new Type[] { new ObjectType(ToyForPartials.class),
                        SimpleTypes.INT, SimpleTypes.INT }, SimpleTypes.INT),
                        PartialCalls.makePartialMethodCall(ToyForPartials.class.getName(), "addThree",
                                new Type[] { SimpleTypes.INT, SimpleTypes.INT, SimpleTypes.INT }, SimpleTypes.INT,
                                new Expression[] { new GetValue("a", SimpleTypes.INT)}),
                        new Expression[] { new GetValue("obj", new ObjectType(ToyForPartials.class)),
                                new GetValue("b", SimpleTypes.INT),
                                new GetValue("c", SimpleTypes.INT)}));


        ToyForPartials toy = new ToyForPartials();
        Object result = generator.invokeMethod("TestPartialCalls",method, toy, 10, 12, 20);
        Assertions.assertEquals(42, result);
    }

    @TestAllImplementations
    public void testPartialMethodCallWithoutObject2(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("partialcalltest", Access.PUBLIC, new Field[] {
                new Field("obj", new ObjectType(ToyForPartials.class)),
                new Field("a", SimpleTypes.INT),
                new Field("b", SimpleTypes.INT),
                new Field("c", SimpleTypes.INT),
        },
                SimpleTypes.INT,
                new Invoke(new FunctionType(new Type[] { new ObjectType(ToyForPartials.class), SimpleTypes.INT },
                        SimpleTypes.INT),
                        PartialCalls.makePartialMethodCall(ToyForPartials.class.getName(), "addThree",
                                new Type[] { SimpleTypes.INT, SimpleTypes.INT, SimpleTypes.INT }, SimpleTypes.INT,
                                new Expression[] { new GetValue("a", SimpleTypes.INT),
                                        new GetValue("b", SimpleTypes.INT)}),
                        new Expression[] { new GetValue("obj", new ObjectType(ToyForPartials.class)),
                                new GetValue("c", SimpleTypes.INT)}));


        ToyForPartials toy = new ToyForPartials();
        Object result = generator.invokeMethod("TestPartialCalls",method, toy, 10, 12, 20);
        Assertions.assertEquals(42, result);
    }


    @TestAllImplementations
    public void testPartialStaticMethodCall(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("partialcalltest", Access.PUBLIC, new Field[] {
                new Field("a", SimpleTypes.INT),
                new Field("b", SimpleTypes.INT),
                new Field("c", SimpleTypes.INT),
        },
                SimpleTypes.INT,
                new Invoke(new FunctionType(new Type[] { SimpleTypes.INT, SimpleTypes.INT }, SimpleTypes.INT),
                        PartialCalls.makePartialStaticMethodCall(ToyForPartials.class.getName(),
                                "addThreeStatic",
                                new Type[] { SimpleTypes.INT, SimpleTypes.INT, SimpleTypes.INT }, SimpleTypes.INT,
                                new Expression[] { new GetValue("a", SimpleTypes.INT)}),
                        new Expression[] { new GetValue("b", SimpleTypes.INT),
                                new GetValue("c", SimpleTypes.INT)}));


        Object result = generator.invokeMethod("TestPartialCalls",method, 10, 12, 20);
        Assertions.assertEquals(42, result);
    }

    @TestAllImplementations
    public void testPartialStaticMethodCall2(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("partialcalltest", Access.PUBLIC, new Field[] {
                new Field("a", SimpleTypes.INT),
                new Field("b", SimpleTypes.INT),
                new Field("c", SimpleTypes.INT),
        },
                SimpleTypes.INT,
                new Invoke(new FunctionType(new Type[] { SimpleTypes.INT }, SimpleTypes.INT),
                        PartialCalls.makePartialStaticMethodCall(ToyForPartials.class.getName(),
                                "addThreeStatic",
                                new Type[] { SimpleTypes.INT, SimpleTypes.INT, SimpleTypes.INT }, SimpleTypes.INT,
                                new Expression[] { new GetValue("a", SimpleTypes.INT),
                                        new GetValue("b", SimpleTypes.INT)}),
                        new Expression[] { new GetValue("c", SimpleTypes.INT)}));


        Object result = generator.invokeMethod("TestPartialCalls",method, 10, 12, 20);
        Assertions.assertEquals(42, result);
    }

    @TestAllImplementations
    public void testPartialMethodTailCall(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("partialcalltest", Access.PUBLIC, new Field[] {
                new Field("obj", new ObjectType(ToyForPartials.class)),
                new Field("a", SimpleTypes.INT),
                new Field("b", SimpleTypes.INT),
                new Field("c", SimpleTypes.INT),
        },
                SimpleTypes.INT,
                new Invoke(new FunctionType(new Type[] { SimpleTypes.INT, SimpleTypes.INT }, SimpleTypes.INT),
                        PartialCalls.makePartialTailCallMethodCall(ToyForPartials.class.getName(), "addThree$$TC$$",
                                new Type[] { SimpleTypes.INT, SimpleTypes.INT, SimpleTypes.INT }, SimpleTypes.INT,
                                new GetValue("obj", new ObjectType(ToyForPartials.class)),
                                new Expression[] { new GetValue("a", SimpleTypes.INT)}),
                        new Expression[] { new GetValue("b", SimpleTypes.INT),
                                new GetValue("c", SimpleTypes.INT)}));


        ToyForPartials toy = new ToyForPartials();
        Object result = generator.invokeMethod("TestPartialCalls",method, toy, 10, 12, 20);
        Assertions.assertEquals(42, result);
    }

    @TestAllImplementations
    public void testPartialMethodTailCall2(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("partialcalltest", Access.PUBLIC, new Field[] {
                new Field("obj", new ObjectType(ToyForPartials.class)),
                new Field("a", SimpleTypes.INT),
                new Field("b", SimpleTypes.INT),
                new Field("c", SimpleTypes.INT),
        },
                SimpleTypes.INT,
                new Invoke(new FunctionType(new Type[] { SimpleTypes.INT }, SimpleTypes.INT),
                        PartialCalls.makePartialTailCallMethodCall(ToyForPartials.class.getName(), "addThree$$TC$$",
                                new Type[] { SimpleTypes.INT, SimpleTypes.INT, SimpleTypes.INT }, SimpleTypes.INT,
                                new GetValue("obj", new ObjectType(ToyForPartials.class)),
                                new Expression[] { new GetValue("a", SimpleTypes.INT),
                                        new GetValue("b", SimpleTypes.INT)}),
                        new Expression[] { new GetValue("c", SimpleTypes.INT)}));


        ToyForPartials toy = new ToyForPartials();
        Object result = generator.invokeMethod("TestPartialCalls",method, toy, 10, 12, 20);
        Assertions.assertEquals(42, result);
    }

    @TestAllImplementations
    public void testPartialMethodTailCallWithoutObject(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("partialcalltest", Access.PUBLIC, new Field[] {
                new Field("obj", new ObjectType(ToyForPartials.class)),
                new Field("a", SimpleTypes.INT),
                new Field("b", SimpleTypes.INT),
                new Field("c", SimpleTypes.INT),
        },
                SimpleTypes.INT,
                new Invoke(new FunctionType(new Type[] { new ObjectType(ToyForPartials.class),
                        SimpleTypes.INT, SimpleTypes.INT }, SimpleTypes.INT),
                        PartialCalls.makePartialTailCallMethodCall(ToyForPartials.class.getName(), "addThree$$TC$$",
                                new Type[] { SimpleTypes.INT, SimpleTypes.INT, SimpleTypes.INT }, SimpleTypes.INT,
                                new Expression[] { new GetValue("a", SimpleTypes.INT)}),
                        new Expression[] { new GetValue("obj", new ObjectType(ToyForPartials.class)),
                                new GetValue("b", SimpleTypes.INT),
                                new GetValue("c", SimpleTypes.INT)}));


        ToyForPartials toy = new ToyForPartials();
        Object result = generator.invokeMethod("TestPartialCalls",method, toy, 10, 12, 20);
        Assertions.assertEquals(42, result);
    }

    @TestAllImplementations
    public void testPartialMethodTailCallWithoutObject2(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("partialcalltest", Access.PUBLIC, new Field[] {
                new Field("obj", new ObjectType(ToyForPartials.class)),
                new Field("a", SimpleTypes.INT),
                new Field("b", SimpleTypes.INT),
                new Field("c", SimpleTypes.INT),
        },
                SimpleTypes.INT,
                new Invoke(new FunctionType(new Type[] { new ObjectType(ToyForPartials.class),
                        SimpleTypes.INT }, SimpleTypes.INT),
                        PartialCalls.makePartialTailCallMethodCall(ToyForPartials.class.getName(), "addThree$$TC$$",
                                new Type[] { SimpleTypes.INT, SimpleTypes.INT, SimpleTypes.INT }, SimpleTypes.INT,
                                new Expression[] { new GetValue("a", SimpleTypes.INT),
                                        new GetValue("b", SimpleTypes.INT)}),
                        new Expression[] { new GetValue("obj", new ObjectType(ToyForPartials.class)),
                                new GetValue("c", SimpleTypes.INT)}));


        ToyForPartials toy = new ToyForPartials();
        Object result = generator.invokeMethod("TestPartialCalls",method, toy, 10, 12, 20);
        Assertions.assertEquals(42, result);
    }


    @TestAllImplementations
    public void testPartialStaticMethodTailCall(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("partialcalltest", Access.PUBLIC, new Field[] {
                new Field("a", SimpleTypes.INT),
                new Field("b", SimpleTypes.INT),
                new Field("c", SimpleTypes.INT),
        },
                SimpleTypes.INT,
                new Invoke(new FunctionType(new Type[] { SimpleTypes.INT, SimpleTypes.INT }, SimpleTypes.INT),
                        PartialCalls.makePartialTailCallStaticMethodCall(ToyForPartials.class.getName(),
                                "addThreeStatic$$TC$$",
                                new Type[] { SimpleTypes.INT, SimpleTypes.INT, SimpleTypes.INT }, SimpleTypes.INT,
                                new Expression[] { new GetValue("a", SimpleTypes.INT)}),
                        new Expression[] { new GetValue("b", SimpleTypes.INT),
                                new GetValue("c", SimpleTypes.INT)}));


        Object result = generator.invokeMethod("TestPartialCalls",method, 10, 12, 20);
        Assertions.assertEquals(42, result);
    }

    @TestAllImplementations
    public void testPartialStaticMethodTailCall2(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("partialcalltest", Access.PUBLIC, new Field[] {
                new Field("a", SimpleTypes.INT),
                new Field("b", SimpleTypes.INT),
                new Field("c", SimpleTypes.INT),
        },
                SimpleTypes.INT,
                new Invoke(new FunctionType(new Type[] { SimpleTypes.INT }, SimpleTypes.INT),
                        PartialCalls.makePartialTailCallStaticMethodCall(ToyForPartials.class.getName(),
                                "addThreeStatic$$TC$$",
                                new Type[] { SimpleTypes.INT, SimpleTypes.INT, SimpleTypes.INT }, SimpleTypes.INT,
                                new Expression[] { new GetValue("a", SimpleTypes.INT),
                                        new GetValue("b", SimpleTypes.INT)}),
                        new Expression[] { new GetValue("c", SimpleTypes.INT)}));


        Object result = generator.invokeMethod("TestPartialCalls",method, 10, 12, 20);
        Assertions.assertEquals(42, result);
    }

    @TestAllImplementations
    public void testPartialInvoke(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("partialcalltest", Access.PUBLIC, new Field[] {
                new Field("obj", new ObjectType(ToyForPartials.class)),
                new Field("a", SimpleTypes.INT),
                new Field("b", SimpleTypes.INT),
                new Field("c", SimpleTypes.INT),
        },
                SimpleTypes.INT,
                new Invoke(new FunctionType(new Type[] { SimpleTypes.INT, SimpleTypes.INT }, SimpleTypes.INT),
                        PartialCalls.makePartialInvoke(
                                new FunctionType(new Type[] { SimpleTypes.INT, SimpleTypes.INT, SimpleTypes.INT},
                                        SimpleTypes.INT),
                                MethodPtrs.makeJavaMethodPtr(ToyForPartials.class.getName(),
                                        "addThree",
                                        new Type[] { SimpleTypes.INT, SimpleTypes.INT, SimpleTypes.INT},
                                        SimpleTypes.INT,
                                        new GetValue("obj", new ObjectType(ToyForPartials.class))),
                                new Expression[] { new GetValue("a", SimpleTypes.INT)}),
                        new Expression[] { new GetValue("b", SimpleTypes.INT),
                                new GetValue("c", SimpleTypes.INT)}));


        ToyForPartials toy = new ToyForPartials();
        Object result = generator.invokeMethod("TestPartialCalls",method, toy, 10, 12, 20);
        Assertions.assertEquals(42, result);
    }

    @TestAllImplementations
    public void testPartialInvoke2(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("partialcalltest", Access.PUBLIC, new Field[] {
                new Field("obj", new ObjectType(ToyForPartials.class)),
                new Field("a", SimpleTypes.INT),
                new Field("b", SimpleTypes.INT),
                new Field("c", SimpleTypes.INT),
        },
                SimpleTypes.INT,
                new Invoke(new FunctionType(new Type[] { SimpleTypes.INT }, SimpleTypes.INT),
                        PartialCalls.makePartialInvoke(
                                new FunctionType(new Type[] { SimpleTypes.INT, SimpleTypes.INT, SimpleTypes.INT},
                                        SimpleTypes.INT),
                                MethodPtrs.makeJavaMethodPtr(ToyForPartials.class.getName(),
                                        "addThree",
                                        new Type[] { SimpleTypes.INT, SimpleTypes.INT, SimpleTypes.INT},
                                        SimpleTypes.INT,
                                        new GetValue("obj", new ObjectType(ToyForPartials.class))),
                                new Expression[] { new GetValue("a", SimpleTypes.INT),
                                    new GetValue("b", SimpleTypes.INT)}),
                        new Expression[] { new GetValue("c", SimpleTypes.INT)}));


        ToyForPartials toy = new ToyForPartials();
        Object result = generator.invokeMethod("TestPartialCalls",method, toy, 10, 12, 20);
        Assertions.assertEquals(42, result);
    }

    @TestAllImplementations
    public void testPartialInvokeWithoutObject(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("partialcalltest", Access.PUBLIC, new Field[] {
                new Field("obj", new ObjectType(ToyForPartials.class)),
                new Field("a", SimpleTypes.INT),
                new Field("b", SimpleTypes.INT),
                new Field("c", SimpleTypes.INT),
        },
                SimpleTypes.INT,
                new Invoke(new FunctionType(new Type[] { new ObjectType(ToyForPartials.class),
                        SimpleTypes.INT, SimpleTypes.INT }, SimpleTypes.INT),
                        PartialCalls.makePartialInvokeWithoutObject(
                                new FunctionType(new Type[] { new ObjectType(ToyForPartials.class),
                                        SimpleTypes.INT, SimpleTypes.INT, SimpleTypes.INT},
                                        SimpleTypes.INT),
                                MethodPtrs.makeJavaMethodPtr(ToyForPartials.class.getName(),
                                        "addThree",
                                        new Type[] { SimpleTypes.INT, SimpleTypes.INT, SimpleTypes.INT},
                                        SimpleTypes.INT),
                                new Expression[] { new GetValue("a", SimpleTypes.INT)}),
                        new Expression[] { new GetValue("obj", new ObjectType(ToyForPartials.class)),
                            new GetValue("b", SimpleTypes.INT),
                            new GetValue("c", SimpleTypes.INT)}));


        ToyForPartials toy = new ToyForPartials();
        Object result = generator.invokeMethod("TestPartialCalls",method, toy, 10, 12, 20);
        Assertions.assertEquals(42, result);
    }

    @TestAllImplementations
    public void testPartialInvokeWithoutObject2(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("partialcalltest", Access.PUBLIC, new Field[] {
                new Field("obj", new ObjectType(ToyForPartials.class)),
                new Field("a", SimpleTypes.INT),
                new Field("b", SimpleTypes.INT),
                new Field("c", SimpleTypes.INT),
        },
                SimpleTypes.INT,
                new Invoke(new FunctionType(new Type[] { new ObjectType(ToyForPartials.class),
                        SimpleTypes.INT }, SimpleTypes.INT),
                        PartialCalls.makePartialInvokeWithoutObject(
                                new FunctionType(new Type[] { new ObjectType(ToyForPartials.class),
                                        SimpleTypes.INT, SimpleTypes.INT, SimpleTypes.INT},
                                        SimpleTypes.INT),
                                MethodPtrs.makeJavaMethodPtr(ToyForPartials.class.getName(),
                                        "addThree",
                                        new Type[] { SimpleTypes.INT, SimpleTypes.INT, SimpleTypes.INT},
                                        SimpleTypes.INT),
                                new Expression[] {
                                        new GetValue("a", SimpleTypes.INT),
                                        new GetValue("b", SimpleTypes.INT)}),
                        new Expression[] { new GetValue("obj", new ObjectType(ToyForPartials.class)),
                                new GetValue("c", SimpleTypes.INT)}));


        ToyForPartials toy = new ToyForPartials();
        Object result = generator.invokeMethod("TestPartialCalls",method, toy, 10, 12, 20);
        Assertions.assertEquals(42, result);
    }
}
