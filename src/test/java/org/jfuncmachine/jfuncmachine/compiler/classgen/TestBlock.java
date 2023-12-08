package org.jfuncmachine.jfuncmachine.compiler.classgen;

import org.jfuncmachine.jfuncmachine.compiler.exceptions.JFuncMachineException;
import org.jfuncmachine.jfuncmachine.compiler.model.Access;
import org.jfuncmachine.jfuncmachine.compiler.model.MethodDef;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.*;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.bool.BinaryComparison;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.bool.tests.Tests;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.constants.*;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.conv.ToByte;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.conv.ToChar;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.conv.ToShort;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.javainterop.CallJavaMethod;
import org.jfuncmachine.jfuncmachine.compiler.model.inline.Inlines;
import org.jfuncmachine.jfuncmachine.compiler.model.types.Field;
import org.jfuncmachine.jfuncmachine.compiler.model.types.SimpleTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class TestBlock {
    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(ClassGeneratorProvider.class)
    @Retention(RetentionPolicy.RUNTIME)
    private @interface TestAllImplementations {}

    @TestAllImplementations
    public void testBlock(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("bindingtest", Access.PUBLIC, new Field[] { },
                SimpleTypes.INT,
                new Block(new Expression[] {
                        new ByteConstant((byte) 3),
                        new IntConstant(5),
                        new StringConstant("foo"),
                        new ShortConstant((short) 7),
                        new IntConstant(42)
                }));

        Object result = generator.invokeMethod("TestBinding",method);
        Assertions.assertEquals(42, (Integer) result);
    }

    @TestAllImplementations
    public void testBlocksInsideExpr(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("bindingtest", Access.PUBLIC, new Field[] { },
                SimpleTypes.INT,
                new InlineCall(Inlines.IntAdd,
                    new Expression[]{
                            new Block(new Expression[]{
                                    new ByteConstant((byte) 3),
                                    new IntConstant(5),
                                    new StringConstant("foo"),
                                    new ShortConstant((short) 7),
                                    new IntConstant(20)
                            }),
                            new Block(new Expression[]{
                                    new ByteConstant((byte) 3),
                                    new IntConstant(5),
                                    new StringConstant("foo"),
                                    new ShortConstant((short) 7),
                                    new IntConstant(22)
                            }),
                    }));

        Object result = generator.invokeMethod("TestBinding",method);
        Assertions.assertEquals(42, (Integer) result);
    }
}