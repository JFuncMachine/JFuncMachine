package org.jfuncmachine.jfuncmachine.compiler.classgen;

import org.jfuncmachine.jfuncmachine.compiler.model.Access;
import org.jfuncmachine.jfuncmachine.compiler.model.MethodDef;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.GetValue;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.boxing.Box;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.boxing.Unbox;
import org.jfuncmachine.jfuncmachine.compiler.model.types.Field;
import org.jfuncmachine.jfuncmachine.compiler.model.types.ObjectType;
import org.jfuncmachine.jfuncmachine.compiler.model.types.SimpleTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestBoxing {
    @Test
    public void testBoxBoolean() {
        MethodDef method = new MethodDef("boxtest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.INT)},
                new ObjectType(SimpleTypes.BOOLEAN.getBoxTypeName()),
                new Box(new GetValue("x", SimpleTypes.INT), SimpleTypes.BOOLEAN,
                        new ObjectType(SimpleTypes.BOOLEAN.getBoxTypeName())));

        ClassGeneratorOptions options = new ClassGeneratorOptionsBuilder().withAutobox(false).build();
        ClassGenerator generator = new ClassGenerator(options);
        Object result = generator.invokeMethod(method, 0);
        Assertions.assertFalse((Boolean) result, "0 should be false");
        result = generator.invokeMethod(method, 1);
        Assertions.assertTrue((Boolean) result, "1 should be true");
    }

    @Test
    public void testBoxByte() {
        MethodDef method = new MethodDef("boxtest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.INT)},
                new ObjectType(SimpleTypes.BYTE.getBoxTypeName()),
                new Box(new GetValue("x", SimpleTypes.INT), SimpleTypes.BYTE,
                        new ObjectType(SimpleTypes.BYTE.getBoxTypeName())));

        ClassGeneratorOptions options = new ClassGeneratorOptionsBuilder().withAutobox(false).build();
        ClassGenerator generator = new ClassGenerator(options);
        Object result = generator.invokeMethod(method, 127);
        Assertions.assertEquals((Byte) result, Byte.valueOf((byte)127), "Box value should be 127");
        result = generator.invokeMethod(method, 0);
        Assertions.assertEquals((Byte) result, Byte.valueOf((byte)0), "Box value should be 0");
    }

    @Test
    public void testBoxChar() {
        MethodDef method = new MethodDef("boxtest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.INT)},
                new ObjectType(SimpleTypes.CHAR.getBoxTypeName()),
                new Box(new GetValue("x", SimpleTypes.INT), SimpleTypes.CHAR,
                        new ObjectType(SimpleTypes.CHAR.getBoxTypeName())));

        ClassGeneratorOptions options = new ClassGeneratorOptionsBuilder().withAutobox(false).build();
        ClassGenerator generator = new ClassGenerator(options);
        Object result = generator.invokeMethod(method, 127);
        Assertions.assertEquals((Character) result, Character.valueOf((char)127), "Box value should be 127");
        result = generator.invokeMethod(method, 0);
        Assertions.assertEquals((Character) result, Character.valueOf((char)0), "Box value should be 0");
    }

    @Test
    public void testBoxDouble() {
        MethodDef method = new MethodDef("boxtest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.DOUBLE)},
                new ObjectType(SimpleTypes.DOUBLE.getBoxTypeName()),
                new Box(new GetValue("x", SimpleTypes.DOUBLE), SimpleTypes.DOUBLE,
                        new ObjectType(SimpleTypes.DOUBLE.getBoxTypeName())));

        ClassGeneratorOptions options = new ClassGeneratorOptionsBuilder().withAutobox(false).build();
        ClassGenerator generator = new ClassGenerator(options);
        Object result = generator.invokeMethod(method, 3.14);
        Assertions.assertEquals((Double) result, Double.valueOf(3.14), "Box value should be 127");
        result = generator.invokeMethod(method, -1.0);
        Assertions.assertEquals((Double) result, Double.valueOf(-1.0), "Box value should be 0");
    }

    @Test
    public void testBoxFloat() {
        MethodDef method = new MethodDef("boxtest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.FLOAT)},
                new ObjectType(SimpleTypes.FLOAT.getBoxTypeName()),
                new Box(new GetValue("x", SimpleTypes.FLOAT), SimpleTypes.FLOAT,
                        new ObjectType(SimpleTypes.FLOAT.getBoxTypeName())));

        ClassGeneratorOptions options = new ClassGeneratorOptionsBuilder().withAutobox(false).build();
        ClassGenerator generator = new ClassGenerator(options);
        Object result = generator.invokeMethod(method, 3.14f);
        Assertions.assertEquals((Float) result, Float.valueOf(3.14f), "Box value should be 127");
        result = generator.invokeMethod(method, -1.0f);
        Assertions.assertEquals((Float) result, Float.valueOf(-1.0f), "Box value should be 0");
    }

    @Test
    public void testBoxInt() {
        MethodDef method = new MethodDef("boxtest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.INT)},
                new ObjectType(SimpleTypes.INT.getBoxTypeName()),
                new Box(new GetValue("x", SimpleTypes.INT), SimpleTypes.INT,
                        new ObjectType(SimpleTypes.INT.getBoxTypeName())));

        ClassGeneratorOptions options = new ClassGeneratorOptionsBuilder().withAutobox(false).build();
        ClassGenerator generator = new ClassGenerator(options);
        Object result = generator.invokeMethod(method, 127);
        Assertions.assertEquals((Integer) result, Integer.valueOf(127), "Box value should be 127");
        result = generator.invokeMethod(method, 0);
        Assertions.assertEquals((Integer) result, Integer.valueOf(0), "Box value should be 0");
    }

    @Test
    public void testBoxLong() {
        MethodDef method = new MethodDef("boxtest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.LONG)},
                new ObjectType(SimpleTypes.LONG.getBoxTypeName()),
                new Box(new GetValue("x", SimpleTypes.LONG), SimpleTypes.LONG,
                        new ObjectType(SimpleTypes.LONG.getBoxTypeName())));

        ClassGeneratorOptions options = new ClassGeneratorOptionsBuilder().withAutobox(false).build();
        ClassGenerator generator = new ClassGenerator(options);
        Object result = generator.invokeMethod(method, 127l);
        Assertions.assertEquals((Long) result, Long.valueOf(127l), "Box value should be 127");
        result = generator.invokeMethod(method, 0l);
        Assertions.assertEquals((Long) result, Long.valueOf(0l), "Box value should be 0");
    }

    @Test
    public void testBoxShort() {
        MethodDef method = new MethodDef("boxtest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.INT)},
                new ObjectType(SimpleTypes.SHORT.getBoxTypeName()),
                new Box(new GetValue("x", SimpleTypes.INT), SimpleTypes.SHORT,
                        new ObjectType(SimpleTypes.SHORT.getBoxTypeName())));

        ClassGeneratorOptions options = new ClassGeneratorOptionsBuilder().withAutobox(false).build();
        ClassGenerator generator = new ClassGenerator(options);
        Object result = generator.invokeMethod(method, 127);
        Assertions.assertEquals((Short) result, Short.valueOf((short)127), "Box value should be 127");
        result = generator.invokeMethod(method, 0);
        Assertions.assertEquals((Short) result, Short.valueOf((short)0), "Box value should be 0");
    }

    @Test
    public void testAutoboxBoolean() {
        MethodDef method = new MethodDef("boxtest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.INT)},
                new ObjectType(SimpleTypes.BOOLEAN.getBoxTypeName()),
                new GetValue("x", SimpleTypes.INT));
        ClassGenerator generator = new ClassGenerator();
        Object result = generator.invokeMethod(method, 0);
        Assertions.assertFalse((Boolean) result, "0 should be false");
        result = generator.invokeMethod(method, 1);
        Assertions.assertTrue((Boolean) result, "1 should be true");
    }

    @Test
    public void testAutoboxByte() {
        MethodDef method = new MethodDef("boxtest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.INT)},
                new ObjectType(SimpleTypes.BYTE.getBoxTypeName()),
                new GetValue("x", SimpleTypes.INT));

        ClassGenerator generator = new ClassGenerator();
        Object result = generator.invokeMethod(method, 127);
        Assertions.assertEquals((Byte) result, Byte.valueOf((byte)127), "Box value should be 127");
        result = generator.invokeMethod(method, 0);
        Assertions.assertEquals((Byte) result, Byte.valueOf((byte)0), "Box value should be 0");
    }

    @Test
    public void testAutoboxChar() {
        MethodDef method = new MethodDef("boxtest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.INT)},
                new ObjectType(SimpleTypes.CHAR.getBoxTypeName()),
                new GetValue("x", SimpleTypes.INT));

        ClassGenerator generator = new ClassGenerator();
        Object result = generator.invokeMethod(method, 127);
        Assertions.assertEquals((Character) result, Character.valueOf((char)127), "Box value should be 127");
        result = generator.invokeMethod(method, 0);
        Assertions.assertEquals((Character) result, Character.valueOf((char)0), "Box value should be 0");
    }

    @Test
    public void testAutoboxDouble() {
        MethodDef method = new MethodDef("boxtest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.DOUBLE)},
                new ObjectType(SimpleTypes.DOUBLE.getBoxTypeName()),
                new GetValue("x", SimpleTypes.DOUBLE));

        ClassGenerator generator = new ClassGenerator();
        Object result = generator.invokeMethod(method, 3.14);
        Assertions.assertEquals((Double) result, Double.valueOf(3.14), "Box value should be 127");
        result = generator.invokeMethod(method, -1.0);
        Assertions.assertEquals((Double) result, Double.valueOf(-1.0), "Box value should be 0");
    }

    @Test
    public void testAutoboxFloat() {
        MethodDef method = new MethodDef("boxtest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.FLOAT)},
                new ObjectType(SimpleTypes.FLOAT.getBoxTypeName()),
                new GetValue("x", SimpleTypes.FLOAT));

        ClassGenerator generator = new ClassGenerator();
        Object result = generator.invokeMethod(method, 3.14f);
        Assertions.assertEquals((Float) result, Float.valueOf(3.14f), "Box value should be 127");
        result = generator.invokeMethod(method, -1.0f);
        Assertions.assertEquals((Float) result, Float.valueOf(-1.0f), "Box value should be 0");
    }

    @Test
    public void testAutoboxInt() {
        MethodDef method = new MethodDef("boxtest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.INT)},
                new ObjectType(SimpleTypes.INT.getBoxTypeName()),
                new GetValue("x", SimpleTypes.INT));

        ClassGenerator generator = new ClassGenerator();
        Object result = generator.invokeMethod(method, 127);
        Assertions.assertEquals((Integer) result, Integer.valueOf(127), "Box value should be 127");
        result = generator.invokeMethod(method, 0);
        Assertions.assertEquals((Integer) result, Integer.valueOf(0), "Box value should be 0");
    }

    @Test
    public void testAutoboxLong() {
        MethodDef method = new MethodDef("boxtest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.LONG)},
                new ObjectType(SimpleTypes.LONG.getBoxTypeName()),
                new GetValue("x", SimpleTypes.LONG));

        ClassGenerator generator = new ClassGenerator();
        Object result = generator.invokeMethod(method, 127l);
        Assertions.assertEquals((Long) result, Long.valueOf(127l), "Box value should be 127");
        result = generator.invokeMethod(method, 0l);
        Assertions.assertEquals((Long) result, Long.valueOf(0l), "Box value should be 0");
    }

    @Test
    public void testAutoboxShort() {
        MethodDef method = new MethodDef("boxtest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.INT)},
                new ObjectType(SimpleTypes.SHORT.getBoxTypeName()),
                new GetValue("x", SimpleTypes.INT));

        ClassGenerator generator = new ClassGenerator();
        Object result = generator.invokeMethod(method, 127);
        Assertions.assertEquals((Short) result, Short.valueOf((short)127), "Box value should be 127");
        result = generator.invokeMethod(method, 0);
        Assertions.assertEquals((Short) result, Short.valueOf((short)0), "Box value should be 0");
    }


    @Test
    public void testAutoboxString() {
        MethodDef method = new MethodDef("boxtest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.STRING)},
                SimpleTypes.STRING,
                new GetValue("x", SimpleTypes.STRING));
        ClassGenerator generator = new ClassGenerator();
        Object result = generator.invokeMethod(method, "foo");
        Assertions.assertEquals((String) result, "foo", "foo shouldn't be boxed");
        result = generator.invokeMethod(method, "bar");
        Assertions.assertEquals((String) result, "bar", "bar shouldn't be boxed");
    }
    @Test
    public void testUnboxBoolean() {
        MethodDef method = new MethodDef("boxtest", Access.PUBLIC, new Field[] {
                new Field("x", new ObjectType(SimpleTypes.BOOLEAN.getBoxTypeName()))},
                SimpleTypes.BOOLEAN,
                new Unbox(new GetValue("x", new ObjectType(SimpleTypes.BOOLEAN.getBoxTypeName()))));

        ClassGeneratorOptions options = new ClassGeneratorOptionsBuilder().withAutobox(false).build();
        ClassGenerator generator = new ClassGenerator(options);
        Object result = generator.invokeMethod(method, Boolean.FALSE);
        Assertions.assertFalse((Boolean) result, "0 should be false");
        result = generator.invokeMethod(method, Boolean.TRUE);
        Assertions.assertTrue((Boolean) result, "1 should be true");
    }

    @Test
    public void testUnboxByte() {
        MethodDef method = new MethodDef("boxtest", Access.PUBLIC, new Field[] {
                new Field("x", new ObjectType(SimpleTypes.BYTE.getBoxTypeName()))},
                SimpleTypes.BYTE,
                new Unbox(new GetValue("x", new ObjectType(SimpleTypes.BYTE.getBoxTypeName()))));

        ClassGeneratorOptions options = new ClassGeneratorOptionsBuilder().withAutobox(false).build();
        ClassGenerator generator = new ClassGenerator(options);
        Object result = generator.invokeMethod(method, (byte) 127);
        Assertions.assertEquals((Byte) result, Byte.valueOf((byte) 127));
        result = generator.invokeMethod(method, (byte) 0);
        Assertions.assertEquals((Byte) result, Byte.valueOf((byte) 0));
    }

    @Test
    public void testUnboxChar() {
        MethodDef method = new MethodDef("boxtest", Access.PUBLIC, new Field[] {
                new Field("x", new ObjectType(SimpleTypes.CHAR.getBoxTypeName()))},
                SimpleTypes.CHAR,
                new Unbox(new GetValue("x", new ObjectType(SimpleTypes.CHAR.getBoxTypeName()))));

        ClassGeneratorOptions options = new ClassGeneratorOptionsBuilder().withAutobox(false).build();
        ClassGenerator generator = new ClassGenerator(options);
        Object result = generator.invokeMethod(method, (char) 127);
        Assertions.assertEquals((Character) result, Character.valueOf((char) 127));
        result = generator.invokeMethod(method, (char) 0);
        Assertions.assertEquals((Character) result, Character.valueOf((char) 0));
    }

    @Test
    public void testUnboxDouble() {
        MethodDef method = new MethodDef("boxtest", Access.PUBLIC, new Field[] {
                new Field("x", new ObjectType(SimpleTypes.DOUBLE.getBoxTypeName()))},
                SimpleTypes.DOUBLE,
                new Unbox(new GetValue("x", new ObjectType(SimpleTypes.DOUBLE.getBoxTypeName()))));

        ClassGeneratorOptions options = new ClassGeneratorOptionsBuilder().withAutobox(false).build();
        ClassGenerator generator = new ClassGenerator(options);
        Object result = generator.invokeMethod(method, (double) 3.14);
        Assertions.assertEquals((Double) result, Double.valueOf((double) 3.14));
        result = generator.invokeMethod(method, (double) 0);
        Assertions.assertEquals((Double) result, Double.valueOf((double) 0));
    }

    @Test
    public void testUnboxFloat() {
        MethodDef method = new MethodDef("boxtest", Access.PUBLIC, new Field[] {
                new Field("x", new ObjectType(SimpleTypes.FLOAT.getBoxTypeName()))},
                SimpleTypes.FLOAT,
                new Unbox(new GetValue("x", new ObjectType(SimpleTypes.FLOAT.getBoxTypeName()))));

        ClassGeneratorOptions options = new ClassGeneratorOptionsBuilder().withAutobox(false).build();
        ClassGenerator generator = new ClassGenerator(options);
        Object result = generator.invokeMethod(method, (float) 3.14);
        Assertions.assertEquals((Float) result, Float.valueOf((float) 3.14));
        result = generator.invokeMethod(method, (float) 0);
        Assertions.assertEquals((Float) result, Float.valueOf((float) 0));
    }

    @Test
    public void testUnboxInt() {
        MethodDef method = new MethodDef("boxtest", Access.PUBLIC, new Field[] {
                new Field("x", new ObjectType(SimpleTypes.INT.getBoxTypeName()))},
                SimpleTypes.INT,
                new Unbox(new GetValue("x", new ObjectType(SimpleTypes.INT.getBoxTypeName()))));

        ClassGeneratorOptions options = new ClassGeneratorOptionsBuilder().withAutobox(false).build();
        ClassGenerator generator = new ClassGenerator(options);
        Object result = generator.invokeMethod(method, (int) 127);
        Assertions.assertEquals((Integer) result, Integer.valueOf((int) 127));
        result = generator.invokeMethod(method, (int) 0);
        Assertions.assertEquals((Integer) result, Integer.valueOf((int) 0));
    }

    @Test
    public void testUnboxLong() {
        MethodDef method = new MethodDef("boxtest", Access.PUBLIC, new Field[] {
                new Field("x", new ObjectType(SimpleTypes.LONG.getBoxTypeName()))},
                SimpleTypes.LONG,
                new Unbox(new GetValue("x", new ObjectType(SimpleTypes.LONG.getBoxTypeName()))));

        ClassGeneratorOptions options = new ClassGeneratorOptionsBuilder().withAutobox(false).build();
        ClassGenerator generator = new ClassGenerator(options);
        Object result = generator.invokeMethod(method, (long) 127);
        Assertions.assertEquals((Long) result, Long.valueOf((long) 127));
        result = generator.invokeMethod(method, (long) 0);
        Assertions.assertEquals((Long) result, Long.valueOf((long) 0));
    }

    @Test
    public void testUnboxShort() {
        MethodDef method = new MethodDef("boxtest", Access.PUBLIC, new Field[] {
                new Field("x", new ObjectType(SimpleTypes.SHORT.getBoxTypeName()))},
                SimpleTypes.SHORT,
                new Unbox(new GetValue("x", new ObjectType(SimpleTypes.SHORT.getBoxTypeName()))));

        ClassGeneratorOptions options = new ClassGeneratorOptionsBuilder().withAutobox(false).build();
        ClassGenerator generator = new ClassGenerator(options);
        Object result = generator.invokeMethod(method, (short) 127);
        Assertions.assertEquals((Short) result, Short.valueOf((short) 127));
        result = generator.invokeMethod(method, (short) 0);
        Assertions.assertEquals((Short) result, Short.valueOf((short) 0));
    }
}
