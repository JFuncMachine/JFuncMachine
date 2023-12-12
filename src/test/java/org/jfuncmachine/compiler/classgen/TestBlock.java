package org.jfuncmachine.compiler.classgen;

import org.jfuncmachine.compiler.model.Access;
import org.jfuncmachine.compiler.model.MethodDef;
import org.jfuncmachine.compiler.model.expr.Block;
import org.jfuncmachine.compiler.model.expr.Expression;
import org.jfuncmachine.compiler.model.expr.GetValue;
import org.jfuncmachine.compiler.model.expr.InlineCall;
import org.jfuncmachine.compiler.model.expr.constants.ByteConstant;
import org.jfuncmachine.compiler.model.expr.constants.IntConstant;
import org.jfuncmachine.compiler.model.expr.constants.ShortConstant;
import org.jfuncmachine.compiler.model.expr.constants.StringConstant;
import org.jfuncmachine.compiler.model.expr.javainterop.SetJavaField;
import org.jfuncmachine.compiler.model.inline.Inlines;
import org.jfuncmachine.compiler.model.types.Field;
import org.jfuncmachine.compiler.model.types.ObjectType;
import org.jfuncmachine.compiler.model.types.SimpleTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class TestBlock {
    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(ClassGeneratorProvider.class)
    @Retention(RetentionPolicy.RUNTIME)
    private @interface TestAllImplementations {}

    @TestAllImplementations
    public void testBlock(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("blocktest", Access.PUBLIC, new Field[] { },
                SimpleTypes.INT,
                new Block(new Expression[] {
                        new ByteConstant((byte) 3),
                        new IntConstant(5),
                        new StringConstant("foo"),
                        new ShortConstant((short) 7),
                        new IntConstant(42)
                }));

        Object result = generator.invokeMethod("TestBlock",method);
        Assertions.assertEquals(42, (Integer) result);
    }

    @TestAllImplementations
    public void testBlocksInsideExpr(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("blocktest", Access.PUBLIC, new Field[] { },
                SimpleTypes.INT,
                new InlineCall(Inlines.IntAdd,
                    new Expression[]{
                            new Block(new Expression[]{
                                    new ByteConstant((byte) 3),
                                    new IntConstant(5),
                                    new StringConstant("foo"),
                                    new ShortConstant((short) 7),
                                    new IntConstant(20)
                            }),
                            new Block(new Expression[]{
                                    new ByteConstant((byte) 3),
                                    new IntConstant(5),
                                    new StringConstant("foo"),
                                    new ShortConstant((short) 7),
                                    new IntConstant(22)
                            }),
                    }));

        Object result = generator.invokeMethod("TestBlock",method);
        Assertions.assertEquals(42, (Integer) result);
    }

    @TestBlock.TestAllImplementations
    public void testBlock2(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("blocktest", Access.PUBLIC, new Field[] {
                new Field("toy", new ObjectType(ToyClass.class.getName()))
        },
                SimpleTypes.STRING,
                new Block(new Expression[] {
                        new SetJavaField(ToyClass.class.getName(), "memberInt",
                                SimpleTypes.INT,
                                new GetValue("toy", new ObjectType(ToyClass.class.getName())),
                                new IntConstant(73)),
                        new StringConstant("moe")
                }));

        ToyClass toy = new ToyClass(3);
        Object result = generator.invokeMethod("TestBlock",method, toy);
        Assertions.assertEquals(result, "moe");
        Assertions.assertEquals(toy.memberInt, 73);
    }
}