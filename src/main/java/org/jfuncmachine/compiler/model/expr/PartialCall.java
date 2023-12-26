package org.jfuncmachine.compiler.model.expr;

import org.jfuncmachine.compiler.classgen.*;
import org.jfuncmachine.compiler.model.Access;
import org.jfuncmachine.compiler.model.MethodDef;
import org.jfuncmachine.compiler.model.types.*;
import org.objectweb.asm.Opcodes;

import java.util.Set;

/** A lambda (anonymous function) representing a partial call of some kind of function */
public class PartialCall extends Expression {
    /** The lambda parameters */
    public final Field[] parameters;
    /** The body of the lambda */
    public final Expression body;
    /** The return type of the lambda */
    public final Type returnType;
    /** The type of the interface representing the lambda */
    public final Type interfaceType;
    /** The name of the method in the lambda's interface */
    public final String interfaceMethodName;
    /** The types of the lambda parameters */
    public Type[] parameterTypes;
    /** If true, the interface describing this lambda should use objects instead of native types. This allows
     * it to be compatible with the java.util.function package */
    public final boolean useObjectInterface;

    /** The arguments that have already been supplied for the function call */
    public final Expression[] partialArguments;

    /** Create a partial call lambda expression
     * @param parameters The lambda parameters and their types
     * @param returnType The lambda return type
     * @param body The body of the lambda
     * @param partialArguments The arguments that have already been supplied to the function
     */
    public PartialCall(Field[] parameters, Type returnType, Expression[] partialArguments,
                       Expression body) {
        super(null, 0);
        this.parameters = parameters;
        this.returnType = returnType;
        this.partialArguments = partialArguments;
        this.useObjectInterface = false;
        this.interfaceMethodName = null;
        this.body = body;
        parameterTypes = new Type[parameters.length];
        for (int i=0; i < parameters.length; i++) parameterTypes[i] = parameters[i].type;
        this.interfaceType = null;
    }

    /** Create a partial call lambda expression
     * @param parameters The lambda parameters and their types
     * @param returnType The lambda return type
     * @param body The body of the lambda
     * @param partialArguments The arguments that have already been supplied to the function
     * @param filename The name of the source file where this partial call is defined
     * @param lineNumber The line number in the source file where this partial call is defined
     */
    public PartialCall(Field[] parameters, Type returnType, Expression[] partialArguments,
                       Expression body, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.parameters = parameters;
        this.returnType = returnType;
        this.partialArguments = partialArguments;
        this.useObjectInterface = false;
        this.interfaceMethodName = null;
        this.body = body;
        parameterTypes = new Type[parameters.length];
        for (int i=0; i < parameters.length; i++) parameterTypes[i] = parameters[i].type;
        this.interfaceType = null;
    }

    public Type getType() {
        if (interfaceType != null) {
            return interfaceType;
        } else {
            return new FunctionType(parameterTypes, body.getType());
        }
    }

    @Override
    public void reset() {
        body.reset();
    }

    public void findCaptured(Environment env) {
        body.findCaptured(env);
    }

    @Override
    public Expression convertToFullTailCalls(boolean inTailPosition) {
        return this;
    }

