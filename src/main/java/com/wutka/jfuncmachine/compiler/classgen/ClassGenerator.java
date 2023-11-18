package com.wutka.jfuncmachine.compiler.classgen;

import com.wutka.jfuncmachine.compiler.model.Access;
import com.wutka.jfuncmachine.compiler.model.ClassDef;
import com.wutka.jfuncmachine.compiler.model.ClassField;
import com.wutka.jfuncmachine.compiler.model.MethodDef;
import com.wutka.jfuncmachine.compiler.model.expr.Expression;
import com.wutka.jfuncmachine.compiler.model.expr.boxing.Autobox;
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
import java.util.*;

/**
 * Generates classes from ClassDef structures.
 *
 * A single ClassDef structure can result in multiple classes being generated if
 * the class contains any lambdas. This class can write the generated classes to
 * an output directory, return them as an array of GeneratedClass structures containing
 * class names and byte arrays, or it can immediate load the classes and make them
 * available.
 */
public class ClassGenerator {
    /** The options used for class generation. */
    public final ClassGeneratorOptions options;

    /** The current class being generated. */
    public ClassDef currentClass;

    /** The current method being generated. */
    public MethodDef currentMethod;

    /** A list of lambdas that have been added during class generation. */
    protected List<MethodDef> addedLambdas = new ArrayList<>();

    /** A map of lambda interfaces that have been added during class generation. */
    protected Map<FunctionType, LambdaIntInfo> interfaces = new HashMap<>();

    /** Local variables that belong to the current method being generated. */
    protected List<LocalVariableNode> localVariables = new ArrayList<>();

    /** A list of all the try-catch blocks defined during the current method. */
    protected List<TryCatchBlockNode> tryCatchBlocks = new ArrayList<>();

    /** The serial number of the next lambda to be generated. */
    protected int nextLambda;

    /** The class that emits Java instructions for the method. */
    public InstructionGenerator instGen;

    /** A class loader that can be used to immediately load defined classes. */
    public GeneratedClassLoader classLoader = new GeneratedClassLoader(ClassGenerator.class.getClassLoader());

    protected Map<String,Class> loadedClasses = new HashMap<>();

    /** Creates a new class generator using the default options. */
    public ClassGenerator() {
        this.options = new ClassGeneratorOptions();
    }

    /**
     * Creates a new class generator with the specified options.
     *
     * @param options An instance of ClassGenerator options.
     * @see ClassGeneratorOptionsBuilder
     */
    public ClassGenerator(ClassGeneratorOptions options) {
        this.options = options;
    }

    /**
     * Generates all the classes resulting from the given array of ClassDef objects and writes
     * them to the specified directory.
     *
     * Keep in mind that if a ClassDef file contains a lambda expression, it can result in more
     * than one class definition being generated, since lambdas require an interface definition.
     *
     * @param classes The class definitions of the classes to be generated.
     * @param outputDirectory The name of the directory where the &period;class files should be written.
     * @throws IOException
     */
    public synchronized void generate(ClassDef[] classes, String outputDirectory)
        throws IOException {
        for (ClassDef classDef: classes) {
            generate(classDef, outputDirectory);
        }
    }

    /**
     * Generates all the classes resulting from the given ClassDef object and writes
     * them to the specified directory.
     *
     * Keep in mind that if a ClassDef file contains a lambda expression, it can result in more
     * than one class definition being generated, since lambdas require an interface definition.
     *
     * @param classDef The class definition to be generated
     * @param outputDirectory The name of the directory where the &period;class files should be written.
     * @throws IOException
     */
    public synchronized void generate(ClassDef classDef, String outputDirectory)
            throws IOException {
        GeneratedClass[] classes = generateClassBytes(classDef);

        writeClasses(classes, outputDirectory);
    }

    /**
     * Generates all the classes resulting from the given array of ClassDef objects and returns them
     * as an array of GeneratedClass objects which contain the class name and class bytes.
     *
     * Keep in mind that if a class file contains a lambda expression, it can result in more
     * than one class definition being generated, since lambdas require an interface definition.
     *
     * @param classDefs The class definitions of the classDefs to be generated.
     * @return An array of GeneratedClass objects containing the class bytes for each class
     * @see GeneratedClass
     */
    public synchronized GeneratedClass[] generateClassBytes(ClassDef[] classDefs) {
        List<GeneratedClass> allGenerated = new ArrayList<>();
        for (ClassDef classDef: classDefs) {
            GeneratedClass[] generatedClasses = generateClassBytes(classDef);
            allGenerated.addAll(Arrays.asList(generatedClasses));
        }
        return allGenerated.toArray(new GeneratedClass[0]);
    }

