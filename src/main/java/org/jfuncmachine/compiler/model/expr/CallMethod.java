package org.jfuncmachine.compiler.model.expr;

import org.jfuncmachine.compiler.classgen.*;
import org.jfuncmachine.compiler.model.Access;
import org.jfuncmachine.compiler.model.ClassDef;
import org.jfuncmachine.compiler.model.MethodDef;
import org.jfuncmachine.compiler.model.expr.boxing.Autobox;
import org.jfuncmachine.compiler.model.expr.javainterop.CallJavaMethod;
import org.jfuncmachine.compiler.model.types.*;
import org.jfuncmachine.runtime.TailCall;
import org.objectweb.asm.Opcodes;

/** An expression to invoke a method.
 * This method differs from the methods in the javainterop package in that it can apply certain optimizations.
 * For example, if this method call is a recursive call to the method currently being defined, and the
 * localTailCallsToLoops option is true, this expression updates the local variables representing the
 * method parameters and jumps back to the beginning of the method.
 * Likewise, if full tail recursion is enabled, this method should be used for any methods generated by
 * JFuncMachine so it can implement the looping necessary to handle full tail calls.
 */
public class CallMethod extends Expression {
    /** The name of the class containing the method */
    public final String className;
    /** The name of the method to call */
    public final String name;
    /** The object to call the method on */
    public final Expression target;
    /** The method argument values */
    public final Expression[] arguments;
    /** The types of the method arguments */
    public final Type[] parameterTypes;
    /** The return type of the method */
    public final Type returnType;

    /** Create a new method call expression
     * @param className The name of the class containing the method
     * @param name The name of the method
     * @param parameterTypes The types of the method parameters
     * @param returnType The return type of the method
     * @param target The object to invoke the method on
     * @param arguments The method argument values
     */
    public CallMethod(String className, String name, Type[] parameterTypes, Type returnType,
                      Expression target, Expression[] arguments) {
        super(null, 0);
        this.className = className;
        this.name = name;
        this.target = target;
        this.arguments = arguments;
        this.parameterTypes = parameterTypes;
        this.returnType = returnType;
    }

    /** Create a new method call expression
     * @param className The name of the class containing the method
     * @param name The name of the method
     * @param parameterTypes The types of the method parameters
     * @param returnType The return type of the method
     * @param target The object to invoke the method on
     * @param arguments The method argument values
     * @param filename The source filename this expression is associated with
     * @param lineNumber The source line number this expression is associated with
     */
    public CallMethod(String className, String name, Type[] parameterTypes, Type returnType,
                      Expression target, Expression[] arguments,
                      String filename, int lineNumber) {
        super(filename, lineNumber);
        this.className = className;
        this.name = name;
        this.target = target;
        this.arguments = arguments;
        this.parameterTypes = parameterTypes;
        this.returnType = returnType;
    }

    /** Create a new method call expression
     * @param name The name of the method
     * @param parameterTypes The types of the method parameters
     * @param returnType The return type of the method
     * @param target The object to invoke the method on
     * @param arguments The method argument values
     */
    public CallMethod(String name, Type[] parameterTypes, Type returnType,
                      Expression target, Expression[] arguments) {
        super(null, 0);
        this.className = null;
        this.name = name;
        this.target = target;
        this.arguments = arguments;
        this.parameterTypes = parameterTypes;
        this.returnType = returnType;
    }

    /** Create a new method call expression that calls the current method recursively
     * @param parameterTypes The types of the method parameters
     * @param returnType The return type of the method
     * @param target The object to invoke the method on
     * @param arguments The method argument values
     */
    public CallMethod(Type[] parameterTypes, Type returnType,
                      Expression target, Expression[] arguments) {
        super(null, 0);
        this.className = null;
        this.name = null;
        this.target = target;
        this.arguments = arguments;
        this.parameterTypes = parameterTypes;
        this.returnType = returnType;
    }

    /** Create a new method call expression
     * @param name The name of the method
     * @param parameterTypes The types of the method parameters
     * @param returnType The return type of the method
     * @param target The object to invoke the method on
     * @param arguments The method argument values
     * @param filename The source filename this expression is associated with
     * @param lineNumber The source line number this expression is associated with
     */
    public CallMethod(String name, Type[] parameterTypes, Type returnType,
                      Expression target, Expression[] arguments,
                      String filename, int lineNumber) {
        super(filename, lineNumber);
        this.className = null;
        this.name = name;
        this.target = target;
        this.arguments = arguments;
        this.parameterTypes = parameterTypes;
        this.returnType = returnType;
    }

