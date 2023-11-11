package com.wutka.jfuncmachine.compiler.model.expr;

import com.wutka.jfuncmachine.compiler.classgen.*;
import com.wutka.jfuncmachine.compiler.model.Access;
import com.wutka.jfuncmachine.compiler.model.MethodDef;
import com.wutka.jfuncmachine.compiler.model.types.*;
import org.objectweb.asm.Opcodes;

import java.util.Set;

public class Lambda extends Expression {
    public final Field[] parameters;
    public final Expression body;
    public final Type returnType;
    public final Type interfaceType;
    public Type[] parameterTypes;
    public final boolean useObjectInterface;

    public Lambda(Field[] parameters, Type returnType, boolean useObjectInterface, Expression body) {
        super(null, 0);
        this.parameters = parameters;
        this.returnType = returnType;
        this.useObjectInterface = useObjectInterface;
        this.body = body;
        parameterTypes = new Type[parameters.length];
        for (int i=0; i < parameters.length; i++) parameterTypes[i] = parameters[i].type;
        this.interfaceType = null;
    }

    public Lambda(Field[] parameters, Type returnType, boolean useObjectInterface, Expression body,
                  String filename, int lineNumber) {
        super(filename, lineNumber);
        this.parameters = parameters;
        this.returnType = returnType;
        this.useObjectInterface = useObjectInterface;
        this.body = body;
        parameterTypes = new Type[parameters.length];
        for (int i=0; i < parameters.length; i++) parameterTypes[i] = parameters[i].type;
        this.interfaceType = null;
    }

    public Lambda(Type interfaceType, Field[] parameters, Type returnType, boolean useObjectInterface, Expression body) {
        super(null, 0);
        this.parameters = parameters;
        this.returnType = returnType;
        this.useObjectInterface = useObjectInterface;
        this.body = body;
        parameterTypes = new Type[parameters.length];
        for (int i=0; i < parameters.length; i++) parameterTypes[i] = parameters[i].type;
        this.interfaceType = interfaceType;
    }

    public Lambda(Type interfaceType, Field[] parameters, Type returnType, boolean useObjectInterface, Expression body,
                  String filename, int lineNumber) {
        super(filename, lineNumber);
        this.parameters = parameters;
        this.returnType = returnType;
        this.useObjectInterface = useObjectInterface;
        this.body = body;
        parameterTypes = new Type[parameters.length];
        for (int i=0; i < parameters.length; i++) parameterTypes[i] = parameters[i].type;
        this.interfaceType = interfaceType;
    }

    public Type getType() {
        if (interfaceType != null) {
            return interfaceType;
        } else {
            return new FunctionType(parameterTypes, body.getType());
        }
    }

    public void findCaptured(Environment env) {}

    @Override
    public void generate(ClassGenerator generator, Environment env) {
        // Start a capture analysis to see what variables this lambda captures
        env.startCaptureAnalysis();
        body.findCaptured(env);
        Set<EnvVar> capturedValues = env.getCaptured();

        EnvVar[] envVars = capturedValues.toArray(new EnvVar[0]);
        Type[] capturedParameterTypes = new Type[envVars.length]       ;
        Field[] capturedFields = new Field[envVars.length];
        for (int i=0; i < capturedParameterTypes.length; i++) {
            capturedParameterTypes[i] = envVars[i].type;
            capturedFields[i] = new Field(envVars[i].name, envVars[i].type);
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

        // extendedType is the type of the lambda with the captured parameters included
        FunctionType extendedType = new FunctionType(
                allParameterTypes, returnType);

        // Allocate a method name for this lambda
        LambdaInfo lambdaInfo = generator.allocateLambda(extendedType);
        LambdaIntInfo intInfo = null;
        if (interfaceType == null) {
            // If there was no interface specified to indicate the return type, create one (if necessary)
            intInfo = generator.allocateLambdaInt(new FunctionType(
                    parameterTypes, returnType));
        }

        // Create a declaration for the lambda method
        MethodDef lambdaMethod = new MethodDef(lambdaInfo.name,
                Access.PRIVATE + Access.STATIC + Access.SYNTHETIC,
                allFields, returnType, body);

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

            generator.instGen.rawIntOpcode(opcode, envVar.value);
        }

        String indyClass;
        if (interfaceType == null) {
            // If there was no interface type specified, get the name of the interface generated for this lambda
            indyClass = intInfo.packageName + "." + intInfo.name;
        } else {
            indyClass = ((ObjectType) interfaceType).className;
        }

        org.objectweb.asm.Type signatureType;
        if (useObjectInterface) {
            ObjectType[] objectParams = new ObjectType[parameters.length];
            for (int i = 0; i < objectParams.length; i++) objectParams[i] = new ObjectType();
            signatureType = org.objectweb.asm.Type.getType(generator.methodDescriptor(objectParams, new ObjectType()));
        } else {
            signatureType = org.objectweb.asm.Type.getType(generator.methodDescriptor(parameterTypes, returnType));
        }

        // Call invokedynamic to generate a lambda method handle
        generator.instGen.invokedynamic(ClassGenerator.lambdaIntMethodName,
                generator.lambdaInDyDescriptor(capturedParameterTypes, indyClass),
                // Boilerplate for Java's built-in lambda bootstrap
                new Handle(Opcodes.H_INVOKESTATIC, "java/lang/invoke/LambdaMetafactory", "metafactory",
                        "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodHandle;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
                        false),
                // Create an ASM Type descriptor for this descriptor
                signatureType,
                // Create a handle for the generated lambda method
                new Handle(Opcodes.H_INVOKESTATIC, lambdaInfo.packageName.replace('.', '/')+
                        "/"+ generator.currentClass.name, lambdaInfo.name,
                        generator.lambdaMethodDescriptor(capturedParameterTypes, parameterTypes, returnType), false),
                // Create an another ASM Type descriptor for this descriptor
                org.objectweb.asm.Type.getType(generator.methodDescriptor(parameterTypes, returnType)));
    }
}
