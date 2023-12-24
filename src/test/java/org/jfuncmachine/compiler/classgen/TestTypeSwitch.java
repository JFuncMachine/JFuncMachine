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
    @ArgumentsSource(ClassGeneratorProvider.class)
    @Retention(RetentionPolicy.RUNTIME)
    private @interface TestAllImplementations {}

    @TestAllImplementations
    public void testSimpleTypeSwitch(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("switchtest", Access.PUBLIC, new Field[] {
                new Field("x", new ObjectType())},
                SimpleTypes.STRING,
                new TypeSwitch(new GetValue("x", new ObjectType()),
                        new TypeSwitchCase[] {
                            new TypeSwitchCase("java.lang.Boolean", new StringConstant("larry")),
                            new TypeSwitchCase("java.lang.Double", new StringConstant("moe")),
                            new TypeSwitchCase("java.lang.Character", new StringConstant("curly")),
                        },
                    new StringConstant("nobody")));

        Object result = generator.invokeMethod("TestSwitch",method, 3.14);
        Assertions.assertEquals(result, "moe");
        result = generator.invokeMethod("TestSwitch",method, true);
        Assertions.assertEquals(result, "larry");
        result = generator.invokeMethod("TestSwitch",method, 'c');
        Assertions.assertEquals(result, "curly");
        result = generator.invokeMethod("TestSwitch",method, 4);
        Assertions.assertEquals(result, "nobody");
    }

    @TestAllImplementations
    public void testComplexTypeSwitch(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("switchtest", Access.PUBLIC, new Field[] {
                new Field("x", new ObjectType())},
                SimpleTypes.STRING,
                new TypeSwitch(new GetValue("x", new ObjectType()),
                        new TypeSwitchCase[] {
                                new TypeSwitchCase(SimpleTypes.BOOLEAN.getBoxType(), new StringConstant("larry")),
                                new TypeSwitchCase(SimpleTypes.DOUBLE.getBoxType(), new StringConstant("moe")),
                                new TypeSwitchCase(SimpleTypes.CHAR.getBoxType(),
                                        new If(new BinaryComparison(Tests.EQ,
                                                new GetValue("$caseMatchVar", SimpleTypes.CHAR.getBoxType()),
                                                new CharConstant('c')),
                                                new IntConstant(1), new IntConstant(0)),
                                        new StringConstant("curly")),
                                new TypeSwitchCase(SimpleTypes.CHAR.getBoxType(),
                                        new If(new BinaryComparison(Tests.EQ,
                                                new GetValue("$caseMatchVar", SimpleTypes.CHAR.getBoxType()),
                                                new CharConstant('s')),
                                                new IntConstant(1), new IntConstant(0)),
                                        new StringConstant("shemp")),
                                new TypeSwitchCase(SimpleTypes.CHAR.getBoxType(),
                                        new If(new BinaryComparison(Tests.EQ,
                                                new GetValue("$caseMatchVar", SimpleTypes.CHAR.getBoxType()),
                                                new CharConstant('j')),
                                                new IntConstant(1), new IntConstant(0)),
                                        new StringConstant("curly joe")),
                                new TypeSwitchCase("foobar", new StringConstant("barbaz")),
                                new TypeSwitchCase(42, new StringConstant("universe"))
                        },
                        new StringConstant("nobody")));

        Object result = generator.invokeMethod("TestSwitch",method, 3.14);
        Assertions.assertEquals(result, "moe");
        result = generator.invokeMethod("TestSwitch",method, true);
        Assertions.assertEquals(result, "larry");
        result = generator.invokeMethod("TestSwitch",method, 'c');
        Assertions.assertEquals(result, "curly");
        result = generator.invokeMethod("TestSwitch",method, 's');
        Assertions.assertEquals(result, "shemp");
        result = generator.invokeMethod("TestSwitch",method, 'j');
        Assertions.assertEquals(result, "curly joe");
        result = generator.invokeMethod("TestSwitch",method, "foobar");
        Assertions.assertEquals(result, "barbaz");
        result = generator.invokeMethod("TestSwitch",method, 42);
        Assertions.assertEquals(result, "universe");
        result = generator.invokeMethod("TestSwitch",method, 4);
        Assertions.assertEquals(result, "nobody");
    }
}