    /**
     * Generates all the classes resulting from the given ClassDef object and returns them
     * as an array of GeneratedClass objects which contain the class name and class bytes.
     *
     * Keep in mind that if a ClassDef file contains a lambda expression, it can result in more
     * than one class definition being generated, since lambdas require an interface definition.
     *
     * @param classDef The class definition to be generated.
     * @return An array of GeneratedClass objects containing the class bytes for each class
     * @see GeneratedClass
     */
    public synchronized GeneratedClass[] generateClassBytes(ClassDef classDef) {
        this.currentClass = classDef;

        List<GeneratedClass> generatedClasses = new ArrayList<>();

        ClassNode newNode = createClassNode(classDef);
        generatedClasses.add(
                new GeneratedClass(classDef.getFullClassName(),
                        classNodeToBytes(newNode)));

        for (LambdaIntInfo info: interfaces.values()) {
            generatedClasses.add(generateLambdaInterface(info));
        }

        interfaces.clear();
        nextLambda = 0;

        return generatedClasses.toArray(new GeneratedClass[0]);
    }

    /**
     * Generates all the classes resulting from the given array of ClassDef objects and loads them
     * into the current Java VM.
     *
     * Keep in mind that if a ClassDef file contains a lambda expression, it can result in more
     * than one class definition being generated, since lambdas require an interface definition.
     *
     * @param classDefs The class definitions of the classes to be generated.
     */
    public synchronized void generateAndLoad(ClassDef[] classDefs) {
        List<GeneratedClass> generated = new ArrayList<>();

        for (ClassDef classDef: classDefs) {
            GeneratedClass[] generatedClasses = generateClassBytes(classDef);
            for (GeneratedClass genClass: generatedClasses) {
                genClass.loadedClass = classLoader.defineClass(genClass.className, genClass.classBytes);
                loadedClasses.put(genClass.className, genClass.loadedClass);
            }
            generated.addAll(Arrays.asList(generatedClasses));
        }

        for (GeneratedClass genClass: generated) {
            classLoader.resolve(genClass.loadedClass);
        }
    }

    /**
     * Generates all the classes resulting from the given ClassDef object and loads them
     * into the current Java VM.
     *
     * Keep in mind that if a ClassDef file contains a lambda expression, it can result in more
     * than one class definition being generated, since lambdas require an interface definition.
     *
     * @param classDef The class definitions of the classes to be generated.
     */
    public synchronized void generateAndLoad(ClassDef classDef) {
        GeneratedClass[] classes = generateClassBytes(classDef);

        for (GeneratedClass genClass: classes) {
            genClass.loadedClass = classLoader.defineClass(genClass.className, genClass.classBytes);
            loadedClasses.put(genClass.className, genClass.loadedClass);
        }

        for (GeneratedClass genClass: classes) {
            classLoader.resolve(genClass.loadedClass);
        }

    }

