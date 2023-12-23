package org.jfuncmachine.compiler.classgen;

import org.jfuncmachine.compiler.exceptions.JFuncMachineException;
import org.jfuncmachine.compiler.model.Access;
import org.jfuncmachine.compiler.model.MethodDef;
import org.jfuncmachine.compiler.model.expr.Cast;
import org.jfuncmachine.compiler.model.expr.GetValue;
import org.jfuncmachine.compiler.model.types.ArrayType;
import org.jfuncmachine.compiler.model.types.Field;
import org.jfuncmachine.compiler.model.types.ObjectType;
import org.jfuncmachine.compiler.model.types.SimpleTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class TestCast {
    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(ClassGeneratorNoAutoboxProvider.class)
    @Retention(RetentionPolicy.RUNTIME)
    private @interface TestAllImplementations {}

    @TestAllImplementations
    public void testCast(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("casttest", Access.PUBLIC, new Field[] {
                new Field("x", new ObjectType())},
                SimpleTypes.STRING,
                new Cast(SimpleTypes.STRING, new GetValue("x", new ObjectType())));

        Object result = generator.invokeMethod("TestCast",method, "foo");
        Assertions.assertEquals(result, "foo");

    }

    @TestAllImplementations
    public void testIntegerCast(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("casttest", Access.PUBLIC, new Field[] {
                new Field("x", new ObjectType())},
                SimpleTypes.INT.getBoxType(),
                new Cast(SimpleTypes.INT.getBoxType(), new GetValue("x", new ObjectType())));

        Object result = generator.invokeMethod("TestCast",method, 14);
        Assertions.assertEquals(result, Integer.valueOf(14));

    }

    @TestAllImplementations
    public void testArrayCast(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("casttest", Access.PUBLIC, new Field[] {
                new Field("x", new ObjectType())},
                new ArrayType(SimpleTypes.STRING),
                new Cast(new ArrayType(SimpleTypes.STRING), new GetValue("x", new ObjectType())));

        String[] foo = new String[0];
        Object result = generator.invokeMethod("TestCast",method, new Object[] { foo });
        Assertions.assertEquals(result, foo);

    }

    @TestAllImplementations
    public void testCastInt(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("casttest", Access.PUBLIC, new Field[] {
                new Field("x", new ObjectType())},
                SimpleTypes.INT,
                new Cast(SimpleTypes.INT, new GetValue("x", new ObjectType())));

        Assertions.assertThrows(JFuncMachineException.class,
                () -> generator.invokeMethod("TestCast",method, 14));

    }

}
