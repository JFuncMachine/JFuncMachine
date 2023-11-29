![](images/jfuncmachine2.gif)

# JFuncMachine

JFuncMachine is a library for creating functional languages that
compile to Java byte codes.

There will be more detailed information soon, but here
is an example HelloWorld that creates a "Hello World" program,
loads it, and runs it.

```java

package org.jfuncmachine.jfuncmachine.examples;

import org.jfuncmachine.jfuncmachine.compiler.classgen.ClassGenerator;
import org.jfuncmachine.jfuncmachine.compiler.model.Access;
import org.jfuncmachine.jfuncmachine.compiler.model.ClassDef;
import org.jfuncmachine.jfuncmachine.compiler.model.ClassField;
import org.jfuncmachine.jfuncmachine.compiler.model.MethodDef;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.Expression;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.constants.StringConstant;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.javainterop.CallJavaMethod;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.javainterop.GetJavaStaticField;
import org.jfuncmachine.jfuncmachine.compiler.model.types.ArrayType;
import org.jfuncmachine.jfuncmachine.compiler.model.types.Field;
import org.jfuncmachine.jfuncmachine.compiler.model.types.ObjectType;
import org.jfuncmachine.jfuncmachine.compiler.model.types.SimpleTypes;

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

            // Create a org.jfuncmachine.test.HelloWorld class
            ClassDef newClass = new ClassDef("org.jfuncmachine.test", "HelloWorld",
                    // Make it a public class
                    Access.PUBLIC,
                    // Containing one method, the main method, and no fields
                    new MethodDef[] { main }, new ClassField[0], new String[0]);

            // Create a class generator to generate the class
            ClassGenerator generator = new ClassGenerator();

            // Generate and load the class
            generator.generateAndLoad(newClass);

            // Get the loaded class from the generator's internal class loader
            Class<?> helloWorldClass = generator.getLoadedClass("org.jfuncmachine.test.HelloWorld");

            // Locate the HelloWorld method
            Method mainMethod = helloWorldClass.getMethod("main", String[].class);

            // Invoke it
            mainMethod.invoke(null, new Object[] { new String[0] });

        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }
}
```

There is a shortcut, rather than creating the ClassDef, generating
it and loading it, you can use the invokeMethod method on the
ClassGenerator to directly execute a MethodDef (the ClassGenerator
creates a temporary class, loads it, locates the method, and invokes it).

After the MethodDef in the above Hello World program you could just do:

```java
            // Create a class generator to generate the class
            ClassGenerator generator = new ClassGenerator();

            // Generate and load the class
            generator.invokeMethod(main, (Object) new String[0]);

```

[Javadoc](http://www.jfuncmachine.org/javadoc/)
