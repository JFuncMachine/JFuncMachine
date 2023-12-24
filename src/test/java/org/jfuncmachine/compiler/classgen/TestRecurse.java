package org.jfuncmachine.compiler.classgen;

import org.jfuncmachine.compiler.model.*;
import org.jfuncmachine.compiler.model.expr.*;
import org.jfuncmachine.compiler.model.expr.bool.BinaryComparison;
import org.jfuncmachine.compiler.model.expr.bool.tests.Tests;
import org.jfuncmachine.compiler.model.expr.boxing.Box;
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

public class TestRecurse {
    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(ClassGeneratorProvider.class)
    @Retention(RetentionPolicy.RUNTIME)
    private @interface TestAllImplementations {}

    @TestAllImplementations
    public void testLambdaRecurse(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("lambainvoketest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.INT) },
                SimpleTypes.INT,
                new Binding(new Binding.BindingPair[]{
                        new Binding.BindingPair("fact",
                                new Lambda(new Field[]{
                                        new Field("n", SimpleTypes.INT),
                                        new Field("acc", SimpleTypes.INT)
                                }, SimpleTypes.INT,
                                        new If(new BinaryComparison(Tests.LT,
                                                new GetValue("n", SimpleTypes.INT),
                                                new IntConstant(2)),
                                                new GetValue("acc", SimpleTypes.INT),
                                                new Recurse(SimpleTypes.INT, new Expression[] {
                                                        new InlineCall(Inlines.IntSub, new Expression[] {
                                                                new GetValue("n", SimpleTypes.INT),
                                                                new IntConstant(1)
                                                        }),
                                                        new InlineCall(Inlines.IntMul, new Expression[] {
                                                                new GetValue("n", SimpleTypes.INT),
                                                                new GetValue("acc", SimpleTypes.INT)
                                                        })
                                                }))))
                }, Binding.Visibility.Previous,
                        new Invoke(new FunctionType(new Type[] { SimpleTypes.INT, SimpleTypes.INT }, SimpleTypes.INT),
                        new GetValue("fact", new ObjectType()), new Expression[] {
                                new GetValue("x", SimpleTypes.INT),
                                new IntConstant(1)
                })));


        Object result = generator.invokeMethod("TestRecurse",method, 10);
        Assertions.assertEquals(3628800, result);
    }

    @TestAllImplementations
    public void testLambdaRecurseAdjustForTCO(String generatorType, ClassGenerator generator) {
        Type returnType = SimpleTypes.INT;
        if (generator.options.fullTailCalls) {
            returnType = new ObjectType();
        }
        MethodDef method = new MethodDef("lambainvoketest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.INT) },
                returnType,
                new Binding(new Binding.BindingPair[]{
                        new Binding.BindingPair("fact",
                                new Lambda(new Field[]{
                                        new Field("n", SimpleTypes.INT),
                                        new Field("acc", SimpleTypes.INT)
                                }, returnType,
                                        new If(new BinaryComparison(Tests.LT,
                                                new GetValue("n", SimpleTypes.INT),
                                                new IntConstant(2)),
                                                new Box(new GetValue("acc", SimpleTypes.INT)),
                                                new Recurse(returnType, new Expression[] {
                                                        new InlineCall(Inlines.IntSub, new Expression[] {
                                                                new GetValue("n", SimpleTypes.INT),
                                                                new IntConstant(1)
                                                        }),
                                                        new InlineCall(Inlines.IntMul, new Expression[] {
                                                                new GetValue("n", SimpleTypes.INT),
                                                                new GetValue("acc", SimpleTypes.INT)
                                                        })
                                                }))))
                }, Binding.Visibility.Previous,
                        new Invoke(new FunctionType(new Type[] { SimpleTypes.INT, SimpleTypes.INT }, returnType),
                                new GetValue("fact", new ObjectType()), new Expression[] {
                                new GetValue("x", SimpleTypes.INT),
                                new IntConstant(1)
                        })));


        Object result = generator.invokeMethod("TestRecurse",method, 10);
        Assertions.assertEquals(3628800, result);
    }
}
