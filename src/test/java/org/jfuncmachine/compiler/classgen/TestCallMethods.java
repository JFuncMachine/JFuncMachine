package org.jfuncmachine.compiler.classgen;

import org.jfuncmachine.compiler.model.*;
import org.jfuncmachine.compiler.model.expr.*;
import org.jfuncmachine.compiler.model.expr.bool.BinaryComparison;
import org.jfuncmachine.compiler.model.expr.bool.tests.Tests;
import org.jfuncmachine.compiler.model.expr.constants.IntConstant;
import org.jfuncmachine.compiler.model.inline.Inlines;
import org.jfuncmachine.compiler.model.types.Field;
import org.jfuncmachine.compiler.model.types.FunctionType;
import org.jfuncmachine.compiler.model.types.ObjectType;
import org.jfuncmachine.compiler.model.types.SimpleTypes;
import org.jfuncmachine.compiler.model.types.Type;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class TestCallMethods {
    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(ClassGeneratorProvider.class)
    @Retention(RetentionPolicy.RUNTIME)
    private @interface TestAllImplementations {}

    @TestAllImplementations
    public void testCallMethod(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("fact", Access.PUBLIC, new Field[] {
                new Field("n", SimpleTypes.INT),
                new Field("acc", SimpleTypes.INT) },
        SimpleTypes.INT,
                new If(new BinaryComparison(Tests.LT, new GetValue("n", SimpleTypes.INT),
                        new IntConstant(2)),
                        new GetValue("acc", SimpleTypes.INT),
                        new CallMethod("fact", new Type[] { SimpleTypes.INT, SimpleTypes.INT },
                                SimpleTypes.INT,
                                new GetValue("this", new ObjectType()),
                                new Expression[] {
                                        new InlineCall(Inlines.IntSub, new Expression[] {
                                                new GetValue("n", SimpleTypes.INT),
                                                new IntConstant(1)
                                        }),
                                        new InlineCall(Inlines.IntMul, new Expression[] {
                                                new GetValue("n", SimpleTypes.INT),
                                                new GetValue("acc", SimpleTypes.INT)
                                        })
                                })));

        MethodDef callFactMethod = new MethodDef("callfact", Access.PUBLIC, new Field[]{
                new Field("n", SimpleTypes.INT)
        }, SimpleTypes.INT,
                new Binding(new Binding.BindingPair[]{
                        new Binding.BindingPair("f",
                                new CallMethod("fact", new Type[]{SimpleTypes.INT, SimpleTypes.INT},
                                        SimpleTypes.INT,
                                        new GetValue("this", new ObjectType()),
                                        new Expression[]{new GetValue("n", SimpleTypes.INT),
                                                new IntConstant(1)}))
                }, Binding.Visibility.Separate, new GetValue("f", SimpleTypes.INT)));

        MethodDef[] methods = {
            ConstructorDef.generateWith(new Field[0]),
                method, callFactMethod
        };

        ClassDef classDef = new ClassDef("org.jfuncmachine.temp", "TestCall",
                ToyClass.class.getPackageName(), ToyClass.class.getSimpleName(),
                Access.PUBLIC, methods, new ClassField[0], new String[0]);

        Object result = generator.invokeMethod(classDef, "callfact", 10);
        Assertions.assertEquals(3628800, result);
    }

    @TestAllImplementations
    public void testCallMethodSelfRecurse(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("fact", Access.PUBLIC, new Field[] {
                new Field("n", SimpleTypes.INT),
                new Field("acc", SimpleTypes.INT) },
                SimpleTypes.INT,
                new If(new BinaryComparison(Tests.LT, new GetValue("n", SimpleTypes.INT),
                        new IntConstant(2)),
                        new GetValue("acc", SimpleTypes.INT),
                        new CallMethod(new Type[] { SimpleTypes.INT, SimpleTypes.INT },
                                SimpleTypes.INT,
                                new GetValue("this", new ObjectType()),
                                new Expression[] {
                                        new InlineCall(Inlines.IntSub, new Expression[] {
                                                new GetValue("n", SimpleTypes.INT),
                                                new IntConstant(1)
                                        }),
                                        new InlineCall(Inlines.IntMul, new Expression[] {
                                                new GetValue("n", SimpleTypes.INT),
                                                new GetValue("acc", SimpleTypes.INT)
                                        })
                                })));

        MethodDef callFactMethod = new MethodDef("callfact", Access.PUBLIC, new Field[]{
                new Field("n", SimpleTypes.INT)
        }, SimpleTypes.INT,
                new Binding(new Binding.BindingPair[]{
                        new Binding.BindingPair("f",
                                new CallMethod("fact", new Type[]{SimpleTypes.INT, SimpleTypes.INT},
                                        SimpleTypes.INT,
                                        new GetValue("this", new ObjectType()),
                                        new Expression[]{new GetValue("n", SimpleTypes.INT),
                                                new IntConstant(1)}))
                }, Binding.Visibility.Separate, new GetValue("f", SimpleTypes.INT)));

        MethodDef[] methods = {
                ConstructorDef.generateWith(new Field[0]),
                method, callFactMethod
        };

        ClassDef classDef = new ClassDef("org.jfuncmachine.temp", "TestCall",
                ToyClass.class.getPackageName(), ToyClass.class.getSimpleName(),
                Access.PUBLIC, methods, new ClassField[0], new String[0]);

        Object result = generator.invokeMethod(classDef, "callfact", 10);
        Assertions.assertEquals(3628800, result);
    }

    @TestAllImplementations
    public void testCallStaticMethod(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("fact", Access.PUBLIC + Access.STATIC, new Field[] {
                new Field("n", SimpleTypes.INT),
                new Field("acc", SimpleTypes.INT) },
                SimpleTypes.INT,
                new If(new BinaryComparison(Tests.LT, new GetValue("n", SimpleTypes.INT),
                        new IntConstant(2)),
                        new GetValue("acc", SimpleTypes.INT),
                        new CallStaticMethod("fact", new Type[] { SimpleTypes.INT, SimpleTypes.INT },
                                SimpleTypes.INT,
                                new Expression[] {
                                        new InlineCall(Inlines.IntSub, new Expression[] {
                                                new GetValue("n", SimpleTypes.INT),
                                                new IntConstant(1)
                                        }),
                                        new InlineCall(Inlines.IntMul, new Expression[] {
                                                new GetValue("n", SimpleTypes.INT),
                                                new GetValue("acc", SimpleTypes.INT)
                                        })
                                })));

        MethodDef callFactMethod = new MethodDef("callfact", Access.PUBLIC + Access.STATIC,
                new Field[]{ new Field("n", SimpleTypes.INT)
        }, SimpleTypes.INT,
                new Binding(new Binding.BindingPair[]{
                        new Binding.BindingPair("f",
                                new CallStaticMethod("fact", new Type[]{SimpleTypes.INT, SimpleTypes.INT},
                                        SimpleTypes.INT,
                                        new Expression[]{new GetValue("n", SimpleTypes.INT),
                                                new IntConstant(1)}))
                }, Binding.Visibility.Separate, new GetValue("f", SimpleTypes.INT)));

        MethodDef[] methods = {
                ConstructorDef.generateWith(new Field[0]),
                method, callFactMethod
        };

        ClassDef classDef = new ClassDef("org.jfuncmachine.temp", "TestCall",
                ToyClass.class.getPackageName(), ToyClass.class.getSimpleName(),
                Access.PUBLIC, methods, new ClassField[0], new String[0]);

        Object result = generator.invokeMethod(classDef, "callfact", 10);
        Assertions.assertEquals(3628800, result);
    }

    @TestAllImplementations
    public void testCallStaticMethodSelfRecurse(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("fact", Access.PUBLIC + Access.STATIC, new Field[] {
                new Field("n", SimpleTypes.INT),
                new Field("acc", SimpleTypes.INT) },
                SimpleTypes.INT,
                new If(new BinaryComparison(Tests.LT, new GetValue("n", SimpleTypes.INT),
                        new IntConstant(2)),
                        new GetValue("acc", SimpleTypes.INT),
                        new CallStaticMethod(new Type[] { SimpleTypes.INT, SimpleTypes.INT },
                                SimpleTypes.INT,
                                new Expression[] {
                                        new InlineCall(Inlines.IntSub, new Expression[] {
                                                new GetValue("n", SimpleTypes.INT),
                                                new IntConstant(1)
                                        }),
                                        new InlineCall(Inlines.IntMul, new Expression[] {
                                                new GetValue("n", SimpleTypes.INT),
                                                new GetValue("acc", SimpleTypes.INT)
                                        })
                                })));

        MethodDef callFactMethod = new MethodDef("callfact", Access.PUBLIC + Access.STATIC,
                new Field[]{ new Field("n", SimpleTypes.INT)
                }, SimpleTypes.INT,
                new Binding(new Binding.BindingPair[]{
                        new Binding.BindingPair("f",
                                new CallStaticMethod("fact", new Type[]{SimpleTypes.INT, SimpleTypes.INT},
                                        SimpleTypes.INT,
                                        new Expression[]{new GetValue("n", SimpleTypes.INT),
                                                new IntConstant(1)}))
                }, Binding.Visibility.Separate, new GetValue("f", SimpleTypes.INT)));

        MethodDef[] methods = {
                ConstructorDef.generateWith(new Field[0]),
                method, callFactMethod
        };

        ClassDef classDef = new ClassDef("org.jfuncmachine.temp", "TestCall",
                ToyClass.class.getPackageName(), ToyClass.class.getSimpleName(),
                Access.PUBLIC, methods, new ClassField[0], new String[0]);

        Object result = generator.invokeMethod(classDef, "callfact", 10);
        Assertions.assertEquals(3628800, result);
    }

    @TestAllImplementations
    public void testTailCallMethod() {
        ClassGenerator generator = new ClassGenerator();
        MethodDef method = new MethodDef("fact", Access.PUBLIC, new Field[] {
                new Field("n", SimpleTypes.INT),
                new Field("acc", SimpleTypes.INT) },
                SimpleTypes.INT, true,
                new If(new BinaryComparison(Tests.LT, new GetValue("n", SimpleTypes.INT),
                        new IntConstant(2)),
                        new GetValue("acc", SimpleTypes.INT),
                        new CallTailCallMethod("fact", new Type[] { SimpleTypes.INT, SimpleTypes.INT },
                                SimpleTypes.INT,
                                new GetValue("this", new ObjectType()),
                                new Expression[] {
                                        new InlineCall(Inlines.IntSub, new Expression[] {
                                                new GetValue("n", SimpleTypes.INT),
                                                new IntConstant(1)
                                        }),
                                        new InlineCall(Inlines.IntMul, new Expression[] {
                                                new GetValue("n", SimpleTypes.INT),
                                                new GetValue("acc", SimpleTypes.INT)
                                        })
                                })));

        MethodDef callFactMethod = new MethodDef("callfact", Access.PUBLIC, new Field[]{
                new Field("n", SimpleTypes.INT)
        }, SimpleTypes.INT,
                new Binding(new Binding.BindingPair[]{
                        new Binding.BindingPair("f",
                                new CallTailCallMethod("fact", new Type[]{SimpleTypes.INT, SimpleTypes.INT},
                                        SimpleTypes.INT,
                                        new GetValue("this", new ObjectType()),
                                        new Expression[]{new GetValue("n", SimpleTypes.INT),
                                                new IntConstant(1)}))
                }, Binding.Visibility.Separate, new GetValue("f", SimpleTypes.INT)));

        MethodDef[] methods = {
                ConstructorDef.generateWith(new Field[0]),
                method, callFactMethod
        };

        ClassDef classDef = new ClassDef("org.jfuncmachine.temp", "TestCall",
                ToyClass.class.getPackageName(), ToyClass.class.getSimpleName(),
                Access.PUBLIC, methods, new ClassField[0], new String[0]);

        Object result = generator.invokeMethod(classDef, "callfact", 10);
        Assertions.assertEquals(3628800, result);
    }

    @TestAllImplementations
    public void testTailCallStaticMethod() {
        ClassGenerator generator = new ClassGenerator();
        MethodDef method = new MethodDef("fact", Access.PUBLIC + Access.STATIC, new Field[] {
                new Field("n", SimpleTypes.INT),
                new Field("acc", SimpleTypes.INT) },
                SimpleTypes.INT, true,
                new If(new BinaryComparison(Tests.LT, new GetValue("n", SimpleTypes.INT),
                        new IntConstant(2)),
                        new GetValue("acc", SimpleTypes.INT),
                        new CallTailCallStaticMethod("fact", new Type[] { SimpleTypes.INT, SimpleTypes.INT },
                                SimpleTypes.INT,
                                new Expression[] {
                                        new InlineCall(Inlines.IntSub, new Expression[] {
                                                new GetValue("n", SimpleTypes.INT),
                                                new IntConstant(1)
                                        }),
                                        new InlineCall(Inlines.IntMul, new Expression[] {
                                                new GetValue("n", SimpleTypes.INT),
                                                new GetValue("acc", SimpleTypes.INT)
                                        })
                                })));

        MethodDef callFactMethod = new MethodDef("callfact", Access.PUBLIC + Access.STATIC,
                new Field[]{ new Field("n", SimpleTypes.INT)
                }, SimpleTypes.INT,
                new Binding(new Binding.BindingPair[]{
                        new Binding.BindingPair("f",
                                new CallTailCallStaticMethod("fact", new Type[]{SimpleTypes.INT, SimpleTypes.INT},
                                        SimpleTypes.INT,
                                        new Expression[]{new GetValue("n", SimpleTypes.INT),
                                                new IntConstant(1)}))
                }, Binding.Visibility.Separate, new GetValue("f", SimpleTypes.INT)));

        MethodDef[] methods = {
                ConstructorDef.generateWith(new Field[0]),
                method, callFactMethod
        };

        ClassDef classDef = new ClassDef("org.jfuncmachine.temp", "TestCall",
                ToyClass.class.getPackageName(), ToyClass.class.getSimpleName(),
                Access.PUBLIC, methods, new ClassField[0], new String[0]);

        Object result = generator.invokeMethod(classDef, "callfact", 10);
        Assertions.assertEquals(3628800, result);
    }

    @TestAllImplementations
    public void testLambdaRecurseWithSelfCall(String generatorType, ClassGenerator generator) {
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
                                                new CallStaticMethod(
                                                        new Type[] { SimpleTypes.INT, SimpleTypes.INT },
                                                        SimpleTypes.INT, new Expression[] {
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
}
