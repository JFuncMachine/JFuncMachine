package org.jfuncmachine.compiler.classgen;

import org.jfuncmachine.compiler.model.Access;
import org.jfuncmachine.compiler.model.ClassDef;
import org.jfuncmachine.compiler.model.ClassField;
import org.jfuncmachine.compiler.model.ConstructorDef;
import org.jfuncmachine.compiler.model.MethodDef;
import org.jfuncmachine.compiler.model.expr.Block;
import org.jfuncmachine.compiler.model.expr.Catch;
import org.jfuncmachine.compiler.model.expr.Expression;
import org.jfuncmachine.compiler.model.expr.GetValue;
import org.jfuncmachine.compiler.model.expr.Throw;
import org.jfuncmachine.compiler.model.expr.TryCatchFinally;
import org.jfuncmachine.compiler.model.expr.constants.IntConstant;
import org.jfuncmachine.compiler.model.expr.constants.StringConstant;
import org.jfuncmachine.compiler.model.expr.javainterop.CallJavaConstructor;
import org.jfuncmachine.compiler.model.expr.javainterop.SetJavaField;
import org.jfuncmachine.compiler.model.types.Field;
import org.jfuncmachine.compiler.model.types.ObjectType;
import org.jfuncmachine.compiler.model.types.SimpleTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class TestLineNumbersInException {
    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(ClassGeneratorProvider.class)
    @Retention(RetentionPolicy.RUNTIME)
    private @interface TestAllImplementations {}

    @TestAllImplementations
    public void testThrow(String generatorType, ClassGenerator generator) {
        MethodDef method = new MethodDef("trycatchtest", Access.PUBLIC, new Field[] { },
                SimpleTypes.STRING,
                new Throw(new ObjectType("java.lang.RuntimeException"),
                        new CallJavaConstructor("java.lang.RuntimeException",
                                new Expression[] { new StringConstant("Something happened")},
                                "LineNumberTest.java", 40)));

        ClassDef classDef = new ClassDef("org.jfuncmachine.test", "LineNumberTest",
                Access.PUBLIC, new MethodDef[] {ConstructorDef.generateWith(new Field[0]), method }, new ClassField[0], new String[0],
                "LineNumberTest.java", 42);

        boolean foundFilename = false;
        boolean foundLineNumber = false;
        try {
            Object result = generator.invokeMethod(classDef, method.name);
        } catch (Exception exc) {
            Stack<Throwable> excs = new Stack<>();
            excs.push(exc);
            while (!excs.isEmpty()) {
                Throwable e = excs.pop();
                if (e.getCause() != null) {
                    excs.push(e.getCause());
                }
                for (StackTraceElement elem : e.getStackTrace()) {
                    if (elem.getFileName() != null && elem.getFileName().equals("LineNumberTest.java")) {
                        foundFilename = true;
                        if (elem.getLineNumber() == 40 || elem.getLineNumber() == 42) {
                            foundLineNumber = true;
                        }
                    }
                }
            }
        }
        Assertions.assertTrue(foundFilename, "The filename was not present in the exception");
        Assertions.assertTrue(foundLineNumber, "The line number was not present in the exception");
    }
}