    /** Create a new method call expression that calls the current method recursively
     * @param parameterTypes The types of the method parameters
     * @param returnType The return type of the method
     * @param target The object to invoke the method on
     * @param arguments The method argument values
     * @param filename The source filename this expression is associated with
     * @param lineNumber The source line number this expression is associated with
     */
    public CallMethod(Type[] parameterTypes, Type returnType,
                      Expression target, Expression[] arguments,
                      String filename, int lineNumber) {
        super(filename, lineNumber);
        this.className = null;
        this.name = null;
        this.target = target;
        this.arguments = arguments;
        this.parameterTypes = parameterTypes;
        this.returnType = returnType;
    }

    public Type getType() {
        return returnType;
    }

    @Override
    public void reset() {
        for (Expression expr: arguments) {
            expr.reset();
        }
        target.reset();
    }

    public void findCaptured(Environment env) {
        for (Expression expr: arguments) {
            expr.findCaptured(env);
        }
        target.findCaptured(env);
    }

    @Override
    public Expression convertToFullTailCalls(boolean inTailPosition) {
        if (inTailPosition && getType().getJVMTypeRepresentation() != 'A') {
            return new CallMethod(className, name, parameterTypes, new ObjectType(),
                    target, arguments, filename, lineNumber);
        }
        return this;
    }

    @Override
    public void generate(ClassGenerator generator, Environment env, boolean inTailPosition) {
        String invokeClassName = className;
        if (invokeClassName == null) {
            invokeClassName = generator.currentClass.getFullClassName();
        }

        String methodName = name;

        if (methodName == null) {
            methodName = generator.currentMethod.name;
            if (methodName.endsWith("$$TC$$")) {
                methodName = methodName.substring(0, methodName.length()-6);
            }
        }

        int[] argumentLocations = new int[arguments.length];
        int argPos = 1;

        boolean tailCallReturn = inTailPosition && generator.currentMethod.isTailCallable;
        boolean makeTailCall = inTailPosition &&
                !(generator.options.localTailCallsToLoops &&
                        (name == null || isCurrentFunc(generator.currentClass, generator.currentMethod))) &&
                generator.options.fullTailCalls;
        boolean localCall = inTailPosition &&
                generator.options.localTailCallsToLoops &&
                    (name == null || isCurrentFunc(generator.currentClass, generator.currentMethod));

        if (!tailCallReturn && !localCall) {
            target.generate(generator, env, false);
        }

        if (localCall || !tailCallReturn) {
            for (int i = 0; i < arguments.length; i++) {
                Expression expr = arguments[i];
                if (generator.options.autobox) {
                    expr = Autobox.autobox(expr, parameterTypes[i]);
                }
                expr.generate(generator, env, false);
                argumentLocations[i] = argPos;
                argPos += parameterTypes[i].getStackSize();
            }
        }

        if (localCall) {
            for (int i = arguments.length-1; i >= 0; i--) {
                generator.instGen.rawIntOpcode(EnvVar.setOpcode(arguments[i].getType()), argumentLocations[i]);
            }
            generator.instGen.lineNumber(lineNumber);
            generator.instGen.gotolabel(generator.currentMethod.startLabel);
        } else if (tailCallReturn) {
            generateTailLambda(invokeClassName, methodName, parameterTypes, target, arguments, generator, env);
        } else {
            if (!makeTailCall) {
                generator.instGen.lineNumber(lineNumber);
                generator.instGen.invokevirtual(
                        generator.className(invokeClassName),
                        methodName, generator.methodDescriptor(parameterTypes, returnType));
            } else {
                generator.instGen.lineNumber(lineNumber);
                generator.instGen.invokevirtual(
                        generator.className(invokeClassName),
                        methodName+"$$TC$$", generator.methodDescriptor(parameterTypes, new ObjectType()));
                if (!tailCallReturn) {
                    Label loopStart = new Label();
                    Label loopEnd = new Label();
                    generator.instGen.label(loopStart);
                    generator.instGen.dup();
                    generator.instGen.instance_of(generator.className(TailCall.class.getName()));
                    generator.instGen.rawJumpOpcode(Opcodes.IFEQ, loopEnd);
                    generator.instGen.invokeinterface(generator.className(TailCall.class.getName()), "invoke",
                            generator.methodDescriptor(new Type[0], new ObjectType()));
                    generator.instGen.gotolabel(loopStart);
                    generator.instGen.label(loopEnd);
                    if (returnType.getBoxTypeName() != null) {
                        switch (returnType) {
                            case BooleanType b -> {
                                generator.instGen.checkcast(b.getBoxTypeName());
                                generator.instGen.invokevirtual("java.lang.Boolean",
                                        "booleanValue", "()Z");
                            }
                            case ByteType b -> {
                                generator.instGen.checkcast(b.getBoxTypeName());
                                generator.instGen.invokevirtual("java.lang.Byte",
                                        "byteValue", "()B");
                            }
                            case CharType c -> {
                                generator.instGen.checkcast(c.getBoxTypeName());
                                generator.instGen.invokevirtual("java.lang.Character",
                                        "charValue", "()C");
                            }
                            case DoubleType d -> {
                                generator.instGen.checkcast(d.getBoxTypeName());
                                generator.instGen.invokevirtual("java.lang.Double",
                                        "doubleValue", "()D");
                            }
                            case FloatType f -> {
                                generator.instGen.checkcast(f.getBoxTypeName());
                                generator.instGen.invokevirtual("java.lang.Float",
                                        "floatValue", "()F");
                            }
                            case IntType i -> {
                                generator.instGen.checkcast(i.getBoxTypeName());
                                generator.instGen.invokevirtual("java.lang.Integer",
                                        "intValue", "()I");
                            }
                            case LongType l -> {
                                generator.instGen.checkcast(l.getBoxTypeName());
                                generator.instGen.invokevirtual("java.lang.Long",
                                        "longValue", "()J");
                            }
                            case ShortType s -> {
                                generator.instGen.checkcast(s.getBoxTypeName());
                                generator.instGen.invokevirtual("java.lang.Short",
                                        "shortValue", "()S");
                            }
                            default -> {
                            }
                        }
                    } else if (returnType instanceof StringType) {
                        generator.instGen.checkcast("java/lang/String");
                    } else if (returnType instanceof ObjectType objectType && !objectType.equals(new ObjectType())) {
                        generator.instGen.checkcast(generator.className(objectType.className));
                    }
                }
            }
        }
    }

