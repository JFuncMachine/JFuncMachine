package com.wutka.jfuncmachine.compiler.classgen;

import com.wutka.jfuncmachine.compiler.exceptions.JFuncMachineException;
import com.wutka.jfuncmachine.compiler.model.ClassDef;
import com.wutka.jfuncmachine.compiler.model.MethodDef;
import com.wutka.jfuncmachine.compiler.model.types.*;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.lang.reflect.Method;

public class InstructionGenerator {
    protected ClassGenerator classGen;
    protected ClassDef generatingClass;
    protected InsnList instructionList;

    protected InstructionGenerator(ClassGenerator classGen, ClassDef generatingClass, InsnList instructionList) {
        this.classGen = classGen;
        this.generatingClass = generatingClass;
        this.instructionList = instructionList;
    }

    public InstructionGenerator aaload() { instructionList.add(new InsnNode(Opcodes.AALOAD)); return this; }
    public InstructionGenerator aastore() { instructionList.add(new InsnNode(Opcodes.AASTORE)); return this; }
    public InstructionGenerator aconst_null() { instructionList.add(new InsnNode(Opcodes.AASTORE)); return this; }
    public InstructionGenerator aload(int index) { instructionList.add(new VarInsnNode(Opcodes.ALOAD, index)); return this; }
    public InstructionGenerator anewarray(String type) { instructionList.add(new TypeInsnNode(Opcodes.ANEWARRAY, type)); return this; }
    public InstructionGenerator areturn() { instructionList.add(new InsnNode(Opcodes.ARETURN)); return this; }
    public InstructionGenerator arraylength() { instructionList.add(new InsnNode(Opcodes.ARRAYLENGTH)); return this; }
    public InstructionGenerator astore(int index) { instructionList.add(new VarInsnNode(Opcodes.ASTORE, index)); return this; }
    public InstructionGenerator athrow() { instructionList.add(new InsnNode(Opcodes.ATHROW)); return this; }
    public InstructionGenerator baload() { instructionList.add(new InsnNode(Opcodes.BALOAD)); return this; }
    public InstructionGenerator bastore() { instructionList.add(new InsnNode(Opcodes.BASTORE)); return this; }
    public InstructionGenerator bipush(int value) { instructionList.add(new IntInsnNode(Opcodes.BIPUSH, value)); return this; }
    public InstructionGenerator caload() { instructionList.add(new InsnNode(Opcodes.CALOAD)); return this; }
    public InstructionGenerator castore() { instructionList.add(new InsnNode(Opcodes.CASTORE)); return this; }
    public InstructionGenerator checkcast(String type) { instructionList.add(new TypeInsnNode(Opcodes.CHECKCAST, type)); return this; }
    public InstructionGenerator d2f() { instructionList.add(new InsnNode(Opcodes.D2F)); return this; }
    public InstructionGenerator d2i() { instructionList.add(new InsnNode(Opcodes.D2I)); return this; }
    public InstructionGenerator d2l() { instructionList.add(new InsnNode(Opcodes.D2L)); return this; }
    public InstructionGenerator dadd() { instructionList.add(new InsnNode(Opcodes.DADD)); return this; }
    public InstructionGenerator daload() { instructionList.add(new InsnNode(Opcodes.DALOAD)); return this; }
    public InstructionGenerator dastore() { instructionList.add(new InsnNode(Opcodes.DASTORE)); return this; }
    public InstructionGenerator dcmpg() { instructionList.add(new InsnNode(Opcodes.DCMPG)); return this; }
    public InstructionGenerator dcmpl() { instructionList.add(new InsnNode(Opcodes.DCMPL)); return this; }
    public InstructionGenerator dconst_0() { instructionList.add(new InsnNode(Opcodes.DCONST_0)); return this; }
    public InstructionGenerator dconst_1() { instructionList.add(new InsnNode(Opcodes.DCONST_1)); return this; }
    public InstructionGenerator ddiv() { instructionList.add(new InsnNode(Opcodes.DDIV)); return this; }
    public InstructionGenerator dload(int index) { instructionList.add(new VarInsnNode(Opcodes.DLOAD, index)); return this; }
    public InstructionGenerator dmul() { instructionList.add(new InsnNode(Opcodes.DMUL)); return this; }
    public InstructionGenerator dneg() { instructionList.add(new InsnNode(Opcodes.DNEG)); return this; }
    public InstructionGenerator drem() { instructionList.add(new InsnNode(Opcodes.DREM)); return this; }
    public InstructionGenerator dreturn() { instructionList.add(new InsnNode(Opcodes.DRETURN)); return this; }
    public InstructionGenerator dstore(int index) { instructionList.add(new VarInsnNode(Opcodes.DSTORE, index)); return this; }
    public InstructionGenerator dsub() { instructionList.add(new InsnNode(Opcodes.DSUB)); return this; }
    public InstructionGenerator dup() { instructionList.add(new InsnNode(Opcodes.DUP)); return this; }
    public InstructionGenerator dup_x1() { instructionList.add(new InsnNode(Opcodes.DUP_X1)); return this; }
    public InstructionGenerator dup_x2() { instructionList.add(new InsnNode(Opcodes.DUP_X2)); return this; }
    public InstructionGenerator dup2() { instructionList.add(new InsnNode(Opcodes.DUP2)); return this; }
    public InstructionGenerator dup2_x1() { instructionList.add(new InsnNode(Opcodes.DUP2_X1)); return this; }
    public InstructionGenerator dup2_x2() { instructionList.add(new InsnNode(Opcodes.DUP2_X2)); return this; }
    public InstructionGenerator f2d() { instructionList.add(new InsnNode(Opcodes.F2D)); return this; }
    public InstructionGenerator f2i() { instructionList.add(new InsnNode(Opcodes.F2I)); return this; }
    public InstructionGenerator f2l() { instructionList.add(new InsnNode(Opcodes.F2L)); return this; }
    public InstructionGenerator fadd() { instructionList.add(new InsnNode(Opcodes.FADD)); return this; }
    public InstructionGenerator faload() { instructionList.add(new InsnNode(Opcodes.FALOAD)); return this; }
    public InstructionGenerator fastore() { instructionList.add(new InsnNode(Opcodes.FASTORE)); return this; }
    public InstructionGenerator fcmpg() { instructionList.add(new InsnNode(Opcodes.FCMPG)); return this; }
    public InstructionGenerator fcmpl() { instructionList.add(new InsnNode(Opcodes.FCMPL)); return this; }
    public InstructionGenerator fconst_0() { instructionList.add(new InsnNode(Opcodes.FCONST_0)); return this; }
    public InstructionGenerator fconst_1() { instructionList.add(new InsnNode(Opcodes.FCONST_1)); return this; }
    public InstructionGenerator fconst_2() { instructionList.add(new InsnNode(Opcodes.FCONST_2)); return this; }
    public InstructionGenerator fdiv() { instructionList.add(new InsnNode(Opcodes.FDIV)); return this; }
    public InstructionGenerator fload(int index) { instructionList.add(new VarInsnNode(Opcodes.FLOAD, index)); return this; }
    public InstructionGenerator fmul() { instructionList.add(new InsnNode(Opcodes.FMUL)); return this; }
    public InstructionGenerator fneg() { instructionList.add(new InsnNode(Opcodes.FNEG)); return this; }
    public InstructionGenerator frem() { instructionList.add(new InsnNode(Opcodes.FREM)); return this; }
    public InstructionGenerator freturn() { instructionList.add(new InsnNode(Opcodes.FRETURN)); return this; }
    public InstructionGenerator fstore(int index) { instructionList.add(new VarInsnNode(Opcodes.FSTORE, index)); return this; }
    public InstructionGenerator fsub() { instructionList.add(new InsnNode(Opcodes.FSUB)); return this; }
    public InstructionGenerator getfield(String owner, String name, String descriptor) {
        instructionList.add(new FieldInsnNode(Opcodes.GETFIELD, owner, name, descriptor));
        return this;
    }
    public InstructionGenerator getstatic(String owner, String name, String descriptor) {
        instructionList.add(new FieldInsnNode(Opcodes.GETSTATIC, owner, name, descriptor));
        return this;
    }
    public InstructionGenerator gotolabel(Label label) { instructionList.add(new JumpInsnNode(Opcodes.GOTO, new LabelNode(label.label))); return this; }
    public InstructionGenerator i2b() { instructionList.add(new InsnNode(Opcodes.I2B)); return this; }
    public InstructionGenerator i2c() { instructionList.add(new InsnNode(Opcodes.I2C)); return this; }
    public InstructionGenerator i2d() { instructionList.add(new InsnNode(Opcodes.I2D)); return this; }
    public InstructionGenerator i2f() { instructionList.add(new InsnNode(Opcodes.I2F)); return this; }
    public InstructionGenerator i2l() { instructionList.add(new InsnNode(Opcodes.I2L)); return this; }
    public InstructionGenerator i2s() { instructionList.add(new InsnNode(Opcodes.I2S)); return this; }
    public InstructionGenerator iadd() { instructionList.add(new InsnNode(Opcodes.IADD)); return this; }
    public InstructionGenerator iaload() { instructionList.add(new InsnNode(Opcodes.IALOAD)); return this; }
    public InstructionGenerator iand() { instructionList.add(new InsnNode(Opcodes.IAND)); return this; }
    public InstructionGenerator iastore() { instructionList.add(new InsnNode(Opcodes.IASTORE)); return this; }
    public InstructionGenerator iconst_m1() { instructionList.add(new InsnNode(Opcodes.ICONST_M1)); return this; }
    public InstructionGenerator iconst_0() { instructionList.add(new InsnNode(Opcodes.ICONST_0)); return this; }
    public InstructionGenerator iconst_1() { instructionList.add(new InsnNode(Opcodes.ICONST_1)); return this; }
    public InstructionGenerator iconst_2() { instructionList.add(new InsnNode(Opcodes.ICONST_2)); return this; }
    public InstructionGenerator iconst_3() { instructionList.add(new InsnNode(Opcodes.ICONST_3)); return this; }
    public InstructionGenerator iconst_4() { instructionList.add(new InsnNode(Opcodes.ICONST_4)); return this; }
    public InstructionGenerator iconst_5() { instructionList.add(new InsnNode(Opcodes.ICONST_5)); return this; }
    public InstructionGenerator idiv() { instructionList.add(new InsnNode(Opcodes.IDIV)); return this; }
    public InstructionGenerator if_acmpeq(Label label) { instructionList.add(new JumpInsnNode(Opcodes.IF_ACMPEQ, new LabelNode(label.label))); return this; }
    public InstructionGenerator if_acmpne(Label label) { instructionList.add(new JumpInsnNode(Opcodes.IF_ACMPNE, new LabelNode(label.label))); return this; }
    public InstructionGenerator if_icmpeq(Label label) { instructionList.add(new JumpInsnNode(Opcodes.IF_ICMPEQ, new LabelNode(label.label))); return this; }
    public InstructionGenerator if_icmpne(Label label) { instructionList.add(new JumpInsnNode(Opcodes.IF_ICMPNE, new LabelNode(label.label))); return this; }
    public InstructionGenerator if_icmplt(Label label) { instructionList.add(new JumpInsnNode(Opcodes.IF_ICMPLT, new LabelNode(label.label))); return this; }
    public InstructionGenerator if_icmpge(Label label) { instructionList.add(new JumpInsnNode(Opcodes.IF_ICMPGE, new LabelNode(label.label))); return this; }
    public InstructionGenerator if_icmpgt(Label label) { instructionList.add(new JumpInsnNode(Opcodes.IF_ICMPGT, new LabelNode(label.label))); return this; }
    public InstructionGenerator if_icmple(Label label) { instructionList.add(new JumpInsnNode(Opcodes.IF_ICMPLE, new LabelNode(label.label))); return this; }
    public InstructionGenerator ifeq(Label label) { instructionList.add(new JumpInsnNode(Opcodes.IFEQ, new LabelNode(label.label))); return this; }
    public InstructionGenerator ifne(Label label) { instructionList.add(new JumpInsnNode(Opcodes.IFNE, new LabelNode(label.label))); return this; }
    public InstructionGenerator iflt(Label label) { instructionList.add(new JumpInsnNode(Opcodes.IFLT, new LabelNode(label.label))); return this; }
    public InstructionGenerator ifge(Label label) { instructionList.add(new JumpInsnNode(Opcodes.IFGE, new LabelNode(label.label))); return this; }
    public InstructionGenerator ifgt(Label label) { instructionList.add(new JumpInsnNode(Opcodes.IFGT, new LabelNode(label.label))); return this; }
    public InstructionGenerator ifle(Label label) { instructionList.add(new JumpInsnNode(Opcodes.IFLE, new LabelNode(label.label))); return this; }
    public InstructionGenerator ifnonnull(Label label) { instructionList.add(new JumpInsnNode(Opcodes.IFNONNULL, new LabelNode(label.label))); return this; }
    public InstructionGenerator ifnull(Label label) { instructionList.add(new JumpInsnNode(Opcodes.IFNULL, new LabelNode(label.label))); return this; }
    public InstructionGenerator iinc(int index, int incr) { instructionList.add(new IincInsnNode(index, incr)); return this; }
    public InstructionGenerator iload(int index) { instructionList.add(new VarInsnNode(Opcodes.ILOAD, index)); return this; }
    public InstructionGenerator imul() { instructionList.add(new InsnNode(Opcodes.IMUL)); return this; }
    public InstructionGenerator ineg() { instructionList.add(new InsnNode(Opcodes.INEG)); return this; }
    public InstructionGenerator instance_of(String type) { instructionList.add(new TypeInsnNode(Opcodes.INSTANCEOF, type)); return this; }
    public InstructionGenerator invokedynamic(String name, String descriptor, Handle handle, Object... bootstrapArgs) {
        for (int i=0; i < bootstrapArgs.length; i++) {
            if (bootstrapArgs[i] instanceof Handle jfHandle) {
                bootstrapArgs[i] = jfHandle.getAsmHandle();
            }
        }
        instructionList.add(new InvokeDynamicInsnNode(name, descriptor, handle.getAsmHandle(), bootstrapArgs));
        return this;
    }
    public InstructionGenerator invokeinterface(String owner, String name, String descriptor) {
        instructionList.add(new MethodInsnNode(Opcodes.INVOKEINTERFACE, owner, name, descriptor));
        return this;
    }
    public InstructionGenerator invokespecial(String owner, String name, String descriptor) {
        instructionList.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, owner, name, descriptor));
        return this;
    }
    public InstructionGenerator invokestatic(String owner, String name, String descriptor) {
        instructionList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, owner, name, descriptor));
        return this;
    }
    public InstructionGenerator invokevirtual(String owner, String name, String descriptor) {
        instructionList.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, owner, name, descriptor));
        return this;
    }
    public InstructionGenerator ior() { instructionList.add(new InsnNode(Opcodes.IOR)); return this; }
    public InstructionGenerator irem() { instructionList.add(new InsnNode(Opcodes.IREM)); return this; }
    public InstructionGenerator ireturn() { instructionList.add(new InsnNode(Opcodes.IRETURN)); return this; }
    public InstructionGenerator ishl() { instructionList.add(new InsnNode(Opcodes.ISHL)); return this; }
    public InstructionGenerator ishr() { instructionList.add(new InsnNode(Opcodes.ISHR)); return this; }
    public InstructionGenerator istore(int index) { instructionList.add(new VarInsnNode(Opcodes.ISTORE, index)); return this; }
    public InstructionGenerator isub() { instructionList.add(new InsnNode(Opcodes.ISUB)); return this; }
    public InstructionGenerator iushr() { instructionList.add(new InsnNode(Opcodes.IUSHR)); return this; }
    public InstructionGenerator ixor() { instructionList.add(new InsnNode(Opcodes.IXOR)); return this; }
    public InstructionGenerator jsr(Label label) { instructionList.add(new JumpInsnNode(Opcodes.JSR, new LabelNode(label.label))); return this; }
    public InstructionGenerator l2d() { instructionList.add(new InsnNode(Opcodes.L2D)); return this; }
    public InstructionGenerator l2f() { instructionList.add(new InsnNode(Opcodes.L2F)); return this; }
    public InstructionGenerator l2i() { instructionList.add(new InsnNode(Opcodes.L2I)); return this; }
    public InstructionGenerator label(Label label) { instructionList.add(new LabelNode(label.label)); return this; }
    public InstructionGenerator ladd() { instructionList.add(new InsnNode(Opcodes.LADD)); return this; }
    public InstructionGenerator laload() { instructionList.add(new InsnNode(Opcodes.LALOAD)); return this; }
    public InstructionGenerator land() { instructionList.add(new InsnNode(Opcodes.LAND)); return this; }
    public InstructionGenerator lastore() { instructionList.add(new InsnNode(Opcodes.LASTORE)); return this; }
    public InstructionGenerator lcmp() { instructionList.add(new InsnNode(Opcodes.LCMP)); return this; }
    public InstructionGenerator lconst_0() { instructionList.add(new InsnNode(Opcodes.LCONST_0)); return this; }
    public InstructionGenerator lconst_1() { instructionList.add(new InsnNode(Opcodes.LCONST_1)); return this; }
    public InstructionGenerator ldc(double d) { instructionList.add(new LdcInsnNode(d)); return this; }
    public InstructionGenerator ldc(int i) { instructionList.add(new LdcInsnNode(i)); return this; }
    public InstructionGenerator ldc(float f) { instructionList.add(new LdcInsnNode(f)); return this; }
    public InstructionGenerator ldc(long l) { instructionList.add(new LdcInsnNode(l)); return this; }
    public InstructionGenerator ldc(String s) { instructionList.add(new LdcInsnNode(s)); return this; }
    public InstructionGenerator ldc(Method m) { instructionList.add(new LdcInsnNode(m)); return this; }
    public InstructionGenerator ldc(Handle h) { instructionList.add(new LdcInsnNode(h.getAsmHandle())); return this; }
    public InstructionGenerator ldc(ConstantDynamic cd) { instructionList.add(new LdcInsnNode(cd.getAsmConstantDynamic())); return this; }
    public InstructionGenerator ldc(Object o) { instructionList.add(new LdcInsnNode(o)); return this; }
    public InstructionGenerator ldiv() { instructionList.add(new InsnNode(Opcodes.LDIV)); return this; }
    public InstructionGenerator lload(int index) { instructionList.add(new VarInsnNode(Opcodes.LLOAD, index)); return this; }
    public InstructionGenerator lmul() { instructionList.add(new InsnNode(Opcodes.LMUL)); return this; }
    public InstructionGenerator lneg() { instructionList.add(new InsnNode(Opcodes.LNEG)); return this; }
    public InstructionGenerator lookupswitch(Label default_label, int[] keys, Label[] labels) {
        LabelNode[] labelNodes = new LabelNode[labels.length];
        for (int i=0; i < labels.length; i++) {
            labelNodes[i] = new LabelNode(labels[i].label);
        }
        instructionList.add(
                new LookupSwitchInsnNode(new LabelNode(default_label.label), keys, labelNodes));
        return this;
    }
    public InstructionGenerator lor() { instructionList.add(new InsnNode(Opcodes.LOR)); return this; }
    public InstructionGenerator lrem() { instructionList.add(new InsnNode(Opcodes.LREM)); return this; }
    public InstructionGenerator lreturn() { instructionList.add(new InsnNode(Opcodes.LRETURN)); return this; }
    public InstructionGenerator lshl() { instructionList.add(new InsnNode(Opcodes.LSHL)); return this; }
    public InstructionGenerator lshr() { instructionList.add(new InsnNode(Opcodes.LSHR)); return this; }
    public InstructionGenerator lstore(int index) { instructionList.add(new VarInsnNode(Opcodes.LSTORE, index)); return this; }
    public InstructionGenerator lsub() { instructionList.add(new InsnNode(Opcodes.LSUB)); return this; }
    public InstructionGenerator lushr() { instructionList.add(new InsnNode(Opcodes.LUSHR)); return this; }
    public InstructionGenerator lxor() { instructionList.add(new InsnNode(Opcodes.LXOR)); return this; }
    public InstructionGenerator monitorenter() { instructionList.add(new InsnNode(Opcodes.MONITORENTER)); return this; }
    public InstructionGenerator monitorexit() { instructionList.add(new InsnNode(Opcodes.MONITOREXIT)); return this; }
    public InstructionGenerator multianewarray(String descriptor, int dims) {
        instructionList.add(new MultiANewArrayInsnNode(descriptor, dims));
        return this;
    }
    public InstructionGenerator new_object(String type) { instructionList.add(new TypeInsnNode(Opcodes.NEW, type)); return this; }
    public InstructionGenerator newarray(Type type) {
        int arrayType = switch (type) {
            case BooleanType b -> 4;
            case CharType c -> 5;
            case FloatType f -> 6;
            case DoubleType d -> 7;
            case ByteType b -> 8;
            case ShortType s -> 9;
            case IntType i -> 10;
            case LongType l -> 11;
            default -> throw new JFuncMachineException("Invalid type for newarray instruction");
        };
        instructionList.add(new IntInsnNode(Opcodes.NEWARRAY, arrayType));
        return this;
    }
    public InstructionGenerator nop() { instructionList.add(new InsnNode(Opcodes.NOP)); return this; }
    public InstructionGenerator pop() { instructionList.add(new InsnNode(Opcodes.POP)); return this; }
    public InstructionGenerator pop2() { instructionList.add(new InsnNode(Opcodes.POP2)); return this; }
    public InstructionGenerator putfield(String owner, String name, String descriptor) {
        instructionList.add(new FieldInsnNode(Opcodes.PUTFIELD, owner, name, descriptor));
        return this;
    }
    public InstructionGenerator putstatic(String owner, String name, String descriptor) {
        instructionList.add(new FieldInsnNode(Opcodes.PUTSTATIC, owner, name, descriptor));
        return this;
    }
    public InstructionGenerator ret(int index) { instructionList.add(new VarInsnNode(Opcodes.RET, index)); return this; }
    public InstructionGenerator return_void() { instructionList.add(new InsnNode(Opcodes.RETURN)); return this; }
    public InstructionGenerator saload() { instructionList.add(new InsnNode(Opcodes.SALOAD)); return this; }
    public InstructionGenerator sastore() { instructionList.add(new InsnNode(Opcodes.SASTORE)); return this; }
    public InstructionGenerator sipush(int value) { instructionList.add(new IntInsnNode(Opcodes.SIPUSH, value)); return this; }
    public InstructionGenerator swap() { instructionList.add(new InsnNode(Opcodes.SWAP)); return this; }
    public InstructionGenerator tableswitch(int min, int max, Label default_label, Label[] labels) {
        LabelNode[] labelNodes = new LabelNode[labels.length];
        for (int i=0; i < labels.length; i++) {
            labelNodes[i] = new LabelNode(labels[i].label);
        }
        instructionList.add(
                new TableSwitchInsnNode(min, max, new LabelNode(default_label.label), labelNodes));
        return this;
    }

    public InstructionGenerator return_by_type(Type type) {
        int opcode = switch(type) {
            case ArrayType a -> Opcodes.ARETURN;
            case BooleanType b -> Opcodes.IRETURN;
            case ByteType b -> Opcodes.IRETURN;
            case CharType c -> Opcodes.IRETURN;
            case DoubleType d -> Opcodes.DRETURN;
            case FloatType f -> Opcodes.FRETURN;
            case FunctionType f -> Opcodes.ARETURN;
            case IntType i -> Opcodes.IRETURN;
            case LongType l -> Opcodes.LRETURN;
            case ObjectType o -> Opcodes.ARETURN;
            case ShortType s -> Opcodes.IRETURN;
            case StringType s -> Opcodes.ARETURN;
            case UnitType s -> Opcodes.RETURN;
        };
        instructionList.add(new InsnNode(opcode));
        return this;
    }

    public InstructionGenerator rawOpcode(int opcode) { instructionList.add(new InsnNode(opcode)); return this; }
    public InstructionGenerator rawIntOpcode(int opcode, int index) {
        instructionList.add(new IntInsnNode(opcode, index));
        return this;
    }
    public InstructionGenerator rawJumpOpcode(int opcode, Label label) {
        instructionList.add(new JumpInsnNode(opcode, new LabelNode(label.label)));
        return this;
    }

    public InstructionGenerator generateLambda(MethodDef lambda) {
        classGen.addMethodToGenerate(lambda);
        return this;
    }

    public InstructionGenerator generateLocalVariable(String name, Type type,
                                                      Label startLoc, Label endLoc,
                                                      int index) {
        classGen.addLocalVariable(new LocalVariableNode(name, type.getTypeDescriptor(),
                    type.getTypeDescriptor(),
                new LabelNode(startLoc.label), new LabelNode(endLoc.label), index));
        return this;
    }
}
