package com.wutka.jfuncmachine.compiler.model.expr;

import com.wutka.jfuncmachine.compiler.classgen.*;
import com.wutka.jfuncmachine.compiler.model.Access;
import com.wutka.jfuncmachine.compiler.model.Class;
import com.wutka.jfuncmachine.compiler.model.Method;
import com.wutka.jfuncmachine.compiler.model.types.*;
import org.objectweb.asm.Opcodes;

import java.util.Set;

public class Lambda extends Method {
    public Type[] capturedParameterTypes;
    public Type[] parameterTypes;

    public Lambda(String name, Field[] parameters, Type returnType, Expression body) {
        super(name, Access.PRIVATE + Access.STATIC, parameters, returnType, body);
        parameterTypes = new Type[parameters.length];
        for (int i=0; i < parameters.length; i++) parameterTypes[i] = parameters[i].type;
    }

    public Lambda(String name, Field[] parameters, Type returnType, Expression body,
                  String filename, int lineNumber) {
        super(name, Access.PRIVATE + Access.STATIC, parameters, returnType, body, filename, lineNumber);
        parameterTypes = new Type[parameters.length];
        for (int i=0; i < parameters.length; i++) parameterTypes[i] = parameters[i].type;
    }
    public Type getType() {
        return new FunctionType(name, parameterTypes, body.getType());
    }

    public void findCaptured(Environment env) {}

    public void generate(InstructionGenerator generator, Environment env) {
        env.startCaptureAnalysis();
        body.findCaptured(env);
        Set<EnvVar> capturedValues = env.getCaptured();
        EnvVar[] envVars = capturedValues.toArray(new EnvVar[0]);
        capturedParameterTypes = new Type[envVars.length]       ;
        for (int i=0; i < capturedParameterTypes.length; i++) {
            capturedParameterTypes[i] = envVars[i].type;
        }

        generator.generateLambda(this);

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

        Class generatingClass = generator.getGeneratingClass();
        generator.invokedynamic(name, Naming.methodDescriptor(capturedParameterTypes, returnType),
                new Handle(Opcodes.H_INVOKESTATIC, "java/lang/invoke/LambdaMetafactory", "metafactory",
                        "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodHandle;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
                        false),
                Naming.methodDescriptor(parameterTypes, returnType),
                new Handle(Opcodes.H_INVOKESTATIC, Naming.className(generatingClass), name,
                        Naming.lambdaMethodDescriptor(capturedParameterTypes, parameterTypes, returnType), false),
                Naming.methodDescriptor(parameterTypes, returnType));
    }
}
