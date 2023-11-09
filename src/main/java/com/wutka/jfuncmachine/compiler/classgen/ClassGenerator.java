package com.wutka.jfuncmachine.compiler.classgen;

import com.wutka.jfuncmachine.compiler.model.Access;
import com.wutka.jfuncmachine.compiler.model.ClassDef;
import com.wutka.jfuncmachine.compiler.model.ClassField;
import com.wutka.jfuncmachine.compiler.model.MethodDef;
import com.wutka.jfuncmachine.compiler.model.types.Field;
import com.wutka.jfuncmachine.compiler.model.types.FunctionType;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InnerClassNode;
import org.objectweb.asm.tree.MethodNode;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassGenerator {
    public final ClassGeneratorOptions options;

    public String currentPackageName;
    public String currentClassName;

    public List<MethodDef> addedLambdas = new ArrayList<>();
    public int nextLambdaInt;
    public int nextLambda;
    public Map<FunctionType, LambdaInterfaceInfo> interfaces = new HashMap<>();

    public ClassGenerator() {
        this.options = new ClassGeneratorOptions();
    }

    public ClassGenerator(ClassGeneratorOptions options) {
        this.options = options;
    }

    public void generate(ClassDef clazz, String outputDirectory)
        throws IOException {
        currentPackageName = clazz.packageName;
        currentClassName = clazz.name;
        ClassNode newNode = createClassNode(clazz);

        writeClassNode(newNode, outputDirectory);

        for (LambdaInterfaceInfo info: interfaces.values()) {
            generateLambdaInterface(info, outputDirectory);
        }

        interfaces.clear();
        nextLambda = 0;
        nextLambdaInt = 0;
    }

    public void writeClassNode(ClassNode newNode, String outputDirectory) throws IOException {
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS + ClassWriter.COMPUTE_FRAMES);
        newNode.accept(writer);

        File outDir = new File(outputDirectory+File.separator+
                currentPackageName.replace('.', File.separatorChar));
        outDir.mkdirs();
        Files.write(new File(outDir, currentClassName+".class").toPath(), writer.toByteArray());
    }

    public ClassNode createClassNode(ClassDef clazz) {
        ClassNode newNode = new ClassNode();
        newNode.version =
                switch (options.javaVersion) {
                    case 1 -> Opcodes.V1_1;
                    case 2 -> Opcodes.V1_2;
                    case 3 -> Opcodes.V1_3;
                    case 4 -> Opcodes.V1_4;
                    case 5 -> Opcodes.V1_5;
                    case 6 -> Opcodes.V1_6;
                    case 7 -> Opcodes.V1_7;
                    case 8 -> Opcodes.V1_8;
                    case 9 -> Opcodes.V9;
                    case 10 -> Opcodes.V10;
                    case 11 -> Opcodes.V11;
                    case 12 -> Opcodes.V12;
                    case 13 -> Opcodes.V13;
                    case 14 -> Opcodes.V14;
                    case 15 -> Opcodes.V15;
                    case 16 -> Opcodes.V16;
                    case 17 -> Opcodes.V17;
                    case 18 -> Opcodes.V18;
                    case 19 -> Opcodes.V19;
                    case 20 -> Opcodes.V20;
                    case 21 -> Opcodes.V21;
                    case 22 -> Opcodes.V22;
                    default -> Opcodes.V21;
                };
        newNode.access = clazz.access;
        newNode.name = Naming.className(clazz);
        newNode.signature = Naming.classSignature(clazz);

        if ((clazz.access & Opcodes.ACC_INTERFACE) == 0) {
            newNode.superName = Naming.className(clazz.superPackageName, clazz.superName);
            newNode.innerClasses.add(
                    new InnerClassNode("java/lang/invoke/MethodHandles$Lookup",
                            "java/lang/invoke/MethodHandles", "Lookup",
                            Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC + Opcodes.ACC_FINAL));
        } else {
            newNode.superName = "java/lang/Object";
        }

        for (MethodDef methodDef : clazz.methodDefs) {
            MethodNode methodNode = generateMethod(methodDef, clazz);
            newNode.methods.add(methodNode);
        }
        for (MethodDef methodDef : addedLambdas) {
            MethodNode methodNode = generateMethod(methodDef, clazz);
            newNode.methods.add(methodNode);
        }
        addedLambdas.clear();

        return newNode;
    }

    public MethodNode generateMethod(MethodDef methodDef, ClassDef clazz) {
        String methodSignature;
        if (methodDef.getReturnType() instanceof FunctionType funcType) {
            LambdaInterfaceInfo info = interfaces.get(funcType);
            methodSignature = Naming.lambdaReturnDescriptor(methodDef,
                    info.packageName+"."+info.name);
        } else {
            methodSignature = Naming.methodDescriptor(methodDef);
        }
        MethodNode newMethod = new MethodNode(methodDef.access, methodDef.name,
                methodSignature, null, null);

        if ((methodDef.access & Access.ABSTRACT) == 0) {
            InstructionGenerator instructionGenerator =
                    new InstructionGenerator(this, clazz, newMethod.instructions);
            Environment env = new Environment(methodDef);
            for (Field f : methodDef.parameters) {
                env.allocate(f.name, f.type);
            }
            instructionGenerator.label(methodDef.startLabel);
            methodDef.body.generate(instructionGenerator, env);
            instructionGenerator.return_by_type(methodDef.returnType);
        }

        return newMethod;
    }

    public void generateLambdaInterface(LambdaInterfaceInfo info, String outputDirectory)
        throws IOException {
        String className = info.name;

        Field[] params = new Field[info.type.parameterTypes.length];
        for (int i=0; i < params.length; i++) {
            params[i] = new Field("param"+i, info.type.parameterTypes[i]);
        }

        MethodDef interfaceMethod = new MethodDef("apply", Access.PUBLIC + Access.ABSTRACT,
                params, info.type.returnType, null);

        ClassDef classDef = new ClassDef(currentPackageName, className,
                Access.INTERFACE + Access.PUBLIC + Access.ABSTRACT,
                new MethodDef[] { interfaceMethod }, new ClassField[0]);

        ClassNode newNode = createClassNode(classDef);

        currentClassName = className;
        writeClassNode(newNode, outputDirectory);
    }

    public void addMethodToGenerate(MethodDef method) {
        addedLambdas.add(method);
    }

    protected String generateLambdaName() {
        String name = "lambda$"+nextLambda;
        nextLambda++;
        return name;
    }

    protected String generateLambdaIntName() {
        String name = currentClassName+"$lambdaint$"+nextLambdaInt;
        nextLambdaInt++;
        return name;
    }

    public LambdaInfo allocateLambda(FunctionType type, FunctionType extendedType) {
        LambdaInterfaceInfo intInfo;
        if (interfaces.containsKey(type)) {
            intInfo = interfaces.get(type);
        } else {
            intInfo = new LambdaInterfaceInfo(currentPackageName, generateLambdaIntName(), type);
        }

        LambdaInfo info = new LambdaInfo(currentPackageName, generateLambdaName(), intInfo, extendedType);

        return info;
    }
}
