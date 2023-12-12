package org.jfuncmachine.jfuncmachine.compiler.classgen;

import org.jfuncmachine.jfuncmachine.compiler.model.Access;
import org.jfuncmachine.jfuncmachine.compiler.model.MethodDef;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.*;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.constants.IntConstant;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.constants.StringConstant;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.javainterop.CallJavaConstructor;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.javainterop.SetJavaField;
import org.jfuncmachine.jfuncmachine.compiler.model.types.Field;
import org.jfuncmachine.jfuncmachine.compiler.model.types.ObjectType;
import org.jfuncmachine.jfuncmachine.compiler.model.types.SimpleTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class TestTryCatchFinally {
    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(ClassGeneratorProvider.class)
    @Retention(RetentionPolicy.RUNTIME)
    private @interface TestAllImplementations {}

    @TestAllImplementations
    public void testTryCatchNoThrow(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("trycatchtest", Access.PUBLIC, new Field[] { },
                SimpleTypes.STRING,
                new TryCatchFinally(new StringConstant("moe"),
                        new Catch[] {
                                new Catch("java.lang.Exception", "exc",
                                        new StringConstant("larry"))
                        }, null));

        Object result = generator.invokeMethod("TestTryCatchFinally",method);
        Assertions.assertEquals(result, "moe");

    }

    @TestAllImplementations
    public void testTryCatchThrow(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("trycatchtest", Access.PUBLIC, new Field[] { },
                SimpleTypes.STRING,
                new TryCatchFinally(
                        new Throw(new ObjectType("java.lang.Exception"),
                                new CallJavaConstructor("java.lang.Exception",
                                        new Expression[] { new StringConstant("Nyuknyuknyuk") })),
                        new Catch[] {
                                new Catch("java.lang.Exception", "exc",
                                        new StringConstant("curly"))
                        }, null));

        Object result = generator.invokeMethod("TestTryCatchFinally",method);
        Assertions.assertEquals(result, "curly");

    }

    @TestAllImplementations
    public void testTryFinally(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("trycatchtest", Access.PUBLIC, new Field[] {
                new Field("toy", new ObjectType(ToyClass.class.getName()))
        },
                SimpleTypes.STRING,
                new TryCatchFinally(
                        new Block(new Expression[] {
                                new SetJavaField(ToyClass.class.getName(), "memberInt",
                                        SimpleTypes.INT,
                                        new GetValue("toy", new ObjectType(ToyClass.class.getName())),
                                        new IntConstant(73)),
                                new StringConstant("moe")
                        }),
                        new Catch[] { },
                        new SetJavaField(ToyClass.class.getName(), "memberInt",
                                SimpleTypes.INT,
                                new GetValue("toy", new ObjectType(ToyClass.class.getName())),
                                new IntConstant(42))));

        ToyClass toy = new ToyClass(3);
        Object result = generator.invokeMethod("TestTryCatchFinally",method, toy);
        Assertions.assertEquals(result, "moe");
        Assertions.assertEquals(toy.memberInt, 42);
    }

    @TestAllImplementations
    public void testTryCatchNoThrowFinally(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("trycatchtest", Access.PUBLIC, new Field[] {
                new Field("toy", new ObjectType(ToyClass.class.getName()))
        },
                SimpleTypes.STRING,
                new TryCatchFinally(
                        new Block(new Expression[] {
                                new SetJavaField(ToyClass.class.getName(), "memberInt",
                                        SimpleTypes.INT,
                                        new GetValue("toy", new ObjectType(ToyClass.class.getName())),
                                        new IntConstant(73)),
                                new StringConstant("moe")
                        }),
                        new Catch[] {
                                new Catch("java.lang.Exception", "exc",
                                        new StringConstant("curly"))
                        },
                        new SetJavaField(ToyClass.class.getName(), "memberInt",
                                SimpleTypes.INT,
                                new GetValue("toy", new ObjectType(ToyClass.class.getName())),
                                new IntConstant(42))));

        ToyClass toy = new ToyClass(3);
        Object result = generator.invokeMethod("TestTryCatchFinally",method, toy);
        Assertions.assertEquals(result, "moe");
        Assertions.assertEquals(toy.memberInt, 42);
    }

    @TestAllImplementations
    public void testTryCatchThrowFinally(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("trycatchtest", Access.PUBLIC, new Field[] {
                new Field("toy", new ObjectType(ToyClass.class.getName()))
        },
                SimpleTypes.STRING,
                new TryCatchFinally(
                        new Block(new Expression[] {
                                new SetJavaField(ToyClass.class.getName(), "memberInt",
                                        SimpleTypes.INT,
                                        new GetValue("toy", new ObjectType(ToyClass.class.getName())),
                                        new IntConstant(73)),
                                new Throw(new ObjectType("java.lang.Exception"),
                                        new CallJavaConstructor("java.lang.Exception",
                                                new Expression[] { new StringConstant("Nyuknyuknyuk") })),
                                new StringConstant("moe")
                        }),
                        new Catch[] {
                                new Catch("java.lang.Exception", "exc",
                                        new StringConstant("curly"))
                        },
                        new SetJavaField(ToyClass.class.getName(), "memberInt",
                                SimpleTypes.INT,
                                new GetValue("toy", new ObjectType(ToyClass.class.getName())),
                                new IntConstant(42))));

        ToyClass toy = new ToyClass(3);
        Object result = generator.invokeMethod("TestTryCatchFinally",method, toy);
        Assertions.assertEquals(result, "curly");
        Assertions.assertEquals(toy.memberInt, 42);
    }
}
