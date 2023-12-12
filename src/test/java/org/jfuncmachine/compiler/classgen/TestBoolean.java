package org.jfuncmachine.compiler.classgen;

import org.jfuncmachine.compiler.model.Access;
import org.jfuncmachine.compiler.model.MethodDef;
import org.jfuncmachine.compiler.model.expr.GetValue;
import org.jfuncmachine.compiler.model.expr.If;
import org.jfuncmachine.compiler.model.expr.bool.*;
import org.jfuncmachine.compiler.model.expr.bool.tests.Tests;
import org.jfuncmachine.compiler.model.expr.constants.BooleanConstant;
import org.jfuncmachine.compiler.model.types.Field;
import org.jfuncmachine.compiler.model.types.ObjectType;
import org.jfuncmachine.compiler.model.types.SimpleTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class TestBoolean {
    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(ClassGeneratorProvider.class)
    @Retention(RetentionPolicy.RUNTIME)
    private @interface TestAllImplementations {}

    @TestAllImplementations
    public void testIntEq(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.INT),
                new Field("y", SimpleTypes.INT)},
                SimpleTypes.BOOLEAN,
                new If(new BinaryComparison(Tests.EQ, new GetValue("x", SimpleTypes.INT),
                        new GetValue("y", SimpleTypes.INT)),
                        new BooleanConstant(true), new BooleanConstant(false)));

        Object result = generator.invokeMethod("TestBoolean",method, 0, 0);
        Assertions.assertTrue((Boolean) result, "0 should equal 0");
        result = generator.invokeMethod("TestBoolean",method, 5, 5);
        Assertions.assertTrue((Boolean) result, "5 should equal 5");
        result = generator.invokeMethod("TestBoolean",method, 0, 5);
        Assertions.assertFalse((Boolean) result, "0 should not equal 5");
        result = generator.invokeMethod("TestBoolean",method, 5, 0);
        Assertions.assertFalse((Boolean) result, "5 should not equal 0");
    }

    @TestAllImplementations
    public void testIntNe(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.INT),
                new Field("y", SimpleTypes.INT)},
                SimpleTypes.BOOLEAN,
                new If(new BinaryComparison(Tests.NE, new GetValue("x", SimpleTypes.INT),
                        new GetValue("y", SimpleTypes.INT)),
                        new BooleanConstant(true), new BooleanConstant(false)));

        Object result = generator.invokeMethod("TestBoolean",method, 0, 0);
        Assertions.assertFalse((Boolean) result, "0 should equal 0");
        result = generator.invokeMethod("TestBoolean",method, 5, 5);
        Assertions.assertFalse((Boolean) result, "5 should equal 5");
        result = generator.invokeMethod("TestBoolean",method, 0, 5);
        Assertions.assertTrue((Boolean) result, "0 should not equal 5");
        result = generator.invokeMethod("TestBoolean",method, 5, 0);
        Assertions.assertTrue((Boolean) result, "5 should not equal 0");

    }

    @TestAllImplementations
    public void testIntLt(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.INT),
                new Field("y", SimpleTypes.INT)},
                SimpleTypes.BOOLEAN,
                new If(new BinaryComparison(Tests.LT, new GetValue("x", SimpleTypes.INT),
                        new GetValue("y", SimpleTypes.INT)),
                        new BooleanConstant(true), new BooleanConstant(false)));

        Object result = generator.invokeMethod("TestBoolean",method, 0, 0);
        Assertions.assertFalse((Boolean) result, "0 should not be < 0");
        result = generator.invokeMethod("TestBoolean",method, 5, 5);
        Assertions.assertFalse((Boolean) result, "5 should not be < 5");
        result = generator.invokeMethod("TestBoolean",method, 0, 5);
        Assertions.assertTrue((Boolean) result, "0 should be < 5");
        result = generator.invokeMethod("TestBoolean",method, 5, 0);
        Assertions.assertFalse((Boolean) result, "5 should not be < 0");

    }

    @TestAllImplementations
    public void testIntLe(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.INT),
                new Field("y", SimpleTypes.INT)},
                SimpleTypes.BOOLEAN,
                new If(new BinaryComparison(Tests.LE, new GetValue("x", SimpleTypes.INT),
                        new GetValue("y", SimpleTypes.INT)),
                        new BooleanConstant(true), new BooleanConstant(false)));

        Object result = generator.invokeMethod("TestBoolean",method, 0, 0);
        Assertions.assertTrue((Boolean) result, "0 should be <= 0");
        result = generator.invokeMethod("TestBoolean",method, 5, 5);
        Assertions.assertTrue((Boolean) result, "5 should be <= 5");
        result = generator.invokeMethod("TestBoolean",method, 0, 5);
        Assertions.assertTrue((Boolean) result, "0 should be <= 5");
        result = generator.invokeMethod("TestBoolean",method, 5, 0);
        Assertions.assertFalse((Boolean) result, "5 should not be <= 0");

    }

    @TestAllImplementations
    public void testIntGt(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.INT),
                new Field("y", SimpleTypes.INT)},
                SimpleTypes.BOOLEAN,
                new If(new BinaryComparison(Tests.GT, new GetValue("x", SimpleTypes.INT),
                        new GetValue("y", SimpleTypes.INT)),
                        new BooleanConstant(true), new BooleanConstant(false)));

        Object result = generator.invokeMethod("TestBoolean",method, 0, 0);
        Assertions.assertFalse((Boolean) result, "0 should not be > 0");
        result = generator.invokeMethod("TestBoolean",method, 5, 5);
        Assertions.assertFalse((Boolean) result, "5 should not be > 5");
        result = generator.invokeMethod("TestBoolean",method, 0, 5);
        Assertions.assertFalse((Boolean) result, "0 should not be > 5");
        result = generator.invokeMethod("TestBoolean",method, 5, 0);
        Assertions.assertTrue((Boolean) result, "5 should be > 0");

    }

    @TestAllImplementations
    public void testIntGe(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.INT),
                new Field("y", SimpleTypes.INT)},
                SimpleTypes.BOOLEAN,
                new If(new BinaryComparison(Tests.GE, new GetValue("x", SimpleTypes.INT),
                        new GetValue("y", SimpleTypes.INT)),
                        new BooleanConstant(true), new BooleanConstant(false)));

        Object result = generator.invokeMethod("TestBoolean",method, 0, 0);
        Assertions.assertTrue((Boolean) result, "0 should be >= 0");
        result = generator.invokeMethod("TestBoolean",method, 5, 5);
        Assertions.assertTrue((Boolean) result, "5 should be >= 5");
        result = generator.invokeMethod("TestBoolean",method, 0, 5);
        Assertions.assertFalse((Boolean) result, "0 should not be >= 5");
        result = generator.invokeMethod("TestBoolean",method, 5, 0);
        Assertions.assertTrue((Boolean) result, "5 should be >= 0");

    }

    @TestAllImplementations
    public void testIntegerEq(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.INT),
                new Field("y", new ObjectType(SimpleTypes.INT.getBoxTypeName()))},
                SimpleTypes.BOOLEAN,
                new If(new BinaryComparison(Tests.EQ, new GetValue("x", SimpleTypes.INT),
                        new GetValue("y", new ObjectType(SimpleTypes.INT.getBoxTypeName()))),
                        new BooleanConstant(true), new BooleanConstant(false)));

        Object result = generator.invokeMethod("TestBoolean",method, 0, 0);
        Assertions.assertTrue((Boolean) result, "0 should equal 0");
        result = generator.invokeMethod("TestBoolean",method, 5, 5);
        Assertions.assertTrue((Boolean) result, "5 should equal 5");
        result = generator.invokeMethod("TestBoolean",method, 0, 5);
        Assertions.assertFalse((Boolean) result, "0 should not equal 5");
        result = generator.invokeMethod("TestBoolean",method, 5, 0);
        Assertions.assertFalse((Boolean) result, "5 should not equal 0");

    }

    @TestAllImplementations
    public void testIntegerNe(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.INT),
                new Field("y", new ObjectType(SimpleTypes.INT.getBoxTypeName()))},
                SimpleTypes.BOOLEAN,
                new If(new BinaryComparison(Tests.NE, new GetValue("x", SimpleTypes.INT),
                        new GetValue("y", new ObjectType(SimpleTypes.INT.getBoxTypeName()))),
                        new BooleanConstant(true), new BooleanConstant(false)));

        Object result = generator.invokeMethod("TestBoolean",method, 0, 0);
        Assertions.assertFalse((Boolean) result, "0 should equal 0");
        result = generator.invokeMethod("TestBoolean",method, 5, 5);
        Assertions.assertFalse((Boolean) result, "5 should equal 5");
        result = generator.invokeMethod("TestBoolean",method, 0, 5);
        Assertions.assertTrue((Boolean) result, "0 should not equal 5");
        result = generator.invokeMethod("TestBoolean",method, 5, 0);
        Assertions.assertTrue((Boolean) result, "5 should not equal 0");

    }

    @TestAllImplementations
    public void testIntegerLt(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.INT),
                new Field("y", new ObjectType(SimpleTypes.INT.getBoxTypeName()))},
                SimpleTypes.BOOLEAN,
                new If(new BinaryComparison(Tests.LT, new GetValue("x", SimpleTypes.INT),
                        new GetValue("y", new ObjectType(SimpleTypes.INT.getBoxTypeName()))),
                        new BooleanConstant(true), new BooleanConstant(false)));

        Object result = generator.invokeMethod("TestBoolean",method, 0, 0);
        Assertions.assertFalse((Boolean) result, "0 should not be < 0");
        result = generator.invokeMethod("TestBoolean",method, 5, 5);
        Assertions.assertFalse((Boolean) result, "5 should not be < 5");
        result = generator.invokeMethod("TestBoolean",method, 0, 5);
        Assertions.assertTrue((Boolean) result, "0 should be < 5");
        result = generator.invokeMethod("TestBoolean",method, 5, 0);
        Assertions.assertFalse((Boolean) result, "5 should not be < 0");

    }

    @TestAllImplementations
    public void testIntegerLe(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.INT),
                new Field("y", new ObjectType(SimpleTypes.INT.getBoxTypeName()))},
                SimpleTypes.BOOLEAN,
                new If(new BinaryComparison(Tests.LE, new GetValue("x", SimpleTypes.INT),
                        new GetValue("y", new ObjectType(SimpleTypes.INT.getBoxTypeName()))),
                        new BooleanConstant(true), new BooleanConstant(false)));

        Object result = generator.invokeMethod("TestBoolean",method, 0, 0);
        Assertions.assertTrue((Boolean) result, "0 should be <= 0");
        result = generator.invokeMethod("TestBoolean",method, 5, 5);
        Assertions.assertTrue((Boolean) result, "5 should be <= 5");
        result = generator.invokeMethod("TestBoolean",method, 0, 5);
        Assertions.assertTrue((Boolean) result, "0 should be <= 5");
        result = generator.invokeMethod("TestBoolean",method, 5, 0);
        Assertions.assertFalse((Boolean) result, "5 should not be <= 0");

    }

    @TestAllImplementations
    public void testIntegerGt(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.INT),
                new Field("y", new ObjectType(SimpleTypes.INT.getBoxTypeName()))},
                SimpleTypes.BOOLEAN,
                new If(new BinaryComparison(Tests.GT, new GetValue("x", SimpleTypes.INT),
                        new GetValue("y", new ObjectType(SimpleTypes.INT.getBoxTypeName()))),
                        new BooleanConstant(true), new BooleanConstant(false)));

        Object result = generator.invokeMethod("TestBoolean",method, 0, 0);
        Assertions.assertFalse((Boolean) result, "0 should not be > 0");
        result = generator.invokeMethod("TestBoolean",method, 5, 5);
        Assertions.assertFalse((Boolean) result, "5 should not be > 5");
        result = generator.invokeMethod("TestBoolean",method, 0, 5);
        Assertions.assertFalse((Boolean) result, "0 should not be > 5");
        result = generator.invokeMethod("TestBoolean",method, 5, 0);
        Assertions.assertTrue((Boolean) result, "5 should be > 0");

    }

    @TestAllImplementations
    public void testIntegerGe(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.INT),
                new Field("y", new ObjectType(SimpleTypes.INT.getBoxTypeName()))},
                SimpleTypes.BOOLEAN,
                new If(new BinaryComparison(Tests.GE, new GetValue("x", SimpleTypes.INT),
                        new GetValue("y", new ObjectType(SimpleTypes.INT.getBoxTypeName()))),
                        new BooleanConstant(true), new BooleanConstant(false)));

        Object result = generator.invokeMethod("TestBoolean",method, 0, 0);
        Assertions.assertTrue((Boolean) result, "0 should be >= 0");
        result = generator.invokeMethod("TestBoolean",method, 5, 5);
        Assertions.assertTrue((Boolean) result, "5 should be >= 5");
        result = generator.invokeMethod("TestBoolean",method, 0, 5);
        Assertions.assertFalse((Boolean) result, "0 should not be >= 5");
        result = generator.invokeMethod("TestBoolean",method, 5, 0);
        Assertions.assertTrue((Boolean) result, "5 should be >= 0");

    }

    @TestAllImplementations
    public void testDoubleEq(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.DOUBLE),
                new Field("y", SimpleTypes.DOUBLE)},
                SimpleTypes.BOOLEAN,
                new If(new BinaryComparison(Tests.EQ, new GetValue("x", SimpleTypes.DOUBLE),
                        new GetValue("y", SimpleTypes.DOUBLE)),
                        new BooleanConstant(true), new BooleanConstant(false)));

        Object result = generator.invokeMethod("TestBoolean",method, 0.0, 0.0);
        Assertions.assertTrue((Boolean) result, "0 should equal 0");
        result = generator.invokeMethod("TestBoolean",method, 5.0, 5.0);
        Assertions.assertTrue((Boolean) result, "5 should equal 5");
        result = generator.invokeMethod("TestBoolean",method, 0.0, 5.0);
        Assertions.assertFalse((Boolean) result, "0 should not equal 5");
        result = generator.invokeMethod("TestBoolean",method, 5.0, 0.0);
        Assertions.assertFalse((Boolean) result, "5 should not equal 0");

    }

    @TestAllImplementations
    public void testDoubleNe(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.DOUBLE),
                new Field("y", SimpleTypes.DOUBLE)},
                SimpleTypes.BOOLEAN,
                new If(new BinaryComparison(Tests.NE, new GetValue("x", SimpleTypes.DOUBLE),
                        new GetValue("y", SimpleTypes.DOUBLE)),
                        new BooleanConstant(true), new BooleanConstant(false)));

        Object result = generator.invokeMethod("TestBoolean",method, 0.0, 0.0);
        Assertions.assertFalse((Boolean) result, "0 should equal 0");
        result = generator.invokeMethod("TestBoolean",method, 5.0, 5.0);
        Assertions.assertFalse((Boolean) result, "5 should equal 5");
        result = generator.invokeMethod("TestBoolean",method, 0.0, 5.0);
        Assertions.assertTrue((Boolean) result, "0 should not equal 5");
        result = generator.invokeMethod("TestBoolean",method, 5.0, 0.0);
        Assertions.assertTrue((Boolean) result, "5 should not equal 0");

    }

    @TestAllImplementations
    public void testDoubleLt(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.DOUBLE),
                new Field("y", SimpleTypes.DOUBLE)},
                SimpleTypes.BOOLEAN,
                new If(new BinaryComparison(Tests.LT, new GetValue("x", SimpleTypes.DOUBLE),
                        new GetValue("y", SimpleTypes.DOUBLE)),
                        new BooleanConstant(true), new BooleanConstant(false)));

        Object result = generator.invokeMethod("TestBoolean",method, 0.0, 0.0);
        Assertions.assertFalse((Boolean) result, "0 should not be < 0");
        result = generator.invokeMethod("TestBoolean",method, 5.0, 5.0);
        Assertions.assertFalse((Boolean) result, "5 should not be < 5");
        result = generator.invokeMethod("TestBoolean",method, 0.0, 5.0);
        Assertions.assertTrue((Boolean) result, "0 should be < 5");
        result = generator.invokeMethod("TestBoolean",method, 5.0, 0.0);
        Assertions.assertFalse((Boolean) result, "5 should not be < 0");

    }

    @TestAllImplementations
    public void testDoubleLe(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.DOUBLE),
                new Field("y", SimpleTypes.DOUBLE)},
                SimpleTypes.BOOLEAN,
                new If(new BinaryComparison(Tests.LE, new GetValue("x", SimpleTypes.DOUBLE),
                        new GetValue("y", SimpleTypes.DOUBLE)),
                        new BooleanConstant(true), new BooleanConstant(false)));

        Object result = generator.invokeMethod("TestBoolean",method, 0.0, 0.0);
        Assertions.assertTrue((Boolean) result, "0 should be <= 0");
        result = generator.invokeMethod("TestBoolean",method, 5.0, 5.0);
        Assertions.assertTrue((Boolean) result, "5 should be <= 5");
        result = generator.invokeMethod("TestBoolean",method, 0.0, 5.0);
        Assertions.assertTrue((Boolean) result, "0 should be <= 5");
        result = generator.invokeMethod("TestBoolean",method, 5.0, 0.0);
        Assertions.assertFalse((Boolean) result, "5 should not be <= 0");

    }

    @TestAllImplementations
    public void testDoubleGt(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.DOUBLE),
                new Field("y", SimpleTypes.DOUBLE)},
                SimpleTypes.BOOLEAN,
                new If(new BinaryComparison(Tests.GT, new GetValue("x", SimpleTypes.DOUBLE),
                        new GetValue("y", SimpleTypes.DOUBLE)),
                        new BooleanConstant(true), new BooleanConstant(false)));

        Object result = generator.invokeMethod("TestBoolean",method, 0.0, 0.0);
        Assertions.assertFalse((Boolean) result, "0 should not be > 0");
        result = generator.invokeMethod("TestBoolean",method, 5.0, 5.0);
        Assertions.assertFalse((Boolean) result, "5 should not be > 5");
        result = generator.invokeMethod("TestBoolean",method, 0.0, 5.0);
        Assertions.assertFalse((Boolean) result, "0 should not be > 5");
        result = generator.invokeMethod("TestBoolean",method, 5.0, 0.0);
        Assertions.assertTrue((Boolean) result, "5 should be > 0");

    }

    @TestAllImplementations
    public void testDoubleGe(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.DOUBLE),
                new Field("y", SimpleTypes.DOUBLE)},
                SimpleTypes.BOOLEAN,
                new If(new BinaryComparison(Tests.GE, new GetValue("x", SimpleTypes.DOUBLE),
                        new GetValue("y", SimpleTypes.DOUBLE)),
                        new BooleanConstant(true), new BooleanConstant(false)));

        Object result = generator.invokeMethod("TestBoolean",method, 0.0, 0.0);
        Assertions.assertTrue((Boolean) result, "0 should be >= 0");
        result = generator.invokeMethod("TestBoolean",method, 5.0, 5.0);
        Assertions.assertTrue((Boolean) result, "5 should be >= 5");
        result = generator.invokeMethod("TestBoolean",method, 0.0, 5.0);
        Assertions.assertFalse((Boolean) result, "0 should not be >= 5");
        result = generator.invokeMethod("TestBoolean",method, 5.0, 0.0);
        Assertions.assertTrue((Boolean) result, "5 should be >= 0");

    }

    @TestAllImplementations
    public void testLangDoubleEq(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", new ObjectType(SimpleTypes.DOUBLE.getBoxTypeName())),
                new Field("y", SimpleTypes.DOUBLE)},
                SimpleTypes.BOOLEAN,
                new If(new BinaryComparison(Tests.EQ, new GetValue("x", new ObjectType(SimpleTypes.DOUBLE.getBoxTypeName())),
                        new GetValue("y", SimpleTypes.DOUBLE)),
                        new BooleanConstant(true), new BooleanConstant(false)));

        Object result = generator.invokeMethod("TestBoolean",method, 0.0, 0.0);
        Assertions.assertTrue((Boolean) result, "0 should equal 0");
        result = generator.invokeMethod("TestBoolean",method, 5.0, 5.0);
        Assertions.assertTrue((Boolean) result, "5 should equal 5");
        result = generator.invokeMethod("TestBoolean",method, 0.0, 5.0);
        Assertions.assertFalse((Boolean) result, "0 should not equal 5");
        result = generator.invokeMethod("TestBoolean",method, 5.0, 0.0);
        Assertions.assertFalse((Boolean) result, "5 should not equal 0");

    }

    @TestAllImplementations
    public void testFloatEq(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.FLOAT),
                new Field("y", SimpleTypes.FLOAT)},
                SimpleTypes.BOOLEAN,
                new If(new BinaryComparison(Tests.EQ, new GetValue("x", SimpleTypes.FLOAT),
                        new GetValue("y", SimpleTypes.FLOAT)),
                        new BooleanConstant(true), new BooleanConstant(false)));

        Object result = generator.invokeMethod("TestBoolean",method, (float) 0.0, (float) 0.0);
        Assertions.assertTrue((Boolean) result, "0 should equal 0");
        result = generator.invokeMethod("TestBoolean",method, (float) 5.0, (float) 5.0);
        Assertions.assertTrue((Boolean) result, "5 should equal 5");
        result = generator.invokeMethod("TestBoolean",method, (float) 0.0, (float) 5.0);
        Assertions.assertFalse((Boolean) result, "0 should not equal 5");
        result = generator.invokeMethod("TestBoolean",method, (float) 5.0, (float) 0.0);
        Assertions.assertFalse((Boolean) result, "5 should not equal 0");

    }

    @TestAllImplementations
    public void testLangFloatEq(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", new ObjectType(SimpleTypes.FLOAT.getBoxTypeName())),
                new Field("y", SimpleTypes.FLOAT)},
                SimpleTypes.BOOLEAN,
                new If(new BinaryComparison(Tests.EQ, new GetValue("x", new ObjectType(SimpleTypes.FLOAT.getBoxTypeName())),
                        new GetValue("y", SimpleTypes.FLOAT)),
                        new BooleanConstant(true), new BooleanConstant(false)));

        Object result = generator.invokeMethod("TestBoolean",method, (float) 0.0, (float) 0.0);
        Assertions.assertTrue((Boolean) result, "0 should equal 0");
        result = generator.invokeMethod("TestBoolean",method, (float) 5.0, (float) 5.0);
        Assertions.assertTrue((Boolean) result, "5 should equal 5");
        result = generator.invokeMethod("TestBoolean",method, (float) 0.0, (float) 5.0);
        Assertions.assertFalse((Boolean) result, "0 should not equal 5");
        result = generator.invokeMethod("TestBoolean",method, (float) 5.0, (float) 0.0);
        Assertions.assertFalse((Boolean) result, "5 should not equal 0");

    }

    @TestAllImplementations
    public void testLongEq(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.LONG),
                new Field("y", SimpleTypes.LONG)},
                SimpleTypes.BOOLEAN,
                new If(new BinaryComparison(Tests.EQ, new GetValue("x", SimpleTypes.LONG),
                        new GetValue("y", SimpleTypes.LONG)),
                        new BooleanConstant(true), new BooleanConstant(false)));

        Object result = generator.invokeMethod("TestBoolean",method,  0l, 0l);
        Assertions.assertTrue((Boolean) result, "0 should equal 0");
        result = generator.invokeMethod("TestBoolean",method,  5l, 5l);
        Assertions.assertTrue((Boolean) result, "5 should equal 5");
        result = generator.invokeMethod("TestBoolean",method, 0l, 5l);
        Assertions.assertFalse((Boolean) result, "0 should not equal 5");
        result = generator.invokeMethod("TestBoolean",method, 5l, 0l);
        Assertions.assertFalse((Boolean) result, "5 should not equal 0");

    }

    @TestAllImplementations
    public void testLangLongEq(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", new ObjectType(SimpleTypes.LONG.getBoxTypeName())),
                new Field("y", SimpleTypes.LONG)},
                SimpleTypes.BOOLEAN,
                new If(new BinaryComparison(Tests.EQ, new GetValue("x", new ObjectType(SimpleTypes.LONG.getBoxTypeName())),
                        new GetValue("y", SimpleTypes.LONG)),
                        new BooleanConstant(true), new BooleanConstant(false)));

        Object result = generator.invokeMethod("TestBoolean",method, 0l, 0l);
        Assertions.assertTrue((Boolean) result, "0 should equal 0");
        result = generator.invokeMethod("TestBoolean",method, 5l, 5l);
        Assertions.assertTrue((Boolean) result, "5 should equal 5");
        result = generator.invokeMethod("TestBoolean",method, 0l, 5l);
        Assertions.assertFalse((Boolean) result, "0 should not equal 5");
        result = generator.invokeMethod("TestBoolean",method, 5l, 0l);
        Assertions.assertFalse((Boolean) result, "5 should not equal 0");

    }

    @TestAllImplementations
    public void testAnd(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.INT),
                new Field("y", SimpleTypes.INT)},
                SimpleTypes.BOOLEAN,
                new If(new And(
                        new BinaryComparison(Tests.EQ, new GetValue("x", SimpleTypes.INT),
                            new GetValue("y", SimpleTypes.INT)),
                        new BinaryComparison(Tests.EQ, new GetValue("y", SimpleTypes.INT),
                        new GetValue("x", SimpleTypes.INT))),
                        new BooleanConstant(true), new BooleanConstant(false)));

        Object result = generator.invokeMethod("TestBoolean",method, 0, 0);
        Assertions.assertTrue((Boolean) result, "0 should equal 0");
        result = generator.invokeMethod("TestBoolean",method, 5, 5);
        Assertions.assertTrue((Boolean) result, "5 should equal 5");
        result = generator.invokeMethod("TestBoolean",method, 0, 5);
        Assertions.assertFalse((Boolean) result, "0 should not equal 5");
        result = generator.invokeMethod("TestBoolean",method, 5, 0);
        Assertions.assertFalse((Boolean) result, "5 should not equal 0");

    }

    @TestAllImplementations
    public void testOr(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.INT),
                new Field("y", SimpleTypes.INT)},
                SimpleTypes.BOOLEAN,
                new If(new Or(
                        new BinaryComparison(Tests.EQ, new GetValue("x", SimpleTypes.INT),
                                new GetValue("y", SimpleTypes.INT)),
                        new BinaryComparison(Tests.NE, new GetValue("y", SimpleTypes.INT),
                                new GetValue("x", SimpleTypes.INT))),
                        new BooleanConstant(true), new BooleanConstant(false)));

        Object result = generator.invokeMethod("TestBoolean",method, 0, 0);
        Assertions.assertTrue((Boolean) result, "0 should equal 0");
        result = generator.invokeMethod("TestBoolean",method, 5, 5);
        Assertions.assertTrue((Boolean) result, "5 should equal 5");
        result = generator.invokeMethod("TestBoolean",method, 0, 5);
        Assertions.assertTrue((Boolean) result, "0 should not equal 5");
        result = generator.invokeMethod("TestBoolean",method, 5, 0);
        Assertions.assertTrue((Boolean) result, "5 should not equal 0");
    }

    @TestAllImplementations
    public void testNot(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.INT),
                new Field("y", SimpleTypes.INT)},
                SimpleTypes.BOOLEAN,
                new If(new Not(new BinaryComparison(Tests.NE, new GetValue("x", SimpleTypes.INT),
                        new GetValue("y", SimpleTypes.INT))),
                        new BooleanConstant(true), new BooleanConstant(false)));

        Object result = generator.invokeMethod("TestBoolean",method, 0, 0);
        Assertions.assertTrue((Boolean) result, "0 should equal 0");
        result = generator.invokeMethod("TestBoolean",method, 5, 5);
        Assertions.assertTrue((Boolean) result, "5 should equal 5");
        result = generator.invokeMethod("TestBoolean",method, 0, 5);
        Assertions.assertFalse((Boolean) result, "0 should not equal 5");
        result = generator.invokeMethod("TestBoolean",method, 5, 0);
        Assertions.assertFalse((Boolean) result, "5 should not equal 0");

    }

    @TestAllImplementations
    public void testOrNot(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.INT),
                new Field("y", SimpleTypes.INT)},
                SimpleTypes.BOOLEAN,
                new If(new Or(
                        new BinaryComparison(Tests.EQ, new GetValue("x", SimpleTypes.INT),
                                new GetValue("y", SimpleTypes.INT)),
                        new Not(new BinaryComparison(Tests.EQ, new GetValue("y", SimpleTypes.INT),
                                new GetValue("x", SimpleTypes.INT)))),
                        new BooleanConstant(true), new BooleanConstant(false)));

        Object result = generator.invokeMethod("TestBoolean",method, 0, 0);
        Assertions.assertTrue((Boolean) result, "0 should equal 0");
        result = generator.invokeMethod("TestBoolean",method, 5, 5);
        Assertions.assertTrue((Boolean) result, "5 should equal 5");
        result = generator.invokeMethod("TestBoolean",method, 0, 5);
        Assertions.assertTrue((Boolean) result, "0 should not equal 5");
        result = generator.invokeMethod("TestBoolean",method, 5, 0);
        Assertions.assertTrue((Boolean) result, "5 should not equal 0");
    }

    @TestAllImplementations
    public void testAndNot(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.INT),
                new Field("y", SimpleTypes.INT)},
                SimpleTypes.BOOLEAN,
                new If(new And(
                        new BinaryComparison(Tests.EQ, new GetValue("x", SimpleTypes.INT),
                                new GetValue("y", SimpleTypes.INT)),
                        new Not(new BinaryComparison(Tests.NE, new GetValue("y", SimpleTypes.INT),
                                new GetValue("x", SimpleTypes.INT)))),
                        new BooleanConstant(true), new BooleanConstant(false)));

        Object result = generator.invokeMethod("TestBoolean",method, 0, 0);
        Assertions.assertTrue((Boolean) result, "0 should equal 0");
        result = generator.invokeMethod("TestBoolean",method, 5, 5);
        Assertions.assertTrue((Boolean) result, "5 should equal 5");
        result = generator.invokeMethod("TestBoolean",method, 0, 5);
        Assertions.assertFalse((Boolean) result, "0 should not equal 5");
        result = generator.invokeMethod("TestBoolean",method, 5, 0);
        Assertions.assertFalse((Boolean) result, "5 should not equal 0");

    }

    @TestAllImplementations
    public void testIsNull(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.STRING)},
                SimpleTypes.BOOLEAN,
                new If(new UnaryComparison(Tests.IsNull, new GetValue("x", SimpleTypes.STRING)),
                        new BooleanConstant(true), new BooleanConstant(false)));

        Object result = generator.invokeMethod("TestBoolean",method, "Foobar");
        Assertions.assertFalse((Boolean) result, "String is not null");
        result = generator.invokeMethod("TestBoolean",method, (Object) null);
        Assertions.assertTrue((Boolean) result, "String is null");
    }

    @TestAllImplementations
    public void testIsNotNull(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.STRING)},
                SimpleTypes.BOOLEAN,
                new If(new UnaryComparison(Tests.IsNotNull, new GetValue("x", SimpleTypes.STRING)),
                        new BooleanConstant(true), new BooleanConstant(false)));

        Object result = generator.invokeMethod("TestBoolean",method, "Foobar");
        Assertions.assertTrue((Boolean) result, "String is not null");
        result = generator.invokeMethod("TestBoolean",method, (Object) null);
        Assertions.assertFalse((Boolean) result, "String is null");
    }

    @TestAllImplementations
    public void testStringEq(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.STRING),
                new Field("y", SimpleTypes.STRING)},
                SimpleTypes.BOOLEAN,
                new If(new BinaryComparison(Tests.EQ, new GetValue("x", SimpleTypes.STRING),
                        new GetValue("y", SimpleTypes.STRING)),
                        new BooleanConstant(true), new BooleanConstant(false)));

        Object result = generator.invokeMethod("TestBoolean",method, "foo", "foo");
        Assertions.assertTrue((Boolean) result, "foo should equal foo");
        result = generator.invokeMethod("TestBoolean",method, "bar", "bar");
        Assertions.assertTrue((Boolean) result, "bar should equal bar");
        result = generator.invokeMethod("TestBoolean",method, "foo", "bar");
        Assertions.assertFalse((Boolean) result, "foo should not equal bar");
        result = generator.invokeMethod("TestBoolean",method, "bar", "foo");
        Assertions.assertFalse((Boolean) result, "bar should not equal foo");
    }

    @TestAllImplementations
    public void testStringNe(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.STRING),
                new Field("y", SimpleTypes.STRING)},
                SimpleTypes.BOOLEAN,
                new If(new BinaryComparison(Tests.NE, new GetValue("x", SimpleTypes.STRING),
                        new GetValue("y", SimpleTypes.STRING)),
                        new BooleanConstant(true), new BooleanConstant(false)));

        Object result = generator.invokeMethod("TestBoolean",method, "foo", "foo");
        Assertions.assertFalse((Boolean) result, "foo should equal foo");
        result = generator.invokeMethod("TestBoolean",method, "bar", "bar");
        Assertions.assertFalse((Boolean) result, "bar should equal bar");
        result = generator.invokeMethod("TestBoolean",method, "foo", "bar");
        Assertions.assertTrue((Boolean) result, "foo should not equal bar");
        result = generator.invokeMethod("TestBoolean",method, "bar", "foo");
        Assertions.assertTrue((Boolean) result, "bar should not equal foo");
    }

    @TestAllImplementations
    public void testStringLt(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.STRING),
                new Field("y", SimpleTypes.STRING)},
                SimpleTypes.BOOLEAN,
                new If(new BinaryComparison(Tests.LT, new GetValue("x", SimpleTypes.STRING),
                        new GetValue("y", SimpleTypes.STRING)),
                        new BooleanConstant(true), new BooleanConstant(false)));

        Object result = generator.invokeMethod("TestBoolean",method, "foo", "foo");
        Assertions.assertFalse((Boolean) result, "foo should not be < foo");
        result = generator.invokeMethod("TestBoolean",method, "bar", "bar");
        Assertions.assertFalse((Boolean) result, "bar should not be < bar");
        result = generator.invokeMethod("TestBoolean",method, "foo", "bar");
        Assertions.assertFalse((Boolean) result, "foo should not be < bar");
        result = generator.invokeMethod("TestBoolean",method, "bar", "foo");
        Assertions.assertTrue((Boolean) result, "bar should be < foo");
    }

    @TestAllImplementations
    public void testStringLe(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.STRING),
                new Field("y", SimpleTypes.STRING)},
                SimpleTypes.BOOLEAN,
                new If(new BinaryComparison(Tests.LE, new GetValue("x", SimpleTypes.STRING),
                        new GetValue("y", SimpleTypes.STRING)),
                        new BooleanConstant(true), new BooleanConstant(false)));

        Object result = generator.invokeMethod("TestBoolean",method, "foo", "foo");
        Assertions.assertTrue((Boolean) result, "foo should be <= foo");
        result = generator.invokeMethod("TestBoolean",method, "bar", "bar");
        Assertions.assertTrue((Boolean) result, "bar should be <= bar");
        result = generator.invokeMethod("TestBoolean",method, "foo", "bar");
        Assertions.assertFalse((Boolean) result, "foo should not be <= bar");
        result = generator.invokeMethod("TestBoolean",method, "bar", "foo");
        Assertions.assertTrue((Boolean) result, "bar should be <= foo");
    }

    @TestAllImplementations
    public void testStringGt(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.STRING),
                new Field("y", SimpleTypes.STRING)},
                SimpleTypes.BOOLEAN,
                new If(new BinaryComparison(Tests.GT, new GetValue("x", SimpleTypes.STRING),
                        new GetValue("y", SimpleTypes.STRING)),
                        new BooleanConstant(true), new BooleanConstant(false)));

        Object result = generator.invokeMethod("TestBoolean",method, "foo", "foo");
        Assertions.assertFalse((Boolean) result, "foo should not be > foo");
        result = generator.invokeMethod("TestBoolean",method, "bar", "bar");
        Assertions.assertFalse((Boolean) result, "bar should not be > bar");
        result = generator.invokeMethod("TestBoolean",method, "foo", "bar");
        Assertions.assertTrue((Boolean) result, "foo should be > bar");
        result = generator.invokeMethod("TestBoolean",method, "bar", "foo");
        Assertions.assertFalse((Boolean) result, "bar should not be > foo");
    }

    @TestAllImplementations
    public void testStringGe(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.STRING),
                new Field("y", SimpleTypes.STRING)},
                SimpleTypes.BOOLEAN,
                new If(new BinaryComparison(Tests.GE, new GetValue("x", SimpleTypes.STRING),
                        new GetValue("y", SimpleTypes.STRING)),
                        new BooleanConstant(true), new BooleanConstant(false)));

        Object result = generator.invokeMethod("TestBoolean",method, "foo", "foo");
        Assertions.assertTrue((Boolean) result, "foo should be >= foo");
        result = generator.invokeMethod("TestBoolean",method, "bar", "bar");
        Assertions.assertTrue((Boolean) result, "bar should be >= bar");
        result = generator.invokeMethod("TestBoolean",method, "foo", "bar");
        Assertions.assertTrue((Boolean) result, "foo should be >= bar");
        result = generator.invokeMethod("TestBoolean",method, "bar", "foo");
        Assertions.assertFalse((Boolean) result, "bar should not be >= foo");
    }

    @TestAllImplementations
    public void testStringEqIgnoreCase(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.STRING),
                new Field("y", SimpleTypes.STRING)},
                SimpleTypes.BOOLEAN,
                new If(new BinaryComparison(Tests.EQ_IgnoreCase, new GetValue("x", SimpleTypes.STRING),
                        new GetValue("y", SimpleTypes.STRING)),
                        new BooleanConstant(true), new BooleanConstant(false)));

        Object result = generator.invokeMethod("TestBoolean",method, "foo", "Foo");
        Assertions.assertTrue((Boolean) result, "foo should equal Foo");
        result = generator.invokeMethod("TestBoolean",method, "bar", "Bar");
        Assertions.assertTrue((Boolean) result, "bar should equal Bar");
        result = generator.invokeMethod("TestBoolean",method, "foo", "Bar");
        Assertions.assertFalse((Boolean) result, "foo should not equal Bar");
        result = generator.invokeMethod("TestBoolean",method, "bar", "Foo");
        Assertions.assertFalse((Boolean) result, "bar should not equal Foo");
    }

    @TestAllImplementations
    public void testStringNeIgnoreCase(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.STRING),
                new Field("y", SimpleTypes.STRING)},
                SimpleTypes.BOOLEAN,
                new If(new BinaryComparison(Tests.NE_IgnoreCase, new GetValue("x", SimpleTypes.STRING),
                        new GetValue("y", SimpleTypes.STRING)),
                        new BooleanConstant(true), new BooleanConstant(false)));

        Object result = generator.invokeMethod("TestBoolean",method, "foo", "Foo");
        Assertions.assertFalse((Boolean) result, "foo should equal Foo");
        result = generator.invokeMethod("TestBoolean",method, "bar", "Bar");
        Assertions.assertFalse((Boolean) result, "bar should equal Bar");
        result = generator.invokeMethod("TestBoolean",method, "foo", "Bar");
        Assertions.assertTrue((Boolean) result, "foo should not equal Bar");
        result = generator.invokeMethod("TestBoolean",method, "bar", "Foo");
        Assertions.assertTrue((Boolean) result, "bar should not equal Foo");
    }

    @TestAllImplementations
    public void testStringLtIgnoreCase(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.STRING),
                new Field("y", SimpleTypes.STRING)},
                SimpleTypes.BOOLEAN,
                new If(new BinaryComparison(Tests.LT_IgnoreCase, new GetValue("x", SimpleTypes.STRING),
                        new GetValue("y", SimpleTypes.STRING)),
                        new BooleanConstant(true), new BooleanConstant(false)));

        Object result = generator.invokeMethod("TestBoolean",method, "foo", "Foo");
        Assertions.assertFalse((Boolean) result, "foo should not be < Foo");
        result = generator.invokeMethod("TestBoolean",method, "bar", "Bar");
        Assertions.assertFalse((Boolean) result, "bar should not be < Bar");
        result = generator.invokeMethod("TestBoolean",method, "Foo", "bar");
        Assertions.assertFalse((Boolean) result, "Foo should not be < bar");
        result = generator.invokeMethod("TestBoolean",method, "bar", "Foo");
        Assertions.assertTrue((Boolean) result, "bar should be < foo");
    }

    @TestAllImplementations
    public void testStringLeIgnoreCase(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.STRING),
                new Field("y", SimpleTypes.STRING)},
                SimpleTypes.BOOLEAN,
                new If(new BinaryComparison(Tests.LE_IgnoreCase, new GetValue("x", SimpleTypes.STRING),
                        new GetValue("y", SimpleTypes.STRING)),
                        new BooleanConstant(true), new BooleanConstant(false)));

        Object result = generator.invokeMethod("TestBoolean",method, "foo", "Foo");
        Assertions.assertTrue((Boolean) result, "foo should be <= Foo");
        result = generator.invokeMethod("TestBoolean",method, "bar", "Bar");
        Assertions.assertTrue((Boolean) result, "bar should be <= Bar");
        result = generator.invokeMethod("TestBoolean",method, "Foo", "bar");
        Assertions.assertFalse((Boolean) result, "Foo should not be <= bar");
        result = generator.invokeMethod("TestBoolean",method, "bar", "Foo");
        Assertions.assertTrue((Boolean) result, "bar should be <= Foo");
    }

    @TestAllImplementations
    public void testStringGtIgnoreCase(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.STRING),
                new Field("y", SimpleTypes.STRING)},
                SimpleTypes.BOOLEAN,
                new If(new BinaryComparison(Tests.GT_IgnoreCase, new GetValue("x", SimpleTypes.STRING),
                        new GetValue("y", SimpleTypes.STRING)),
                        new BooleanConstant(true), new BooleanConstant(false)));

        Object result = generator.invokeMethod("TestBoolean",method, "foo", "Foo");
        Assertions.assertFalse((Boolean) result, "foo should not be > Foo");
        result = generator.invokeMethod("TestBoolean",method, "bar", "Bar");
        Assertions.assertFalse((Boolean) result, "bar should not be > Bar");
        result = generator.invokeMethod("TestBoolean",method, "Foo", "bar");
        Assertions.assertTrue((Boolean) result, "Foo should be > bar");
        result = generator.invokeMethod("TestBoolean",method, "bar", "Foo");
        Assertions.assertFalse((Boolean) result, "bar should not be > Foo");
    }

    @TestAllImplementations
    public void testStringGeIgnoreCase(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.STRING),
                new Field("y", SimpleTypes.STRING)},
                SimpleTypes.BOOLEAN,
                new If(new BinaryComparison(Tests.GE_IgnoreCase, new GetValue("x", SimpleTypes.STRING),
                        new GetValue("y", SimpleTypes.STRING)),
                        new BooleanConstant(true), new BooleanConstant(false)));

        Object result = generator.invokeMethod("TestBoolean",method, "foo", "Foo");
        Assertions.assertTrue((Boolean) result, "foo should be >= Foo");
        result = generator.invokeMethod("TestBoolean",method, "bar", "Bar");
        Assertions.assertTrue((Boolean) result, "bar should be >= Bar");
        result = generator.invokeMethod("TestBoolean",method, "Foo", "bar");
        Assertions.assertTrue((Boolean) result, "Foo should be >= bar");
        result = generator.invokeMethod("TestBoolean",method, "bar", "Foo");
        Assertions.assertFalse((Boolean) result, "bar should not be >= Foo");
    }

    @TestAllImplementations
    public void testIfTrue(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.INT)},
                SimpleTypes.BOOLEAN,
                new If(new UnaryComparison(Tests.IsTrue, new GetValue("x", SimpleTypes.INT)),
                        new BooleanConstant(true), new BooleanConstant(false)));

        Object result = generator.invokeMethod("TestBoolean",method, 1);
        Assertions.assertTrue((Boolean) result, "1 should be true");
        result = generator.invokeMethod("TestBoolean",method, 0);
        Assertions.assertFalse((Boolean) result, "0 should be false");
    }

    @TestAllImplementations
    public void testIfFalse(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.INT)},
                SimpleTypes.BOOLEAN,
                new If(new UnaryComparison(Tests.IsFalse, new GetValue("x", SimpleTypes.INT)),
                        new BooleanConstant(true), new BooleanConstant(false)));

        Object result = generator.invokeMethod("TestBoolean",method, 1);
        Assertions.assertFalse((Boolean) result, "1 should be true");
        result = generator.invokeMethod("TestBoolean",method, 0);
        Assertions.assertTrue((Boolean) result, "0 should be false");
    }

    @TestAllImplementations
    public void testIsInstance(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", new ObjectType())},
                SimpleTypes.BOOLEAN,
                new If(new InstanceofComparison(Tests.IsTrue, new GetValue("x", new ObjectType()),
                        "java.lang.String"),
                        new BooleanConstant(true), new BooleanConstant(false)));

        Object result = generator.invokeMethod("TestBoolean",method, "Foo");
        Assertions.assertTrue((Boolean) result, "Foo should be an instance of string");
        result = generator.invokeMethod("TestBoolean",method, 1);
        Assertions.assertFalse((Boolean) result, "1 should not be an instance of java.lang.Integer");
    }

    @TestAllImplementations
    public void testIsNotInstance(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", new ObjectType())},
                SimpleTypes.BOOLEAN,
                new If(new InstanceofComparison(Tests.IsFalse, new GetValue("x", new ObjectType()),
                        "java.lang.String"),
                        new BooleanConstant(true), new BooleanConstant(false)));

        Object result = generator.invokeMethod("TestBoolean",method, "Foo");
        Assertions.assertFalse((Boolean) result, "Foo should be an instance of string");
        result = generator.invokeMethod("TestBoolean",method, 1);
        Assertions.assertTrue((Boolean) result, "1 should not be an instance of java.lang.Integer");
    }
}
