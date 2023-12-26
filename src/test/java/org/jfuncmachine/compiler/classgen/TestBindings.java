package org.jfuncmachine.compiler.classgen;

import org.jfuncmachine.compiler.exceptions.JFuncMachineException;
import org.jfuncmachine.compiler.model.Access;
import org.jfuncmachine.compiler.model.MethodDef;
import org.jfuncmachine.compiler.model.expr.*;
import org.jfuncmachine.compiler.model.expr.bool.BinaryComparison;
import org.jfuncmachine.compiler.model.expr.bool.tests.Tests;
import org.jfuncmachine.compiler.model.expr.constants.*;
import org.jfuncmachine.compiler.model.expr.conv.ToByte;
import org.jfuncmachine.compiler.model.expr.conv.ToChar;
import org.jfuncmachine.compiler.model.expr.conv.ToShort;
import org.jfuncmachine.compiler.model.expr.javainterop.CallJavaMethod;
import org.jfuncmachine.compiler.model.inline.Inlines;
import org.jfuncmachine.compiler.model.types.Field;
import org.jfuncmachine.compiler.model.types.SimpleTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class TestBindings {
    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(ClassGeneratorProvider.class)
    @Retention(RetentionPolicy.RUNTIME)
    private @interface TestAllImplementations {}


    @TestAllImplementations
    public void testBoolBindingSeparate(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("bindingtest", Access.PUBLIC, new Field[] { },
                SimpleTypes.INT,
                new Binding(new Binding.BindingPair[] {
                        new Binding.BindingPair("x", new BooleanConstant(false)),
                        new Binding.BindingPair("y", new BooleanConstant(true)),
                }, Binding.Visibility.Separate,
                        new InlineCall(Inlines.IntOr,
                                new Expression[]{
                                        new GetValue("x", SimpleTypes.BOOLEAN),
                                        new GetValue("y", SimpleTypes.BOOLEAN)
                                })));

        Object result = generator.invokeMethod("TestBinding",method);
        Assertions.assertEquals(1, (Integer) result);
    }

    @TestAllImplementations
    public void testByteBindingSeparate(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("bindingtest", Access.PUBLIC, new Field[] { },
                SimpleTypes.BYTE,
                new Binding(new Binding.BindingPair[] {
                        new Binding.BindingPair("x", new ByteConstant((byte) 20)),
                        new Binding.BindingPair("y", new ByteConstant((byte) 22)),
                }, Binding.Visibility.Separate,
                        new ToByte(new InlineCall(Inlines.IntAdd,
                                new Expression[]{
                                        new GetValue("x", SimpleTypes.BYTE),
                                        new GetValue("y", SimpleTypes.BYTE)
                                }))));

        Object result = generator.invokeMethod("TestBinding",method);
        Assertions.assertEquals((byte) 42, (Byte) result);
    }

    @TestAllImplementations
    public void testCharBindingSeparate(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("bindingtest", Access.PUBLIC, new Field[] { },
                SimpleTypes.CHAR,
                new Binding(new Binding.BindingPair[] {
                        new Binding.BindingPair("x", new CharConstant((char) 20)),
                        new Binding.BindingPair("y", new CharConstant((char) 22)),
                }, Binding.Visibility.Separate,
                        new ToChar(new InlineCall(Inlines.IntAdd,
                                new Expression[]{
                                        new GetValue("x", SimpleTypes.CHAR),
                                        new GetValue("y", SimpleTypes.CHAR)
                                }))));

        Object result = generator.invokeMethod("TestBinding",method);
        Assertions.assertEquals((char) 42, (Character) result);
    }

    @TestAllImplementations
    public void testDoubleBindingSeparate(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("bindingtest", Access.PUBLIC, new Field[] { },
                SimpleTypes.DOUBLE,
                new Binding(new Binding.BindingPair[] {
                        new Binding.BindingPair("x", new DoubleConstant(20)),
                        new Binding.BindingPair("y", new DoubleConstant(22)),
                }, Binding.Visibility.Separate,
                        new InlineCall(Inlines.DoubleAdd,
                                new Expression[]{
                                        new GetValue("x", SimpleTypes.DOUBLE),
                                        new GetValue("y", SimpleTypes.DOUBLE)
                                })));

        Object result = generator.invokeMethod("TestBinding",method);
        Assertions.assertEquals((double) 42, (Double) result);
    }

    @TestAllImplementations
    public void testFloatBindingSeparate(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("bindingtest", Access.PUBLIC, new Field[] { },
                SimpleTypes.FLOAT,
                new Binding(new Binding.BindingPair[] {
                        new Binding.BindingPair("x", new FloatConstant(20)),
                        new Binding.BindingPair("y", new FloatConstant(22)),
                }, Binding.Visibility.Separate,
                        new InlineCall(Inlines.FloatAdd,
                                new Expression[]{
                                        new GetValue("x", SimpleTypes.FLOAT),
                                        new GetValue("y", SimpleTypes.FLOAT)
                                })));

        Object result = generator.invokeMethod("TestBinding",method);
        Assertions.assertEquals((float) 42, (Float) result);
    }

    @TestAllImplementations
    public void testIntBindingSeparate(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("bindingtest", Access.PUBLIC, new Field[] { },
                SimpleTypes.INT,
                new Binding(new Binding.BindingPair[] {
                        new Binding.BindingPair("x", new IntConstant(20)),
                        new Binding.BindingPair("y", new IntConstant(22)),
                }, Binding.Visibility.Separate,
                        new InlineCall(Inlines.IntAdd,
                                new Expression[]{
                                        new GetValue("x", SimpleTypes.INT),
                                        new GetValue("y", SimpleTypes.INT)
                                })));

        Object result = generator.invokeMethod("TestBinding",method);
        Assertions.assertEquals(42, (Integer) result);
    }

    @TestAllImplementations
    public void testLongBindingSeparate(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("bindingtest", Access.PUBLIC, new Field[] { },
                SimpleTypes.LONG,
                new Binding(new Binding.BindingPair[] {
                        new Binding.BindingPair("x", new LongConstant(20)),
                        new Binding.BindingPair("y", new LongConstant(22)),
                }, Binding.Visibility.Separate,
                        new InlineCall(Inlines.LongAdd,
                                new Expression[]{
                                        new GetValue("x", SimpleTypes.LONG),
                                        new GetValue("y", SimpleTypes.LONG)
                                })));

        Object result = generator.invokeMethod("TestBinding",method);
        Assertions.assertEquals((long) 42, (Long) result);
    }

    @TestAllImplementations
    public void testShortBindingSeparate(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("bindingtest", Access.PUBLIC, new Field[] { },
                SimpleTypes.SHORT,
                new Binding(new Binding.BindingPair[] {
                        new Binding.BindingPair("x", new ShortConstant((short) 20)),
                        new Binding.BindingPair("y", new ShortConstant((short) 22)),
                }, Binding.Visibility.Separate,
                        new ToShort(new InlineCall(Inlines.IntAdd,
                                new Expression[]{
                                        new GetValue("x", SimpleTypes.SHORT),
                                        new GetValue("y", SimpleTypes.SHORT)
                                }))));

        Object result = generator.invokeMethod("TestBinding",method);
        Assertions.assertEquals((short) 42, (Short) result);
    }

    @TestAllImplementations
    public void testStringBindingSeparate(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("bindingtest", Access.PUBLIC, new Field[] { },
                SimpleTypes.STRING,
                new Binding(new Binding.BindingPair[] {
                        new Binding.BindingPair("x", new StringConstant("foo")),
                        new Binding.BindingPair("y", new StringConstant("bar")),
                }, Binding.Visibility.Separate,
                        new CallJavaMethod("java.lang.String", "concat",
                               SimpleTypes.STRING,
                               new GetValue("x", SimpleTypes.STRING),
                                        new Expression[]{
                                                new GetValue("y", SimpleTypes.STRING)
                                        })));

        Object result = generator.invokeMethod("TestBinding",method);
        Assertions.assertEquals("foobar",result);
    }

    @TestAllImplementations
    public void testByteBindingPrevious(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("bindingtest", Access.PUBLIC, new Field[] { },
                SimpleTypes.BYTE,
                new Binding(new Binding.BindingPair[] {
                        new Binding.BindingPair("x", new ByteConstant((byte) 20)),
                        new Binding.BindingPair("y",
                                new ToByte(new InlineCall(Inlines.IntAdd,
                                        new Expression[] {
                                                new IntConstant(22),
                                                new GetValue("x", SimpleTypes.BYTE)
                                        })))
                }, Binding.Visibility.Previous,
                        new GetValue("y", SimpleTypes.BYTE)));

        Object result = generator.invokeMethod("TestBinding",method);
        Assertions.assertEquals((byte) 42, (Byte) result);
    }

    @TestAllImplementations
    public void testByteBindingAccessSeparateFails(String generatorType, ClassGenerator generator) {
        Assertions.assertThrows(JFuncMachineException.class,
                () -> {
                    MethodDef method = new MethodDef("bindingtest", Access.PUBLIC, new Field[]{},
                            SimpleTypes.BYTE,
                            new Binding(new Binding.BindingPair[]{
                                    new Binding.BindingPair("x", new ByteConstant((byte) 20)),
                                    new Binding.BindingPair("y",
                                            new ToByte(new InlineCall(Inlines.IntAdd,
                                                    new Expression[]{
                                                            new IntConstant(22),
                                                            new GetValue("x", SimpleTypes.BYTE)
                                                    })))
                            }, Binding.Visibility.Separate,
                                    new GetValue("y", SimpleTypes.BYTE)));

                    Object result = generator.invokeMethod("TestBinding", method);
                });
    }

    @TestAllImplementations
    public void testLongRecursiveBinding(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("bindingtest", Access.PUBLIC, new Field[] {
                new Field("n", SimpleTypes.LONG)
        },
                SimpleTypes.LONG,
                new Binding("func", new Binding.BindingPair[] {
                        new Binding.BindingPair("n", new GetValue("n", SimpleTypes.LONG)),
                        new Binding.BindingPair("acc", new LongConstant(1l))
                }, Binding.Visibility.Separate,
                        new If(new BinaryComparison(Tests.LT, new GetValue("n", SimpleTypes.LONG),
                                new LongConstant(2l)),
                            new GetValue("acc", SimpleTypes.LONG),
                            new BindingRecurse("func",
                                    SimpleTypes.LONG,
                                    new Expression[] {
                                            new InlineCall(Inlines.LongSub, new Expression[] {
                                                    new GetValue("n", SimpleTypes.LONG),
                                                    new LongConstant(1l),
                                            }),
                                            new InlineCall(Inlines.LongMul, new Expression[] {
                                                    new GetValue("n", SimpleTypes.LONG),
                                                    new GetValue("acc", SimpleTypes.LONG)
                                            })
                                    }))));

        Object result = generator.invokeMethod("TestBinding",method, 10);
        Assertions.assertEquals((long) 3628800, (Long) result);
    }

    @TestAllImplementations
    public void testVeryLongRecursiveBinding(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("bindingtest", Access.PUBLIC, new Field[] {
                new Field("n", SimpleTypes.LONG)
        },
                SimpleTypes.LONG,
                new Binding("func", new Binding.BindingPair[] {
                        new Binding.BindingPair("n", new GetValue("n", SimpleTypes.LONG)),
                        new Binding.BindingPair("acc", new LongConstant(1l))
                }, Binding.Visibility.Separate,
                        new If(new BinaryComparison(Tests.LT, new GetValue("n", SimpleTypes.LONG),
                                new LongConstant(2l)),
                                new GetValue("acc", SimpleTypes.LONG),
                                new BindingRecurse("func",
                                        SimpleTypes.LONG,
                                        new Expression[] {
                                                new InlineCall(Inlines.LongSub, new Expression[] {
                                                        new GetValue("n", SimpleTypes.LONG),
                                                        new LongConstant(1l),
                                                }),
                                                new InlineCall(Inlines.LongMul, new Expression[] {
                                                        new GetValue("n", SimpleTypes.LONG),
                                                        new GetValue("acc", SimpleTypes.LONG)
                                                })
                                        }))));

        Object result = generator.invokeMethod("TestBinding",method, 10000000);
        Assertions.assertEquals((long) 0, (Long) result);
    }

    @TestAllImplementations
    public void testSetIntVariable(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("bindingtest", Access.PUBLIC, new Field[] { },
                SimpleTypes.INT,
                new Binding(
                        new Binding.BindingPair[] {
                            new Binding.BindingPair("x", new IntConstant(20))
                        },
                        Binding.Visibility.Separate,
                        new Block(new Expression[] {
                                new SetValue("x",
                                        new InlineCall(Inlines.IntAdd, new Expression[]{
                                                new GetValue("x", SimpleTypes.INT),
                                                new IntConstant(22)
                                        })),
                                new GetValue("x", SimpleTypes.INT)
                        })));

        Object result = generator.invokeMethod("TestBinding",method);
        Assertions.assertEquals(42, (Integer) result);
    }
}