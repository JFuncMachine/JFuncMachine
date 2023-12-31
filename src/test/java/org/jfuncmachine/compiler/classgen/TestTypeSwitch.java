package org.jfuncmachine.compiler.classgen;

import org.jfuncmachine.compiler.model.Access;
import org.jfuncmachine.compiler.model.MethodDef;
import org.jfuncmachine.compiler.model.expr.*;
import org.jfuncmachine.compiler.model.expr.bool.BinaryComparison;
import org.jfuncmachine.compiler.model.expr.bool.tests.Tests;
import org.jfuncmachine.compiler.model.expr.constants.CharConstant;
import org.jfuncmachine.compiler.model.expr.constants.IntConstant;
import org.jfuncmachine.compiler.model.expr.constants.StringConstant;
import org.jfuncmachine.compiler.model.types.Field;
import org.jfuncmachine.compiler.model.types.ObjectType;
import org.jfuncmachine.compiler.model.types.SimpleTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class TestTypeSwitch {
    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(ClassGeneratorWithJava16Provider.class)
    @Retention(RetentionPolicy.RUNTIME)
    private @interface TestAllImplementations {}

    @TestAllImplementations
    public void testSimpleTypeSwitch(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("switchtest", Access.PUBLIC, new Field[] {
                new Field("x", new ObjectType())},
                SimpleTypes.STRING,
                new TypeSwitch(new GetValue("x", new ObjectType()),
                        new TypeSwitchCase[] {
                            new TypeSwitchCase(new ObjectType("java.lang.Boolean"), new StringConstant("larry")),
                            new TypeSwitchCase(new ObjectType("java.lang.Double"), new StringConstant("moe")),
                            new TypeSwitchCase(new ObjectType("java.lang.Character"), new StringConstant("curly")),
                        },
                    new StringConstant("nobody")));

        Object result = generator.invokeMethod("TestSwitch",method, 3.14);
        Assertions.assertEquals("moe", result);
        result = generator.invokeMethod("TestSwitch",method, true);
        Assertions.assertEquals("larry", result);
        result = generator.invokeMethod("TestSwitch",method, 'c');
        Assertions.assertEquals("curly", result);
        result = generator.invokeMethod("TestSwitch",method, 4);
        Assertions.assertEquals("nobody", result);
    }

    @TestAllImplementations
    public void testComplexTypeSwitch(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("switchtest", Access.PUBLIC, new Field[] {
                new Field("x", new ObjectType())},
                SimpleTypes.STRING,
                new TypeSwitch(new GetValue("x", new ObjectType()),
                        new TypeSwitchCase[] {
                                new TypeSwitchCase((ObjectType)SimpleTypes.BOOLEAN.getBoxType(), new StringConstant("larry")),
                                new TypeSwitchCase((ObjectType)SimpleTypes.DOUBLE.getBoxType(), new StringConstant("moe")),
                                new TypeSwitchCase((ObjectType)SimpleTypes.CHAR.getBoxType(),
                                        new BinaryComparison(Tests.EQ,
                                            new GetValue("$caseMatchVar", SimpleTypes.CHAR.getBoxType()),
                                            new CharConstant('c')),
                                        new StringConstant("curly")),
                                new TypeSwitchCase((ObjectType)SimpleTypes.CHAR.getBoxType(),
                                        new BinaryComparison(Tests.EQ,
                                            new GetValue("$caseMatchVar", SimpleTypes.CHAR.getBoxType()),
                                            new CharConstant('s')),
                                        new StringConstant("shemp")),
                                new TypeSwitchCase((ObjectType)SimpleTypes.CHAR.getBoxType(),
                                        new BinaryComparison(Tests.EQ,
                                            new GetValue("$caseMatchVar", SimpleTypes.CHAR.getBoxType()),
                                            new CharConstant('j')),
                                        new StringConstant("curly joe")),
                                new TypeSwitchCase("foobar", new StringConstant("barbaz")),
                                new TypeSwitchCase(42, new StringConstant("universe"))
                        },
                        new StringConstant("nobody")));

        Object result = generator.invokeMethod("TestSwitch",method, 3.14);
        Assertions.assertEquals("moe", result);
        result = generator.invokeMethod("TestSwitch",method, true);
        Assertions.assertEquals("larry", result);
        result = generator.invokeMethod("TestSwitch",method, 'c');
        Assertions.assertEquals("curly", result);
        result = generator.invokeMethod("TestSwitch",method, 's');
        Assertions.assertEquals("shemp", result);
        result = generator.invokeMethod("TestSwitch",method, 'j');
        Assertions.assertEquals("curly joe", result);
        result = generator.invokeMethod("TestSwitch",method, "foobar");
        Assertions.assertEquals("barbaz", result);
        result = generator.invokeMethod("TestSwitch",method, 42);
        Assertions.assertEquals("universe", result);
        result = generator.invokeMethod("TestSwitch",method, 4);
        Assertions.assertEquals("nobody", result);
    }
}
