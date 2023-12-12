package org.jfuncmachine.jfuncmachine.compiler.classgen;

import org.jfuncmachine.jfuncmachine.compiler.model.Access;
import org.jfuncmachine.jfuncmachine.compiler.model.MethodDef;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.*;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.bool.BinaryComparison;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.bool.tests.Tests;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.constants.IntConstant;
import org.jfuncmachine.jfuncmachine.compiler.model.inline.Inlines;
import org.jfuncmachine.jfuncmachine.compiler.model.types.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

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
}