    /**
     * Convert a ClassNode object into an array of bytes representing the class.
     * @param classNode The ClassNode object to convert
     * @return An array of bytes representing the class described by classNode
     */
    protected byte[] classNodeToBytes(ClassNode classNode) {
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS + ClassWriter.COMPUTE_FRAMES);
        classNode.accept(writer);
        return writer.toByteArray();
    }

    /**
     * Writes an array of generated class files to the specified output directory.
     *
     * @param classes The classes to write
     * @param outputDirectory The destination output directory
     * @throws IOException
     */
    public void writeClasses(GeneratedClass[] classes, String outputDirectory)
        throws IOException {
        for (GeneratedClass genClass: classes) {
            int dotPos = genClass.className.lastIndexOf('.');

            String packageName;
            String className;

            // If there is no . in the class name, there is no package name
            if (dotPos < 0) {
                packageName = null;
                className = genClass.className;
            } else {
                packageName = genClass.className.substring(0, dotPos);
                className = genClass.className.substring(dotPos+1);
            }

            File outDir;
            if (packageName != null) {
                // If there is a package name, make sure all the directories that make up the
                // package have been created
                outDir = new File(outputDirectory + File.separator +
                        packageName.replace('.', File.separatorChar));
                outDir.mkdirs();
            } else {
                // If there is no package, write the file directly to the output directory
                outDir = new File(outputDirectory);
            }

            // Write the file to the destination directory
            Files.write(new File(outDir, className + ".class").toPath(),
                    genClass.classBytes);
        }
    }

    /**
     * Createa a new ClassNode from the given ClassDef file
     *
     * @param classDef The definition for the class to create
     * @return A ClassNode (ObjectWeb ASM) representation of the class
     */
    protected synchronized ClassNode createClassNode(ClassDef classDef) {
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

        newNode.access = classDef.access;
        newNode.name = className(classDef);
        newNode.signature = classSignature(classDef);

        if ((classDef.access & Opcodes.ACC_INTERFACE) == 0) {
            newNode.superName = className(classDef.superPackageName, classDef.superName);

            // Assume that we are going to use lambdas at some point and just add this in now
            newNode.innerClasses.add(
                    new InnerClassNode("java/lang/invoke/MethodHandles$Lookup",
                            "java/lang/invoke/MethodHandles", "Lookup",
                            Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC + Opcodes.ACC_FINAL));
        } else {
            newNode.superName = className(classDef.superPackageName, classDef.superName);
        }

        for (MethodDef methodDef : classDef.methodDefs) {
            // Update the currentMethod field to indicate what method we are currently working on
            currentMethod = methodDef;

            // Generate the bytecode for the method
            MethodNode methodNode = generateMethod(methodDef, classDef);

            // If the method defined any try-catch blocks, add them to the method node
            methodNode.tryCatchBlocks.addAll(tryCatchBlocks);

            // Clear out the accumulated try catch blocks
            tryCatchBlocks.clear();

            // Add the method to the current class
            newNode.methods.add(methodNode);
        }

        // If there were any lambdas generated by the methods, add those now
        for (MethodDef methodDef : addedLambdas) {
            // Update the currentMethod field to indicate what method we are currently working on
            currentMethod = methodDef;

            // Generate the bytecode for the method
            MethodNode methodNode = generateMethod(methodDef, classDef);

            // If the method defined any try-catch blocks, add them to the method node
            methodNode.tryCatchBlocks.addAll(tryCatchBlocks);

            // Clear out the accumulated try catch blocks
            tryCatchBlocks.clear();

            // Add the method to the current class
            newNode.methods.add(methodNode);
        }
        // Clear out the accumulated lambdas
        addedLambdas.clear();

        return newNode;
    }

    /**
     * Generates a method from the given method definition and class definition.
     *
     * @param methodDef The definition of the method
     * @param classDef The definition of the class the method belongs to
     * @return A MethodNode (ObjectWeb ASM) representing the method
     */
    protected synchronized MethodNode generateMethod(MethodDef methodDef, ClassDef classDef) {
        String methodSignature;

        // Reset the array containing any generated local variables
        localVariables = new ArrayList<>();

        // If this method returns a FunctionType, it is returning the result of a lambda.
        // Make sure there is an interface created to describe the lambda
        if (methodDef.getReturnType() instanceof FunctionType funcType) {

            // Allocate a lambda interface in a locally held map of generated lambdas
            LambdaIntInfo info = allocateLambdaInt(funcType);

            // Create a method signature where the return value is the object type of
            // the generated lambda interface
            methodSignature = lambdaReturnDescriptor(methodDef,
                    info.packageName+"."+info.name);
        } else {
            methodSignature = methodDescriptor(methodDef);
        }

        // Create the new method node
        MethodNode newMethod = new MethodNode(methodDef.access, methodDef.name,
                methodSignature, null, null);

        // Set the list of local variables for the new method, this will actually get populated
        // by the instruction generator below
        newMethod.localVariables = localVariables;

        // If the method is not abstract, generate the bytecode for it
        if ((methodDef.access & Access.ABSTRACT) == 0) {

            // Create a generator for this method
            instGen =
                    new InstructionGenerator(this, classDef, newMethod.instructions);

            // Create an empty environment (for local variables)
            Environment env = new Environment(methodDef);

            // Populate the environment with the method parameter names and types
            for (Field f : methodDef.parameters) {
                env.allocate(f.name, f.type);
            }

            // Create a label to mark the beginning of the method, this is used for recursion where a
            // method calling itself can be replaced with a jump back to the beginning of the method
            instGen.label(methodDef.startLabel);

            // If autoboxing is enabled, make sure the result of the method is autoboxed to match
            // its declared return type
            if (options.autobox) {
                Autobox.autobox(methodDef.body, methodDef.returnType).generate(this, env);
            } else {
                // If there is no autoboxing, just generate the method body as-is
                methodDef.body.generate(this, env);
            }

            // Generated a return instruction
            instGen.return_by_type(methodDef.returnType);
        }

        return newMethod;
    }

    /**
     * Generates the bytecode for a lambda interface.
     *
     * @param info A descriptor for the lambda interface and its single method
     * @return A GeneratedClass containing the bytecode and the class name
     */
    public synchronized GeneratedClass generateLambdaInterface(LambdaIntInfo info) {
        String className = info.name;

        Field[] params = new Field[info.type.parameterTypes.length];
        for (int i=0; i < params.length; i++) {
            params[i] = new Field("param"+i, info.type.parameterTypes[i]);
        }

        MethodDef interfaceMethod = new MethodDef(options.lambdaMethodName,
                Access.PUBLIC + Access.ABSTRACT,
                params, info.type.returnType, null);

        ClassDef classDef = new ClassDef(currentClass.packageName, className,
                Access.INTERFACE + Access.PUBLIC + Access.ABSTRACT,
                new MethodDef[] { interfaceMethod }, new ClassField[0]);

        ClassNode newNode = createClassNode(classDef);
        currentClass = classDef;
        currentMethod = interfaceMethod;

        return new GeneratedClass(classDef.getFullClassName(),
                classNodeToBytes(newNode));
    }

    /**
     * Add a method that needs to be generated.
     *
     * This is usually done by the method generation when there is a lambda
     *
     * @param method The method to be genrated
     */
    public synchronized void addMethodToGenerate(MethodDef method) {
        addedLambdas.add(method);
    }

    /**
     * Adds a definition of a local variable that is being used by the current method.
     *
     * @param node A node describing the local variable
     */
    public synchronized void addLocalVariable(LocalVariableNode node) { localVariables.add(node); }

    /**
     * Adds a try-catch block definition that is being generated by the current method.
     *
     * @param node A node describing a try-catch block
     */
    public synchronized void addTryCatch(TryCatchBlockNode node) { tryCatchBlocks.add(node); }


    /**
     * Generates the name of a lambda method that is internal to the class being generated
     *
     * @return A string describing the current lambda
     */
    protected synchronized String generateLambdaName() {
        String name = "lambda$"+nextLambda;
        nextLambda++;
        return name;
    }

    /**
     * Generates a name for a lambda interface encoding its type in the name
     *
     * @param functionType The type of the lambda interface
     * @return A string name of the lambda interface
     */
    protected String generateLambdaIntName(FunctionType functionType) {
        StringBuilder nameBuilder = new StringBuilder();

        // If we aren't sharing lambda interfaces across classes in this package
        // make the interface name start with the current class name
        if (!options.sharedLambdaInterfaces) {
            nameBuilder.append(currentClass.name);
        }

        // Make "Lambda_" part of the name
        nameBuilder.append("Lambda_");

        // Generate a method descriptor that encodes the parameter and return types
        String funcSig = methodDescriptor(functionType.parameterTypes, functionType.returnType);

        // Convert the characters in the name that aren't valid class name characters
        funcSig = funcSig.replace('(', '$');
        funcSig = funcSig.replace(')', '$');
        funcSig = funcSig.replace(";", "$");
        funcSig = funcSig.replace(".", "_");
        funcSig = funcSig.replace("/", "_");
        funcSig = funcSig.replace("[", "Arr_");

        nameBuilder.append(funcSig);
        return nameBuilder.toString();
    }

    /**
     * Allocates a lambda interface name for the given function type and saves it in a map of interfaces.
     *
     * @param functionType The type of the single method in the interface
     * @return A descriptor describing the interface
     */
    public synchronized LambdaIntInfo allocateLambdaInt(FunctionType functionType) {
        LambdaIntInfo info = interfaces.get(functionType);

        // If the interface has already been defined, return its info
        if (info != null) return info;

        info = new LambdaIntInfo(currentClass.packageName, generateLambdaIntName(functionType),
                functionType);
        interfaces.put(functionType, info);
        return info;
    }

    /**
     * Allocates a new lambda interface with a unique name.
     *
     * @param type The type of the lambda method
     * @return A descriptor describing the lambda
     */
    public LambdaInfo allocateLambda(FunctionType type) {

        LambdaInfo info = new LambdaInfo(generateLambdaName(), type);

        return info;
    }

    /**
     * Converts a dot-separated class name into a slash-separated one
     *
     * @param classDef The class definition containing the package name and class name
     * @return A slash-separated class name
     */
    public String className(ClassDef classDef) {
        return className(classDef.packageName, classDef.name);
    }

    /**
     * Converts a dot-separated class name into a slash-separated one
     *
     * @param packageName The package name of the class
     * @param name The name of the class
     * @return A slash-separated class name
     */
    public String className(String packageName, String name) {
        return packageName.replace('.', '/')+"/"+name;
    }

    /**
     * Converts a dot-separated class name into a slash-separated one
     *
     * @param name A class name presumably containing both a package and class name
     * @return A slash-separated class name
     */
    public String className(String name) {
        return name.replace('.', '/');
    }

    /**
     * Returns a class signature name for a class (L + class signature + ;)
     *
     * @param classDef
     * @return
     */
    public String classSignature(ClassDef classDef) {
        return "L"+className(classDef)+";";
    }

    /**
     * Creates a method descriptor for the given method definition. The descriptor encodes the types of
     * the method parameters as well as the return type as per the JVM spec.
     *
     * For example: A method that takes a string and an integer as parameters and returns void would
     * be encoded as: (Ljava/lang/String;I)V
     *
     * @param methodDef The definition of the method to generate the descriptor for
     * @return A string descriptor encoding the types according to the JVM spec
     */
    public String methodDescriptor(MethodDef methodDef) {
        StringBuilder builder = new StringBuilder("(");
        for (Field f: methodDef.parameters) {
            builder.append(getTypeDescriptor(f.type));
        }
        builder.append(")");
        builder.append(getTypeDescriptor(methodDef.getReturnType()));
        return builder.toString();
    }

    /**
     * Generates a method descritor for a method that returns a lambda.
     *
     * @param methodDef The definition of the method
     * @param lambdaInterfaceName The type of interface being returned
     * @return A string descriptor of the method according to the JVM spec
     */
    public String lambdaReturnDescriptor(MethodDef methodDef, String lambdaInterfaceName) {
        StringBuilder builder = new StringBuilder("(");
        for (Field f: methodDef.parameters) {
            builder.append(getTypeDescriptor(f.type));
        }
        builder.append(")");
        builder.append("L"+lambdaInterfaceName.replace('.', '/')+";");
        return builder.toString();
    }

    /**
     * Creates a method descriptor from an array of expressions that represent the method parameters, and the
     * method's return type.
     *
     * @param arguments An array of expressions that represent the methods parameters
     * @param returnType The return type of the method
     * @return A string descriptor of the method according to the JVM spec
     */
    public String methodDescriptor(Expression[] arguments, Type returnType) {
        StringBuilder builder = new StringBuilder("(");
        for (Expression expr: arguments) {
            builder.append(getTypeDescriptor(expr.getType()));
        }
        builder.append(")");
        builder.append(getTypeDescriptor(returnType));
        return builder.toString();
    }

    /**
     * Creates a method descriptor from the given parameter types and return type.
     *
     * @param parameterTypes An array describing the types of the method's parameters
     * @param returnType The return type of the method
     * @return A string descriptor of the method according to the JVM spec
     */
    public String methodDescriptor(Type[] parameterTypes, Type returnType) {
        StringBuilder builder = new StringBuilder("(");
        for (Type type: parameterTypes) {
            builder.append(getTypeDescriptor(type));
        }
        builder.append(")");
        builder.append(getTypeDescriptor(returnType));
        return builder.toString();
    }

    /**
     * Creates a method descriptor specifically used to describe a lambda to the invokesynamic instruction.
     *
     * @param parameterTypes The types of the method's parameters
     * @param className The class name of the lambda's interface
     * @return A string descriptor of the lambda according to the JVM spec
     */
    public String lambdaInDyDescriptor(Type[] parameterTypes, String className) {
        StringBuilder builder = new StringBuilder("(");
        for (Type type: parameterTypes) {
            builder.append(getTypeDescriptor(type));
        }
        builder.append(")");
        builder.append("L"+className.replace('.', '/')+";");
        return builder.toString();
    }

    /**
     * Returns a descriptor of a lambda's method type, which includes the lambda's declared parameters
     * as well as any local variables captured by the lambda.
     * @param capturedParameterTypes The types of the variables captured by the lambda
     * @param parameterTypes The types of the methods declared parameters
     * @param returnType The return type of the lambda
     * @return A string descriptor of the lambda according to the JVM spec
     */
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

    /**
     * Return a JVM type descriptor for a given type. This is done here instead of inside the Type interface
     * in order to handle the FunctionType correctly.
     *
     * @param type The type to return the descriptor for
     * @return A JVM type descriptor for the given type
     */
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

    /** Returns a class previously loaded by the internal classloader.
     *
     * @param className The fully-qualified name of the class to return
     * @return The class if it was loaded, or null if there is no class with that name
     */
    public Class getLoadedClass(String className) {
        return loadedClasses.get(className);
    }
}
