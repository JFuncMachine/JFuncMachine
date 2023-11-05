package com.wutka.jfuncmachine.compiler.classgen;

import com.wutka.jfuncmachine.compiler.model.types.Boolean;
import com.wutka.jfuncmachine.compiler.model.types.Char;
import com.wutka.jfuncmachine.compiler.model.types.Double;
import com.wutka.jfuncmachine.compiler.model.types.Float;
import com.wutka.jfuncmachine.compiler.model.types.Int;
import com.wutka.jfuncmachine.compiler.model.types.Long;
import com.wutka.jfuncmachine.compiler.model.types.Type;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.IincInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.InvokeDynamicInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.LookupSwitchInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MultiANewArrayInsnNode;
import org.objectweb.asm.tree.TableSwitchInsnNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

import java.lang.reflect.Method;

public class InstructionGenerator {
    protected InsnList instructionList;

    protected InstructionGenerator(InsnList instructionList) {
        this.instructionList = instructionList;
    }

    public InstructionGenerator aaload() { instructionList.insert(new InsnNode(Opcodes.AALOAD)); return this; }
    public InstructionGenerator aastore() { instructionList.insert(new InsnNode(Opcodes.AASTORE)); return this; }
    public InstructionGenerator aconst_null() { instructionList.insert(new InsnNode(Opcodes.AASTORE)); return this; }
    public InstructionGenerator aload(int index) { instructionList.insert(new VarInsnNode(Opcodes.ALOAD, index)); return this; }
    public InstructionGenerator anewarray(String type) { instructionList.insert(new TypeInsnNode(Opcodes.ANEWARRAY, type)); return this; }
    public InstructionGenerator areturn() { instructionList.insert(new InsnNode(Opcodes.ARETURN)); return this; }
    public InstructionGenerator arraylength() { instructionList.insert(new InsnNode(Opcodes.ARRAYLENGTH)); return this; }
    public InstructionGenerator astore(int index) { instructionList.insert(new VarInsnNode(Opcodes.ASTORE, index)); return this; }
    public InstructionGenerator athrow() { instructionList.insert(new InsnNode(Opcodes.ATHROW)); return this; }
    public InstructionGenerator baload() { instructionList.insert(new InsnNode(Opcodes.BALOAD)); return this; }
    public InstructionGenerator bastore() { instructionList.insert(new InsnNode(Opcodes.BASTORE)); return this; }
    public InstructionGenerator bipush(int value) { instructionList.insert(new IntInsnNode(Opcodes.BIPUSH, value)); return this; }
    public InstructionGenerator caload() { instructionList.insert(new InsnNode(Opcodes.CALOAD)); return this; }
    public InstructionGenerator castore() { instructionList.insert(new InsnNode(Opcodes.CASTORE)); return this; }
    public InstructionGenerator checkcast(String type) { instructionList.insert(new TypeInsnNode(Opcodes.CHECKCAST, type)); return this; }
    public InstructionGenerator d2f() { instructionList.insert(new InsnNode(Opcodes.D2F)); return this; }
    public InstructionGenerator d2i() { instructionList.insert(new InsnNode(Opcodes.D2I)); return this; }
    public InstructionGenerator d2l() { instructionList.insert(new InsnNode(Opcodes.D2L)); return this; }
    public InstructionGenerator dadd() { instructionList.insert(new InsnNode(Opcodes.DADD)); return this; }
    public InstructionGenerator daload() { instructionList.insert(new InsnNode(Opcodes.DALOAD)); return this; }
    public InstructionGenerator dastore() { instructionList.insert(new InsnNode(Opcodes.DASTORE)); return this; }
    public InstructionGenerator dcmpg() { instructionList.insert(new InsnNode(Opcodes.DCMPG)); return this; }
    public InstructionGenerator dcmpl() { instructionList.insert(new InsnNode(Opcodes.DCMPL)); return this; }
    public InstructionGenerator dconst_0() { instructionList.insert(new InsnNode(Opcodes.DCONST_0)); return this; }
    public InstructionGenerator dconst_1() { instructionList.insert(new InsnNode(Opcodes.DCONST_1)); return this; }
    public InstructionGenerator ddiv() { instructionList.insert(new InsnNode(Opcodes.DDIV)); return this; }
    public InstructionGenerator dload(int index) { instructionList.insert(new VarInsnNode(Opcodes.DLOAD, index)); return this; }
    public InstructionGenerator dmul() { instructionList.insert(new InsnNode(Opcodes.DMUL)); return this; }
    public InstructionGenerator dneg() { instructionList.insert(new InsnNode(Opcodes.DNEG)); return this; }
    public InstructionGenerator drem() { instructionList.insert(new InsnNode(Opcodes.DREM)); return this; }
    public InstructionGenerator dreturn() { instructionList.insert(new InsnNode(Opcodes.DRETURN)); return this; }
    public InstructionGenerator dstore(int index) { instructionList.insert(new VarInsnNode(Opcodes.DSTORE, index)); return this; }
    public InstructionGenerator dsub() { instructionList.insert(new InsnNode(Opcodes.DSUB)); return this; }
    public InstructionGenerator dup() { instructionList.insert(new InsnNode(Opcodes.DUP)); return this; }
    public InstructionGenerator dup_x1() { instructionList.insert(new InsnNode(Opcodes.DUP_X1)); return this; }
    public InstructionGenerator dup_x2() { instructionList.insert(new InsnNode(Opcodes.DUP_X2)); return this; }
    public InstructionGenerator dup2() { instructionList.insert(new InsnNode(Opcodes.DUP2)); return this; }
    public InstructionGenerator dup2_x1() { instructionList.insert(new InsnNode(Opcodes.DUP2_X1)); return this; }
    public InstructionGenerator dup2_x2() { instructionList.insert(new InsnNode(Opcodes.DUP2_X2)); return this; }
    public InstructionGenerator f2d() { instructionList.insert(new InsnNode(Opcodes.F2D)); return this; }
    public InstructionGenerator f2i() { instructionList.insert(new InsnNode(Opcodes.F2I)); return this; }
    public InstructionGenerator f2l() { instructionList.insert(new InsnNode(Opcodes.F2L)); return this; }
    public InstructionGenerator fadd() { instructionList.insert(new InsnNode(Opcodes.FADD)); return this; }
    public InstructionGenerator faload() { instructionList.insert(new InsnNode(Opcodes.FALOAD)); return this; }
    public InstructionGenerator fastore() { instructionList.insert(new InsnNode(Opcodes.FASTORE)); return this; }
    public InstructionGenerator fcmpg() { instructionList.insert(new InsnNode(Opcodes.FCMPG)); return this; }
    public InstructionGenerator fcmpl() { instructionList.insert(new InsnNode(Opcodes.FCMPL)); return this; }
    public InstructionGenerator fconst_0() { instructionList.insert(new InsnNode(Opcodes.FCONST_0)); return this; }
    public InstructionGenerator fconst_1() { instructionList.insert(new InsnNode(Opcodes.FCONST_1)); return this; }
    public InstructionGenerator fconst_2() { instructionList.insert(new InsnNode(Opcodes.FCONST_2)); return this; }
    public InstructionGenerator fdiv() { instructionList.insert(new InsnNode(Opcodes.FDIV)); return this; }
    public InstructionGenerator fload(int index) { instructionList.insert(new VarInsnNode(Opcodes.FLOAD, index)); return this; }
    public InstructionGenerator fmul() { instructionList.insert(new InsnNode(Opcodes.FMUL)); return this; }
    public InstructionGenerator fneg() { instructionList.insert(new InsnNode(Opcodes.FNEG)); return this; }
    public InstructionGenerator frem() { instructionList.insert(new InsnNode(Opcodes.FREM)); return this; }
    public InstructionGenerator freturn() { instructionList.insert(new InsnNode(Opcodes.FRETURN)); return this; }
    public InstructionGenerator fstore(int index) { instructionList.insert(new VarInsnNode(Opcodes.FSTORE, index)); return this; }
    public InstructionGenerator fsub() { instructionList.insert(new InsnNode(Opcodes.FSUB)); return this; }
    public InstructionGenerator getfield(String owner, String name, String descriptor) {
        instructionList.insert(new FieldInsnNode(Opcodes.GETFIELD, owner, name, descriptor));
        return this;
    }
    public InstructionGenerator getstatic(String owner, String name, String descriptor) {
        instructionList.insert(new FieldInsnNode(Opcodes.GETSTATIC, owner, name, descriptor));
        return this;
    }
    public InstructionGenerator gotolabel(Label label) { instructionList.insert(new JumpInsnNode(Opcodes.GOTO, new LabelNode(label.label))); return this; }
    public InstructionGenerator i2b() { instructionList.insert(new InsnNode(Opcodes.I2B)); return this; }
    public InstructionGenerator i2c() { instructionList.insert(new InsnNode(Opcodes.I2C)); return this; }
    public InstructionGenerator i2d() { instructionList.insert(new InsnNode(Opcodes.I2D)); return this; }
    public InstructionGenerator i2f() { instructionList.insert(new InsnNode(Opcodes.I2F)); return this; }
    public InstructionGenerator i2l() { instructionList.insert(new InsnNode(Opcodes.I2L)); return this; }
    public InstructionGenerator i2s() { instructionList.insert(new InsnNode(Opcodes.I2S)); return this; }
    public InstructionGenerator iadd() { instructionList.insert(new InsnNode(Opcodes.IADD)); return this; }
    public InstructionGenerator iaload() { instructionList.insert(new InsnNode(Opcodes.IALOAD)); return this; }
    public InstructionGenerator iand() { instructionList.insert(new InsnNode(Opcodes.IAND)); return this; }
    public InstructionGenerator iastore() { instructionList.insert(new InsnNode(Opcodes.IASTORE)); return this; }
    public InstructionGenerator iconst_m1() { instructionList.insert(new InsnNode(Opcodes.ICONST_M1)); return this; }
    public InstructionGenerator iconst_0() { instructionList.insert(new InsnNode(Opcodes.ICONST_0)); return this; }
    public InstructionGenerator iconst_1() { instructionList.insert(new InsnNode(Opcodes.ICONST_1)); return this; }
    public InstructionGenerator iconst_2() { instructionList.insert(new InsnNode(Opcodes.ICONST_2)); return this; }
    public InstructionGenerator iconst_3() { instructionList.insert(new InsnNode(Opcodes.ICONST_3)); return this; }
    public InstructionGenerator iconst_4() { instructionList.insert(new InsnNode(Opcodes.ICONST_4)); return this; }
    public InstructionGenerator iconst_5() { instructionList.insert(new InsnNode(Opcodes.ICONST_5)); return this; }
    public InstructionGenerator idiv() { instructionList.insert(new InsnNode(Opcodes.IDIV)); return this; }
    public InstructionGenerator if_acmpeq(Label label) { instructionList.insert(new JumpInsnNode(Opcodes.IF_ACMPEQ, new LabelNode(label.label))); return this; }
    public InstructionGenerator if_acmpne(Label label) { instructionList.insert(new JumpInsnNode(Opcodes.IF_ACMPNE, new LabelNode(label.label))); return this; }
    public InstructionGenerator if_icmpeq(Label label) { instructionList.insert(new JumpInsnNode(Opcodes.IF_ICMPEQ, new LabelNode(label.label))); return this; }
    public InstructionGenerator if_icmpne(Label label) { instructionList.insert(new JumpInsnNode(Opcodes.IF_ICMPNE, new LabelNode(label.label))); return this; }
    public InstructionGenerator if_icmplt(Label label) { instructionList.insert(new JumpInsnNode(Opcodes.IF_ICMPLT, new LabelNode(label.label))); return this; }
    public InstructionGenerator if_icmpge(Label label) { instructionList.insert(new JumpInsnNode(Opcodes.IF_ICMPGE, new LabelNode(label.label))); return this; }
    public InstructionGenerator if_icmpgt(Label label) { instructionList.insert(new JumpInsnNode(Opcodes.IF_ICMPGT, new LabelNode(label.label))); return this; }
    public InstructionGenerator if_icmple(Label label) { instructionList.insert(new JumpInsnNode(Opcodes.IF_ICMPLE, new LabelNode(label.label))); return this; }
    public InstructionGenerator ifeq(Label label) { instructionList.insert(new JumpInsnNode(Opcodes.IFEQ, new LabelNode(label.label))); return this; }
    public InstructionGenerator ifne(Label label) { instructionList.insert(new JumpInsnNode(Opcodes.IFNE, new LabelNode(label.label))); return this; }
    public InstructionGenerator iflt(Label label) { instructionList.insert(new JumpInsnNode(Opcodes.IFLT, new LabelNode(label.label))); return this; }
    public InstructionGenerator ifge(Label label) { instructionList.insert(new JumpInsnNode(Opcodes.IFGE, new LabelNode(label.label))); return this; }
    public InstructionGenerator ifgt(Label label) { instructionList.insert(new JumpInsnNode(Opcodes.IFGT, new LabelNode(label.label))); return this; }
    public InstructionGenerator ifle(Label label) { instructionList.insert(new JumpInsnNode(Opcodes.IFLE, new LabelNode(label.label))); return this; }
    public InstructionGenerator ifnonnull(Label label) { instructionList.insert(new JumpInsnNode(Opcodes.IFNONNULL, new LabelNode(label.label))); return this; }
    public InstructionGenerator ifnull(Label label) { instructionList.insert(new JumpInsnNode(Opcodes.IFNULL, new LabelNode(label.label))); return this; }
    public InstructionGenerator iinc(int index, int incr) { instructionList.insert(new IincInsnNode(index, incr)); return this; }
    public InstructionGenerator iload(int index) { instructionList.insert(new VarInsnNode(Opcodes.ILOAD, index)); return this; }
    public InstructionGenerator imul() { instructionList.insert(new InsnNode(Opcodes.IMUL)); return this; }
    public InstructionGenerator ineg() { instructionList.insert(new InsnNode(Opcodes.INEG)); return this; }
    public InstructionGenerator instance_of(String type) { instructionList.insert(new TypeInsnNode(Opcodes.INSTANCEOF, type)); return this; }
    public InstructionGenerator invokedynamic(String name, String descriptor, Handle handle, Object... bootstrapArgs) {
        instructionList.insert(new InvokeDynamicInsnNode(name, descriptor, handle.getAsmHandle(), bootstrapArgs));
        return this;
    }
    public InstructionGenerator invokeinterface(String owner, String name, String descriptor) {
        instructionList.insert(new MethodInsnNode(Opcodes.INVOKEINTERFACE, owner, name, descriptor));
        return this;
    }
    public InstructionGenerator invokespecial(String owner, String name, String descriptor) {
        instructionList.insert(new MethodInsnNode(Opcodes.INVOKESPECIAL, owner, name, descriptor));
        return this;
    }
    public InstructionGenerator invokestatic(String owner, String name, String descriptor) {
        instructionList.insert(new MethodInsnNode(Opcodes.INVOKESTATIC, owner, name, descriptor));
        return this;
    }
    public InstructionGenerator invokevirtual(String owner, String name, String descriptor) {
        instructionList.insert(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, owner, name, descriptor));
        return this;
    }
    public InstructionGenerator ior() { instructionList.insert(new InsnNode(Opcodes.IOR)); return this; }
    public InstructionGenerator irem() { instructionList.insert(new InsnNode(Opcodes.IREM)); return this; }
    public InstructionGenerator ireturn() { instructionList.insert(new InsnNode(Opcodes.IRETURN)); return this; }
    public InstructionGenerator ishl() { instructionList.insert(new InsnNode(Opcodes.ISHL)); return this; }
    public InstructionGenerator ishr() { instructionList.insert(new InsnNode(Opcodes.ISHR)); return this; }
    public InstructionGenerator istore(int index) { instructionList.insert(new VarInsnNode(Opcodes.ISTORE, index)); return this; }
    public InstructionGenerator isub() { instructionList.insert(new InsnNode(Opcodes.ISUB)); return this; }
    public InstructionGenerator iushr() { instructionList.insert(new InsnNode(Opcodes.IUSHR)); return this; }
    public InstructionGenerator ixor() { instructionList.insert(new InsnNode(Opcodes.IXOR)); return this; }
    public InstructionGenerator jsr(Label label) { instructionList.insert(new JumpInsnNode(Opcodes.JSR, new LabelNode(label.label))); return this; }
    public InstructionGenerator l2d() { instructionList.insert(new InsnNode(Opcodes.L2D)); return this; }
    public InstructionGenerator l2f() { instructionList.insert(new InsnNode(Opcodes.L2F)); return this; }
    public InstructionGenerator l2i() { instructionList.insert(new InsnNode(Opcodes.L2I)); return this; }
    public InstructionGenerator label(Label label) { instructionList.insert(new LabelNode(label.label)); return this; }
    public InstructionGenerator ladd() { instructionList.insert(new InsnNode(Opcodes.LADD)); return this; }
    public InstructionGenerator laload() { instructionList.insert(new InsnNode(Opcodes.LALOAD)); return this; }
    public InstructionGenerator land() { instructionList.insert(new InsnNode(Opcodes.LAND)); return this; }
    public InstructionGenerator lastore() { instructionList.insert(new InsnNode(Opcodes.LASTORE)); return this; }
    public InstructionGenerator lcmp() { instructionList.insert(new InsnNode(Opcodes.LCMP)); return this; }
    public InstructionGenerator lconst_0() { instructionList.insert(new InsnNode(Opcodes.LCONST_0)); return this; }
    public InstructionGenerator lconst_1() { instructionList.insert(new InsnNode(Opcodes.LCONST_1)); return this; }
    public InstructionGenerator ldc(double d) { instructionList.insert(new LdcInsnNode(d)); return this; }
    public InstructionGenerator ldc(int i) { instructionList.insert(new LdcInsnNode(i)); return this; }
    public InstructionGenerator ldc(float f) { instructionList.insert(new LdcInsnNode(f)); return this; }
    public InstructionGenerator ldc(long l) { instructionList.insert(new LdcInsnNode(l)); return this; }
    public InstructionGenerator ldc(String s) { instructionList.insert(new LdcInsnNode(s)); return this; }
    public InstructionGenerator ldc(Method m) { instructionList.insert(new LdcInsnNode(m)); return this; }
    public InstructionGenerator ldc(Handle h) { instructionList.insert(new LdcInsnNode(h.getAsmHandle())); return this; }
    public InstructionGenerator ldc(ConstantDynamic cd) { instructionList.insert(new LdcInsnNode(cd.getAsmConstantDynamic())); return this; }
    public InstructionGenerator ldc(Object o) { instructionList.insert(new LdcInsnNode(o)); return this; }
    public InstructionGenerator ldiv() { instructionList.insert(new InsnNode(Opcodes.LDIV)); return this; }
    public InstructionGenerator lload(int index) { instructionList.insert(new VarInsnNode(Opcodes.LLOAD, index)); return this; }
    public InstructionGenerator lmul() { instructionList.insert(new InsnNode(Opcodes.LMUL)); return this; }
    public InstructionGenerator lneg() { instructionList.insert(new InsnNode(Opcodes.LNEG)); return this; }
    public InstructionGenerator lookupswitch(Label default_label, int[] keys, Label[] labels) {
        LabelNode[] labelNodes = new LabelNode[labels.length];
        for (int i=0; i < labels.length; i++) {
            labelNodes[i] = new LabelNode(labels[i].label);
        }
        instructionList.insert(
                new LookupSwitchInsnNode(new LabelNode(default_label.label), keys, labelNodes));
        return this;
    }
    public InstructionGenerator lor() { instructionList.insert(new InsnNode(Opcodes.LOR)); return this; }
    public InstructionGenerator lrem() { instructionList.insert(new InsnNode(Opcodes.LREM)); return this; }
    public InstructionGenerator lreturn() { instructionList.insert(new InsnNode(Opcodes.LRETURN)); return this; }
    public InstructionGenerator lshl() { instructionList.insert(new InsnNode(Opcodes.LSHL)); return this; }
    public InstructionGenerator lshr() { instructionList.insert(new InsnNode(Opcodes.LSHR)); return this; }
    public InstructionGenerator lstore(int index) { instructionList.insert(new VarInsnNode(Opcodes.LSTORE, index)); return this; }
    public InstructionGenerator lsub() { instructionList.insert(new InsnNode(Opcodes.LSUB)); return this; }
    public InstructionGenerator lushr() { instructionList.insert(new InsnNode(Opcodes.LUSHR)); return this; }
    public InstructionGenerator lxor() { instructionList.insert(new InsnNode(Opcodes.LXOR)); return this; }
    public InstructionGenerator monitorenter() { instructionList.insert(new InsnNode(Opcodes.MONITORENTER)); return this; }
    public InstructionGenerator monitorexit() { instructionList.insert(new InsnNode(Opcodes.MONITOREXIT)); return this; }
    public InstructionGenerator multianewarray(String descriptor, int dims) {
        instructionList.insert(new MultiANewArrayInsnNode(descriptor, dims));
        return this;
    }
    public InstructionGenerator new_object(String type) { instructionList.insert(new TypeInsnNode(Opcodes.NEW, type)); return this; }
    public InstructionGenerator newarray(Type type) {
        int arrayType = switch (type) {
            case Boolean b -> 4;
            case Char c -> 5;
            case Float f -> 6;
            case Double d -> 7;
            case com.wutka.jfuncmachine.compiler.model.types.Byte b -> 8;
            case com.wutka.jfuncmachine.compiler.model.types.Short s -> 9;
            case Int i -> 10;
            case Long l -> 11;
            default -> throw new RuntimeException("Invalid type for newarray instruction");
        };
        instructionList.insert(new IntInsnNode(Opcodes.NEWARRAY, arrayType));
        return this;
    }
    public InstructionGenerator nop() { instructionList.insert(new InsnNode(Opcodes.NOP)); return this; }
    public InstructionGenerator pop() { instructionList.insert(new InsnNode(Opcodes.POP)); return this; }
    public InstructionGenerator pop2() { instructionList.insert(new InsnNode(Opcodes.POP2)); return this; }
    public InstructionGenerator putfield(String owner, String name, String descriptor) {
        instructionList.insert(new FieldInsnNode(Opcodes.PUTFIELD, owner, name, descriptor));
        return this;
    }
    public InstructionGenerator putstatic(String owner, String name, String descriptor) {
        instructionList.insert(new FieldInsnNode(Opcodes.PUTSTATIC, owner, name, descriptor));
        return this;
    }
    public InstructionGenerator ret(int index) { instructionList.insert(new VarInsnNode(Opcodes.RET, index)); return this; }
    public InstructionGenerator return_void() { instructionList.insert(new InsnNode(Opcodes.RETURN)); return this; }
    public InstructionGenerator saload() { instructionList.insert(new InsnNode(Opcodes.SALOAD)); return this; }
    public InstructionGenerator sastore() { instructionList.insert(new InsnNode(Opcodes.SASTORE)); return this; }
    public InstructionGenerator sipush(int value) { instructionList.insert(new IntInsnNode(Opcodes.SIPUSH, value)); return this; }
    public InstructionGenerator swap() { instructionList.insert(new InsnNode(Opcodes.SWAP)); return this; }
    public InstructionGenerator tableswitch(int min, int max, Label default_label, Label[] labels) {
        LabelNode[] labelNodes = new LabelNode[labels.length];
        for (int i=0; i < labels.length; i++) {
            labelNodes[i] = new LabelNode(labels[i].label);
        }
        instructionList.insert(
                new TableSwitchInsnNode(min, max, new LabelNode(default_label.label), labelNodes));
        return this;
    }
}
