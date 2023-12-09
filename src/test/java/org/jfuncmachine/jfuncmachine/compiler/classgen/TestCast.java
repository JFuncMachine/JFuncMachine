package org.jfuncmachine.jfuncmachine.compiler.classgen;

import org.jfuncmachine.jfuncmachine.compiler.model.Access;
import org.jfuncmachine.jfuncmachine.compiler.model.MethodDef;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.Cast;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.GetValue;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.boxing.Box;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.boxing.Unbox;
import org.jfuncmachine.jfuncmachine.compiler.model.types.Field;
import org.jfuncmachine.jfuncmachine.compiler.model.types.ObjectType;
import org.jfuncmachine.jfuncmachine.compiler.model.types.SimpleTypes;
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
                new Cast("java.lang.String", new GetValue("x", new ObjectType())));

        Object result = generator.invokeMethod("TestCast",method, "foo");
        Assertions.assertEquals(result, "foo");

    }
}