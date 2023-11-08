package com.wutka.jfuncmachine.compiler.model.expr;

import com.wutka.jfuncmachine.compiler.classgen.*;
import com.wutka.jfuncmachine.compiler.model.Access;
import com.wutka.jfuncmachine.compiler.model.ClassDef;
import com.wutka.jfuncmachine.compiler.model.MethodDef;
import com.wutka.jfuncmachine.compiler.model.types.*;
import org.objectweb.asm.Opcodes;

import java.util.Set;

public class Lambda extends Expression {
    public final String name;
    public final Field[] parameters;
    public final Expression body;
    public final Type returnType;
    public Type[] parameterTypes;

    public Lambda(String name, Field[] parameters, Type returnType, Expression body) {
        super(null, 0);
        this.name = name;
        this.parameters = parameters;
        this.returnType = returnType;
        this.body = body;
        parameterTypes = new Type[parameters.length];
        for (int i=0; i < parameters.length; i++) parameterTypes[i] = parameters[i].type;
    }

    public Lambda(String name, Field[] parameters, Type returnType, Expression body,
                  String filename, int lineNumber) {
        super(filename, lineNumber);
        this.name = name;
        this.parameters = parameters;
        this.returnType = returnType;
        this.body = body;
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
        Type[] capturedParameterTypes = new Type[envVars.length]       ;
        Field[] capturedFields = new Field[envVars.length];
        for (int i=0; i < capturedParameterTypes.length; i++) {
            capturedParameterTypes[i] = envVars[i].type;
            capturedFields[i] = new Field(envVars[i].name, envVars[i].type);
        }

        Field[] allFields  =
                new Field[capturedFields.length + parameters.length];
        for (int i=0; i < capturedFields.length; i++) {
            allFields[i] = capturedFields[i];
        }
        for (int i=0; i < parameterTypes.length; i++) {
            allFields[i+capturedParameterTypes.length] = parameters[i];
        }

        MethodDef lambdaMethod = new MethodDef(name, Access.PRIVATE + Access.STATIC,
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

        ClassDef generatingClass = generator.getGeneratingClass();
        generator.invokedynamic(name, Naming.methodDescriptor(capturedParameterTypes, returnType),
                new Handle(Opcodes.H_INVOKESTATIC, "java/lang/invoke/LambdaMetafactory", "metafactory",
                        "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodHandle;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
                        false),
                Naming.methodDescriptor(capturedParameterTypes, returnType),
                new Handle(Opcodes.H_INVOKESTATIC, Naming.className(generatingClass), name,
                        Naming.lambdaMethodDescriptor(capturedParameterTypes, parameterTypes, returnType), false),
                Naming.methodDescriptor(capturedParameterTypes, returnType));
    }
}