    @Override
    public void generate(ClassGenerator generator, Environment env, boolean inTailPosition) {
        // Start a capture analysis to see what variables this lambda captures
        env.startCaptureAnalysis();
        body.findCaptured(env);
        Set<EnvVar> capturedValues = env.getCaptured();

        EnvVar[] envVars = capturedValues.toArray(new EnvVar[0]);
        Type[] capturedParameterTypes = new Type[envVars.length]       ;
        Field[] capturedFields = new Field[envVars.length];
        for (int i=0; i < capturedParameterTypes.length; i++) {
            capturedParameterTypes[i] = envVars[i].type;
            String varName = envVars[i].name;
            if (varName.equals("this")) {
                varName = "$this";
            }
            capturedFields[i] = new Field(varName, envVars[i].type);
        }

        // The lambda's actual type will be a combination of the captured variables
        // first followed by the declared parameters
        Field[] allFields  =
                new Field[capturedFields.length + parameters.length];
        Type[] allParameterTypes =
                new Type[capturedFields.length + parameters.length];
        for (int i=0; i < capturedFields.length; i++) {
            allFields[i] = capturedFields[i];
            allParameterTypes[i] = capturedFields[i].type;
        }
        for (int i=0; i < parameterTypes.length; i++) {
            allFields[i+capturedParameterTypes.length] = parameters[i];
            allParameterTypes[i+capturedParameterTypes.length] = parameters[i].type;
        }

        Type[] allCapturedTypes = new Type[capturedFields.length + partialArguments.length];
        for (int i=0; i < capturedFields.length; i++) {
            allCapturedTypes[i] = capturedParameterTypes[i];
        }
        for (int i=0; i < partialArguments.length; i++) {
            allCapturedTypes[i+capturedFields.length] = parameterTypes[i];
        }

        Type[] restParameterTypes = new Type[allParameterTypes.length-capturedFields.length-partialArguments.length];
        for (int i=0; i < restParameterTypes.length; i++) {
            restParameterTypes[i] = allParameterTypes[i + capturedFields.length + partialArguments.length];
        }

        // extendedType is the type of the lambda with the captured parameters included
        FunctionType extendedType;

        extendedType = new FunctionType(allParameterTypes, returnType);

        LambdaIntInfo intInfo = null;
        LambdaInfo lambdaInfo = generator.allocateLambda(extendedType);

        if (interfaceType == null) {
            // If there was no interface specified to indicate the return type, create one (if necessary)
            intInfo = generator.allocateLambdaInt(new FunctionType(
                    restParameterTypes, returnType));
        }

        MethodDef lambdaMethod = new MethodDef(lambdaInfo.name,
                Access.PRIVATE + Access.STATIC + Access.SYNTHETIC,
                allFields, returnType, body, true, filename, lineNumber);
        lambdaMethod.numCapturedParameters = capturedFields.length;

        // Schedule the generation of the lambda method
        generator.addMethodToGenerate(lambdaMethod);

        for (EnvVar envVar: capturedValues) {
            int opcode = switch (envVar.type) {
                case BooleanType b -> Opcodes.ILOAD;
                case ByteType b -> Opcodes.ILOAD;
                case CharType c -> Opcodes.ILOAD;
                case DoubleType d -> Opcodes.DLOAD;
                case FloatType f -> Opcodes.FLOAD;
                case IntType i -> Opcodes.ILOAD;
                case LongType l -> Opcodes.LLOAD;
                case ShortType s -> Opcodes.ILOAD;
                default -> Opcodes.ALOAD;
            };

            generator.instGen.rawIntOpcode(opcode, envVar.index);
        }

        for (Expression expr: partialArguments) {
            expr.generate(generator, env, false);
        }

        String indyClass;
        if (interfaceType == null) {
            // If there was no interface type specified, get the name of the interface generated for this lambda
            indyClass = intInfo.packageName + "." + intInfo.name;
        } else {
            indyClass = ((ObjectType) interfaceType).className;
        }

        org.objectweb.asm.Type signatureType;
        if (useObjectInterface || generator.options.genericLambdas) {
            ObjectType[] objectParams = new ObjectType[restParameterTypes.length];
            for (int i = 0; i < objectParams.length; i++) objectParams[i] = new ObjectType();
            signatureType = org.objectweb.asm.Type.getType(generator.methodDescriptor(objectParams, new ObjectType()));
        } else {
            signatureType = org.objectweb.asm.Type.getType(generator.methodDescriptor(restParameterTypes,
                    returnType));
        }

        String inDyMethodName = generator.options.lambdaMethodName;
        if (interfaceMethodName != null) {
            inDyMethodName = interfaceMethodName;
        }

        Handle handle = new Handle(Handle.INVOKESTATIC, generator.className(generator.currentClass), lambdaInfo.name,
                generator.lambdaMethodDescriptor(capturedParameterTypes, parameterTypes, returnType),
                false);

        generator.instGen.lineNumber(lineNumber);
        // Call invokedynamic to generate a lambda method handle
        generator.instGen.invokedynamic(inDyMethodName,
                generator.lambdaInDyDescriptor(allCapturedTypes, indyClass),
                // Boilerplate for Java's built-in lambda bootstrap
                new Handle(Handle.INVOKESTATIC, "java/lang/invoke/LambdaMetafactory", "metafactory",
                        "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodHandle;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
                        false),
                // Create an ASM Type descriptor for this descriptor
                signatureType,
                // Create a handle for the generated lambda method
                handle,
                // Create an another ASM Type descriptor for this descriptor
                org.objectweb.asm.Type.getType(generator.methodDescriptor(restParameterTypes, returnType)));
    }
}
