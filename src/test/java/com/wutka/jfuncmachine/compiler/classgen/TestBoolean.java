package com.wutka.jfuncmachine.compiler.classgen;

import com.wutka.jfuncmachine.compiler.model.Access;
import com.wutka.jfuncmachine.compiler.model.MethodDef;
import com.wutka.jfuncmachine.compiler.model.expr.GetValue;
import com.wutka.jfuncmachine.compiler.model.expr.If;
import com.wutka.jfuncmachine.compiler.model.expr.bool.And;
import com.wutka.jfuncmachine.compiler.model.expr.bool.BinaryComparison;
import com.wutka.jfuncmachine.compiler.model.expr.bool.Not;
import com.wutka.jfuncmachine.compiler.model.expr.bool.Or;
import com.wutka.jfuncmachine.compiler.model.expr.bool.tests.Tests;
import com.wutka.jfuncmachine.compiler.model.expr.constants.IntConstant;
import com.wutka.jfuncmachine.compiler.model.types.Field;
import com.wutka.jfuncmachine.compiler.model.types.ObjectType;
import com.wutka.jfuncmachine.compiler.model.types.SimpleTypes;
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
}
