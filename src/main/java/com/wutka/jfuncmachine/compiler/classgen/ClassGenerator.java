package com.wutka.jfuncmachine.compiler.classgen;

import com.wutka.jfuncmachine.compiler.model.Class;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ClassGenerator {
    public final ClassGeneratorOptions options;

    public ClassGenerator() {
        this.options = new ClassGeneratorOptions();
    }

    public ClassGenerator(ClassGeneratorOptions options) {
        this.options = options;
    }

    public void generate(Class clazz, String outputDirectory)
        throws IOException {
        ClassNode newNode = createClassNode(clazz);

        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS + ClassWriter.COMPUTE_FRAMES);
        newNode.accept(writer);

        File outDir = new File(outputDirectory+File.separator+
                clazz.packageName.replace('.', File.separatorChar));
        outDir.mkdirs();
        Files.write(new File(outDir, clazz.name+".class").toPath(), writer.toByteArray());
    }

    public ClassNode createClassNode(Class clazz) {
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
        newNode.access =
                switch (clazz.access) {
                    case Public -> Opcodes.ACC_PUBLIC;
                    case Private -> Opcodes.ACC_PRIVATE;
                    case Protected -> Opcodes.ACC_PROTECTED;
                };
        newNode.name = Naming.className(clazz);
        newNode.signature = Naming.classSignature(clazz);
        newNode.superName = Naming.className(clazz.superPackageName, clazz.superName);

        return newNode;
    }
}
