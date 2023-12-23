package org.jfuncmachine.compiler.classgen;

import org.jfuncmachine.compiler.model.Access;
import org.jfuncmachine.compiler.model.MethodDef;
import org.jfuncmachine.compiler.model.expr.GetValue;
import org.jfuncmachine.compiler.model.expr.IntSwitch;
import org.jfuncmachine.compiler.model.expr.IntSwitchCase;
import org.jfuncmachine.compiler.model.expr.constants.StringConstant;
import org.jfuncmachine.compiler.model.types.Field;
import org.jfuncmachine.compiler.model.types.SimpleTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class TestIntSwitch {
    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(ClassGeneratorProvider.class)
    @Retention(RetentionPolicy.RUNTIME)
    private @interface TestAllImplementations {}

    @TestAllImplementations
    public void testTableSwitch(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("switchtest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.INT)},
                SimpleTypes.STRING,
                new IntSwitch(new GetValue("x", SimpleTypes.INT),
                        new IntSwitchCase[] {
                            new IntSwitchCase(2, new StringConstant("larry")),
                            new IntSwitchCase(1, new StringConstant("moe")),
                            new IntSwitchCase(3, new StringConstant("curly")),
                        },
                    new StringConstant("nobody")));

        Object result = generator.invokeMethod("TestSwitch",method, 1);
        Assertions.assertEquals(result, "moe");
        result = generator.invokeMethod("TestSwitch",method, 2);
        Assertions.assertEquals(result, "larry");
        result = generator.invokeMethod("TestSwitch",method, 3);
        Assertions.assertEquals(result, "curly");
        result = generator.invokeMethod("TestSwitch",method, 4);
        Assertions.assertEquals(result, "nobody");

    }

    @TestAllImplementations
    public void testLookupSwitch(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("switchtest", Access.PUBLIC, new Field[] {
                new Field("x", SimpleTypes.INT)},
                SimpleTypes.STRING,
                new IntSwitch(new GetValue("x", SimpleTypes.INT),
                        new IntSwitchCase[] {
                                new IntSwitchCase(42, new StringConstant("larry")),
                                new IntSwitchCase(1, new StringConstant("moe")),
                                new IntSwitchCase(73, new StringConstant("curly")),
                        },
                        new StringConstant("nobody")));

        Object result = generator.invokeMethod("TestSwitch",method, 1);
        Assertions.assertEquals(result, "moe");
        result = generator.invokeMethod("TestSwitch",method, 42);
        Assertions.assertEquals(result, "larry");
        result = generator.invokeMethod("TestSwitch",method, 73);
        Assertions.assertEquals(result, "curly");
        result = generator.invokeMethod("TestSwitch",method, 4);
        Assertions.assertEquals(result, "nobody");

    }
}
