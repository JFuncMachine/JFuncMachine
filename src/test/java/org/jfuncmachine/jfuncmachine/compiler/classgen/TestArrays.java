package org.jfuncmachine.jfuncmachine.compiler.classgen;

import org.jfuncmachine.jfuncmachine.compiler.model.Access;
import org.jfuncmachine.jfuncmachine.compiler.model.MethodDef;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.ArrayGet;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.ArraySet;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.GetValue;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.If;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.bool.*;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.bool.tests.Tests;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.constants.BooleanConstant;
import org.jfuncmachine.jfuncmachine.compiler.model.types.ArrayType;
import org.jfuncmachine.jfuncmachine.compiler.model.types.Field;
import org.jfuncmachine.jfuncmachine.compiler.model.types.ObjectType;
import org.jfuncmachine.jfuncmachine.compiler.model.types.SimpleTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class TestArrays {
    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(ClassGeneratorProvider.class)
    @Retention(RetentionPolicy.RUNTIME)
    private @interface TestAllImplementations {}

    @TestAllImplementations
    public void testArrayGetByte(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("arraytest", Access.PUBLIC, new Field[] {
                new Field("arr", new ArrayType(SimpleTypes.BYTE)),
                new Field("i", SimpleTypes.INT)},
                SimpleTypes.BYTE,
                new ArrayGet(new GetValue("arr", new ArrayType(SimpleTypes.BYTE)),
                             new GetValue("i", SimpleTypes.INT)));

        byte[] bytes = new byte[] { 1, 2, 5, 3 };
        Object result = generator.invokeMethod("TestArray",method, bytes, 2);
        Assertions.assertEquals((byte) 5, (Byte) result);
    }

    @TestAllImplementations
    public void testArrayGetBoolean(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("arraytest", Access.PUBLIC, new Field[] {
                new Field("arr", new ArrayType(SimpleTypes.BOOLEAN)),
                new Field("i", SimpleTypes.INT)},
                SimpleTypes.BOOLEAN,
                new ArrayGet(new GetValue("arr", new ArrayType(SimpleTypes.BOOLEAN)),
                        new GetValue("i", SimpleTypes.INT)));

        boolean[] bools = new boolean[] { false, false, true, false, false };
        Object result = generator.invokeMethod("TestArray",method, bools, 2);
        Assertions.assertTrue((Boolean) result);
    }

    @TestAllImplementations
    public void testArrayGetChar(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("arraytest", Access.PUBLIC, new Field[] {
                new Field("arr", new ArrayType(SimpleTypes.CHAR)),
                new Field("i", SimpleTypes.INT)},
                SimpleTypes.CHAR,
                new ArrayGet(new GetValue("arr", new ArrayType(SimpleTypes.CHAR)),
                        new GetValue("i", SimpleTypes.INT)));

        char[] chars = new char[] { 1, 2, 5, 3 };
        Object result = generator.invokeMethod("TestArray",method, chars, 2);
        Assertions.assertEquals((char) 5, (Character) result);
    }

    @TestAllImplementations
    public void testArrayGetDouble(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("arraytest", Access.PUBLIC, new Field[] {
                new Field("arr", new ArrayType(SimpleTypes.DOUBLE)),
                new Field("i", SimpleTypes.INT)},
                SimpleTypes.DOUBLE,
                new ArrayGet(new GetValue("arr", new ArrayType(SimpleTypes.DOUBLE)),
                        new GetValue("i", SimpleTypes.INT)));

        double[] doubles = new double[] { 1, 2, 5, 3 };
        Object result = generator.invokeMethod("TestArray",method, doubles, 2);
        Assertions.assertEquals((double) 5, (Double) result);
    }

    @TestAllImplementations
    public void testArrayGetFloat(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("arraytest", Access.PUBLIC, new Field[] {
                new Field("arr", new ArrayType(SimpleTypes.FLOAT)),
                new Field("i", SimpleTypes.INT)},
                SimpleTypes.FLOAT,
                new ArrayGet(new GetValue("arr", new ArrayType(SimpleTypes.FLOAT)),
                        new GetValue("i", SimpleTypes.INT)));

        float[] floats = new float[] { 1, 2, 5, 3 };
        Object result = generator.invokeMethod("TestArray",method, floats, 2);
        Assertions.assertEquals((float) 5, (Float) result);
    }

    @TestAllImplementations
    public void testArrayGetInt(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("arraytest", Access.PUBLIC, new Field[] {
                new Field("arr", new ArrayType(SimpleTypes.INT)),
                new Field("i", SimpleTypes.INT)},
                SimpleTypes.INT,
                new ArrayGet(new GetValue("arr", new ArrayType(SimpleTypes.INT)),
                        new GetValue("i", SimpleTypes.INT)));

        int[] ints = new int[] { 1, 2, 5, 3 };
        Object result = generator.invokeMethod("TestArray",method, ints, 2);
        Assertions.assertEquals((int) 5, (Integer) result);
    }

    @TestAllImplementations
    public void testArrayGetLong(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("arraytest", Access.PUBLIC, new Field[] {
                new Field("arr", new ArrayType(SimpleTypes.LONG)),
                new Field("i", SimpleTypes.INT)},
                SimpleTypes.LONG,
                new ArrayGet(new GetValue("arr", new ArrayType(SimpleTypes.LONG)),
                        new GetValue("i", SimpleTypes.INT)));

        long[] longs = new long[] { 1, 2, 5, 3 };
        Object result = generator.invokeMethod("TestArray",method, longs, 2);
        Assertions.assertEquals((long) 5, (Long) result);
    }

    @TestAllImplementations
    public void testArrayGetShort(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("arraytest", Access.PUBLIC, new Field[] {
                new Field("arr", new ArrayType(SimpleTypes.SHORT)),
                new Field("i", SimpleTypes.INT)},
                SimpleTypes.SHORT,
                new ArrayGet(new GetValue("arr", new ArrayType(SimpleTypes.SHORT)),
                        new GetValue("i", SimpleTypes.INT)));

        short[] shorts = new short[] { 1, 2, 5, 3 };
        Object result = generator.invokeMethod("TestArray",method, shorts, 2);
        Assertions.assertEquals((short) 5, (Short) result);
    }

    @TestAllImplementations
    public void testArrayGetString(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("arraytest", Access.PUBLIC, new Field[] {
                new Field("arr", new ArrayType(SimpleTypes.STRING)),
                new Field("i", SimpleTypes.INT)},
                SimpleTypes.STRING,
                new ArrayGet(new GetValue("arr", new ArrayType(SimpleTypes.STRING)),
                        new GetValue("i", SimpleTypes.INT)));

        String[] strings = new String[] { "moe", "larry", "curly", "shemp", "curly joe" };
        Object result = generator.invokeMethod("TestArray",method, strings, 2);
        Assertions.assertEquals("curly", result);
    }

    @TestAllImplementations
    public void testArrayGetObject(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("arraytest", Access.PUBLIC, new Field[] {
                new Field("arr", new ArrayType(new ObjectType())),
                new Field("i", SimpleTypes.INT)},
                new ObjectType(),
                new ArrayGet(new GetValue("arr", new ArrayType(new ObjectType())),
                        new GetValue("i", SimpleTypes.INT)));

        String[] strings = new String[] { "moe", "larry", "curly", "shemp", "curly joe" };
        Object result = generator.invokeMethod("TestArray",method, strings, 2);
        Assertions.assertEquals("curly", result);
    }

    @TestAllImplementations
    public void testArraySetBoolean(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("arraytest", Access.PUBLIC, new Field[] {
                new Field("arr", new ArrayType(SimpleTypes.BOOLEAN)),
                new Field("i", SimpleTypes.INT),
                new Field( "x", SimpleTypes.BOOLEAN)},
                SimpleTypes.UNIT,
                new ArraySet(new GetValue("arr", new ArrayType(SimpleTypes.BOOLEAN)),
                        new GetValue("i", SimpleTypes.INT),
                        new GetValue("x", SimpleTypes.BOOLEAN)));

        boolean[] bools = new boolean[] { false, false, false, false, false };
        Object result = generator.invokeMethod("TestArray",method, bools, 2, true);
        Assertions.assertTrue(bools[2]);
    }

    @TestAllImplementations
    public void testArraySetByte(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("arraytest", Access.PUBLIC, new Field[] {
                new Field("arr", new ArrayType(SimpleTypes.BYTE)),
                new Field("i", SimpleTypes.INT),
                new Field( "x", SimpleTypes.BYTE)},
                SimpleTypes.UNIT,
                new ArraySet(new GetValue("arr", new ArrayType(SimpleTypes.BYTE)),
                        new GetValue("i", SimpleTypes.INT),
                        new GetValue("x", SimpleTypes.BYTE)));

        byte[] bytes = new byte[] { 1, 2, 5, 3 };
        Object result = generator.invokeMethod("TestArray",method, bytes, 2, (byte) 42);
        Assertions.assertEquals((byte) 42, bytes[2]);
    }

    @TestAllImplementations
    public void testArraySetChar(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("arraytest", Access.PUBLIC, new Field[] {
                new Field("arr", new ArrayType(SimpleTypes.CHAR)),
                new Field("i", SimpleTypes.INT),
                new Field( "x", SimpleTypes.CHAR)},
                SimpleTypes.UNIT,
                new ArraySet(new GetValue("arr", new ArrayType(SimpleTypes.CHAR)),
                        new GetValue("i", SimpleTypes.INT),
                        new GetValue("x", SimpleTypes.CHAR)));

        char[] chars = new char[] { 1, 2, 5, 3 };
        Object result = generator.invokeMethod("TestArray",method, chars, 2, (char) 42);
        Assertions.assertEquals((char) 42, chars[2]);
    }

    @TestAllImplementations
    public void testArraySetDouble(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("arraytest", Access.PUBLIC, new Field[] {
                new Field("arr", new ArrayType(SimpleTypes.DOUBLE)),
                new Field("i", SimpleTypes.INT),
                new Field( "x", SimpleTypes.DOUBLE)},
                SimpleTypes.UNIT,
                new ArraySet(new GetValue("arr", new ArrayType(SimpleTypes.DOUBLE)),
                        new GetValue("i", SimpleTypes.INT),
                        new GetValue("x", SimpleTypes.DOUBLE)));

        double[] doubles = new double[] { 1, 2, 5, 3 };
        Object result = generator.invokeMethod("TestArray",method, doubles, 2, (double) 42);
        Assertions.assertEquals((double) 42, doubles[2]);
    }

    @TestAllImplementations
    public void testArraySetFloat(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("arraytest", Access.PUBLIC, new Field[] {
                new Field("arr", new ArrayType(SimpleTypes.FLOAT)),
                new Field("i", SimpleTypes.INT),
                new Field( "x", SimpleTypes.FLOAT)},
                SimpleTypes.UNIT,
                new ArraySet(new GetValue("arr", new ArrayType(SimpleTypes.FLOAT)),
                        new GetValue("i", SimpleTypes.INT),
                        new GetValue("x", SimpleTypes.FLOAT)));

        float[] floats = new float[] { 1, 2, 5, 3 };
        Object result = generator.invokeMethod("TestArray",method, floats, 2, (float) 42);
        Assertions.assertEquals((float) 42, floats[2]);
    }

    @TestAllImplementations
    public void testArraySetInt(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("arraytest", Access.PUBLIC, new Field[] {
                new Field("arr", new ArrayType(SimpleTypes.INT)),
                new Field("i", SimpleTypes.INT),
                new Field( "x", SimpleTypes.INT)},
                SimpleTypes.UNIT,
                new ArraySet(new GetValue("arr", new ArrayType(SimpleTypes.INT)),
                        new GetValue("i", SimpleTypes.INT),
                        new GetValue("x", SimpleTypes.INT)));

        int[] ints = new int[] { 1, 2, 5, 3 };
        Object result = generator.invokeMethod("TestArray",method, ints, 2, (int) 42);
        Assertions.assertEquals((int) 42, ints[2]);
    }

    @TestAllImplementations
    public void testArraySetLong(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("arraytest", Access.PUBLIC, new Field[] {
                new Field("arr", new ArrayType(SimpleTypes.LONG)),
                new Field("i", SimpleTypes.INT),
                new Field( "x", SimpleTypes.LONG)},
                SimpleTypes.UNIT,
                new ArraySet(new GetValue("arr", new ArrayType(SimpleTypes.LONG)),
                        new GetValue("i", SimpleTypes.INT),
                        new GetValue("x", SimpleTypes.LONG)));

        long[] longs = new long[] { 1, 2, 5, 3 };
        Object result = generator.invokeMethod("TestArray",method, longs, 2, (long) 42);
        Assertions.assertEquals((long) 42, longs[2]);
    }

    @TestAllImplementations
    public void testArraySetShort(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("arraytest", Access.PUBLIC, new Field[] {
                new Field("arr", new ArrayType(SimpleTypes.SHORT)),
                new Field("i", SimpleTypes.INT),
                new Field( "x", SimpleTypes.SHORT)},
                SimpleTypes.UNIT,
                new ArraySet(new GetValue("arr", new ArrayType(SimpleTypes.SHORT)),
                        new GetValue("i", SimpleTypes.INT),
                        new GetValue("x", SimpleTypes.SHORT)));

        short[] shorts = new short[] { 1, 2, 5, 3 };
        Object result = generator.invokeMethod("TestArray",method, shorts, 2, (short) 42);
        Assertions.assertEquals((short) 42, shorts[2]);
    }

    @TestAllImplementations
    public void testArraySetString(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("arraytest", Access.PUBLIC, new Field[] {
                new Field("arr", new ArrayType(SimpleTypes.STRING)),
                new Field("i", SimpleTypes.INT),
                new Field( "x", SimpleTypes.STRING)},
                SimpleTypes.UNIT,
                new ArraySet(new GetValue("arr", new ArrayType(SimpleTypes.STRING)),
                        new GetValue("i", SimpleTypes.INT),
                        new GetValue("x", SimpleTypes.STRING)));

        String[] strings = new String[] { "moe", "larry", "curly" };
        Object result = generator.invokeMethod("TestArray",method, strings, 2, "shemp");
        Assertions.assertEquals("shemp", strings[2]);
    }

    @TestAllImplementations
    public void testArraySetObject(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("arraytest", Access.PUBLIC, new Field[] {
                new Field("arr", new ArrayType(new ObjectType())),
                new Field("i", SimpleTypes.INT),
                new Field( "x", new ObjectType())},
                SimpleTypes.UNIT,
                new ArraySet(new GetValue("arr", new ArrayType(new ObjectType())),
                        new GetValue("i", SimpleTypes.INT),
                        new GetValue("x", new ObjectType())));

        String[] strings = new String[] { "moe", "larry", "curly" };
        Object result = generator.invokeMethod("TestArray",method, strings, 2, "shemp");
        Assertions.assertEquals("shemp", strings[2]);
    }
}