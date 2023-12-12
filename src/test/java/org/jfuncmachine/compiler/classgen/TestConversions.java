package org.jfuncmachine.compiler.classgen;

import org.jfuncmachine.compiler.model.Access;
import org.jfuncmachine.compiler.model.MethodDef;
import org.jfuncmachine.compiler.model.expr.constants.*;
import org.jfuncmachine.compiler.model.expr.conv.*;
import org.jfuncmachine.compiler.model.types.Field;
import org.jfuncmachine.compiler.model.types.SimpleTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class TestConversions {
    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(ClassGeneratorProvider.class)
    @Retention(RetentionPolicy.RUNTIME)
    private @interface TestAllImplementations {}

    @TestAllImplementations
    public void testByteConversion(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("testconst", Access.PUBLIC, new Field[] { },
                SimpleTypes.BYTE,
                new ToByte(new BooleanConstant(true)));

        Object result = generator.invokeMethod("TestConstants",method);
        Assertions.assertEquals((Byte) result, (byte) 1, "True should convert to 1");

        method = new MethodDef("testconst", Access.PUBLIC, new Field[] { },
                SimpleTypes.BYTE,
                new ToByte(new ByteConstant((byte) 42)));

        result = generator.invokeMethod("TestConstants2",method);
        Assertions.assertEquals((Byte) result, (byte) 42, "Byte 42 should convert to 42");

        method = new MethodDef("testconst", Access.PUBLIC, new Field[] { },
                SimpleTypes.BYTE,
                new ToByte(new CharConstant((char) 42)));

        result = generator.invokeMethod("TestConstants3",method);
        Assertions.assertEquals((Byte) result, (byte) 42, "Char 42 should convert to 42");

        method = new MethodDef("testconst", Access.PUBLIC, new Field[] { },
                SimpleTypes.BYTE,
                new ToByte(new DoubleConstant(42.0)));

        result = generator.invokeMethod("TestConstants4",method);
        Assertions.assertEquals((Byte) result, (byte) 42, "Double 42 should convert to 42");

        method = new MethodDef("testconst", Access.PUBLIC, new Field[] { },
                SimpleTypes.BYTE,
                new ToByte(new FloatConstant((float) 42)));

        result = generator.invokeMethod("TestConstants5",method);
        Assertions.assertEquals((Byte) result, (byte) 42, "Float 42 should convert to 42");

        method = new MethodDef("testconst", Access.PUBLIC, new Field[] { },
                SimpleTypes.BYTE,
                new ToByte(new IntConstant(42)));

        result = generator.invokeMethod("TestConstants6",method);
        Assertions.assertEquals((Byte) result, (byte) 42, "Int 42 should convert to 42");

        method = new MethodDef("testconst", Access.PUBLIC, new Field[] { },
                SimpleTypes.BYTE,
                new ToByte(new LongConstant(42l)));

        result = generator.invokeMethod("TestConstants7",method);
        Assertions.assertEquals((Byte) result, (byte) 42, "Long 42 should convert to 42");

        method = new MethodDef("testconst", Access.PUBLIC, new Field[] { },
                SimpleTypes.BYTE,
                new ToByte(new ShortConstant((short) 42)));

        result = generator.invokeMethod("TestConstants8",method);
        Assertions.assertEquals((Byte) result, (byte) 42, "Short 42 should convert to 42");
    }

    @TestAllImplementations
    public void testCharConversion(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("testconst", Access.PUBLIC, new Field[] { },
                SimpleTypes.CHAR,
                new ToChar(new BooleanConstant(true)));

        Object result = generator.invokeMethod("TestConstants",method);
        Assertions.assertEquals((Character) result, (char) 1, "True should convert to 1");

        method = new MethodDef("testconst", Access.PUBLIC, new Field[] { },
                SimpleTypes.CHAR,
                new ToChar(new ByteConstant((byte) 42)));

        result = generator.invokeMethod("TestConstants2",method);
        Assertions.assertEquals((Character) result, (char) 42, "Byte 42 should convert to 42");

        method = new MethodDef("testconst", Access.PUBLIC, new Field[] { },
                SimpleTypes.CHAR,
                new ToChar(new CharConstant((char) 42)));

        result = generator.invokeMethod("TestConstants3",method);
        Assertions.assertEquals((Character) result, (char) 42, "Char 42 should convert to 42");

        method = new MethodDef("testconst", Access.PUBLIC, new Field[] { },
                SimpleTypes.CHAR,
                new ToChar(new DoubleConstant(42.0)));

        result = generator.invokeMethod("TestConstants4",method);
        Assertions.assertEquals((Character) result, (char) 42, "Double 42 should convert to 42");

        method = new MethodDef("testconst", Access.PUBLIC, new Field[] { },
                SimpleTypes.CHAR,
                new ToChar(new FloatConstant((float) 42)));

        result = generator.invokeMethod("TestConstants5",method);
        Assertions.assertEquals((Character) result, (char) 42, "Float 42 should convert to 42");

        method = new MethodDef("testconst", Access.PUBLIC, new Field[] { },
                SimpleTypes.CHAR,
                new ToChar(new IntConstant(42)));

        result = generator.invokeMethod("TestConstants6",method);
        Assertions.assertEquals((Character) result, (char) 42, "Int 42 should convert to 42");

        method = new MethodDef("testconst", Access.PUBLIC, new Field[] { },
                SimpleTypes.CHAR,
                new ToChar(new LongConstant(42l)));

        result = generator.invokeMethod("TestConstants7",method);
        Assertions.assertEquals((Character) result, (char) 42, "Long 42 should convert to 42");

        method = new MethodDef("testconst", Access.PUBLIC, new Field[] { },
                SimpleTypes.CHAR,
                new ToChar(new ShortConstant((short) 42)));

        result = generator.invokeMethod("TestConstants8",method);
        Assertions.assertEquals((Character) result, (char) 42, "Short 42 should convert to 42");
    }

    @TestAllImplementations
    public void testDoubleConversion(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("testconst", Access.PUBLIC, new Field[] { },
                SimpleTypes.DOUBLE,
                new ToDouble(new BooleanConstant(true)));

        Object result = generator.invokeMethod("TestConstants",method);
        Assertions.assertEquals((Double) result, (double) 1, "True should convert to 1");

        method = new MethodDef("testconst", Access.PUBLIC, new Field[] { },
                SimpleTypes.DOUBLE,
                new ToDouble(new ByteConstant((byte) 42)));

        result = generator.invokeMethod("TestConstants2",method);
        Assertions.assertEquals((Double) result, (double) 42, "Byte 42 should convert to 42");

        method = new MethodDef("testconst", Access.PUBLIC, new Field[] { },
                SimpleTypes.DOUBLE,
                new ToDouble(new CharConstant((char) 42)));

        result = generator.invokeMethod("TestConstants3",method);
        Assertions.assertEquals((Double) result, (double) 42, "Char 42 should convert to 42");

        method = new MethodDef("testconst", Access.PUBLIC, new Field[] { },
                SimpleTypes.DOUBLE,
                new ToDouble(new DoubleConstant(42.0)));

        result = generator.invokeMethod("TestConstants4",method);
        Assertions.assertEquals((Double) result, (double) 42, "Double 42 should convert to 42");

        method = new MethodDef("testconst", Access.PUBLIC, new Field[] { },
                SimpleTypes.DOUBLE,
                new ToDouble(new FloatConstant((float) 42)));

        result = generator.invokeMethod("TestConstants5",method);
        Assertions.assertEquals((Double) result, (double) 42, "Float 42 should convert to 42");

        method = new MethodDef("testconst", Access.PUBLIC, new Field[] { },
                SimpleTypes.DOUBLE,
                new ToDouble(new IntConstant(42)));

        result = generator.invokeMethod("TestConstants6",method);
        Assertions.assertEquals((Double) result, (double) 42, "Int 42 should convert to 42");

        method = new MethodDef("testconst", Access.PUBLIC, new Field[] { },
                SimpleTypes.DOUBLE,
                new ToDouble(new LongConstant(42l)));

        result = generator.invokeMethod("TestConstants7",method);
        Assertions.assertEquals((Double) result, (double) 42, "Long 42 should convert to 42");

        method = new MethodDef("testconst", Access.PUBLIC, new Field[] { },
                SimpleTypes.DOUBLE,
                new ToDouble(new ShortConstant((short) 42)));

        result = generator.invokeMethod("TestConstants8",method);
        Assertions.assertEquals((Double) result, (double) 42, "Short 42 should convert to 42");
    }

    @TestAllImplementations
    public void testFloatConversion(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("testconst", Access.PUBLIC, new Field[] { },
                SimpleTypes.FLOAT,
                new ToFloat(new BooleanConstant(true)));

        Object result = generator.invokeMethod("TestConstants",method);
        Assertions.assertEquals((Float) result, (byte) 1, "True should convert to 1");

        method = new MethodDef("testconst", Access.PUBLIC, new Field[] { },
                SimpleTypes.FLOAT,
                new ToFloat(new ByteConstant((byte) 42)));

        result = generator.invokeMethod("TestConstants2",method);
        Assertions.assertEquals((Float) result, (float) 42, "Byte 42 should convert to 42");

        method = new MethodDef("testconst", Access.PUBLIC, new Field[] { },
                SimpleTypes.FLOAT,
                new ToFloat(new CharConstant((char) 42)));

        result = generator.invokeMethod("TestConstants3",method);
        Assertions.assertEquals((Float) result, (float) 42, "Char 42 should convert to 42");

        method = new MethodDef("testconst", Access.PUBLIC, new Field[] { },
                SimpleTypes.FLOAT,
                new ToFloat(new DoubleConstant(42.0)));

        result = generator.invokeMethod("TestConstants4",method);
        Assertions.assertEquals((Float) result, (float) 42, "Double 42 should convert to 42");

        method = new MethodDef("testconst", Access.PUBLIC, new Field[] { },
                SimpleTypes.FLOAT,
                new ToFloat(new FloatConstant((float) 42)));

        result = generator.invokeMethod("TestConstants5",method);
        Assertions.assertEquals((Float) result, (float) 42, "Float 42 should convert to 42");

        method = new MethodDef("testconst", Access.PUBLIC, new Field[] { },
                SimpleTypes.FLOAT,
                new ToFloat(new IntConstant(42)));

        result = generator.invokeMethod("TestConstants6",method);
        Assertions.assertEquals((Float) result, (float) 42, "Int 42 should convert to 42");

        method = new MethodDef("testconst", Access.PUBLIC, new Field[] { },
                SimpleTypes.FLOAT,
                new ToFloat(new LongConstant(42l)));

        result = generator.invokeMethod("TestConstants7",method);
        Assertions.assertEquals((Float) result, (float) 42, "Long 42 should convert to 42");

        method = new MethodDef("testconst", Access.PUBLIC, new Field[] { },
                SimpleTypes.FLOAT,
                new ToFloat(new ShortConstant((short) 42)));

        result = generator.invokeMethod("TestConstants8",method);
        Assertions.assertEquals((Float) result, (float) 42, "Short 42 should convert to 42");
    }

    @TestAllImplementations
    public void testIntConversion(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("testconst", Access.PUBLIC, new Field[] { },
                SimpleTypes.INT,
                new ToInt(new BooleanConstant(true)));

        Object result = generator.invokeMethod("TestConstants",method);
        Assertions.assertEquals((Integer) result, 1, "True should convert to 1");

        method = new MethodDef("testconst", Access.PUBLIC, new Field[] { },
                SimpleTypes.INT,
                new ToInt(new ByteConstant((byte) 42)));

        result = generator.invokeMethod("TestConstants2",method);
        Assertions.assertEquals((Integer) result, 42, "Byte 42 should convert to 42");

        method = new MethodDef("testconst", Access.PUBLIC, new Field[] { },
                SimpleTypes.INT,
                new ToInt(new CharConstant((char) 42)));

        result = generator.invokeMethod("TestConstants3",method);
        Assertions.assertEquals((Integer) result, 42, "Char 42 should convert to 42");

        method = new MethodDef("testconst", Access.PUBLIC, new Field[] { },
                SimpleTypes.INT,
                new ToInt(new DoubleConstant(42.0)));

        result = generator.invokeMethod("TestConstants4",method);
        Assertions.assertEquals((Integer) result, 42, "Double 42 should convert to 42");

        method = new MethodDef("testconst", Access.PUBLIC, new Field[] { },
                SimpleTypes.INT,
                new ToInt(new FloatConstant((float) 42)));

        result = generator.invokeMethod("TestConstants5",method);
        Assertions.assertEquals((Integer) result, 42, "Float 42 should convert to 42");

        method = new MethodDef("testconst", Access.PUBLIC, new Field[] { },
                SimpleTypes.INT,
                new ToInt(new IntConstant(42)));

        result = generator.invokeMethod("TestConstants6",method);
        Assertions.assertEquals((Integer) result, 42, "Int 42 should convert to 42");

        method = new MethodDef("testconst", Access.PUBLIC, new Field[] { },
                SimpleTypes.INT,
                new ToInt(new LongConstant(42l)));

        result = generator.invokeMethod("TestConstants7",method);
        Assertions.assertEquals((Integer) result, 42, "Long 42 should convert to 42");

        method = new MethodDef("testconst", Access.PUBLIC, new Field[] { },
                SimpleTypes.INT,
                new ToInt(new ShortConstant((short) 42)));

        result = generator.invokeMethod("TestConstants8",method);
        Assertions.assertEquals((Integer) result, 42, "Short 42 should convert to 42");
    }

    @TestAllImplementations
    public void testLongConversion(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("testconst", Access.PUBLIC, new Field[] { },
                SimpleTypes.LONG,
                new ToLong(new BooleanConstant(true)));

        Object result = generator.invokeMethod("TestConstants",method);
        Assertions.assertEquals((Long) result, (long) 1, "True should convert to 1");

        method = new MethodDef("testconst", Access.PUBLIC, new Field[] { },
                SimpleTypes.LONG,
                new ToLong(new ByteConstant((byte) 42)));

        result = generator.invokeMethod("TestConstants2",method);
        Assertions.assertEquals((Long) result, (long) 42, "Byte 42 should convert to 42");

        method = new MethodDef("testconst", Access.PUBLIC, new Field[] { },
                SimpleTypes.LONG,
                new ToLong(new CharConstant((char) 42)));

        result = generator.invokeMethod("TestConstants3",method);
        Assertions.assertEquals((Long) result, (long) 42, "Char 42 should convert to 42");

        method = new MethodDef("testconst", Access.PUBLIC, new Field[] { },
                SimpleTypes.LONG,
                new ToLong(new DoubleConstant(42.0)));

        result = generator.invokeMethod("TestConstants4",method);
        Assertions.assertEquals((Long) result, (long) 42, "Double 42 should convert to 42");

        method = new MethodDef("testconst", Access.PUBLIC, new Field[] { },
                SimpleTypes.LONG,
                new ToLong(new FloatConstant((float) 42)));

        result = generator.invokeMethod("TestConstants5",method);
        Assertions.assertEquals((Long) result, (long) 42, "Float 42 should convert to 42");

        method = new MethodDef("testconst", Access.PUBLIC, new Field[] { },
                SimpleTypes.LONG,
                new ToLong(new IntConstant(42)));

        result = generator.invokeMethod("TestConstants6",method);
        Assertions.assertEquals((Long) result, (long) 42, "Int 42 should convert to 42");

        method = new MethodDef("testconst", Access.PUBLIC, new Field[] { },
                SimpleTypes.LONG,
                new ToLong(new LongConstant(42l)));

        result = generator.invokeMethod("TestConstants7",method);
        Assertions.assertEquals((Long) result, (long) 42, "Long 42 should convert to 42");

        method = new MethodDef("testconst", Access.PUBLIC, new Field[] { },
                SimpleTypes.LONG,
                new ToLong(new ShortConstant((short) 42)));

        result = generator.invokeMethod("TestConstants8",method);
        Assertions.assertEquals((Long) result, (long) 42, "Short 42 should convert to 42");
    }

    @TestAllImplementations
    public void testShortConversion(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("testconst", Access.PUBLIC, new Field[] { },
                SimpleTypes.SHORT,
                new ToShort(new BooleanConstant(true)));

        Object result = generator.invokeMethod("TestConstants",method);
        Assertions.assertEquals((Short) result, (short) 1, "True should convert to 1");

        method = new MethodDef("testconst", Access.PUBLIC, new Field[] { },
                SimpleTypes.SHORT,
                new ToShort(new ByteConstant((byte) 42)));

        result = generator.invokeMethod("TestConstants2",method);
        Assertions.assertEquals((Short) result, (short) 42, "Byte 42 should convert to 42");

        method = new MethodDef("testconst", Access.PUBLIC, new Field[] { },
                SimpleTypes.SHORT,
                new ToShort(new CharConstant((char) 42)));

        result = generator.invokeMethod("TestConstants3",method);
        Assertions.assertEquals((Short) result, (short) 42, "Char 42 should convert to 42");

        method = new MethodDef("testconst", Access.PUBLIC, new Field[] { },
                SimpleTypes.SHORT,
                new ToShort(new DoubleConstant(42.0)));

        result = generator.invokeMethod("TestConstants4",method);
        Assertions.assertEquals((Short) result, (short) 42, "Double 42 should convert to 42");

        method = new MethodDef("testconst", Access.PUBLIC, new Field[] { },
                SimpleTypes.SHORT,
                new ToShort(new FloatConstant((float) 42)));

        result = generator.invokeMethod("TestConstants5",method);
        Assertions.assertEquals((Short) result, (short) 42, "Float 42 should convert to 42");

        method = new MethodDef("testconst", Access.PUBLIC, new Field[] { },
                SimpleTypes.SHORT,
                new ToShort(new IntConstant(42)));

        result = generator.invokeMethod("TestConstants6",method);
        Assertions.assertEquals((Short) result, (short) 42, "Int 42 should convert to 42");

        method = new MethodDef("testconst", Access.PUBLIC, new Field[] { },
                SimpleTypes.SHORT,
                new ToShort(new LongConstant(42l)));

        result = generator.invokeMethod("TestConstants7",method);
        Assertions.assertEquals((Short) result, (short) 42, "Long 42 should convert to 42");

        method = new MethodDef("testconst", Access.PUBLIC, new Field[] { },
                SimpleTypes.SHORT,
                new ToShort(new ShortConstant((short) 42)));

        result = generator.invokeMethod("TestConstants8",method);
        Assertions.assertEquals((Short) result, (short) 42, "Short 42 should convert to 42");
    }

    @TestAllImplementations
    public void testUnitConversion(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("testconst", Access.PUBLIC, new Field[] { },
                SimpleTypes.UNIT,
                new ToUnit(new BooleanConstant(true)));

        Object result = generator.invokeMethod("TestConstants",method);
        Assertions.assertNull(result, "Unit conversion should be null");

        method = new MethodDef("testconst", Access.PUBLIC, new Field[] { },
                SimpleTypes.UNIT,
                new ToUnit(new ByteConstant((byte) 42)));

        result = generator.invokeMethod("TestConstants2",method);
        Assertions.assertNull(result, "Unit conversion should be null");

        method = new MethodDef("testconst", Access.PUBLIC, new Field[] { },
                SimpleTypes.UNIT,
                new ToUnit(new CharConstant((char) 42)));

        result = generator.invokeMethod("TestConstants3",method);
        Assertions.assertNull(result, "Unit conversion should be null");

        method = new MethodDef("testconst", Access.PUBLIC, new Field[] { },
                SimpleTypes.UNIT,
                new ToUnit(new DoubleConstant(42.0)));

        result = generator.invokeMethod("TestConstants4",method);
        Assertions.assertNull(result, "Unit conversion should be null");

        method = new MethodDef("testconst", Access.PUBLIC, new Field[] { },
                SimpleTypes.UNIT,
                new ToUnit(new FloatConstant((float) 42)));

        result = generator.invokeMethod("TestConstants5",method);
        Assertions.assertNull(result, "Unit conversion should be null");

        method = new MethodDef("testconst", Access.PUBLIC, new Field[] { },
                SimpleTypes.UNIT,
                new ToUnit(new IntConstant(42)));

        result = generator.invokeMethod("TestConstants6",method);
        Assertions.assertNull(result, "Unit conversion should be null");

        method = new MethodDef("testconst", Access.PUBLIC, new Field[] { },
                SimpleTypes.UNIT,
                new ToUnit(new LongConstant(42l)));

        result = generator.invokeMethod("TestConstants7",method);
        Assertions.assertNull(result, "Unit conversion should be null");

        method = new MethodDef("testconst", Access.PUBLIC, new Field[] { },
                SimpleTypes.UNIT,
                new ToUnit(new ShortConstant((short) 42)));

        result = generator.invokeMethod("TestConstants8",method);
        Assertions.assertNull(result, "Unit conversion should be null");
    }
}
