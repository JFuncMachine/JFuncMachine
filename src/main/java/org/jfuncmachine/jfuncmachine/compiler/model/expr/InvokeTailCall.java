package org.jfuncmachine.jfuncmachine.compiler.model.expr;

import org.jfuncmachine.jfuncmachine.compiler.classgen.ClassGenerator;
import org.jfuncmachine.jfuncmachine.compiler.classgen.Environment;
import org.jfuncmachine.jfuncmachine.compiler.classgen.Label;
import org.jfuncmachine.jfuncmachine.compiler.classgen.LambdaIntInfo;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.boxing.Autobox;
import org.jfuncmachine.jfuncmachine.compiler.model.types.BooleanType;
import org.jfuncmachine.jfuncmachine.compiler.model.types.ByteType;
import org.jfuncmachine.jfuncmachine.compiler.model.types.CharType;
import org.jfuncmachine.jfuncmachine.compiler.model.types.DoubleType;
import org.jfuncmachine.jfuncmachine.compiler.model.types.FloatType;
import org.jfuncmachine.jfuncmachine.compiler.model.types.FunctionType;
import org.jfuncmachine.jfuncmachine.compiler.model.types.IntType;
import org.jfuncmachine.jfuncmachine.compiler.model.types.LongType;
import org.jfuncmachine.jfuncmachine.compiler.model.types.ObjectType;
import org.jfuncmachine.jfuncmachine.compiler.model.types.ShortType;
import org.jfuncmachine.jfuncmachine.compiler.model.types.Type;
import org.jfuncmachine.jfuncmachine.runtime.TailCall;
import org.objectweb.asm.Opcodes;

/** An expression to invoke a method reference */
public class InvokeTailCall extends Expression {
    /** The type of the method reference */
    public final Type targetType;
    /** The name of the interface method representing this method */
    public final String intMethod;
    /** The object to invoke the method on */
    public final Expression target;
    /** The method argument values */
    public final Expression[] arguments;
    /** The method parameter types */
    public final Type[] parameterTypes;
    /** The method return type */
    public final Type returnType;

    /** Create a method reference invocation
     * @param targetType The type of the method reference
     * @param target The interface method that represents this method
     * @param arguments The method arguments
     */
    public InvokeTailCall(FunctionType targetType, Expression target, Expression[] arguments) {
        super(null, 0);
        this.target = target;
        this.arguments = arguments;
        this.targetType = targetType;
        this.parameterTypes = targetType.parameterTypes;
        this.returnType = targetType.returnType;
        this.intMethod = null;
    }

    /** Create a method reference invocation
     * @param targetType The type of the method reference
     * @param target The interface method that represents this method
     * @param arguments The method arguments
     * @param filename The source filename this expression is associated with
     * @param lineNumber The source line number this expression is associated with
     */
    public InvokeTailCall(FunctionType targetType, Expression target, Expression[] arguments,
                          String filename, int lineNumber) {
        super(filename, lineNumber);
        this.target = target;
        this.arguments = arguments;
        this.targetType = targetType;
        this.parameterTypes = targetType.parameterTypes;
        this.returnType = targetType.returnType;
        this.intMethod = null;
    }

    /** Create a method reference invocation
     * @param target The interface method that represents this method
     * @param arguments The method arguments
     */
    public InvokeTailCall(Expression target, Expression[] arguments) {
        super(null, 0);
        this.target = target;
        this.arguments = arguments;
        if (!(target.getType() instanceof FunctionType)) {
            throw generateException(String.format(
                    "Can't determine Invoke type because target type (%s) is not type FunctionType",
                    target.getType()));
        }
        this.targetType = target.getType();
        this.parameterTypes = ((FunctionType) targetType).parameterTypes;
        this.returnType = ((FunctionType) targetType).returnType;
        this.intMethod = null;
    }

    /** Create a method reference invocation
     * @param target The interface method that represents this method
     * @param arguments The method arguments
     * @param filename The source filename this expression is associated with
     * @param lineNumber The source line number this expression is associated with
     */
    public InvokeTailCall(Expression target, Expression[] arguments,
                          String filename, int lineNumber) {
        super(filename, lineNumber);
        this.target = target;
        this.arguments = arguments;
        if (!(target.getType() instanceof FunctionType)) {
            throw generateException(String.format(
                    "Can't determine Invoke type because target type (%s) is not type FunctionType",
                    target.getType()));
        }
        this.targetType = target.getType();
        this.parameterTypes = ((FunctionType) targetType).parameterTypes;
        this.returnType = ((FunctionType) targetType).returnType;
        this.intMethod = null;
    }

    /** Create a method reference invocation
     * @param intMethod The name of the interface method
     * @param targetType The type of the method reference
     * @param parameterTypes The method parameter types
     * @param returnType The method return type
     * @param target The interface method that represents this method
     * @param arguments The method arguments
     */
    public InvokeTailCall(String intMethod, Type targetType, Type[] parameterTypes, Type returnType,
                          Expression target, Expression[] arguments) {
        super(null, 0);
        this.target = target;
        this.arguments = arguments;
        this.targetType = targetType;
        this.parameterTypes = parameterTypes;
        this.returnType = returnType;
        this.intMethod = intMethod;
    }

    /** Create a method reference invocation
     * @param intMethod The name of the interface method
     * @param targetType The type of the method reference
     * @param parameterTypes The method parameter types
     * @param returnType The method return type
     * @param target The interface method that represents this method
     * @param arguments The method arguments
     * @param filename The source filename this expression is associated with
     * @param lineNumber The source line number this expression is associated with
     */
    public InvokeTailCall(String intMethod, Type targetType, Type[] parameterTypes, Type returnType,
                          Expression target, Expression[] arguments,
                          String filename, int lineNumber) {
        super(filename, lineNumber);
        this.target = target;
        this.arguments = arguments;
        this.targetType = targetType;
        this.parameterTypes = parameterTypes;
        this.returnType = returnType;
        this.intMethod = intMethod;
    }

    public Type getType() {
        return returnType;
    }

    public void findCaptured(Environment env) {
        for (Expression expr: arguments) {
            expr.findCaptured(env);
        }
        target.findCaptured(env);
    }

    @Override
    public void generate(ClassGenerator generator, Environment env, boolean inTailPosition) {
        String intMethodName = intMethod;

        if (intMethod == null) {
            intMethodName = generator.options.lambdaMethodName;
        }

        target.generate(generator, env, false);
        for (int i=0; i < arguments.length; i++) {
            Expression expr = arguments[i];
            if (generator.options.autobox) {
                expr = Autobox.autobox(expr, parameterTypes[i]);
            }
            expr.generate(generator, env, false);
        }

        String className;

        if (targetType instanceof FunctionType) {
            LambdaIntInfo intInfo = generator.allocateLambdaInt((FunctionType) targetType);
            className = intInfo.packageName + "." + intInfo.name;
        } else if (targetType instanceof ObjectType) {
            className = ((ObjectType) targetType).className;
        } else {
            throw generateException(String.format("Invalid target type for invoke: %s", targetType));
        }

        generator.instGen.invokeinterface(
                generator.className(className),
                intMethodName, generator.methodDescriptor(parameterTypes, returnType));
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