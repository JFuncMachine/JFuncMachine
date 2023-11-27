package org.jfuncmachine.jfuncmachine.compiler.classgen;

import org.jfuncmachine.jfuncmachine.compiler.model.Access;
import org.jfuncmachine.jfuncmachine.compiler.model.MethodDef;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.GetValue;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.If;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.bool.*;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.bool.tests.Tests;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.constants.IntConstant;
import org.jfuncmachine.jfuncmachine.compiler.model.types.Field;
import org.jfuncmachine.jfuncmachine.compiler.model.types.ObjectType;
import org.jfuncmachine.jfuncmachine.compiler.model.types.SimpleTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestBoolean {
    @Test
    public void testIntEq() {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.INT),
                new Field("y", SimpleTypes.INT)},
                SimpleTypes.BOOLEAN,
                new If(new BinaryComparison(Tests.EQ, new GetValue("x", SimpleTypes.INT),
                        new GetValue("y", SimpleTypes.INT)),
                        new IntConstant(1), new IntConstant(0)));

        ClassGenerator generator = new ClassGenerator();
        Object result = generator.invokeMethod(method, 0, 0);
        Assertions.assertTrue((Boolean) result, "0 should equal 0");
        result = generator.invokeMethod(method, 5, 5);
        Assertions.assertTrue((Boolean) result, "5 should equal 5");
        result = generator.invokeMethod(method, 0, 5);
        Assertions.assertFalse((Boolean) result, "0 should not equal 5");
        result = generator.invokeMethod(method, 5, 0);
        Assertions.assertFalse((Boolean) result, "5 should not equal 0");
    }

    @Test
    public void testIntNe() {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.INT),
                new Field("y", SimpleTypes.INT)},
                SimpleTypes.BOOLEAN,
                new If(new BinaryComparison(Tests.NE, new GetValue("x", SimpleTypes.INT),
                        new GetValue("y", SimpleTypes.INT)),
                        new IntConstant(1), new IntConstant(0)));

        ClassGenerator generator = new ClassGenerator();
        Object result = generator.invokeMethod(method, 0, 0);
        Assertions.assertFalse((Boolean) result, "0 should equal 0");
        result = generator.invokeMethod(method, 5, 5);
        Assertions.assertFalse((Boolean) result, "5 should equal 5");
        result = generator.invokeMethod(method, 0, 5);
        Assertions.assertTrue((Boolean) result, "0 should not equal 5");
        result = generator.invokeMethod(method, 5, 0);
        Assertions.assertTrue((Boolean) result, "5 should not equal 0");

    }

    @Test
    public void testIntLt() {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.INT),
                new Field("y", SimpleTypes.INT)},
                SimpleTypes.BOOLEAN,
                new If(new BinaryComparison(Tests.LT, new GetValue("x", SimpleTypes.INT),
                        new GetValue("y", SimpleTypes.INT)),
                        new IntConstant(1), new IntConstant(0)));

        ClassGenerator generator = new ClassGenerator();
        Object result = generator.invokeMethod(method, 0, 0);
        Assertions.assertFalse((Boolean) result, "0 should not be < 0");
        result = generator.invokeMethod(method, 5, 5);
        Assertions.assertFalse((Boolean) result, "5 should not be < 5");
        result = generator.invokeMethod(method, 0, 5);
        Assertions.assertTrue((Boolean) result, "0 should be < 5");
        result = generator.invokeMethod(method, 5, 0);
        Assertions.assertFalse((Boolean) result, "5 should not be < 0");

    }

    @Test
    public void testIntLe() {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.INT),
                new Field("y", SimpleTypes.INT)},
                SimpleTypes.BOOLEAN,
                new If(new BinaryComparison(Tests.LE, new GetValue("x", SimpleTypes.INT),
                        new GetValue("y", SimpleTypes.INT)),
                        new IntConstant(1), new IntConstant(0)));

        ClassGenerator generator = new ClassGenerator();
        Object result = generator.invokeMethod(method, 0, 0);
        Assertions.assertTrue((Boolean) result, "0 should be <= 0");
        result = generator.invokeMethod(method, 5, 5);
        Assertions.assertTrue((Boolean) result, "5 should be <= 5");
        result = generator.invokeMethod(method, 0, 5);
        Assertions.assertTrue((Boolean) result, "0 should be <= 5");
        result = generator.invokeMethod(method, 5, 0);
        Assertions.assertFalse((Boolean) result, "5 should not be <= 0");

    }

    @Test
    public void testIntGt() {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.INT),
                new Field("y", SimpleTypes.INT)},
                SimpleTypes.BOOLEAN,
                new If(new BinaryComparison(Tests.GT, new GetValue("x", SimpleTypes.INT),
                        new GetValue("y", SimpleTypes.INT)),
                        new IntConstant(1), new IntConstant(0)));

        ClassGenerator generator = new ClassGenerator();
        Object result = generator.invokeMethod(method, 0, 0);
        Assertions.assertFalse((Boolean) result, "0 should not be > 0");
        result = generator.invokeMethod(method, 5, 5);
        Assertions.assertFalse((Boolean) result, "5 should not be > 5");
        result = generator.invokeMethod(method, 0, 5);
        Assertions.assertFalse((Boolean) result, "0 should not be > 5");
        result = generator.invokeMethod(method, 5, 0);
        Assertions.assertTrue((Boolean) result, "5 should be > 0");

    }

    @Test
    public void testIntGe() {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.INT),
                new Field("y", SimpleTypes.INT)},
                SimpleTypes.BOOLEAN,
                new If(new BinaryComparison(Tests.GE, new GetValue("x", SimpleTypes.INT),
                        new GetValue("y", SimpleTypes.INT)),
                        new IntConstant(1), new IntConstant(0)));

        ClassGenerator generator = new ClassGenerator();
        Object result = generator.invokeMethod(method, 0, 0);
        Assertions.assertTrue((Boolean) result, "0 should be >= 0");
        result = generator.invokeMethod(method, 5, 5);
        Assertions.assertTrue((Boolean) result, "5 should be >= 5");
        result = generator.invokeMethod(method, 0, 5);
        Assertions.assertFalse((Boolean) result, "0 should not be >= 5");
        result = generator.invokeMethod(method, 5, 0);
        Assertions.assertTrue((Boolean) result, "5 should be >= 0");

    }

    @Test
    public void testIntegerEq() {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.INT),
                new Field("y", new ObjectType(SimpleTypes.INT.getBoxType()))},
                SimpleTypes.BOOLEAN,
                new If(new BinaryComparison(Tests.EQ, new GetValue("x", SimpleTypes.INT),
                        new GetValue("y", new ObjectType(SimpleTypes.INT.getBoxType()))),
                        new IntConstant(1), new IntConstant(0)));

        ClassGenerator generator = new ClassGenerator();
        Object result = generator.invokeMethod(method, 0, 0);
        Assertions.assertTrue((Boolean) result, "0 should equal 0");
        result = generator.invokeMethod(method, 5, 5);
        Assertions.assertTrue((Boolean) result, "5 should equal 5");
        result = generator.invokeMethod(method, 0, 5);
        Assertions.assertFalse((Boolean) result, "0 should not equal 5");
        result = generator.invokeMethod(method, 5, 0);
        Assertions.assertFalse((Boolean) result, "5 should not equal 0");

    }

    @Test
    public void testIntegerNe() {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.INT),
                new Field("y", new ObjectType(SimpleTypes.INT.getBoxType()))},
                SimpleTypes.BOOLEAN,
                new If(new BinaryComparison(Tests.NE, new GetValue("x", SimpleTypes.INT),
                        new GetValue("y", new ObjectType(SimpleTypes.INT.getBoxType()))),
                        new IntConstant(1), new IntConstant(0)));

        ClassGenerator generator = new ClassGenerator();
        Object result = generator.invokeMethod(method, 0, 0);
        Assertions.assertFalse((Boolean) result, "0 should equal 0");
        result = generator.invokeMethod(method, 5, 5);
        Assertions.assertFalse((Boolean) result, "5 should equal 5");
        result = generator.invokeMethod(method, 0, 5);
        Assertions.assertTrue((Boolean) result, "0 should not equal 5");
        result = generator.invokeMethod(method, 5, 0);
        Assertions.assertTrue((Boolean) result, "5 should not equal 0");

    }

    @Test
    public void testIntegerLt() {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.INT),
                new Field("y", new ObjectType(SimpleTypes.INT.getBoxType()))},
                SimpleTypes.BOOLEAN,
                new If(new BinaryComparison(Tests.LT, new GetValue("x", SimpleTypes.INT),
                        new GetValue("y", new ObjectType(SimpleTypes.INT.getBoxType()))),
                        new IntConstant(1), new IntConstant(0)));

        ClassGenerator generator = new ClassGenerator();
        Object result = generator.invokeMethod(method, 0, 0);
        Assertions.assertFalse((Boolean) result, "0 should not be < 0");
        result = generator.invokeMethod(method, 5, 5);
        Assertions.assertFalse((Boolean) result, "5 should not be < 5");
        result = generator.invokeMethod(method, 0, 5);
        Assertions.assertTrue((Boolean) result, "0 should be < 5");
        result = generator.invokeMethod(method, 5, 0);
        Assertions.assertFalse((Boolean) result, "5 should not be < 0");

    }

    @Test
    public void testIntegerLe() {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.INT),
                new Field("y", new ObjectType(SimpleTypes.INT.getBoxType()))},
                SimpleTypes.BOOLEAN,
                new If(new BinaryComparison(Tests.LE, new GetValue("x", SimpleTypes.INT),
                        new GetValue("y", new ObjectType(SimpleTypes.INT.getBoxType()))),
                        new IntConstant(1), new IntConstant(0)));

        ClassGenerator generator = new ClassGenerator();
        Object result = generator.invokeMethod(method, 0, 0);
        Assertions.assertTrue((Boolean) result, "0 should be <= 0");
        result = generator.invokeMethod(method, 5, 5);
        Assertions.assertTrue((Boolean) result, "5 should be <= 5");
        result = generator.invokeMethod(method, 0, 5);
        Assertions.assertTrue((Boolean) result, "0 should be <= 5");
        result = generator.invokeMethod(method, 5, 0);
        Assertions.assertFalse((Boolean) result, "5 should not be <= 0");

    }

    @Test
    public void testIntegerGt() {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.INT),
                new Field("y", new ObjectType(SimpleTypes.INT.getBoxType()))},
                SimpleTypes.BOOLEAN,
                new If(new BinaryComparison(Tests.GT, new GetValue("x", SimpleTypes.INT),
                        new GetValue("y", new ObjectType(SimpleTypes.INT.getBoxType()))),
                        new IntConstant(1), new IntConstant(0)));

        ClassGenerator generator = new ClassGenerator();
        Object result = generator.invokeMethod(method, 0, 0);
        Assertions.assertFalse((Boolean) result, "0 should not be > 0");
        result = generator.invokeMethod(method, 5, 5);
        Assertions.assertFalse((Boolean) result, "5 should not be > 5");
        result = generator.invokeMethod(method, 0, 5);
        Assertions.assertFalse((Boolean) result, "0 should not be > 5");
        result = generator.invokeMethod(method, 5, 0);
        Assertions.assertTrue((Boolean) result, "5 should be > 0");

    }

    @Test
    public void testIntegerGe() {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.INT),
                new Field("y", new ObjectType(SimpleTypes.INT.getBoxType()))},
                SimpleTypes.BOOLEAN,
                new If(new BinaryComparison(Tests.GE, new GetValue("x", SimpleTypes.INT),
                        new GetValue("y", new ObjectType(SimpleTypes.INT.getBoxType()))),
                        new IntConstant(1), new IntConstant(0)));

        ClassGenerator generator = new ClassGenerator();
        Object result = generator.invokeMethod(method, 0, 0);
        Assertions.assertTrue((Boolean) result, "0 should be >= 0");
        result = generator.invokeMethod(method, 5, 5);
        Assertions.assertTrue((Boolean) result, "5 should be >= 5");
        result = generator.invokeMethod(method, 0, 5);
        Assertions.assertFalse((Boolean) result, "0 should not be >= 5");
        result = generator.invokeMethod(method, 5, 0);
        Assertions.assertTrue((Boolean) result, "5 should be >= 0");

    }

    @Test
    public void testDoubleEq() {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.DOUBLE),
                new Field("y", SimpleTypes.DOUBLE)},
                SimpleTypes.BOOLEAN,
                new If(new BinaryComparison(Tests.EQ, new GetValue("x", SimpleTypes.DOUBLE),
                        new GetValue("y", SimpleTypes.DOUBLE)),
                        new IntConstant(1), new IntConstant(0)));

        ClassGenerator generator = new ClassGenerator();
        Object result = generator.invokeMethod(method, 0.0, 0.0);
        Assertions.assertTrue((Boolean) result, "0 should equal 0");
        result = generator.invokeMethod(method, 5.0, 5.0);
        Assertions.assertTrue((Boolean) result, "5 should equal 5");
        result = generator.invokeMethod(method, 0.0, 5.0);
        Assertions.assertFalse((Boolean) result, "0 should not equal 5");
        result = generator.invokeMethod(method, 5.0, 0.0);
        Assertions.assertFalse((Boolean) result, "5 should not equal 0");

    }

    @Test
    public void testDoubleNe() {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.DOUBLE),
                new Field("y", SimpleTypes.DOUBLE)},
                SimpleTypes.BOOLEAN,
                new If(new BinaryComparison(Tests.NE, new GetValue("x", SimpleTypes.DOUBLE),
                        new GetValue("y", SimpleTypes.DOUBLE)),
                        new IntConstant(1), new IntConstant(0)));

        ClassGenerator generator = new ClassGenerator();
        Object result = generator.invokeMethod(method, 0.0, 0.0);
        Assertions.assertFalse((Boolean) result, "0 should equal 0");
        result = generator.invokeMethod(method, 5.0, 5.0);
        Assertions.assertFalse((Boolean) result, "5 should equal 5");
        result = generator.invokeMethod(method, 0.0, 5.0);
        Assertions.assertTrue((Boolean) result, "0 should not equal 5");
        result = generator.invokeMethod(method, 5.0, 0.0);
        Assertions.assertTrue((Boolean) result, "5 should not equal 0");

    }

    @Test
    public void testDoubleLt() {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.DOUBLE),
                new Field("y", SimpleTypes.DOUBLE)},
                SimpleTypes.BOOLEAN,
                new If(new BinaryComparison(Tests.LT, new GetValue("x", SimpleTypes.DOUBLE),
                        new GetValue("y", SimpleTypes.DOUBLE)),
                        new IntConstant(1), new IntConstant(0)));

        ClassGenerator generator = new ClassGenerator();
        Object result = generator.invokeMethod(method, 0.0, 0.0);
        Assertions.assertFalse((Boolean) result, "0 should not be < 0");
        result = generator.invokeMethod(method, 5.0, 5.0);
        Assertions.assertFalse((Boolean) result, "5 should not be < 5");
        result = generator.invokeMethod(method, 0.0, 5.0);
        Assertions.assertTrue((Boolean) result, "0 should be < 5");
        result = generator.invokeMethod(method, 5.0, 0.0);
        Assertions.assertFalse((Boolean) result, "5 should not be < 0");

    }

    @Test
    public void testDoubleLe() {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.DOUBLE),
                new Field("y", SimpleTypes.DOUBLE)},
                SimpleTypes.BOOLEAN,
                new If(new BinaryComparison(Tests.LE, new GetValue("x", SimpleTypes.DOUBLE),
                        new GetValue("y", SimpleTypes.DOUBLE)),
                        new IntConstant(1), new IntConstant(0)));

        ClassGenerator generator = new ClassGenerator();
        Object result = generator.invokeMethod(method, 0.0, 0.0);
        Assertions.assertTrue((Boolean) result, "0 should be <= 0");
        result = generator.invokeMethod(method, 5.0, 5.0);
        Assertions.assertTrue((Boolean) result, "5 should be <= 5");
        result = generator.invokeMethod(method, 0.0, 5.0);
        Assertions.assertTrue((Boolean) result, "0 should be <= 5");
        result = generator.invokeMethod(method, 5.0, 0.0);
        Assertions.assertFalse((Boolean) result, "5 should not be <= 0");

    }

    @Test
    public void testDoubleGt() {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.DOUBLE),
                new Field("y", SimpleTypes.DOUBLE)},
                SimpleTypes.BOOLEAN,
                new If(new BinaryComparison(Tests.GT, new GetValue("x", SimpleTypes.DOUBLE),
                        new GetValue("y", SimpleTypes.DOUBLE)),
                        new IntConstant(1), new IntConstant(0)));

        ClassGenerator generator = new ClassGenerator();
        Object result = generator.invokeMethod(method, 0.0, 0.0);
        Assertions.assertFalse((Boolean) result, "0 should not be > 0");
        result = generator.invokeMethod(method, 5.0, 5.0);
        Assertions.assertFalse((Boolean) result, "5 should not be > 5");
        result = generator.invokeMethod(method, 0.0, 5.0);
        Assertions.assertFalse((Boolean) result, "0 should not be > 5");
        result = generator.invokeMethod(method, 5.0, 0.0);
        Assertions.assertTrue((Boolean) result, "5 should be > 0");

    }

    @Test
    public void testDoubleGe() {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.DOUBLE),
                new Field("y", SimpleTypes.DOUBLE)},
                SimpleTypes.BOOLEAN,
                new If(new BinaryComparison(Tests.GE, new GetValue("x", SimpleTypes.DOUBLE),
                        new GetValue("y", SimpleTypes.DOUBLE)),
                        new IntConstant(1), new IntConstant(0)));

        ClassGenerator generator = new ClassGenerator();
        Object result = generator.invokeMethod(method, 0.0, 0.0);
        Assertions.assertTrue((Boolean) result, "0 should be >= 0");
        result = generator.invokeMethod(method, 5.0, 5.0);
        Assertions.assertTrue((Boolean) result, "5 should be >= 5");
        result = generator.invokeMethod(method, 0.0, 5.0);
        Assertions.assertFalse((Boolean) result, "0 should not be >= 5");
        result = generator.invokeMethod(method, 5.0, 0.0);
        Assertions.assertTrue((Boolean) result, "5 should be >= 0");

    }

    @Test
    public void testLangDoubleEq() {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", new ObjectType(SimpleTypes.DOUBLE.getBoxType())),
                new Field("y", SimpleTypes.DOUBLE)},
                SimpleTypes.BOOLEAN,
                new If(new BinaryComparison(Tests.EQ, new GetValue("x", new ObjectType(SimpleTypes.DOUBLE.getBoxType())),
                        new GetValue("y", SimpleTypes.DOUBLE)),
                        new IntConstant(1), new IntConstant(0)));

        ClassGenerator generator = new ClassGenerator();
        Object result = generator.invokeMethod(method, 0.0, 0.0);
        Assertions.assertTrue((Boolean) result, "0 should equal 0");
        result = generator.invokeMethod(method, 5.0, 5.0);
        Assertions.assertTrue((Boolean) result, "5 should equal 5");
        result = generator.invokeMethod(method, 0.0, 5.0);
        Assertions.assertFalse((Boolean) result, "0 should not equal 5");
        result = generator.invokeMethod(method, 5.0, 0.0);
        Assertions.assertFalse((Boolean) result, "5 should not equal 0");

    }

    @Test
    public void testFloatEq() {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.FLOAT),
                new Field("y", SimpleTypes.FLOAT)},
                SimpleTypes.BOOLEAN,
                new If(new BinaryComparison(Tests.EQ, new GetValue("x", SimpleTypes.FLOAT),
                        new GetValue("y", SimpleTypes.FLOAT)),
                        new IntConstant(1), new IntConstant(0)));

        ClassGenerator generator = new ClassGenerator();
        Object result = generator.invokeMethod(method, (float) 0.0, (float) 0.0);
        Assertions.assertTrue((Boolean) result, "0 should equal 0");
        result = generator.invokeMethod(method, (float) 5.0, (float) 5.0);
        Assertions.assertTrue((Boolean) result, "5 should equal 5");
        result = generator.invokeMethod(method, (float) 0.0, (float) 5.0);
        Assertions.assertFalse((Boolean) result, "0 should not equal 5");
        result = generator.invokeMethod(method, (float) 5.0, (float) 0.0);
        Assertions.assertFalse((Boolean) result, "5 should not equal 0");

    }

    @Test
    public void testLangFloatEq() {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", new ObjectType(SimpleTypes.FLOAT.getBoxType())),
                new Field("y", SimpleTypes.FLOAT)},
                SimpleTypes.BOOLEAN,
                new If(new BinaryComparison(Tests.EQ, new GetValue("x", new ObjectType(SimpleTypes.FLOAT.getBoxType())),
                        new GetValue("y", SimpleTypes.FLOAT)),
                        new IntConstant(1), new IntConstant(0)));

        ClassGenerator generator = new ClassGenerator();
        Object result = generator.invokeMethod(method, (float) 0.0, (float) 0.0);
        Assertions.assertTrue((Boolean) result, "0 should equal 0");
        result = generator.invokeMethod(method, (float) 5.0, (float) 5.0);
        Assertions.assertTrue((Boolean) result, "5 should equal 5");
        result = generator.invokeMethod(method, (float) 0.0, (float) 5.0);
        Assertions.assertFalse((Boolean) result, "0 should not equal 5");
        result = generator.invokeMethod(method, (float) 5.0, (float) 0.0);
        Assertions.assertFalse((Boolean) result, "5 should not equal 0");

    }

    @Test
    public void testLongEq() {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.LONG),
                new Field("y", SimpleTypes.LONG)},
                SimpleTypes.BOOLEAN,
                new If(new BinaryComparison(Tests.EQ, new GetValue("x", SimpleTypes.LONG),
                        new GetValue("y", SimpleTypes.LONG)),
                        new IntConstant(1), new IntConstant(0)));

        ClassGenerator generator = new ClassGenerator();
        Object result = generator.invokeMethod(method,  0l, 0l);
        Assertions.assertTrue((Boolean) result, "0 should equal 0");
        result = generator.invokeMethod(method,  5l, 5l);
        Assertions.assertTrue((Boolean) result, "5 should equal 5");
        result = generator.invokeMethod(method, 0l, 5l);
        Assertions.assertFalse((Boolean) result, "0 should not equal 5");
        result = generator.invokeMethod(method, 5l, 0l);
        Assertions.assertFalse((Boolean) result, "5 should not equal 0");

    }

    @Test
    public void testLangLongEq() {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", new ObjectType(SimpleTypes.LONG.getBoxType())),
                new Field("y", SimpleTypes.LONG)},
                SimpleTypes.BOOLEAN,
                new If(new BinaryComparison(Tests.EQ, new GetValue("x", new ObjectType(SimpleTypes.LONG.getBoxType())),
                        new GetValue("y", SimpleTypes.LONG)),
                        new IntConstant(1), new IntConstant(0)));

        ClassGenerator generator = new ClassGenerator();
        Object result = generator.invokeMethod(method, 0l, 0l);
        Assertions.assertTrue((Boolean) result, "0 should equal 0");
        result = generator.invokeMethod(method, 5l, 5l);
        Assertions.assertTrue((Boolean) result, "5 should equal 5");
        result = generator.invokeMethod(method, 0l, 5l);
        Assertions.assertFalse((Boolean) result, "0 should not equal 5");
        result = generator.invokeMethod(method, 5l, 0l);
        Assertions.assertFalse((Boolean) result, "5 should not equal 0");

    }

    @Test
    public void testAnd() {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.INT),
                new Field("y", SimpleTypes.INT)},
                SimpleTypes.BOOLEAN,
                new If(new And(
                        new BinaryComparison(Tests.EQ, new GetValue("x", SimpleTypes.INT),
                            new GetValue("y", SimpleTypes.INT)),
                        new BinaryComparison(Tests.EQ, new GetValue("y", SimpleTypes.INT),
                        new GetValue("x", SimpleTypes.INT))),
                        new IntConstant(1), new IntConstant(0)));

        ClassGenerator generator = new ClassGenerator();
        Object result = generator.invokeMethod(method, 0, 0);
        Assertions.assertTrue((Boolean) result, "0 should equal 0");
        result = generator.invokeMethod(method, 5, 5);
        Assertions.assertTrue((Boolean) result, "5 should equal 5");
        result = generator.invokeMethod(method, 0, 5);
        Assertions.assertFalse((Boolean) result, "0 should not equal 5");
        result = generator.invokeMethod(method, 5, 0);
        Assertions.assertFalse((Boolean) result, "5 should not equal 0");

    }

    @Test
    public void testOr() {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.INT),
                new Field("y", SimpleTypes.INT)},
                SimpleTypes.BOOLEAN,
                new If(new Or(
                        new BinaryComparison(Tests.EQ, new GetValue("x", SimpleTypes.INT),
                                new GetValue("y", SimpleTypes.INT)),
                        new BinaryComparison(Tests.NE, new GetValue("y", SimpleTypes.INT),
                                new GetValue("x", SimpleTypes.INT))),
                        new IntConstant(1), new IntConstant(0)));

        ClassGenerator generator = new ClassGenerator();
        Object result = generator.invokeMethod(method, 0, 0);
        Assertions.assertTrue((Boolean) result, "0 should equal 0");
        result = generator.invokeMethod(method, 5, 5);
        Assertions.assertTrue((Boolean) result, "5 should equal 5");
        result = generator.invokeMethod(method, 0, 5);
        Assertions.assertTrue((Boolean) result, "0 should not equal 5");
        result = generator.invokeMethod(method, 5, 0);
        Assertions.assertTrue((Boolean) result, "5 should not equal 0");
    }

    @Test
    public void testNot() {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.INT),
                new Field("y", SimpleTypes.INT)},
                SimpleTypes.BOOLEAN,
                new If(new Not(new BinaryComparison(Tests.NE, new GetValue("x", SimpleTypes.INT),
                        new GetValue("y", SimpleTypes.INT))),
                        new IntConstant(1), new IntConstant(0)));

        ClassGenerator generator = new ClassGenerator();
        Object result = generator.invokeMethod(method, 0, 0);
        Assertions.assertTrue((Boolean) result, "0 should equal 0");
        result = generator.invokeMethod(method, 5, 5);
        Assertions.assertTrue((Boolean) result, "5 should equal 5");
        result = generator.invokeMethod(method, 0, 5);
        Assertions.assertFalse((Boolean) result, "0 should not equal 5");
        result = generator.invokeMethod(method, 5, 0);
        Assertions.assertFalse((Boolean) result, "5 should not equal 0");

    }

    @Test
    public void testOrNot() {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.INT),
                new Field("y", SimpleTypes.INT)},
                SimpleTypes.BOOLEAN,
                new If(new Or(
                        new BinaryComparison(Tests.EQ, new GetValue("x", SimpleTypes.INT),
                                new GetValue("y", SimpleTypes.INT)),
                        new Not(new BinaryComparison(Tests.EQ, new GetValue("y", SimpleTypes.INT),
                                new GetValue("x", SimpleTypes.INT)))),
                        new IntConstant(1), new IntConstant(0)));

        ClassGenerator generator = new ClassGenerator();
        Object result = generator.invokeMethod(method, 0, 0);
        Assertions.assertTrue((Boolean) result, "0 should equal 0");
        result = generator.invokeMethod(method, 5, 5);
        Assertions.assertTrue((Boolean) result, "5 should equal 5");
        result = generator.invokeMethod(method, 0, 5);
        Assertions.assertTrue((Boolean) result, "0 should not equal 5");
        result = generator.invokeMethod(method, 5, 0);
        Assertions.assertTrue((Boolean) result, "5 should not equal 0");
    }

    @Test
    public void testAndNot() {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.INT),
                new Field("y", SimpleTypes.INT)},
                SimpleTypes.BOOLEAN,
                new If(new And(
                        new BinaryComparison(Tests.EQ, new GetValue("x", SimpleTypes.INT),
                                new GetValue("y", SimpleTypes.INT)),
                        new Not(new BinaryComparison(Tests.NE, new GetValue("y", SimpleTypes.INT),
                                new GetValue("x", SimpleTypes.INT)))),
                        new IntConstant(1), new IntConstant(0)));

        ClassGenerator generator = new ClassGenerator();
        Object result = generator.invokeMethod(method, 0, 0);
        Assertions.assertTrue((Boolean) result, "0 should equal 0");
        result = generator.invokeMethod(method, 5, 5);
        Assertions.assertTrue((Boolean) result, "5 should equal 5");
        result = generator.invokeMethod(method, 0, 5);
        Assertions.assertFalse((Boolean) result, "0 should not equal 5");
        result = generator.invokeMethod(method, 5, 0);
        Assertions.assertFalse((Boolean) result, "5 should not equal 0");

    }

    @Test
    public void testIsNull() {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.STRING)},
                SimpleTypes.BOOLEAN,
                new If(new UnaryComparison(Tests.IsNull, new GetValue("x", SimpleTypes.STRING)),
                        new IntConstant(1), new IntConstant(0)));

        ClassGenerator generator = new ClassGenerator();
        Object result = generator.invokeMethod(method, "Foobar");
        Assertions.assertFalse((Boolean) result, "String is not null");
        result = generator.invokeMethod(method, (Object) null);
        Assertions.assertTrue((Boolean) result, "String is null");
    }

    @Test
    public void testIsNotNull() {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.STRING)},
                SimpleTypes.BOOLEAN,
                new If(new UnaryComparison(Tests.IsNotNull, new GetValue("x", SimpleTypes.STRING)),
                        new IntConstant(1), new IntConstant(0)));

        ClassGenerator generator = new ClassGenerator();
        Object result = generator.invokeMethod(method, "Foobar");
        Assertions.assertTrue((Boolean) result, "String is not null");
        result = generator.invokeMethod(method, (Object) null);
        Assertions.assertFalse((Boolean) result, "String is null");
    }

    @Test
    public void testStringEq() {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.STRING),
                new Field("y", SimpleTypes.STRING)},
                SimpleTypes.BOOLEAN,
                new If(new BinaryComparison(Tests.EQ, new GetValue("x", SimpleTypes.STRING),
                        new GetValue("y", SimpleTypes.STRING)),
                        new IntConstant(1), new IntConstant(0)));

        ClassGenerator generator = new ClassGenerator();
        Object result = generator.invokeMethod(method, "foo", "foo");
        Assertions.assertTrue((Boolean) result, "foo should equal foo");
        result = generator.invokeMethod(method, "bar", "bar");
        Assertions.assertTrue((Boolean) result, "bar should equal bar");
        result = generator.invokeMethod(method, "foo", "bar");
        Assertions.assertFalse((Boolean) result, "foo should not equal bar");
        result = generator.invokeMethod(method, "bar", "foo");
        Assertions.assertFalse((Boolean) result, "bar should not equal foo");
    }

    @Test
    public void testStringNe() {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.STRING),
                new Field("y", SimpleTypes.STRING)},
                SimpleTypes.BOOLEAN,
                new If(new BinaryComparison(Tests.NE, new GetValue("x", SimpleTypes.STRING),
                        new GetValue("y", SimpleTypes.STRING)),
                        new IntConstant(1), new IntConstant(0)));

        ClassGenerator generator = new ClassGenerator();
        Object result = generator.invokeMethod(method, "foo", "foo");
        Assertions.assertFalse((Boolean) result, "foo should equal foo");
        result = generator.invokeMethod(method, "bar", "bar");
        Assertions.assertFalse((Boolean) result, "bar should equal bar");
        result = generator.invokeMethod(method, "foo", "bar");
        Assertions.assertTrue((Boolean) result, "foo should not equal bar");
        result = generator.invokeMethod(method, "bar", "foo");
        Assertions.assertTrue((Boolean) result, "bar should not equal foo");
    }

    @Test
    public void testStringLt() {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.STRING),
                new Field("y", SimpleTypes.STRING)},
                SimpleTypes.BOOLEAN,
                new If(new BinaryComparison(Tests.LT, new GetValue("x", SimpleTypes.STRING),
                        new GetValue("y", SimpleTypes.STRING)),
                        new IntConstant(1), new IntConstant(0)));

        ClassGenerator generator = new ClassGenerator();
        Object result = generator.invokeMethod(method, "foo", "foo");
        Assertions.assertFalse((Boolean) result, "foo should not be < foo");
        result = generator.invokeMethod(method, "bar", "bar");
        Assertions.assertFalse((Boolean) result, "bar should not be < bar");
        result = generator.invokeMethod(method, "foo", "bar");
        Assertions.assertFalse((Boolean) result, "foo should not be < bar");
        result = generator.invokeMethod(method, "bar", "foo");
        Assertions.assertTrue((Boolean) result, "bar should be < foo");
    }

    @Test
    public void testStringLe() {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.STRING),
                new Field("y", SimpleTypes.STRING)},
                SimpleTypes.BOOLEAN,
                new If(new BinaryComparison(Tests.LE, new GetValue("x", SimpleTypes.STRING),
                        new GetValue("y", SimpleTypes.STRING)),
                        new IntConstant(1), new IntConstant(0)));

        ClassGenerator generator = new ClassGenerator();
        Object result = generator.invokeMethod(method, "foo", "foo");
        Assertions.assertTrue((Boolean) result, "foo should be <= foo");
        result = generator.invokeMethod(method, "bar", "bar");
        Assertions.assertTrue((Boolean) result, "bar should be <= bar");
        result = generator.invokeMethod(method, "foo", "bar");
        Assertions.assertFalse((Boolean) result, "foo should not be <= bar");
        result = generator.invokeMethod(method, "bar", "foo");
        Assertions.assertTrue((Boolean) result, "bar should be <= foo");
    }

    @Test
    public void testStringGt() {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.STRING),
                new Field("y", SimpleTypes.STRING)},
                SimpleTypes.BOOLEAN,
                new If(new BinaryComparison(Tests.GT, new GetValue("x", SimpleTypes.STRING),
                        new GetValue("y", SimpleTypes.STRING)),
                        new IntConstant(1), new IntConstant(0)));

        ClassGenerator generator = new ClassGenerator();
        Object result = generator.invokeMethod(method, "foo", "foo");
        Assertions.assertFalse((Boolean) result, "foo should not be > foo");
        result = generator.invokeMethod(method, "bar", "bar");
        Assertions.assertFalse((Boolean) result, "bar should not be > bar");
        result = generator.invokeMethod(method, "foo", "bar");
        Assertions.assertTrue((Boolean) result, "foo should be > bar");
        result = generator.invokeMethod(method, "bar", "foo");
        Assertions.assertFalse((Boolean) result, "bar should not be > foo");
    }

    @Test
    public void testStringGe() {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.STRING),
                new Field("y", SimpleTypes.STRING)},
                SimpleTypes.BOOLEAN,
                new If(new BinaryComparison(Tests.GE, new GetValue("x", SimpleTypes.STRING),
                        new GetValue("y", SimpleTypes.STRING)),
                        new IntConstant(1), new IntConstant(0)));

        ClassGenerator generator = new ClassGenerator();
        Object result = generator.invokeMethod(method, "foo", "foo");
        Assertions.assertTrue((Boolean) result, "foo should be >= foo");
        result = generator.invokeMethod(method, "bar", "bar");
        Assertions.assertTrue((Boolean) result, "bar should be >= bar");
        result = generator.invokeMethod(method, "foo", "bar");
        Assertions.assertTrue((Boolean) result, "foo should be >= bar");
        result = generator.invokeMethod(method, "bar", "foo");
        Assertions.assertFalse((Boolean) result, "bar should not be >= foo");
    }

    @Test
    public void testStringEqIgnoreCase() {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.STRING),
                new Field("y", SimpleTypes.STRING)},
                SimpleTypes.BOOLEAN,
                new If(new BinaryComparison(Tests.EQ_IgnoreCase, new GetValue("x", SimpleTypes.STRING),
                        new GetValue("y", SimpleTypes.STRING)),
                        new IntConstant(1), new IntConstant(0)));

        ClassGenerator generator = new ClassGenerator();
        Object result = generator.invokeMethod(method, "foo", "Foo");
        Assertions.assertTrue((Boolean) result, "foo should equal Foo");
        result = generator.invokeMethod(method, "bar", "Bar");
        Assertions.assertTrue((Boolean) result, "bar should equal Bar");
        result = generator.invokeMethod(method, "foo", "Bar");
        Assertions.assertFalse((Boolean) result, "foo should not equal Bar");
        result = generator.invokeMethod(method, "bar", "Foo");
        Assertions.assertFalse((Boolean) result, "bar should not equal Foo");
    }

    @Test
    public void testStringNeIgnoreCase() {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.STRING),
                new Field("y", SimpleTypes.STRING)},
                SimpleTypes.BOOLEAN,
                new If(new BinaryComparison(Tests.NE_IgnoreCase, new GetValue("x", SimpleTypes.STRING),
                        new GetValue("y", SimpleTypes.STRING)),
                        new IntConstant(1), new IntConstant(0)));

        ClassGenerator generator = new ClassGenerator();
        Object result = generator.invokeMethod(method, "foo", "Foo");
        Assertions.assertFalse((Boolean) result, "foo should equal Foo");
        result = generator.invokeMethod(method, "bar", "Bar");
        Assertions.assertFalse((Boolean) result, "bar should equal Bar");
        result = generator.invokeMethod(method, "foo", "Bar");
        Assertions.assertTrue((Boolean) result, "foo should not equal Bar");
        result = generator.invokeMethod(method, "bar", "Foo");
        Assertions.assertTrue((Boolean) result, "bar should not equal Foo");
    }

    @Test
    public void testStringLtIgnoreCase() {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.STRING),
                new Field("y", SimpleTypes.STRING)},
                SimpleTypes.BOOLEAN,
                new If(new BinaryComparison(Tests.LT_IgnoreCase, new GetValue("x", SimpleTypes.STRING),
                        new GetValue("y", SimpleTypes.STRING)),
                        new IntConstant(1), new IntConstant(0)));

        ClassGenerator generator = new ClassGenerator();
        Object result = generator.invokeMethod(method, "foo", "Foo");
        Assertions.assertFalse((Boolean) result, "foo should not be < Foo");
        result = generator.invokeMethod(method, "bar", "Bar");
        Assertions.assertFalse((Boolean) result, "bar should not be < Bar");
        result = generator.invokeMethod(method, "Foo", "bar");
        Assertions.assertFalse((Boolean) result, "Foo should not be < bar");
        result = generator.invokeMethod(method, "bar", "Foo");
        Assertions.assertTrue((Boolean) result, "bar should be < foo");
    }

    @Test
    public void testStringLeIgnoreCase() {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.STRING),
                new Field("y", SimpleTypes.STRING)},
                SimpleTypes.BOOLEAN,
                new If(new BinaryComparison(Tests.LE_IgnoreCase, new GetValue("x", SimpleTypes.STRING),
                        new GetValue("y", SimpleTypes.STRING)),
                        new IntConstant(1), new IntConstant(0)));

        ClassGenerator generator = new ClassGenerator();
        Object result = generator.invokeMethod(method, "foo", "Foo");
        Assertions.assertTrue((Boolean) result, "foo should be <= Foo");
        result = generator.invokeMethod(method, "bar", "Bar");
        Assertions.assertTrue((Boolean) result, "bar should be <= Bar");
        result = generator.invokeMethod(method, "Foo", "bar");
        Assertions.assertFalse((Boolean) result, "Foo should not be <= bar");
        result = generator.invokeMethod(method, "bar", "Foo");
        Assertions.assertTrue((Boolean) result, "bar should be <= Foo");
    }

    @Test
    public void testStringGtIgnoreCase() {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.STRING),
                new Field("y", SimpleTypes.STRING)},
                SimpleTypes.BOOLEAN,
                new If(new BinaryComparison(Tests.GT_IgnoreCase, new GetValue("x", SimpleTypes.STRING),
                        new GetValue("y", SimpleTypes.STRING)),
                        new IntConstant(1), new IntConstant(0)));

        ClassGenerator generator = new ClassGenerator();
        Object result = generator.invokeMethod(method, "foo", "Foo");
        Assertions.assertFalse((Boolean) result, "foo should not be > Foo");
        result = generator.invokeMethod(method, "bar", "Bar");
        Assertions.assertFalse((Boolean) result, "bar should not be > Bar");
        result = generator.invokeMethod(method, "Foo", "bar");
        Assertions.assertTrue((Boolean) result, "Foo should be > bar");
        result = generator.invokeMethod(method, "bar", "Foo");
        Assertions.assertFalse((Boolean) result, "bar should not be > Foo");
    }

    @Test
    public void testStringGeIgnoreCase() {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.STRING),
                new Field("y", SimpleTypes.STRING)},
                SimpleTypes.BOOLEAN,
                new If(new BinaryComparison(Tests.GE_IgnoreCase, new GetValue("x", SimpleTypes.STRING),
                        new GetValue("y", SimpleTypes.STRING)),
                        new IntConstant(1), new IntConstant(0)));

        ClassGenerator generator = new ClassGenerator();
        Object result = generator.invokeMethod(method, "foo", "Foo");
        Assertions.assertTrue((Boolean) result, "foo should be >= Foo");
        result = generator.invokeMethod(method, "bar", "Bar");
        Assertions.assertTrue((Boolean) result, "bar should be >= Bar");
        result = generator.invokeMethod(method, "Foo", "bar");
        Assertions.assertTrue((Boolean) result, "Foo should be >= bar");
        result = generator.invokeMethod(method, "bar", "Foo");
        Assertions.assertFalse((Boolean) result, "bar should not be >= Foo");
    }

    @Test
    public void testIfTrue() {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.INT)},
                SimpleTypes.BOOLEAN,
                new If(new UnaryComparison(Tests.IsTrue, new GetValue("x", SimpleTypes.INT)),
                        new IntConstant(1), new IntConstant(0)));

        ClassGenerator generator = new ClassGenerator();
        Object result = generator.invokeMethod(method, 1);
        Assertions.assertTrue((Boolean) result, "1 should be true");
        result = generator.invokeMethod(method, 0);
        Assertions.assertFalse((Boolean) result, "0 should be false");
    }

    @Test
    public void testIfFalse() {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.INT)},
                SimpleTypes.BOOLEAN,
                new If(new UnaryComparison(Tests.IsFalse, new GetValue("x", SimpleTypes.INT)),
                        new IntConstant(1), new IntConstant(0)));

        ClassGenerator generator = new ClassGenerator();
        Object result = generator.invokeMethod(method, 1);
        Assertions.assertFalse((Boolean) result, "1 should be true");
        result = generator.invokeMethod(method, 0);
        Assertions.assertTrue((Boolean) result, "0 should be false");
    }

    @Test
    public void testIsInstance() {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", new ObjectType())},
                SimpleTypes.BOOLEAN,
                new If(new InstanceofComparison(Tests.IsTrue, new GetValue("x", new ObjectType()),
                        "java.lang.String"),
                        new IntConstant(1), new IntConstant(0)));

        ClassGenerator generator = new ClassGenerator();
        Object result = generator.invokeMethod(method, "Foo");
        Assertions.assertTrue((Boolean) result, "Foo should be an instance of string");
        result = generator.invokeMethod(method, 1);
        Assertions.assertFalse((Boolean) result, "1 should not be an instance of java.lang.Integer");
    }

    @Test
    public void testIsNotInstance() {
        MethodDef method = new MethodDef("iftest", Access.PUBLIC, new Field[] {
                new Field("x", new ObjectType())},
                SimpleTypes.BOOLEAN,
                new If(new InstanceofComparison(Tests.IsFalse, new GetValue("x", new ObjectType()),
                        "java.lang.String"),
                        new IntConstant(1), new IntConstant(0)));

        ClassGenerator generator = new ClassGenerator();
        Object result = generator.invokeMethod(method, "Foo");
        Assertions.assertFalse((Boolean) result, "Foo should be an instance of string");
        result = generator.invokeMethod(method, 1);
        Assertions.assertTrue((Boolean) result, "1 should not be an instance of java.lang.Integer");
    }
}
