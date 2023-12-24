package org.jfuncmachine.compiler.model.expr;

import org.jfuncmachine.compiler.classgen.ClassGenerator;
import org.jfuncmachine.compiler.classgen.EnvVar;
import org.jfuncmachine.compiler.classgen.Environment;
import org.jfuncmachine.compiler.classgen.Handle;
import org.jfuncmachine.compiler.classgen.Label;
import org.jfuncmachine.compiler.classgen.LambdaInfo;
import org.jfuncmachine.compiler.model.Access;
import org.jfuncmachine.compiler.model.ClassDef;
import org.jfuncmachine.compiler.model.MethodDef;
import org.jfuncmachine.compiler.model.expr.boxing.Autobox;
import org.jfuncmachine.compiler.model.expr.javainterop.CallJavaMethod;
import org.jfuncmachine.compiler.model.types.BooleanType;
import org.jfuncmachine.compiler.model.types.ByteType;
import org.jfuncmachine.compiler.model.types.CharType;
import org.jfuncmachine.compiler.model.types.DoubleType;
import org.jfuncmachine.compiler.model.types.Field;
import org.jfuncmachine.compiler.model.types.FloatType;
import org.jfuncmachine.compiler.model.types.FunctionType;
import org.jfuncmachine.compiler.model.types.IntType;
import org.jfuncmachine.compiler.model.types.LongType;
import org.jfuncmachine.compiler.model.types.ObjectType;
import org.jfuncmachine.compiler.model.types.ShortType;
import org.jfuncmachine.compiler.model.types.Type;
import org.jfuncmachine.runtime.TailCall;
import org.objectweb.asm.Opcodes;

/** An expression to invoke the current method recursively.
 * If local tail call elimination is enabled, which is the default, and this call is made
 * in the tail position, this will be turned into a jump back to the beginning of the
 * method after updating the method parameters.
 *
 * If the call is not a tail call, or local tail call elimination is disabled, this
 * will be generated as a method call. While most of this functionality is already
 * present in the CallMethod and CallStaticMethod expressions, this expression
 * allows lambdas to call themselves recursively.
 */
public class Recurse extends Expression {
    /** The method argument values */
    public final Expression[] arguments;

    /** The return type of the method */
    public final Type returnType;

    /** Create a new method call expression
     * @param returnType The return type of the method
     * @param arguments The method argument values
     */
    public Recurse(Type returnType, Expression[] arguments) {
        super(null, 0);
        this.arguments = arguments;
        this.returnType = returnType;
    }

    /** Create a new method call expression
     * @param returnType The return type of the method
     * @param arguments The method argument values
     * @param filename The source filename this expression is associated with
     * @param lineNumber The source line number this expression is associated with
     */
    public Recurse( Type returnType, Expression[] arguments, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.arguments = arguments;
        this.returnType = returnType;
    }

    /** Create a new method call expression
     * @param name The name of the method
     * @param parameterTypes The types of the method parameters
     * @param returnType The return type of the method
     * @param target The object to invoke the method on
     * @param arguments The method argument values
     */
    public Recurse(String name, Type[] parameterTypes, Type returnType,
                   Expression target, Expression[] arguments) {
        super(null, 0);
        this.arguments = arguments;
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
    public Recurse(String name, Type[] parameterTypes, Type returnType,
                   Expression target, Expression[] arguments,
                   String filename, int lineNumber) {
        super(filename, lineNumber);
        this.arguments = arguments;
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
    }

    public void findCaptured(Environment env) {
        for (Expression expr: arguments) {
            expr.findCaptured(env);
        }
    }

    @Override
    public void generate(ClassGenerator generator, Environment env, boolean inTailPosition) {
        String invokeClassName = generator.currentClass.getFullClassName();
        int[] argumentLocations = new int[arguments.length];

        MethodDef currentMethod = generator.currentMethod;
        Type[] parameterTypes = new Type[currentMethod.parameters.length];
        for (int i=0; i < parameterTypes.length; i++) {
            parameterTypes[i] = currentMethod.parameters[i+currentMethod.numCapturedParameters].type;
        }

        boolean tailCallReturn = inTailPosition && currentMethod.isTailCallable;
        boolean makeTailCall = inTailPosition && !generator.options.localTailCallsToLoops &&
                generator.options.fullTailCalls;
        boolean localCall = inTailPosition && generator.options.localTailCallsToLoops;

        if (!tailCallReturn && !localCall && (currentMethod.access & Access.STATIC) == 0) {
            new GetValue("this", new ObjectType()).generate(generator, env, false);
        }

        if (!tailCallReturn) {
            for (int i = 0; i < arguments.length; i++) {
                Expression expr = arguments[i];
                if (generator.options.autobox) {
                    expr = Autobox.autobox(expr, parameterTypes[i]);
                }
                expr.generate(generator, env, false);
            }
        }

        if (localCall) {
            for (int i = arguments.length-1; i >= 0; i--) {
                EnvVar argVar = env.getVar(currentMethod.parameters[i].name);
                argVar.generateSet(generator);
            }
            generator.instGen.lineNumber(lineNumber);
            generator.instGen.gotolabel(currentMethod.startLabel);
        } else if (tailCallReturn) {
            generateTailLambda(arguments, generator, env);
        } else {
            if (!makeTailCall) {
                generator.instGen.lineNumber(lineNumber);
                if ((currentMethod.access & Access.STATIC) == 0) {
                    generator.instGen.invokevirtual(
                            generator.className(invokeClassName),
                            currentMethod.name, generator.methodDescriptor(parameterTypes, returnType));
                } else {
                    generator.instGen.invokestatic(
                            generator.className(invokeClassName),
                            currentMethod.name, generator.methodDescriptor(parameterTypes, returnType));

                }
            } else {
                generator.instGen.lineNumber(lineNumber);
                generator.instGen.invokevirtual(
                        generator.className(invokeClassName),
                        currentMethod.name+"$$TC$$", generator.methodDescriptor(parameterTypes, new ObjectType()));
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
                    }
                }
            }
        }
    }

    protected void generateTailLambda(Expression[] arguments,
                                   ClassGenerator generator, Environment env) {

        MethodDef currentMethod = generator.currentMethod;
        // Start a capture analysis to see what variables this lambda captures


        if ((currentMethod.access & Access.STATIC) == 0) {
            new GetValue("this", new ObjectType()).generate(generator, env, false);
        }

        if (currentMethod.numCapturedParameters > 0) {
            for (int i=0; i < currentMethod.numCapturedParameters; i++) {
                new GetValue(currentMethod.parameters[i].name, currentMethod.parameters[i].type);
            }
        }

        for (Expression expr: arguments) {
            expr.generate(generator, env, false);
        }

        Type[] parameterTypes = new Type[currentMethod.parameters.length];
        for (int i=0; i < parameterTypes.length; i++) {
            parameterTypes[i] = currentMethod.parameters[i].type;
        }

        String methodName = "invoke";

        Handle handle = new Handle(Handle.INVOKEVIRTUAL, generator.className(generator.currentClass), currentMethod.name,
                    generator.lambdaMethodDescriptor(parameterTypes, new Type[0], new ObjectType()), false);

        Type[] dyParameterTypes = parameterTypes;

        if ((currentMethod.access & Access.STATIC) == 0) {
            dyParameterTypes = new Type[parameterTypes.length+1];
            dyParameterTypes[0] = new ObjectType(generator.currentClass.getFullClassName());
            System.arraycopy(parameterTypes, 0, dyParameterTypes, 1, parameterTypes.length);
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
