package org.jfuncmachine.jfuncmachine.examples;

import org.jfuncmachine.jfuncmachine.compiler.classgen.ClassGenerator;
import org.jfuncmachine.jfuncmachine.compiler.classgen.ClassGeneratorOptions;
import org.jfuncmachine.jfuncmachine.compiler.classgen.ClassGeneratorOptionsBuilder;
import org.jfuncmachine.jfuncmachine.compiler.model.Access;
import org.jfuncmachine.jfuncmachine.compiler.model.ClassDef;
import org.jfuncmachine.jfuncmachine.compiler.model.ClassField;
import org.jfuncmachine.jfuncmachine.compiler.model.ConstructorDef;
import org.jfuncmachine.jfuncmachine.compiler.model.MethodDef;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.CallMethod;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.Expression;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.GetValue;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.If;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.InlineCall;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.bool.BinaryComparison;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.bool.tests.Tests;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.constants.LongConstant;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.javainterop.CallJavaConstructor;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.javainterop.CallJavaMethod;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.javainterop.GetJavaStaticField;
import org.jfuncmachine.jfuncmachine.compiler.model.inline.Inlines;
import org.jfuncmachine.jfuncmachine.compiler.model.types.ArrayType;
import org.jfuncmachine.jfuncmachine.compiler.model.types.Field;
import org.jfuncmachine.jfuncmachine.compiler.model.types.ObjectType;
import org.jfuncmachine.jfuncmachine.compiler.model.types.SimpleTypes;
import org.jfuncmachine.jfuncmachine.compiler.model.types.Type;

public class FactorialTimingLocal {
    public static void main(String[] args) {
        try {
            MethodDef factMethod = new MethodDef("fact", Access.PUBLIC,
                    new Field[] { new Field("n", SimpleTypes.LONG),
                        new Field("acc", SimpleTypes.LONG)},
                    SimpleTypes.LONG,
                    new If(new BinaryComparison(Tests.LE, new GetValue("n", SimpleTypes.LONG),
                            new LongConstant(1)),
                            new GetValue("acc", SimpleTypes.LONG),
                            new CallMethod("fact", new Type[] { SimpleTypes.LONG, SimpleTypes.LONG},
                                    SimpleTypes.LONG,
                                    new GetValue("this", new ObjectType()),
                                    new Expression[] {
                                        new InlineCall(Inlines.LongSub, new Expression[] {
                                                new GetValue("n", SimpleTypes.LONG),
                                                new LongConstant(1)}),
                                        new InlineCall(Inlines.LongAdd, new Expression[] {
                                                new GetValue("acc", SimpleTypes.LONG),
                                                new GetValue("n", SimpleTypes.LONG)
                                        })

                                    })
                            ));

            MethodDef mainMethod = new MethodDef("main", Access.PUBLIC + Access.STATIC,
                    new Field[] { new Field("args", new ArrayType(SimpleTypes.STRING))},
                    SimpleTypes.UNIT,
                    new CallJavaMethod("java.io.PrintStream", "println",
                            // Get the PrintStream object from System.out, that is the object
                            // that we will be calling println on
                            SimpleTypes.UNIT, new GetJavaStaticField("java.lang.System", "out",
                            new ObjectType("java.io.PrintStream")),
                            new Expression[] { new CallMethod("fact", new Type[] { SimpleTypes.LONG, SimpleTypes.LONG },
                                SimpleTypes.LONG,
                                    new CallJavaConstructor(null, new Expression[0]),
                                    new Expression[] {
                                            new LongConstant(100000000), new LongConstant(1)
                                    })}));

            ConstructorDef constructor = ConstructorDef.generateWith(new Field[0]);

            ClassGeneratorOptions options = new ClassGeneratorOptionsBuilder()
                    .withLocalTailCallsToLoops(true)
                    .withFullTailCalls(false)
                    .build();

            ClassDef factClass = new ClassDef("org.jfuncmachine.jfuncmachine.examples", "FactorialWithLocal",
                    Access.PUBLIC, new MethodDef[] { constructor, factMethod, mainMethod }, new ClassField[0], new String[0]);
            ClassGenerator generator = new ClassGenerator(options);
            generator.generate(factClass, "test");

            options = new ClassGeneratorOptionsBuilder()
                    .withLocalTailCallsToLoops(false)
                    .withFullTailCalls(true)
                    .build();

            factClass = new ClassDef("org.jfuncmachine.jfuncmachine.examples", "FactorialWithFullTail",
                    Access.PUBLIC, new MethodDef[] { constructor, factMethod, mainMethod }, new ClassField[0], new String[0]);
            generator = new ClassGenerator(options);
            generator.generate(factClass, "test");
        } catch (Exception exc) {
            exc.printStackTrace();
        }

    }
}