    protected boolean isCurrentFunc(ClassDef currentClass, MethodDef currentMethod) {
        if ((currentMethod.access & Access.STATIC) != 0) return false;
        if (className != null && !className.equals(currentClass.getFullClassName())) return false;
        if (!name.equals(currentMethod.name)) return false;
        if (parameterTypes.length != currentMethod.parameters.length) return false;
        for (int i=0; i < parameterTypes.length; i++) {
            if (!parameterTypes[i].equals(currentMethod.parameters[i].type)) return false;
        }
        return true;
    }

    protected void generateTailLambda(String invokeClassName, String name, Type[] parameterTypes,
                                   Expression target, Expression[] arguments,
                                   ClassGenerator generator, Environment env) {
        // Start a capture analysis to see what variables this lambda captures

        LambdaInfo lambdaInfo = generator.allocateLambda(new FunctionType(parameterTypes, new ObjectType()));

        Field[] lambdaFields = new Field[arguments.length];
        for (int i=0; i < lambdaFields.length; i++) {
            lambdaFields[i] = new Field("arg"+i, arguments[i].getType());
        }
        Expression[] lambdaArguments = new Expression[arguments.length];
        for (int i=0; i < lambdaArguments.length; i++) {
            lambdaArguments[i] = new GetValue("arg"+i, arguments[i].getType());
        }
        // Create a declaration for the lambda method
        MethodDef lambdaMethod = new MethodDef(lambdaInfo.name,
                    Access.PRIVATE + Access.SYNTHETIC,
                    lambdaFields, new ObjectType(),
                new CallJavaMethod(invokeClassName, name+"$$TC$$", parameterTypes, new ObjectType(),
                        new GetValue("this", new ObjectType()),
                        lambdaArguments));

        // Schedule the generation of the lambda method
        generator.addMethodToGenerate(lambdaMethod);

        target.generate(generator, env, false);
        for (Expression expr: arguments) {
            expr.generate(generator, env, false);
        }

        String methodName = "invoke";

        Handle handle = new Handle(Handle.INVOKEVIRTUAL, generator.className(generator.currentClass), lambdaInfo.name,
                    generator.lambdaMethodDescriptor(parameterTypes, new Type[0], new ObjectType()), false);

        Type[] dyParameterTypes = new Type[parameterTypes.length+1];
        for (int i=0; i < parameterTypes.length; i++) {
            dyParameterTypes[i+1] = parameterTypes[i];
        }
        dyParameterTypes[0] = new ObjectType(generator.currentClass.getFullClassName());
        generator.instGen.lineNumber(lineNumber);
        // Call invokedynamic to generate a lambda method handle
        generator.instGen.invokedynamic(methodName,
                generator.lambdaInDyDescriptor(dyParameterTypes, TailCall.class.getName()),
                // Boilerplate for Java's built-in lambda bootstrap
                new Handle(Handle.INVOKESTATIC, "java/lang/invoke/LambdaMetafactory", "metafactory",
                        "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodHandle;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
                        false),
                // Create an ASM Type descriptor for this descriptor
                org.objectweb.asm.Type.getType(generator.methodDescriptor(new Type[0], new ObjectType())),
                // Create a handle for the generated lambda method
                handle,
                // Create an another ASM Type descriptor for this descriptor
                org.objectweb.asm.Type.getType(generator.methodDescriptor(new Type[0],  new ObjectType())));

    }
}
