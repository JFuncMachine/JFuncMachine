package com.wutka.jfuncmachine.examples;

import com.wutka.jfuncmachine.compiler.classgen.ClassGenerator;
import com.wutka.jfuncmachine.compiler.model.Access;
import com.wutka.jfuncmachine.compiler.model.ClassDef;
import com.wutka.jfuncmachine.compiler.model.ClassField;
import com.wutka.jfuncmachine.compiler.model.MethodDef;
import com.wutka.jfuncmachine.compiler.model.expr.Expression;
import com.wutka.jfuncmachine.compiler.model.expr.constants.StringConstant;
import com.wutka.jfuncmachine.compiler.model.expr.javaintop.CallJavaMethod;
import com.wutka.jfuncmachine.compiler.model.expr.javaintop.GetJavaStaticField;
import com.wutka.jfuncmachine.compiler.model.types.ArrayType;
import com.wutka.jfuncmachine.compiler.model.types.Field;
import com.wutka.jfuncmachine.compiler.model.types.ObjectType;
import com.wutka.jfuncmachine.compiler.model.types.SimpleTypes;

import java.lang.reflect.Method;

public class HelloWorld {
    public static void main(String[] args) {
        try {
            // Create a public static method named "main"
            MethodDef main = new MethodDef("main", Access.PUBLIC + Access.STATIC,
                    // That takes one argument called "args" that is an array of String
                    new Field[] { new Field("args", new ArrayType(SimpleTypes.STRING)) },
                    // The main method returns type Unit (aka void)
                    SimpleTypes.UNIT,
                    // The only thing the function should do is call System.out.println, which means
                    // call the println method on the static PrintStream field named out in the System class
                    // So create an expression that calls the println method
                    new CallJavaMethod("java.io.PrintStream", "println",
                            // Get the PrintStream object from System.out, that is the object
                            // that we will be calling println on
                            SimpleTypes.UNIT, new GetJavaStaticField("java.lang.System", "out",
                                    new ObjectType("java.io.PrintStream")),
                            // Load up the arguments to println, which is just one, that is a string constant
                            new Expression[] { new StringConstant("Hello World!") }
                            // the function returns void (which in functional languages is called Unit)
                    ));

            // Create a com.wutka.test.HelloWorld class
            ClassDef newClass = new ClassDef("com.wutka.test", "HelloWorld",
                    // Make it a public class
                    Access.PUBLIC,
                    // Containing one method, the main method, and no fields
                    new MethodDef[] { main }, new ClassField[0]);

            // Create a class generator to generate the class
            ClassGenerator generator = new ClassGenerator();

            // Generate and load the class
            generator.generateAndLoad(newClass);

            // Get the loaded class from the generator's internal class loader
            Class<?> helloWorldClass = generator.getLoadedClass("com.wutka.test.HelloWorld");

            // Locate the HelloWorld method
            Method mainMethod = helloWorldClass.getMethod("main", String[].class);

            // Invoke it
            mainMethod.invoke(null, new Object[] { new String[0] });

        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }
}
