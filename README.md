![](images/jfuncmachine2.gif)

# JFuncMachine

JFuncMachine is a library for creating functional languages that
compile to Java byte codes. Language developers often make use
of an intermediate language for code generation. For example,
Idris and Racket, as of December 2023, are using Chez Scheme as an
intermediate language. The Idris compiler doesn't have to generate
native machine code, it just generates Scheme code and lets Chez Scheme
do the translation to the native instructions.

JFuncMachine aims to perform that service for the Java Virtual Machine.
While it isn't quite a proper language like Scheme, contains classes
that represent various execution structures that a compiler might
generate, such as if statements, function calls, lambdas, and many other
features.

## Building
You need at least Java 21 and Gradle 8.5 to build JFuncMachine.
Although it can generate code for older versions of Java,
the source code relies on some of the new features of
Java 21.

At some point the library will be published to a Maven
repo, but for now you can build it locally and publish
it to your local repo.

From the root directory of this repo, just do:
```shell
gradlew publish
```

## Getting Started

```java

package org.jfuncmachine.examples;

import classgen.org.jfuncmachine.compiler.ClassGenerator;
import model.org.jfuncmachine.compiler.Access;
import model.org.jfuncmachine.compiler.ClassDef;
import model.org.jfuncmachine.compiler.ClassField;
import model.org.jfuncmachine.compiler.MethodDef;
import expr.model.org.jfuncmachine.compiler.Expression;
import constants.expr.model.org.jfuncmachine.compiler.StringConstant;
import javainterop.expr.model.org.jfuncmachine.compiler.CallJavaMethod;
import javainterop.expr.model.org.jfuncmachine.compiler.GetJavaStaticField;
import types.model.org.jfuncmachine.compiler.ArrayType;
import types.model.org.jfuncmachine.compiler.Field;
import types.model.org.jfuncmachine.compiler.ObjectType;
import types.model.org.jfuncmachine.compiler.SimpleTypes;

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
