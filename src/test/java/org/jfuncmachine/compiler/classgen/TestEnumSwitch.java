package org.jfuncmachine.compiler.classgen;

import org.jfuncmachine.compiler.model.Access;
import org.jfuncmachine.compiler.model.MethodDef;
import org.jfuncmachine.compiler.model.expr.*;
import org.jfuncmachine.compiler.model.expr.bool.BinaryComparison;
import org.jfuncmachine.compiler.model.expr.bool.tests.Tests;
import org.jfuncmachine.compiler.model.expr.constants.CharConstant;
import org.jfuncmachine.compiler.model.expr.constants.IntConstant;
import org.jfuncmachine.compiler.model.expr.constants.StringConstant;
import org.jfuncmachine.compiler.model.expr.javainterop.GetJavaField;
import org.jfuncmachine.compiler.model.types.Field;
import org.jfuncmachine.compiler.model.types.ObjectType;
import org.jfuncmachine.compiler.model.types.SimpleTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class TestEnumSwitch {
    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(ClassGeneratorWithJava16Provider.class)
    @Retention(RetentionPolicy.RUNTIME)
    private @interface TestAllImplementations {}

    @TestAllImplementations
    public void testSimpleEnumSwitch(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("switchtest", Access.PUBLIC, new Field[] {
                new Field("x", new ObjectType(ToyEnum.class))},
                SimpleTypes.STRING,
                new EnumSwitch(new GetValue("x", new ObjectType(ToyEnum.class)),
                        new EnumSwitchCase[] {
                            new EnumSwitchCase("Moe", new StringConstant("moe")),
                            new EnumSwitchCase("Larry", new StringConstant("larry")),
                            new EnumSwitchCase("Curly", new StringConstant("curly")),
                            new EnumSwitchCase("Shemp", new StringConstant("shemp")),
                            new EnumSwitchCase("CurlyJoe", new StringConstant("curly joe")),
                        },
                    new StringConstant("nobody")));

        Object result = generator.invokeMethod("TestSwitch",method, ToyEnum.Moe);
        Assertions.assertEquals("moe", result);
        result = generator.invokeMethod("TestSwitch",method, ToyEnum.Larry);
        Assertions.assertEquals("larry", result);
        result = generator.invokeMethod("TestSwitch",method, ToyEnum.Curly);
        Assertions.assertEquals("curly", result);
        result = generator.invokeMethod("TestSwitch",method, ToyEnum.Shemp);
        Assertions.assertEquals("shemp", result);
        result = generator.invokeMethod("TestSwitch",method, ToyEnum.CurlyJoe);
        Assertions.assertEquals("curly joe", result);
    }
}
