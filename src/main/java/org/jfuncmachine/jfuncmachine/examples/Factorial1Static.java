package org.jfuncmachine.jfuncmachine.examples;

import org.jfuncmachine.jfuncmachine.compiler.classgen.ClassGenerator;
import org.jfuncmachine.jfuncmachine.compiler.classgen.ClassGeneratorOptions;
import org.jfuncmachine.jfuncmachine.compiler.classgen.ClassGeneratorOptionsBuilder;
import org.jfuncmachine.jfuncmachine.compiler.model.Access;
import org.jfuncmachine.jfuncmachine.compiler.model.MethodDef;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.CallStaticMethod;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.Expression;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.GetValue;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.If;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.InlineCall;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.bool.BinaryComparison;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.bool.tests.Tests;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.constants.LongConstant;
import org.jfuncmachine.jfuncmachine.compiler.model.inline.Inlines;
import org.jfuncmachine.jfuncmachine.compiler.model.types.Field;
import org.jfuncmachine.jfuncmachine.compiler.model.types.SimpleTypes;
import org.jfuncmachine.jfuncmachine.compiler.model.types.Type;

public class Factorial1Static {
    public static void main(String[] args) {
        try {
            MethodDef factMethod = new MethodDef("fact", Access.PUBLIC + Access.STATIC,
                    new Field[] { new Field("n", SimpleTypes.LONG),
                        new Field("acc", SimpleTypes.LONG)},
                    SimpleTypes.LONG,
                    new If(new BinaryComparison(Tests.LE, new GetValue("n", SimpleTypes.LONG),
                            new LongConstant(1)),
                            new GetValue("acc", SimpleTypes.LONG),
                            new CallStaticMethod("fact", new Type[] { SimpleTypes.LONG, SimpleTypes.LONG},
                                    SimpleTypes.LONG,
                                    new Expression[] {
                                        new InlineCall(Inlines.LongSub, new Expression[] {
                                                new GetValue("n", SimpleTypes.LONG),
                                                new LongConstant(1)}),
                                        new InlineCall(Inlines.LongMul, new Expression[] {
                                                new GetValue("acc", SimpleTypes.LONG),
                                                new GetValue("n", SimpleTypes.LONG)
                                        })

                                    })
                            ));

            ClassGeneratorOptions options = new ClassGeneratorOptionsBuilder()
                    .withLocalTailCallsToLoops(true)
                    .withFullTailCalls(true)
                    .build();
            ClassGenerator generator = new ClassGenerator(options);
            Object result = generator.invokeMethod("Factorial", factMethod, 20, 1l);
            System.out.println(result);
        } catch (Exception exc) {
            exc.printStackTrace();
        }

    }
}