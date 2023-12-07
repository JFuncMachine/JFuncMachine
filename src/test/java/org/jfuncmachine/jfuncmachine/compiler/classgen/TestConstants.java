package org.jfuncmachine.jfuncmachine.compiler.classgen;

import org.jfuncmachine.jfuncmachine.compiler.model.Access;
import org.jfuncmachine.jfuncmachine.compiler.model.MethodDef;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.GetValue;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.If;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.bool.And;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.bool.BinaryComparison;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.bool.InstanceofComparison;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.bool.Not;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.bool.Or;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.bool.UnaryComparison;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.bool.tests.Tests;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.constants.BooleanConstant;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.constants.ByteConstant;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.constants.CharConstant;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.constants.DoubleConstant;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.constants.FloatConstant;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.constants.IntConstant;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.constants.LongConstant;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.constants.NullConstant;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.constants.ShortConstant;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.constants.StringConstant;
import org.jfuncmachine.jfuncmachine.compiler.model.types.Field;
import org.jfuncmachine.jfuncmachine.compiler.model.types.ObjectType;
import org.jfuncmachine.jfuncmachine.compiler.model.types.SimpleTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class TestConstants {
    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(ClassGeneratorProvider.class)
    @Retention(RetentionPolicy.RUNTIME)
    private @interface TestAllImplementations {}

    @TestAllImplementations
    public void testBooleanConstant(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("testconst", Access.PUBLIC, new Field[] { },
                SimpleTypes.BOOLEAN,
                new BooleanConstant(true));

        Object result = generator.invokeMethod("TestConstants",method);
        Assertions.assertTrue((Boolean) result, "Constant should be true");

        method = new MethodDef("testconst", Access.PUBLIC, new Field[] { },
                SimpleTypes.BOOLEAN,
                new BooleanConstant(false));

        result = generator.invokeMethod("TestConstants2",method);
        Assertions.assertFalse((Boolean) result, "Constant should be false");
    }

    @TestAllImplementations
    public void testByteConstant(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("testconst", Access.PUBLIC, new Field[] { },
                SimpleTypes.BYTE,
                new ByteConstant((byte) -5));

        Object result = generator.invokeMethod("TestConstants",method);
        Assertions.assertEquals((Byte) result, (byte) -5, "Constant should be -5");

        method = new MethodDef("testconst", Access.PUBLIC, new Field[] { },
                SimpleTypes.BYTE,
                new ByteConstant((byte) 42));

        result = generator.invokeMethod("TestConstants2",method);
        Assertions.assertEquals((Byte) result, (byte) 42, "Constant should be 42");
    }

    @TestAllImplementations
    public void testCharConstant(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("testconst", Access.PUBLIC, new Field[]{},
                SimpleTypes.CHAR,
                new CharConstant((char) -5));

        Object result = generator.invokeMethod("TestConstants", method);
        Assertions.assertEquals((Character) result, (char) -5, "Constant should be -5");

        method = new MethodDef("testconst", Access.PUBLIC, new Field[]{},
                SimpleTypes.CHAR,
                new CharConstant((char) 42));

        result = generator.invokeMethod("TestConstants2", method);
        Assertions.assertEquals((Character) result, (char) 42, "Constant should be 42");
    }

    @TestAllImplementations
    public void testDoubleConstant(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("testconst", Access.PUBLIC, new Field[]{},
                SimpleTypes.DOUBLE,
                new DoubleConstant(-5.5));

        Object result = generator.invokeMethod("TestConstants", method);
        Assertions.assertEquals((Double) result, -5.5, "Constant should be -5.5");

        method = new MethodDef("testconst", Access.PUBLIC, new Field[]{},
                SimpleTypes.DOUBLE,
                new DoubleConstant(42.25));

        result = generator.invokeMethod("TestConstants2", method);
        Assertions.assertEquals((Double) result, 42.25, "Constant should be 42.25");
    }

    @TestAllImplementations
    public void testFloatConstant(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("testconst", Access.PUBLIC, new Field[]{},
                SimpleTypes.FLOAT,
                new FloatConstant((float) -5.5));

        Object result = generator.invokeMethod("TestConstants", method);
        Assertions.assertEquals((Float) result, (float) -5.5, "Constant should be -5.5");

        method = new MethodDef("testconst", Access.PUBLIC, new Field[]{},
                SimpleTypes.FLOAT,
                new FloatConstant((float) 42.25));

        result = generator.invokeMethod("TestConstants2", method);
        Assertions.assertEquals((Float) result, (float) 42.25, "Constant should be 42.25");
    }

    @TestAllImplementations
    public void testIntConstant(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("testconst", Access.PUBLIC, new Field[]{},
                SimpleTypes.INT,
                new IntConstant(-5));

        Object result = generator.invokeMethod("TestConstants", method);
        Assertions.assertEquals((Integer) result, -5, "Constant should be -5");

        method = new MethodDef("testconst", Access.PUBLIC, new Field[]{},
                SimpleTypes.INT,
                new IntConstant(42));

        result = generator.invokeMethod("TestConstants2", method);
        Assertions.assertEquals((Integer) result, 42, "Constant should be 42");
    }

    @TestAllImplementations
    public void testLongConstant(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("testconst", Access.PUBLIC, new Field[]{},
                SimpleTypes.LONG,
                new LongConstant(-5l));

        Object result = generator.invokeMethod("TestConstants", method);
        Assertions.assertEquals((Long) result, -5l, "Constant should be -5");

        method = new MethodDef("testconst", Access.PUBLIC, new Field[]{},
                SimpleTypes.LONG,
                new LongConstant(42l));

        result = generator.invokeMethod("TestConstants2", method);
        Assertions.assertEquals((Long) result, 42l, "Constant should be 42");
    }

    @TestAllImplementations
    public void testNull(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("testconst", Access.PUBLIC, new Field[]{},
                new ObjectType(),
                new NullConstant());

        Object result = generator.invokeMethod("TestConstants", method);
        Assertions.assertNull(result, "Constant should null");
    }

    @TestAllImplementations
    public void testShortConstant(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("testconst", Access.PUBLIC, new Field[]{},
                SimpleTypes.SHORT,
                new ShortConstant((short) -5));

        Object result = generator.invokeMethod("TestConstants", method);
        Assertions.assertEquals((Short) result, (short) -5, "Constant should be -5");

        method = new MethodDef("testconst", Access.PUBLIC, new Field[]{},
                SimpleTypes.SHORT,
                new ShortConstant((short) 42));

        result = generator.invokeMethod("TestConstants2", method);
        Assertions.assertEquals((Short) result, (short) 42, "Constant should be 42");
    }

    @TestAllImplementations
    public void testStringConstant(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("testconst", Access.PUBLIC, new Field[]{},
                SimpleTypes.STRING,
                new StringConstant("foo"));

        Object result = generator.invokeMethod("TestConstants", method);
        Assertions.assertEquals(result, "foo", "Constant should be foo");

        method = new MethodDef("testconst", Access.PUBLIC, new Field[]{},
                SimpleTypes.STRING,
                new StringConstant("bar"));

        result = generator.invokeMethod("TestConstants2", method);
        Assertions.assertEquals(result, "bar", "Constant should be bar");
    }
}
