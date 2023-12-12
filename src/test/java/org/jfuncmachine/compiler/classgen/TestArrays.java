package org.jfuncmachine.compiler.classgen;

import org.jfuncmachine.compiler.model.Access;
import org.jfuncmachine.compiler.model.MethodDef;
import org.jfuncmachine.compiler.model.expr.*;
import org.jfuncmachine.compiler.model.types.ArrayType;
import org.jfuncmachine.compiler.model.types.Field;
import org.jfuncmachine.compiler.model.types.ObjectType;
import org.jfuncmachine.compiler.model.types.SimpleTypes;
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

    @TestAllImplementations
     public void testNewBoolArray(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("arraytest", Access.PUBLIC, new Field[] {
                new Field("n", SimpleTypes.INT)},
                new ArrayType(SimpleTypes.BOOLEAN),
                new NewArray(SimpleTypes.BOOLEAN, new GetValue("n", SimpleTypes.INT)));

        Object result = generator.invokeMethod("TestArray",method, 42);
        Assertions.assertInstanceOf(boolean[].class, result);
        Assertions.assertEquals(42, ((boolean[])result).length);
    }

    @TestAllImplementations
    public void testNewByteArray(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("arraytest", Access.PUBLIC, new Field[] {
                new Field("n", SimpleTypes.INT)},
                new ArrayType(SimpleTypes.BYTE),
                new NewArray(SimpleTypes.BYTE, new GetValue("n", SimpleTypes.INT)));

        Object result = generator.invokeMethod("TestArray",method, 42);
        Assertions.assertInstanceOf(byte[].class, result);
        Assertions.assertEquals(42, ((byte[])result).length);
    }

    @TestAllImplementations
    public void testNewCharArray(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("arraytest", Access.PUBLIC, new Field[] {
                new Field("n", SimpleTypes.INT)},
                new ArrayType(SimpleTypes.CHAR),
                new NewArray(SimpleTypes.CHAR, new GetValue("n", SimpleTypes.INT)));

        Object result = generator.invokeMethod("TestArray",method, 42);
        Assertions.assertInstanceOf(char[].class, result);
        Assertions.assertEquals(42, ((char[])result).length);
    }

    @TestAllImplementations
    public void testNewDoubleArray(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("arraytest", Access.PUBLIC, new Field[] {
                new Field("n", SimpleTypes.INT)},
                new ArrayType(SimpleTypes.DOUBLE),
                new NewArray(SimpleTypes.DOUBLE, new GetValue("n", SimpleTypes.INT)));

        Object result = generator.invokeMethod("TestArray",method, 42);
        Assertions.assertInstanceOf(double[].class, result);
        Assertions.assertEquals(42, ((double[])result).length);
    }

    @TestAllImplementations
    public void testNewFloatArray(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("arraytest", Access.PUBLIC, new Field[] {
                new Field("n", SimpleTypes.INT)},
                new ArrayType(SimpleTypes.FLOAT),
                new NewArray(SimpleTypes.FLOAT, new GetValue("n", SimpleTypes.INT)));

        Object result = generator.invokeMethod("TestArray",method, 42);
        Assertions.assertInstanceOf(float[].class, result);
        Assertions.assertEquals(42, ((float[])result).length);
    }

    @TestAllImplementations
    public void testNewIntArray(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("arraytest", Access.PUBLIC, new Field[] {
                new Field("n", SimpleTypes.INT)},
                new ArrayType(SimpleTypes.INT),
                new NewArray(SimpleTypes.INT, new GetValue("n", SimpleTypes.INT)));

        Object result = generator.invokeMethod("TestArray",method, 42);
        Assertions.assertInstanceOf(int[].class, result);
        Assertions.assertEquals(42, ((int[])result).length);
    }

    @TestAllImplementations
    public void testNewLongArray(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("arraytest", Access.PUBLIC, new Field[] {
                new Field("n", SimpleTypes.INT)},
                new ArrayType(SimpleTypes.LONG),
                new NewArray(SimpleTypes.LONG, new GetValue("n", SimpleTypes.INT)));

        Object result = generator.invokeMethod("TestArray",method, 42);
        Assertions.assertInstanceOf(long[].class, result);
        Assertions.assertEquals(42, ((long[])result).length);
    }

    @TestAllImplementations
    public void testNewShortArray(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("arraytest", Access.PUBLIC, new Field[] {
                new Field("n", SimpleTypes.INT)},
                new ArrayType(SimpleTypes.SHORT),
                new NewArray(SimpleTypes.SHORT, new GetValue("n", SimpleTypes.INT)));

        Object result = generator.invokeMethod("TestArray",method, 42);
        Assertions.assertInstanceOf(short[].class, result);
        Assertions.assertEquals(42, ((short[])result).length);
    }

    @TestAllImplementations
    public void testNewStringArray(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("arraytest", Access.PUBLIC, new Field[] {
                new Field("n", SimpleTypes.INT)},
                new ArrayType(SimpleTypes.STRING),
                new NewArray(SimpleTypes.STRING, new GetValue("n", SimpleTypes.INT)));

        Object result = generator.invokeMethod("TestArray",method, 42);
        Assertions.assertInstanceOf(String[].class, result);
        Assertions.assertEquals(42, ((String[])result).length);
    }

    @TestAllImplementations
    public void testNewObjectArray(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("arraytest", Access.PUBLIC, new Field[] {
                new Field("n", SimpleTypes.INT)},
                new ArrayType(new ObjectType()),
                new NewArray(new ObjectType(), new GetValue("n", SimpleTypes.INT)));

        Object result = generator.invokeMethod("TestArray",method, 42);
        Assertions.assertInstanceOf(Object[].class, result);
        Assertions.assertEquals(42, ((Object[])result).length);
    }


    @TestAllImplementations
    public void testNewBoolArrayWithValues(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("arraytest", Access.PUBLIC, new Field[] {
                new Field("v1", SimpleTypes.BOOLEAN),
                new Field("v2", SimpleTypes.BOOLEAN),
                new Field("v3", SimpleTypes.BOOLEAN),
        },
                new ArrayType(SimpleTypes.BOOLEAN),
                new NewArrayWithValues(SimpleTypes.BOOLEAN,
                        new Expression[] {
                                new GetValue("v1", SimpleTypes.BOOLEAN),
                                new GetValue("v2", SimpleTypes.BOOLEAN),
                                new GetValue("v3", SimpleTypes.BOOLEAN)
                        }));

        Object result = generator.invokeMethod("TestArray",method, false, false, true);
        Assertions.assertInstanceOf(boolean[].class, result);
        Assertions.assertEquals(3, ((boolean[])result).length);
        Assertions.assertTrue(((boolean[])result)[2]);
    }
    @TestAllImplementations
    public void testNewByteArrayWithValues(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("arraytest", Access.PUBLIC, new Field[] {
                    new Field("v1", SimpleTypes.BYTE),
                    new Field("v2", SimpleTypes.BYTE),
                    new Field("v3", SimpleTypes.BYTE),
                },
                new ArrayType(SimpleTypes.BYTE),
                new NewArrayWithValues(SimpleTypes.BYTE,
                        new Expression[] {
                                new GetValue("v1", SimpleTypes.BYTE),
                                new GetValue("v2", SimpleTypes.BYTE),
                                new GetValue("v3", SimpleTypes.BYTE)
                        }));

        Object result = generator.invokeMethod("TestArray",method, (byte) 5, (byte) 10, (byte) 42);
        Assertions.assertInstanceOf(byte[].class, result);
        Assertions.assertEquals(3, ((byte[])result).length);
        Assertions.assertEquals(42, ((byte[])result)[2]);
    }

    @TestAllImplementations
    public void testNewCharArrayWithValues(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("arraytest", Access.PUBLIC, new Field[] {
                new Field("v1", SimpleTypes.CHAR),
                new Field("v2", SimpleTypes.CHAR),
                new Field("v3", SimpleTypes.CHAR),
        },
                new ArrayType(SimpleTypes.CHAR),
                new NewArrayWithValues(SimpleTypes.CHAR,
                        new Expression[] {
                                new GetValue("v1", SimpleTypes.CHAR),
                                new GetValue("v2", SimpleTypes.CHAR),
                                new GetValue("v3", SimpleTypes.CHAR)
                        }));

        Object result = generator.invokeMethod("TestArray",method, (char) 5, (char) 10, (char) 42);
        Assertions.assertInstanceOf(char[].class, result);
        Assertions.assertEquals(3, ((char[])result).length);
        Assertions.assertEquals(42, ((char[])result)[2]);
    }

    @TestAllImplementations
    public void testNewDoubleArrayWithValues(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("arraytest", Access.PUBLIC, new Field[] {
                new Field("v1", SimpleTypes.DOUBLE),
                new Field("v2", SimpleTypes.DOUBLE),
                new Field("v3", SimpleTypes.DOUBLE),
        },
                new ArrayType(SimpleTypes.DOUBLE),
                new NewArrayWithValues(SimpleTypes.DOUBLE,
                        new Expression[] {
                                new GetValue("v1", SimpleTypes.DOUBLE),
                                new GetValue("v2", SimpleTypes.DOUBLE),
                                new GetValue("v3", SimpleTypes.DOUBLE)
                        }));

        Object result = generator.invokeMethod("TestArray",method, (double) 5, (double) 10, (double) 42);
        Assertions.assertInstanceOf(double[].class, result);
        Assertions.assertEquals(3, ((double[])result).length);
        Assertions.assertEquals(42, ((double[])result)[2]);
    }

    @TestAllImplementations
    public void testNewFloatArrayWithValues(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("arraytest", Access.PUBLIC, new Field[] {
                new Field("v1", SimpleTypes.FLOAT),
                new Field("v2", SimpleTypes.FLOAT),
                new Field("v3", SimpleTypes.FLOAT),
        },
                new ArrayType(SimpleTypes.FLOAT),
                new NewArrayWithValues(SimpleTypes.FLOAT,
                        new Expression[] {
                                new GetValue("v1", SimpleTypes.FLOAT),
                                new GetValue("v2", SimpleTypes.FLOAT),
                                new GetValue("v3", SimpleTypes.FLOAT)
                        }));

        Object result = generator.invokeMethod("TestArray",method, (float) 5, (float) 10, (float) 42);
        Assertions.assertInstanceOf(float[].class, result);
        Assertions.assertEquals(3, ((float[])result).length);
        Assertions.assertEquals(42, ((float[])result)[2]);
    }

    @TestAllImplementations
    public void testNewIntArrayWithValues(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("arraytest", Access.PUBLIC, new Field[] {
                new Field("v1", SimpleTypes.INT),
                new Field("v2", SimpleTypes.INT),
                new Field("v3", SimpleTypes.INT),
        },
                new ArrayType(SimpleTypes.INT),
                new NewArrayWithValues(SimpleTypes.INT,
                        new Expression[] {
                                new GetValue("v1", SimpleTypes.INT),
                                new GetValue("v2", SimpleTypes.INT),
                                new GetValue("v3", SimpleTypes.INT)
                        }));

        Object result = generator.invokeMethod("TestArray",method, (int) 5, (int) 10, (int) 42);
        Assertions.assertInstanceOf(int[].class, result);
        Assertions.assertEquals(3, ((int[])result).length);
        Assertions.assertEquals(42, ((int[])result)[2]);
    }

    @TestAllImplementations
    public void testNewLongArrayWithValues(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("arraytest", Access.PUBLIC, new Field[] {
                new Field("v1", SimpleTypes.LONG),
                new Field("v2", SimpleTypes.LONG),
                new Field("v3", SimpleTypes.LONG),
        },
                new ArrayType(SimpleTypes.LONG),
                new NewArrayWithValues(SimpleTypes.LONG,
                        new Expression[] {
                                new GetValue("v1", SimpleTypes.LONG),
                                new GetValue("v2", SimpleTypes.LONG),
                                new GetValue("v3", SimpleTypes.LONG)
                        }));

        Object result = generator.invokeMethod("TestArray",method, (long) 5, (long) 10, (long) 42);
        Assertions.assertInstanceOf(long[].class, result);
        Assertions.assertEquals(3, ((long[])result).length);
        Assertions.assertEquals(42, ((long[])result)[2]);
    }

    @TestAllImplementations
    public void testNewShortArrayWithValues(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("arraytest", Access.PUBLIC, new Field[] {
                new Field("v1", SimpleTypes.SHORT),
                new Field("v2", SimpleTypes.SHORT),
                new Field("v3", SimpleTypes.SHORT),
        },
                new ArrayType(SimpleTypes.SHORT),
                new NewArrayWithValues(SimpleTypes.SHORT,
                        new Expression[] {
                                new GetValue("v1", SimpleTypes.SHORT),
                                new GetValue("v2", SimpleTypes.SHORT),
                                new GetValue("v3", SimpleTypes.SHORT)
                        }));

        Object result = generator.invokeMethod("TestArray",method, (short) 5, (short) 10, (short) 42);
        Assertions.assertInstanceOf(short[].class, result);
        Assertions.assertEquals(3, ((short[])result).length);
        Assertions.assertEquals(42, ((short[])result)[2]);
    }

    @TestAllImplementations
    public void testNewStringArrayWithValues(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("arraytest", Access.PUBLIC, new Field[] {
                new Field("v1", SimpleTypes.STRING),
                new Field("v2", SimpleTypes.STRING),
                new Field("v3", SimpleTypes.STRING),
        },
                new ArrayType(SimpleTypes.STRING),
                new NewArrayWithValues(SimpleTypes.STRING,
                        new Expression[] {
                                new GetValue("v1", SimpleTypes.STRING),
                                new GetValue("v2", SimpleTypes.STRING),
                                new GetValue("v3", SimpleTypes.STRING)
                        }));

        Object result = generator.invokeMethod("TestArray",method, "moe", "larry", "curly");
        Assertions.assertInstanceOf(String[].class, result);
        Assertions.assertEquals(3, ((String[])result).length);
        Assertions.assertEquals("curly", ((String[])result)[2]);
    }

    @TestAllImplementations
    public void testNewObjectArrayWithValues(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("arraytest", Access.PUBLIC, new Field[] {
                new Field("v1", new ObjectType()),
                new Field("v2", new ObjectType()),
                new Field("v3", new ObjectType()),
        },
                new ArrayType(new ObjectType()),
                new NewArrayWithValues(new ObjectType(),
                        new Expression[] {
                                new GetValue("v1", new ObjectType()),
                                new GetValue("v2", new ObjectType()),
                                new GetValue("v3", new ObjectType())
                        }));

        Object result = generator.invokeMethod("TestArray",method, "moe", "larry", "curly");
        Assertions.assertInstanceOf(Object[].class, result);
        Assertions.assertEquals(3, ((Object[])result).length);
        Assertions.assertEquals("curly", ((Object[])result)[2]);
    }
}