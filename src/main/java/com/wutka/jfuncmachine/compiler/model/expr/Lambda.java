package com.wutka.jfuncmachine.compiler.model.expr;

import com.wutka.jfuncmachine.compiler.classgen.*;
import com.wutka.jfuncmachine.compiler.model.Access;
import com.wutka.jfuncmachine.compiler.model.ClassDef;
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

    public Lambda(Field[] parameters, Type returnType, Expression body) {
        super(null, 0);
        this.parameters = parameters;
        this.returnType = returnType;
        this.body = body;
        parameterTypes = new Type[parameters.length];
        for (int i=0; i < parameters.length; i++) parameterTypes[i] = parameters[i].type;
        this.interfaceType = null;
    }

    public Lambda(Field[] parameters, Type returnType, Expression body,
                  String filename, int lineNumber) {
        super(filename, lineNumber);
        this.parameters = parameters;
        this.returnType = returnType;
        this.body = body;
        parameterTypes = new Type[parameters.length];
        for (int i=0; i < parameters.length; i++) parameterTypes[i] = parameters[i].type;
        this.interfaceType = null;
    }

    public Lambda(Type interfaceType, Field[] parameters, Type returnType, Expression body) {
        super(null, 0);
        this.parameters = parameters;
        this.returnType = returnType;
        this.body = body;
        parameterTypes = new Type[parameters.length];
        for (int i=0; i < parameters.length; i++) parameterTypes[i] = parameters[i].type;
        this.interfaceType = interfaceType;
    }

    public Lambda(Type interfaceType, Field[] parameters, Type returnType, Expression body,
                  String filename, int lineNumber) {
        super(filename, lineNumber);
        this.parameters = parameters;
        this.returnType = returnType;
        this.body = body;
        parameterTypes = new Type[parameters.length];
        for (int i=0; i < parameters.length; i++) parameterTypes[i] = parameters[i].type;
        this.interfaceType = interfaceType;
    }

    public Type getType() {
        if (interfaceType != null) {
            return interfaceType;
        } else {
            return new FunctionType(null, parameterTypes, body.getType());
        }
    }

    public void findCaptured(Environment env) {}

    public void generate(InstructionGenerator generator, Environment env) {
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

        FunctionType extendedType = new FunctionType(null, allParameterTypes, returnType);
        LambdaInfo lambdaInfo = generator.allocateLambda(extendedType);
        LambdaIntInfo intInfo = null;
        Type indyIntType = interfaceType;
        if (indyIntType == null) {
            intInfo = generator.allocateLambdaInt(new FunctionType(null, parameterTypes, returnType));
            indyIntType = new FunctionType(null, capturedParameterTypes,
                    new ObjectType(intInfo.packageName+"."+intInfo.name));
        }

        MethodDef lambdaMethod = new MethodDef(lambdaInfo.name, Access.PRIVATE + Access.STATIC + Access.SYNTHETIC,
                allFields, returnType, body);
        generator.generateLambda(lambdaMethod);

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

            generator.rawIntOpcode(opcode, envVar.value);
        }

        String indyClass;
        if (interfaceType == null) {
            indyClass = intInfo.packageName + "." + intInfo.name;
        } else {
            indyClass = ((ObjectType) interfaceType).className;
        }

        Type[] objectTypes = new ObjectType[parameterTypes.length];
        for (int i=0; i < objectTypes.length; i++) objectTypes[i] = new ObjectType();
        ClassDef generatingClass = generator.getGeneratingClass();
        generator.invokedynamic("apply", Naming.lambdaInDyDescriptor(capturedParameterTypes, indyClass),
                new Handle(Opcodes.H_INVOKESTATIC, "java/lang/invoke/LambdaMetafactory", "metafactory",
                        "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodHandle;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
                        false),
                org.objectweb.asm.Type.getType(Naming.methodDescriptor(objectTypes, new ObjectType())),
                new Handle(Opcodes.H_INVOKESTATIC, lambdaInfo.packageName.replace('.', '/')+
                        "/"+ generatingClass.name, lambdaInfo.name,
                        Naming.lambdaMethodDescriptor(capturedParameterTypes, parameterTypes, returnType), false),
                org.objectweb.asm.Type.getType(Naming.methodDescriptor(parameterTypes, returnType)));
    }
}
