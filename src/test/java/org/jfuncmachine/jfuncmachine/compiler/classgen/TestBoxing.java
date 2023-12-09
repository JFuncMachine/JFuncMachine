package org.jfuncmachine.jfuncmachine.compiler.classgen;

import org.jfuncmachine.jfuncmachine.compiler.model.Access;
import org.jfuncmachine.jfuncmachine.compiler.model.MethodDef;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.GetValue;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.boxing.Autobox;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.boxing.Box;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.boxing.Unbox;
import org.jfuncmachine.jfuncmachine.compiler.model.types.Field;
import org.jfuncmachine.jfuncmachine.compiler.model.types.ObjectType;
import org.jfuncmachine.jfuncmachine.compiler.model.types.SimpleTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class TestBoxing {
    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(ClassGeneratorNoAutoboxProvider.class)
    @Retention(RetentionPolicy.RUNTIME)
    private @interface TestAllImplementations {}

    @TestAllImplementations
    public void testBoxBoolean(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("boxtest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.INT)},
                new ObjectType(SimpleTypes.BOOLEAN.getBoxTypeName()),
                new Box(new GetValue("x", SimpleTypes.INT), SimpleTypes.BOOLEAN,
                        new ObjectType(SimpleTypes.BOOLEAN.getBoxTypeName())));

        ClassGeneratorOptions options = new ClassGeneratorOptionsBuilder().withAutobox(false).build();
        Object result = generator.invokeMethod("TestBoxing",method, 0);
        Assertions.assertFalse((Boolean) result, "0 should be false");
        result = generator.invokeMethod("TestBoxing",method, 1);
        Assertions.assertTrue((Boolean) result, "1 should be true");
    }

    @TestAllImplementations
    public void testBoxByte(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("boxtest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.INT)},
                new ObjectType(SimpleTypes.BYTE.getBoxTypeName()),
                new Box(new GetValue("x", SimpleTypes.INT), SimpleTypes.BYTE,
                        new ObjectType(SimpleTypes.BYTE.getBoxTypeName())));

        ClassGeneratorOptions options = new ClassGeneratorOptionsBuilder().withAutobox(false).build();
        Object result = generator.invokeMethod("TestBoxing",method, 127);
        Assertions.assertEquals((Byte) result, Byte.valueOf((byte)127), "Box value should be 127");
        result = generator.invokeMethod("TestBoxing",method, 0);
        Assertions.assertEquals((Byte) result, Byte.valueOf((byte)0), "Box value should be 0");
    }

    @TestAllImplementations
    public void testBoxChar(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("boxtest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.INT)},
                new ObjectType(SimpleTypes.CHAR.getBoxTypeName()),
                new Box(new GetValue("x", SimpleTypes.INT), SimpleTypes.CHAR,
                        new ObjectType(SimpleTypes.CHAR.getBoxTypeName())));

        ClassGeneratorOptions options = new ClassGeneratorOptionsBuilder().withAutobox(false).build();
        Object result = generator.invokeMethod("TestBoxing",method, 127);
        Assertions.assertEquals((Character) result, Character.valueOf((char)127), "Box value should be 127");
        result = generator.invokeMethod("TestBoxing",method, 0);
        Assertions.assertEquals((Character) result, Character.valueOf((char)0), "Box value should be 0");
    }

    @TestAllImplementations
    public void testBoxDouble(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("boxtest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.DOUBLE)},
                new ObjectType(SimpleTypes.DOUBLE.getBoxTypeName()),
                new Box(new GetValue("x", SimpleTypes.DOUBLE), SimpleTypes.DOUBLE,
                        new ObjectType(SimpleTypes.DOUBLE.getBoxTypeName())));

        ClassGeneratorOptions options = new ClassGeneratorOptionsBuilder().withAutobox(false).build();
        Object result = generator.invokeMethod("TestBoxing",method, 3.14);
        Assertions.assertEquals((Double) result, Double.valueOf(3.14), "Box value should be 127");
        result = generator.invokeMethod("TestBoxing",method, -1.0);
        Assertions.assertEquals((Double) result, Double.valueOf(-1.0), "Box value should be 0");
    }

    @TestAllImplementations
    public void testBoxFloat(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("boxtest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.FLOAT)},
                new ObjectType(SimpleTypes.FLOAT.getBoxTypeName()),
                new Box(new GetValue("x", SimpleTypes.FLOAT), SimpleTypes.FLOAT,
                        new ObjectType(SimpleTypes.FLOAT.getBoxTypeName())));

        ClassGeneratorOptions options = new ClassGeneratorOptionsBuilder().withAutobox(false).build();
        Object result = generator.invokeMethod("TestBoxing",method, 3.14f);
        Assertions.assertEquals((Float) result, Float.valueOf(3.14f), "Box value should be 127");
        result = generator.invokeMethod("TestBoxing",method, -1.0f);
        Assertions.assertEquals((Float) result, Float.valueOf(-1.0f), "Box value should be 0");
    }

    @TestAllImplementations
    public void testBoxInt(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("boxtest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.INT)},
                new ObjectType(SimpleTypes.INT.getBoxTypeName()),
                new Box(new GetValue("x", SimpleTypes.INT), SimpleTypes.INT,
                        new ObjectType(SimpleTypes.INT.getBoxTypeName())));

        ClassGeneratorOptions options = new ClassGeneratorOptionsBuilder().withAutobox(false).build();
        Object result = generator.invokeMethod("TestBoxing",method, 127);
        Assertions.assertEquals((Integer) result, Integer.valueOf(127), "Box value should be 127");
        result = generator.invokeMethod("TestBoxing",method, 0);
        Assertions.assertEquals((Integer) result, Integer.valueOf(0), "Box value should be 0");
    }

    @TestAllImplementations
    public void testBoxLong(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("boxtest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.LONG)},
                new ObjectType(SimpleTypes.LONG.getBoxTypeName()),
                new Box(new GetValue("x", SimpleTypes.LONG), SimpleTypes.LONG,
                        new ObjectType(SimpleTypes.LONG.getBoxTypeName())));

        ClassGeneratorOptions options = new ClassGeneratorOptionsBuilder().withAutobox(false).build();
        Object result = generator.invokeMethod("TestBoxing",method, 127l);
        Assertions.assertEquals((Long) result, Long.valueOf(127l), "Box value should be 127");
        result = generator.invokeMethod("TestBoxing",method, 0l);
        Assertions.assertEquals((Long) result, Long.valueOf(0l), "Box value should be 0");
    }

    @TestAllImplementations
    public void testBoxShort(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("boxtest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.INT)},
                new ObjectType(SimpleTypes.SHORT.getBoxTypeName()),
                new Box(new GetValue("x", SimpleTypes.INT), SimpleTypes.SHORT,
                        new ObjectType(SimpleTypes.SHORT.getBoxTypeName())));

        ClassGeneratorOptions options = new ClassGeneratorOptionsBuilder().withAutobox(false).build();
        Object result = generator.invokeMethod("TestBoxing",method, 127);
        Assertions.assertEquals((Short) result, Short.valueOf((short)127), "Box value should be 127");
        result = generator.invokeMethod("TestBoxing",method, 0);
        Assertions.assertEquals((Short) result, Short.valueOf((short)0), "Box value should be 0");
    }

    @TestAllImplementations
    public void testAutoboxBoolean(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("boxtest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.BOOLEAN)},
                new ObjectType(SimpleTypes.BOOLEAN.getBoxTypeName()),
                Autobox.autobox(new GetValue("x", SimpleTypes.BOOLEAN),
                        new ObjectType(SimpleTypes.BOOLEAN.getBoxTypeName())));
        Object result = generator.invokeMethod("TestBoxing",method, false);
        Assertions.assertFalse((Boolean) result, "false should be false");
        result = generator.invokeMethod("TestBoxing",method, true);
        Assertions.assertTrue((Boolean) result, "true should be true");
    }

    @TestAllImplementations
    public void testAutoboxByte(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("boxtest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.BYTE)},
                new ObjectType(SimpleTypes.BYTE.getBoxTypeName()),
                Autobox.autobox(new GetValue("x", SimpleTypes.BYTE),
                        new ObjectType(SimpleTypes.BYTE.getBoxTypeName())));

        Object result = generator.invokeMethod("TestBoxing",method, (byte) 127);
        Assertions.assertEquals((Byte) result, Byte.valueOf((byte)127), "Box value should be 127");
        result = generator.invokeMethod("TestBoxing",method, (byte) 0);
        Assertions.assertEquals((Byte) result, Byte.valueOf((byte)0), "Box value should be 0");
    }

    @TestAllImplementations
    public void testAutoboxChar(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("boxtest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.CHAR)},
                new ObjectType(SimpleTypes.CHAR.getBoxTypeName()),
                Autobox.autobox(new GetValue("x", SimpleTypes.CHAR),
                        new ObjectType(SimpleTypes.CHAR.getBoxTypeName())));

        Object result = generator.invokeMethod("TestBoxing",method, (char) 127);
        Assertions.assertEquals((Character) result, Character.valueOf((char)127), "Box value should be 127");
        result = generator.invokeMethod("TestBoxing",method, (char) 0);
        Assertions.assertEquals((Character) result, Character.valueOf((char)0), "Box value should be 0");
    }

    @TestAllImplementations
    public void testAutoboxDouble(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("boxtest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.DOUBLE)},
                new ObjectType(SimpleTypes.DOUBLE.getBoxTypeName()),
                Autobox.autobox(new GetValue("x", SimpleTypes.DOUBLE),
                        new ObjectType(SimpleTypes.DOUBLE.getBoxTypeName())));

        Object result = generator.invokeMethod("TestBoxing",method, 3.14);
        Assertions.assertEquals((Double) result, Double.valueOf(3.14), "Box value should be 127");
        result = generator.invokeMethod("TestBoxing",method, -1.0);
        Assertions.assertEquals((Double) result, Double.valueOf(-1.0), "Box value should be 0");
    }

    @TestAllImplementations
    public void testAutoboxFloat(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("boxtest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.FLOAT)},
                new ObjectType(SimpleTypes.FLOAT.getBoxTypeName()),
                Autobox.autobox(new GetValue("x", SimpleTypes.FLOAT),
                        new ObjectType(SimpleTypes.FLOAT.getBoxTypeName())));

        Object result = generator.invokeMethod("TestBoxing",method, 3.14f);
        Assertions.assertEquals((Float) result, Float.valueOf(3.14f), "Box value should be 127");
        result = generator.invokeMethod("TestBoxing",method, -1.0f);
        Assertions.assertEquals((Float) result, Float.valueOf(-1.0f), "Box value should be 0");
    }

    @TestAllImplementations
    public void testAutoboxInt(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("boxtest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.INT)},
                new ObjectType(SimpleTypes.INT.getBoxTypeName()),
                Autobox.autobox(new GetValue("x", SimpleTypes.INT),
                        new ObjectType(SimpleTypes.INT.getBoxTypeName())));

        Object result = generator.invokeMethod("TestBoxing",method, 127);
        Assertions.assertEquals((Integer) result, Integer.valueOf(127), "Box value should be 127");
        result = generator.invokeMethod("TestBoxing",method, 0);
        Assertions.assertEquals((Integer) result, Integer.valueOf(0), "Box value should be 0");
    }

    @TestAllImplementations
    public void testAutoboxLong(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("boxtest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.LONG)},
                new ObjectType(SimpleTypes.LONG.getBoxTypeName()),
                Autobox.autobox(new GetValue("x", SimpleTypes.LONG),
                        new ObjectType(SimpleTypes.LONG.getBoxTypeName())));

        Object result = generator.invokeMethod("TestBoxing",method, 127l);
        Assertions.assertEquals((Long) result, Long.valueOf(127l), "Box value should be 127");
        result = generator.invokeMethod("TestBoxing",method, 0l);
        Assertions.assertEquals((Long) result, Long.valueOf(0l), "Box value should be 0");
    }

    @TestAllImplementations
    public void testAutoboxShort(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("boxtest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.SHORT)},
                new ObjectType(SimpleTypes.SHORT.getBoxTypeName()),
                Autobox.autobox(new GetValue("x", SimpleTypes.SHORT),
                        new ObjectType(SimpleTypes.SHORT.getBoxTypeName())));

        Object result = generator.invokeMethod("TestBoxing",method, (short) 127);
        Assertions.assertEquals((Short) result, Short.valueOf((short)127), "Box value should be 127");
        result = generator.invokeMethod("TestBoxing",method, (short) 0);
        Assertions.assertEquals((Short) result, Short.valueOf((short)0), "Box value should be 0");
    }


    @TestAllImplementations
    public void testAutoboxString(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("boxtest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.STRING)},
                SimpleTypes.STRING,
                Autobox.autobox(new GetValue("x", SimpleTypes.STRING), SimpleTypes.STRING));
        Object result = generator.invokeMethod("TestBoxing",method, "foo");
        Assertions.assertEquals((String) result, "foo", "foo shouldn't be boxed");
        result = generator.invokeMethod("TestBoxing",method, "bar");
        Assertions.assertEquals((String) result, "bar", "bar shouldn't be boxed");
    }
    @TestAllImplementations
    public void testUnboxBoolean(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("boxtest", Access.PUBLIC, new Field[] {
                new Field("x", new ObjectType(SimpleTypes.BOOLEAN.getBoxTypeName()))},
                SimpleTypes.BOOLEAN,
                new Unbox(new GetValue("x", new ObjectType(SimpleTypes.BOOLEAN.getBoxTypeName()))));

        ClassGeneratorOptions options = new ClassGeneratorOptionsBuilder().withAutobox(false).build();
        Object result = generator.invokeMethod("TestBoxing",method, Boolean.FALSE);
        Assertions.assertFalse((Boolean) result, "0 should be false");
        result = generator.invokeMethod("TestBoxing",method, Boolean.TRUE);
        Assertions.assertTrue((Boolean) result, "1 should be true");
    }

    @TestAllImplementations
    public void testUnboxByte(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("boxtest", Access.PUBLIC, new Field[] {
                new Field("x", new ObjectType(SimpleTypes.BYTE.getBoxTypeName()))},
                SimpleTypes.BYTE,
                new Unbox(new GetValue("x", new ObjectType(SimpleTypes.BYTE.getBoxTypeName()))));

        ClassGeneratorOptions options = new ClassGeneratorOptionsBuilder().withAutobox(false).build();
        Object result = generator.invokeMethod("TestBoxing",method, (byte) 127);
        Assertions.assertEquals((Byte) result, Byte.valueOf((byte) 127));
        result = generator.invokeMethod("TestBoxing",method, (byte) 0);
        Assertions.assertEquals((Byte) result, Byte.valueOf((byte) 0));
    }

    @TestAllImplementations
    public void testUnboxChar(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("boxtest", Access.PUBLIC, new Field[] {
                new Field("x", new ObjectType(SimpleTypes.CHAR.getBoxTypeName()))},
                SimpleTypes.CHAR,
                new Unbox(new GetValue("x", new ObjectType(SimpleTypes.CHAR.getBoxTypeName()))));

        ClassGeneratorOptions options = new ClassGeneratorOptionsBuilder().withAutobox(false).build();
        Object result = generator.invokeMethod("TestBoxing",method, (char) 127);
        Assertions.assertEquals((Character) result, Character.valueOf((char) 127));
        result = generator.invokeMethod("TestBoxing",method, (char) 0);
        Assertions.assertEquals((Character) result, Character.valueOf((char) 0));
    }

    @TestAllImplementations
    public void testUnboxDouble(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("boxtest", Access.PUBLIC, new Field[] {
                new Field("x", new ObjectType(SimpleTypes.DOUBLE.getBoxTypeName()))},
                SimpleTypes.DOUBLE,
                new Unbox(new GetValue("x", new ObjectType(SimpleTypes.DOUBLE.getBoxTypeName()))));

        ClassGeneratorOptions options = new ClassGeneratorOptionsBuilder().withAutobox(false).build();
        Object result = generator.invokeMethod("TestBoxing",method, (double) 3.14);
        Assertions.assertEquals((Double) result, Double.valueOf((double) 3.14));
        result = generator.invokeMethod("TestBoxing",method, (double) 0);
        Assertions.assertEquals((Double) result, Double.valueOf((double) 0));
    }

    @TestAllImplementations
    public void testUnboxFloat(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("boxtest", Access.PUBLIC, new Field[] {
                new Field("x", new ObjectType(SimpleTypes.FLOAT.getBoxTypeName()))},
                SimpleTypes.FLOAT,
                new Unbox(new GetValue("x", new ObjectType(SimpleTypes.FLOAT.getBoxTypeName()))));

        ClassGeneratorOptions options = new ClassGeneratorOptionsBuilder().withAutobox(false).build();
        Object result = generator.invokeMethod("TestBoxing",method, (float) 3.14);
        Assertions.assertEquals((Float) result, Float.valueOf((float) 3.14));
        result = generator.invokeMethod("TestBoxing",method, (float) 0);
        Assertions.assertEquals((Float) result, Float.valueOf((float) 0));
    }

    @TestAllImplementations
    public void testUnboxInt(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("boxtest", Access.PUBLIC, new Field[] {
                new Field("x", new ObjectType(SimpleTypes.INT.getBoxTypeName()))},
                SimpleTypes.INT,
                new Unbox(new GetValue("x", new ObjectType(SimpleTypes.INT.getBoxTypeName()))));

        ClassGeneratorOptions options = new ClassGeneratorOptionsBuilder().withAutobox(false).build();
        Object result = generator.invokeMethod("TestBoxing",method, (int) 127);
        Assertions.assertEquals((Integer) result, Integer.valueOf((int) 127));
        result = generator.invokeMethod("TestBoxing",method, (int) 0);
        Assertions.assertEquals((Integer) result, Integer.valueOf((int) 0));
    }

    @TestAllImplementations
    public void testUnboxLong(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("boxtest", Access.PUBLIC, new Field[] {
                new Field("x", new ObjectType(SimpleTypes.LONG.getBoxTypeName()))},
                SimpleTypes.LONG,
                new Unbox(new GetValue("x", new ObjectType(SimpleTypes.LONG.getBoxTypeName()))));

        ClassGeneratorOptions options = new ClassGeneratorOptionsBuilder().withAutobox(false).build();
        Object result = generator.invokeMethod("TestBoxing",method, (long) 127);
        Assertions.assertEquals((Long) result, Long.valueOf((long) 127));
        result = generator.invokeMethod("TestBoxing",method, (long) 0);
        Assertions.assertEquals((Long) result, Long.valueOf((long) 0));
    }

    @TestAllImplementations
    public void testUnboxShort(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("boxtest", Access.PUBLIC, new Field[] {
                new Field("x", new ObjectType(SimpleTypes.SHORT.getBoxTypeName()))},
                SimpleTypes.SHORT,
                new Unbox(new GetValue("x", new ObjectType(SimpleTypes.SHORT.getBoxTypeName()))));

        ClassGeneratorOptions options = new ClassGeneratorOptionsBuilder().withAutobox(false).build();
        Object result = generator.invokeMethod("TestBoxing",method, (short) 127);
        Assertions.assertEquals((Short) result, Short.valueOf((short) 127));
        result = generator.invokeMethod("TestBoxing",method, (short) 0);
        Assertions.assertEquals((Short) result, Short.valueOf((short) 0));
    }
}
