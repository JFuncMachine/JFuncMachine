package com.wutka.jfuncmachine.compiler.classgen;

import com.wutka.jfuncmachine.compiler.model.Access;
import com.wutka.jfuncmachine.compiler.model.ClassDef;
import com.wutka.jfuncmachine.compiler.model.ClassField;
import com.wutka.jfuncmachine.compiler.model.MethodDef;
import com.wutka.jfuncmachine.compiler.model.expr.Expression;
import com.wutka.jfuncmachine.compiler.model.types.ArrayType;
import com.wutka.jfuncmachine.compiler.model.types.BooleanType;
import com.wutka.jfuncmachine.compiler.model.types.ByteType;
import com.wutka.jfuncmachine.compiler.model.types.CharType;
import com.wutka.jfuncmachine.compiler.model.types.DoubleType;
import com.wutka.jfuncmachine.compiler.model.types.Field;
import com.wutka.jfuncmachine.compiler.model.types.FloatType;
import com.wutka.jfuncmachine.compiler.model.types.FunctionType;
import com.wutka.jfuncmachine.compiler.model.types.IntType;
import com.wutka.jfuncmachine.compiler.model.types.LongType;
import com.wutka.jfuncmachine.compiler.model.types.ObjectType;
import com.wutka.jfuncmachine.compiler.model.types.ShortType;
import com.wutka.jfuncmachine.compiler.model.types.StringType;
import com.wutka.jfuncmachine.compiler.model.types.Type;
import com.wutka.jfuncmachine.compiler.model.types.UnitType;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InnerClassNode;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TryCatchBlockNode;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassGenerator {
    public static final String lambdaIntMethodName = "apply";
    public final ClassGeneratorOptions options;

    public ClassDef currentClass;
    public MethodDef currentMethod;

    protected List<MethodDef> addedLambdas = new ArrayList<>();
    protected Map<FunctionType, LambdaIntInfo> interfaces = new HashMap<>();
    protected List<LocalVariableNode> localVariables = new ArrayList<>();
    protected List<TryCatchBlockNode> tryCatchBlocks = new ArrayList<>();
    protected int nextLambdaInt;
    protected int nextLambda;

    public InstructionGenerator instGen;

    public ClassGenerator() {
        this.options = new ClassGeneratorOptions();
    }

    public ClassGenerator(ClassGeneratorOptions options) {
        this.options = options;
    }

    public void generate(ClassDef clazz, String outputDirectory)
        throws IOException {
        this.currentClass = clazz;
        ClassNode newNode = createClassNode(clazz);

        writeClassNode(newNode, outputDirectory);

        for (LambdaIntInfo info: interfaces.values()) {
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
                currentClass.packageName.replace('.', File.separatorChar));
        outDir.mkdirs();
        Files.write(new File(outDir, currentClass.name+".class").toPath(),
                writer.toByteArray());
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
        newNode.name = className(clazz);
        newNode.signature = classSignature(clazz);

        if ((clazz.access & Opcodes.ACC_INTERFACE) == 0) {
            newNode.superName = className(clazz.superPackageName, clazz.superName);

            // Assume that we are going to use lambdas at some point and just add this in now
            newNode.innerClasses.add(
                    new InnerClassNode("java/lang/invoke/MethodHandles$Lookup",
                            "java/lang/invoke/MethodHandles", "Lookup",
                            Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC + Opcodes.ACC_FINAL));
        } else {
            newNode.superName = "java/lang/Object";
        }

        for (MethodDef methodDef : clazz.methodDefs) {
            currentMethod = methodDef;
            MethodNode methodNode = generateMethod(methodDef, clazz);
            methodNode.tryCatchBlocks.addAll(tryCatchBlocks);
            tryCatchBlocks.clear();
            newNode.methods.add(methodNode);
        }
        for (MethodDef methodDef : addedLambdas) {
            currentMethod = methodDef;
            MethodNode methodNode = generateMethod(methodDef, clazz);
            methodNode.tryCatchBlocks.addAll(tryCatchBlocks);
            tryCatchBlocks.clear();
            newNode.methods.add(methodNode);
        }
        addedLambdas.clear();

        return newNode;
    }

    public MethodNode generateMethod(MethodDef methodDef, ClassDef clazz) {
        String methodSignature;
        localVariables = new ArrayList<>();
        if (methodDef.getReturnType() instanceof FunctionType funcType) {
            LambdaIntInfo info = allocateLambdaInt(funcType);
            methodSignature = lambdaReturnDescriptor(methodDef,
                    info.packageName+"."+info.name);
        } else {
            methodSignature = methodDescriptor(methodDef);
        }
        MethodNode newMethod = new MethodNode(methodDef.access, methodDef.name,
                methodSignature, null, null);

        newMethod.localVariables = localVariables;
        if ((methodDef.access & Access.ABSTRACT) == 0) {
            instGen =
                    new InstructionGenerator(this, clazz, newMethod.instructions);
            Environment env = new Environment(methodDef);
            for (Field f : methodDef.parameters) {
                env.allocate(f.name, f.type);
            }
            instGen.label(methodDef.startLabel);
            methodDef.body.generate(this, env);
            instGen.return_by_type(methodDef.returnType);
        }

        return newMethod;
    }

    public void generateLambdaInterface(LambdaIntInfo info, String outputDirectory)
        throws IOException {
        String className = info.name;

        Field[] params = new Field[info.type.parameterTypes.length];
        for (int i=0; i < params.length; i++) {
            params[i] = new Field("param"+i, info.type.parameterTypes[i]);
        }

        MethodDef interfaceMethod = new MethodDef(ClassGenerator.lambdaIntMethodName,
                Access.PUBLIC + Access.ABSTRACT,
                params, info.type.returnType, null);

        ClassDef classDef = new ClassDef(currentClass.packageName, className,
                Access.INTERFACE + Access.PUBLIC + Access.ABSTRACT,
                new MethodDef[] { interfaceMethod }, new ClassField[0]);

        ClassNode newNode = createClassNode(classDef);
        currentClass = classDef;
        currentMethod = interfaceMethod;

        writeClassNode(newNode, outputDirectory);
    }

    public void addMethodToGenerate(MethodDef method) {
        addedLambdas.add(method);
    }

    public void addLocalVariable(LocalVariableNode node) { localVariables.add(node); }

    public void addTryCatch(TryCatchBlockNode node) { tryCatchBlocks.add(node); }


    protected String generateLambdaName() {
        String name = "lambda$"+nextLambda;
        nextLambda++;
        return name;
    }

    protected String generateLambdaIntName() {
        String name = currentClass.name+"$lambdaint$"+nextLambdaInt;
        nextLambdaInt++;
        return name;
    }

    public LambdaIntInfo allocateLambdaInt(FunctionType functionType) {
        LambdaIntInfo info = interfaces.get(functionType);
        if (info != null) return info;
        info = new LambdaIntInfo(currentClass.packageName, generateLambdaIntName(),
                functionType);
        interfaces.put(functionType, info);
        return info;
    }

    public LambdaInfo allocateLambda(FunctionType type) {

        LambdaInfo info = new LambdaInfo(currentClass.packageName,
                generateLambdaName(), type);

        return info;
    }

    public String className(ClassDef clazz) {
        return className(clazz.packageName, clazz.name);
    }

    public String className(String packageName, String name) {
        return packageName.replace('.', '/')+"/"+name;
    }

    public String className(String name) {
        return name.replace('.', '/');
    }

    public String classSignature(ClassDef clazz) {
        return "L"+className(clazz)+";";
    }

    public String methodDescriptor(MethodDef methodDef) {
        StringBuilder builder = new StringBuilder("(");
        for (Field f: methodDef.parameters) {
            builder.append(getTypeDescriptor(f.type));
        }
        builder.append(")");
        builder.append(getTypeDescriptor(methodDef.getReturnType()));
        return builder.toString();
    }

    public String lambdaReturnDescriptor(MethodDef methodDef, String lambdaInterfaceName) {
        StringBuilder builder = new StringBuilder("(");
        for (Field f: methodDef.parameters) {
            builder.append(getTypeDescriptor(f.type));
        }
        builder.append(")");
        builder.append("L"+lambdaInterfaceName.replace('.', '/')+";");
        return builder.toString();
    }

    public String methodDescriptor(Expression[] arguments, Type returnType) {
        StringBuilder builder = new StringBuilder("(");
        for (Expression expr: arguments) {
            builder.append(getTypeDescriptor(expr.getType()));
        }
        builder.append(")");
        builder.append(getTypeDescriptor(returnType));
        return builder.toString();
    }

    public String methodDescriptor(Type[] parameterTypes, Type returnType) {
        StringBuilder builder = new StringBuilder("(");
        for (Type type: parameterTypes) {
            builder.append(getTypeDescriptor(type));
        }
        builder.append(")");
        builder.append(getTypeDescriptor(returnType));
        return builder.toString();
    }

    public String lambdaInDyDescriptor(Type[] parameterTypes, String className) {
        StringBuilder builder = new StringBuilder("(");
        for (Type type: parameterTypes) {
            builder.append(getTypeDescriptor(type));
        }
        builder.append(")");
        builder.append("L"+className.replace('.', '/')+";");
        return builder.toString();
    }

    public String lambdaMethodDescriptor(Type[] capturedParameterTypes, Type[] parameterTypes,
                                                Type returnType) {
        StringBuilder builder = new StringBuilder("(");
        for (Type type: capturedParameterTypes) {
            builder.append(getTypeDescriptor(type));
        }
        for (Type type: parameterTypes) {
            builder.append(getTypeDescriptor(type));
        }
        builder.append(")");
        builder.append(getTypeDescriptor(returnType));
        return builder.toString();
    }

    // This used to be in the Type interface, but because of the way interfaces are
    // allocated for function types, the interface for a function type isn't known
    // until generation time
    public String getTypeDescriptor(Type type) {
        return switch (type) {
            case ArrayType at -> "[" + getTypeDescriptor(at.containedType());
            case BooleanType b -> "Z";
            case ByteType b -> "B";
            case CharType c -> "C";
            case DoubleType d -> "D";
            case FloatType f -> "F";
            case FunctionType f -> getTypeDescriptor(allocateLambdaInt(f).getObjectType());
            case IntType i -> "I";
            case LongType l -> "J";
            case ObjectType o -> "L" + o.className.replace('.', '/') + ";";
            case ShortType s -> "S";
            case StringType s -> "Ljava/lang/String;";
            case UnitType v -> "V";

        };
    }
}
