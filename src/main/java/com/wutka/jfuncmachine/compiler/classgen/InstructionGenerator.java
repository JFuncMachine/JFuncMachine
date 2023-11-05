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

    public void aaload() { instructionList.insert(new InsnNode(Opcodes.AALOAD)); }
    public void aastore() { instructionList.insert(new InsnNode(Opcodes.AASTORE)); }
    public void aconst_null() { instructionList.insert(new InsnNode(Opcodes.AASTORE)); }
    public void aload(int index) { instructionList.insert(new VarInsnNode(Opcodes.ALOAD, index)); }
    public void anewarray(String type) { instructionList.insert(new TypeInsnNode(Opcodes.ANEWARRAY, type)); }
    public void areturn() { instructionList.insert(new InsnNode(Opcodes.ARETURN)); }
    public void arraylength() { instructionList.insert(new InsnNode(Opcodes.ARRAYLENGTH)); }
    public void astore(int index) { instructionList.insert(new VarInsnNode(Opcodes.ASTORE, index)); }
    public void athrow() { instructionList.insert(new InsnNode(Opcodes.ATHROW)); }
    public void baload() { instructionList.insert(new InsnNode(Opcodes.BALOAD)); }
    public void bastore() { instructionList.insert(new InsnNode(Opcodes.BASTORE)); }
    public void bipush(int value) { instructionList.insert(new IntInsnNode(Opcodes.BIPUSH, value)); }
    public void caload() { instructionList.insert(new InsnNode(Opcodes.CALOAD)); }
    public void castore() { instructionList.insert(new InsnNode(Opcodes.CASTORE)); }
    public void checkcast(String type) { instructionList.insert(new TypeInsnNode(Opcodes.CHECKCAST, type)); }
    public void d2f() { instructionList.insert(new InsnNode(Opcodes.D2F)); }
    public void d2i() { instructionList.insert(new InsnNode(Opcodes.D2I)); }
    public void d2l() { instructionList.insert(new InsnNode(Opcodes.D2L)); }
    public void dadd() { instructionList.insert(new InsnNode(Opcodes.DADD)); }
    public void daload() { instructionList.insert(new InsnNode(Opcodes.DALOAD)); }
    public void dastore() { instructionList.insert(new InsnNode(Opcodes.DASTORE)); }
    public void dcmpg() { instructionList.insert(new InsnNode(Opcodes.DCMPG)); }
    public void dcmpl() { instructionList.insert(new InsnNode(Opcodes.DCMPL)); }
    public void dconst_0() { instructionList.insert(new InsnNode(Opcodes.DCONST_0)); }
    public void dconst_1() { instructionList.insert(new InsnNode(Opcodes.DCONST_1)); }
    public void ddiv() { instructionList.insert(new InsnNode(Opcodes.DDIV)); }
    public void dload(int index) { instructionList.insert(new VarInsnNode(Opcodes.DLOAD, index)); }
    public void dmul() { instructionList.insert(new InsnNode(Opcodes.DMUL)); }
    public void dneg() { instructionList.insert(new InsnNode(Opcodes.DNEG)); }
    public void drem() { instructionList.insert(new InsnNode(Opcodes.DREM)); }
    public void dreturn() { instructionList.insert(new InsnNode(Opcodes.DRETURN)); }
    public void dstore(int index) { instructionList.insert(new VarInsnNode(Opcodes.DSTORE, index)); }
    public void dsub() { instructionList.insert(new InsnNode(Opcodes.DSUB)); }
    public void dup() { instructionList.insert(new InsnNode(Opcodes.DUP)); }
    public void dup_x1() { instructionList.insert(new InsnNode(Opcodes.DUP_X1)); }
    public void dup_x2() { instructionList.insert(new InsnNode(Opcodes.DUP_X2)); }
    public void dup2() { instructionList.insert(new InsnNode(Opcodes.DUP2)); }
    public void dup2_x1() { instructionList.insert(new InsnNode(Opcodes.DUP2_X1)); }
    public void dup2_x2() { instructionList.insert(new InsnNode(Opcodes.DUP2_X2)); }
    public void f2d() { instructionList.insert(new InsnNode(Opcodes.F2D)); }
    public void f2i() { instructionList.insert(new InsnNode(Opcodes.F2I)); }
    public void f2l() { instructionList.insert(new InsnNode(Opcodes.F2L)); }
    public void fadd() { instructionList.insert(new InsnNode(Opcodes.FADD)); }
    public void faload() { instructionList.insert(new InsnNode(Opcodes.FALOAD)); }
    public void fastore() { instructionList.insert(new InsnNode(Opcodes.FASTORE)); }
    public void fcmpg() { instructionList.insert(new InsnNode(Opcodes.FCMPG)); }
    public void fcmpl() { instructionList.insert(new InsnNode(Opcodes.FCMPL)); }
    public void fconst_0() { instructionList.insert(new InsnNode(Opcodes.FCONST_0)); }
    public void fconst_1() { instructionList.insert(new InsnNode(Opcodes.FCONST_1)); }
    public void fconst_2() { instructionList.insert(new InsnNode(Opcodes.FCONST_2)); }
    public void fdiv() { instructionList.insert(new InsnNode(Opcodes.FDIV)); }
    public void fload(int index) { instructionList.insert(new VarInsnNode(Opcodes.FLOAD, index)); }
    public void fmul() { instructionList.insert(new InsnNode(Opcodes.FMUL)); }
    public void fneg() { instructionList.insert(new InsnNode(Opcodes.FNEG)); }
    public void frem() { instructionList.insert(new InsnNode(Opcodes.FREM)); }
    public void freturn() { instructionList.insert(new InsnNode(Opcodes.FRETURN)); }
    public void fstore(int index) { instructionList.insert(new VarInsnNode(Opcodes.FSTORE, index)); }
    public void fsub() { instructionList.insert(new InsnNode(Opcodes.FSUB)); }
    public void getfield(String owner, String name, String descriptor) {
        instructionList.insert(new FieldInsnNode(Opcodes.GETFIELD, owner, name, descriptor));
    }
    public void getstatic(String owner, String name, String descriptor) {
        instructionList.insert(new FieldInsnNode(Opcodes.GETSTATIC, owner, name, descriptor));
    }
    public void gotolabel(Label label) { instructionList.insert(new JumpInsnNode(Opcodes.GOTO, new LabelNode(label.label))); }
    public void i2b() { instructionList.insert(new InsnNode(Opcodes.I2B)); }
    public void i2c() { instructionList.insert(new InsnNode(Opcodes.I2C)); }
    public void i2d() { instructionList.insert(new InsnNode(Opcodes.I2D)); }
    public void i2f() { instructionList.insert(new InsnNode(Opcodes.I2F)); }
    public void i2l() { instructionList.insert(new InsnNode(Opcodes.I2L)); }
    public void i2s() { instructionList.insert(new InsnNode(Opcodes.I2S)); }
    public void iadd() { instructionList.insert(new InsnNode(Opcodes.IADD)); }
    public void iaload() { instructionList.insert(new InsnNode(Opcodes.IALOAD)); }
    public void iand() { instructionList.insert(new InsnNode(Opcodes.IAND)); }
    public void iastore() { instructionList.insert(new InsnNode(Opcodes.IASTORE)); }
    public void iconst_m1() { instructionList.insert(new InsnNode(Opcodes.ICONST_M1)); }
    public void iconst_0() { instructionList.insert(new InsnNode(Opcodes.ICONST_0)); }
    public void iconst_1() { instructionList.insert(new InsnNode(Opcodes.ICONST_1)); }
    public void iconst_2() { instructionList.insert(new InsnNode(Opcodes.ICONST_2)); }
    public void iconst_3() { instructionList.insert(new InsnNode(Opcodes.ICONST_3)); }
    public void iconst_4() { instructionList.insert(new InsnNode(Opcodes.ICONST_4)); }
    public void iconst_5() { instructionList.insert(new InsnNode(Opcodes.ICONST_5)); }
    public void idiv() { instructionList.insert(new InsnNode(Opcodes.IDIV)); }
    public void if_acmpeq(Label label) { instructionList.insert(new JumpInsnNode(Opcodes.IF_ACMPEQ, new LabelNode(label.label))); }
    public void if_acmpne(Label label) { instructionList.insert(new JumpInsnNode(Opcodes.IF_ACMPNE, new LabelNode(label.label))); }
    public void if_icmpeq(Label label) { instructionList.insert(new JumpInsnNode(Opcodes.IF_ICMPEQ, new LabelNode(label.label))); }
    public void if_icmpne(Label label) { instructionList.insert(new JumpInsnNode(Opcodes.IF_ICMPNE, new LabelNode(label.label))); }
    public void if_icmplt(Label label) { instructionList.insert(new JumpInsnNode(Opcodes.IF_ICMPLT, new LabelNode(label.label))); }
    public void if_icmpge(Label label) { instructionList.insert(new JumpInsnNode(Opcodes.IF_ICMPGE, new LabelNode(label.label))); }
    public void if_icmpgt(Label label) { instructionList.insert(new JumpInsnNode(Opcodes.IF_ICMPGT, new LabelNode(label.label))); }
    public void if_icmple(Label label) { instructionList.insert(new JumpInsnNode(Opcodes.IF_ICMPLE, new LabelNode(label.label))); }
    public void ifeq(Label label) { instructionList.insert(new JumpInsnNode(Opcodes.IFEQ, new LabelNode(label.label))); }
    public void ifne(Label label) { instructionList.insert(new JumpInsnNode(Opcodes.IFNE, new LabelNode(label.label))); }
    public void iflt(Label label) { instructionList.insert(new JumpInsnNode(Opcodes.IFLT, new LabelNode(label.label))); }
    public void ifge(Label label) { instructionList.insert(new JumpInsnNode(Opcodes.IFGE, new LabelNode(label.label))); }
    public void ifgt(Label label) { instructionList.insert(new JumpInsnNode(Opcodes.IFGT, new LabelNode(label.label))); }
    public void ifle(Label label) { instructionList.insert(new JumpInsnNode(Opcodes.IFLE, new LabelNode(label.label))); }
    public void ifnonnull(Label label) { instructionList.insert(new JumpInsnNode(Opcodes.IFNONNULL, new LabelNode(label.label))); }
    public void ifnull(Label label) { instructionList.insert(new JumpInsnNode(Opcodes.IFNULL, new LabelNode(label.label))); }
    public void iinc(int index, int incr) { instructionList.insert(new IincInsnNode(index, incr)); }
    public void iload(int index) { instructionList.insert(new VarInsnNode(Opcodes.ILOAD, index)); }
    public void imul() { instructionList.insert(new InsnNode(Opcodes.IMUL)); }
    public void ineg() { instructionList.insert(new InsnNode(Opcodes.INEG)); }
    public void instance_of(String type) { instructionList.insert(new TypeInsnNode(Opcodes.INSTANCEOF, type)); }
    public void invokedynamic(String name, String descriptor, Handle handle, Object... bootstrapArgs) {
        instructionList.insert(new InvokeDynamicInsnNode(name, descriptor, handle.getAsmHandle(), bootstrapArgs));
    }
    public void invokeinterface(String owner, String name, String descriptor) {
        instructionList.insert(new MethodInsnNode(Opcodes.INVOKEINTERFACE, owner, name, descriptor));
    }
    public void invokespecial(String owner, String name, String descriptor) {
        instructionList.insert(new MethodInsnNode(Opcodes.INVOKESPECIAL, owner, name, descriptor));
    }
    public void invokestatic(String owner, String name, String descriptor) {
        instructionList.insert(new MethodInsnNode(Opcodes.INVOKESTATIC, owner, name, descriptor));
    }
    public void invokevirtual(String owner, String name, String descriptor) {
        instructionList.insert(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, owner, name, descriptor));
    }
    public void ior() { instructionList.insert(new InsnNode(Opcodes.IOR)); }
    public void irem() { instructionList.insert(new InsnNode(Opcodes.IREM)); }
    public void ireturn() { instructionList.insert(new InsnNode(Opcodes.IRETURN)); }
    public void ishl() { instructionList.insert(new InsnNode(Opcodes.ISHL)); }
    public void ishr() { instructionList.insert(new InsnNode(Opcodes.ISHR)); }
    public void istore(int index) { instructionList.insert(new VarInsnNode(Opcodes.ISTORE, index)); }
    public void isub() { instructionList.insert(new InsnNode(Opcodes.ISUB)); }
    public void iushr() { instructionList.insert(new InsnNode(Opcodes.IUSHR)); }
    public void ixor() { instructionList.insert(new InsnNode(Opcodes.IXOR)); }
    public void jsr(Label label) { instructionList.insert(new JumpInsnNode(Opcodes.JSR, new LabelNode(label.label))); }
    public void l2d() { instructionList.insert(new InsnNode(Opcodes.L2D)); }
    public void l2f() { instructionList.insert(new InsnNode(Opcodes.L2F)); }
    public void l2i() { instructionList.insert(new InsnNode(Opcodes.L2I)); }
    public void ladd() { instructionList.insert(new InsnNode(Opcodes.LADD)); }
    public void laload() { instructionList.insert(new InsnNode(Opcodes.LALOAD)); }
    public void land() { instructionList.insert(new InsnNode(Opcodes.LAND)); }
    public void lastore() { instructionList.insert(new InsnNode(Opcodes.LASTORE)); }
    public void lcmp() { instructionList.insert(new InsnNode(Opcodes.LCMP)); }
    public void lconst_0() { instructionList.insert(new InsnNode(Opcodes.LCONST_0)); }
    public void lconst_1() { instructionList.insert(new InsnNode(Opcodes.LCONST_1)); }
    public void ldc(double d) { instructionList.insert(new LdcInsnNode(d)); }
    public void ldc(int i) { instructionList.insert(new LdcInsnNode(i)); }
    public void ldc(float f) { instructionList.insert(new LdcInsnNode(f)); }
    public void ldc(long l) { instructionList.insert(new LdcInsnNode(l)); }
    public void ldc(String s) { instructionList.insert(new LdcInsnNode(s)); }
    public void ldc(Method m) { instructionList.insert(new LdcInsnNode(m)); }
    public void ldc(Handle h) { instructionList.insert(new LdcInsnNode(h.getAsmHandle())); }
    public void ldc(ConstantDynamic cd) { instructionList.insert(new LdcInsnNode(cd.getAsmConstantDynamic())); }
    public void ldc(Object o) { instructionList.insert(new LdcInsnNode(o)); }
    public void ldiv() { instructionList.insert(new InsnNode(Opcodes.LDIV)); }
    public void lload(int index) { instructionList.insert(new VarInsnNode(Opcodes.LLOAD, index)); }
    public void lmul() { instructionList.insert(new InsnNode(Opcodes.LMUL)); }
    public void lneg() { instructionList.insert(new InsnNode(Opcodes.LNEG)); }
    public void lookupswitch(Label default_label, int[] keys, Label[] labels) {
        LabelNode[] labelNodes = new LabelNode[labels.length];
        for (int i=0; i < labels.length; i++) {
            labelNodes[i] = new LabelNode(labels[i].label);
        }
        instructionList.insert(
                new LookupSwitchInsnNode(new LabelNode(default_label.label), keys, labelNodes));
    }
    public void lor() { instructionList.insert(new InsnNode(Opcodes.LOR)); }
    public void lrem() { instructionList.insert(new InsnNode(Opcodes.LREM)); }
    public void lreturn() { instructionList.insert(new InsnNode(Opcodes.LRETURN)); }
    public void lshl() { instructionList.insert(new InsnNode(Opcodes.LSHL)); }
    public void lshr() { instructionList.insert(new InsnNode(Opcodes.LSHR)); }
    public void lstore(int index) { instructionList.insert(new VarInsnNode(Opcodes.LSTORE, index)); }
    public void lsub() { instructionList.insert(new InsnNode(Opcodes.LSUB)); }
    public void lushr() { instructionList.insert(new InsnNode(Opcodes.LUSHR)); }
    public void lxor() { instructionList.insert(new InsnNode(Opcodes.LXOR)); }
    public void monitorenter() { instructionList.insert(new InsnNode(Opcodes.MONITORENTER)); }
    public void monitorexit() { instructionList.insert(new InsnNode(Opcodes.MONITOREXIT)); }
    public void multianewarray(String descriptor, int dims) {
        instructionList.insert(new MultiANewArrayInsnNode(descriptor, dims));
    }
    public void new_object(String type) { instructionList.insert(new TypeInsnNode(Opcodes.NEW, type)); }
    public void newarray(Type type) {
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
    }
    public void nop() { instructionList.insert(new InsnNode(Opcodes.NOP)); }
    public void pop() { instructionList.insert(new InsnNode(Opcodes.POP)); }
    public void pop2() { instructionList.insert(new InsnNode(Opcodes.POP2)); }
    public void putfield(String owner, String name, String descriptor) {
        instructionList.insert(new FieldInsnNode(Opcodes.PUTFIELD, owner, name, descriptor));
    }
    public void putstatic(String owner, String name, String descriptor) {
        instructionList.insert(new FieldInsnNode(Opcodes.PUTSTATIC, owner, name, descriptor));
    }
    public void ret(int index) { instructionList.insert(new VarInsnNode(Opcodes.RET, index)); }
    public void return_void() { instructionList.insert(new InsnNode(Opcodes.RETURN)); }
    public void saload() { instructionList.insert(new InsnNode(Opcodes.SALOAD)); }
    public void sastore() { instructionList.insert(new InsnNode(Opcodes.SASTORE)); }
    public void sipush(int value) { instructionList.insert(new IntInsnNode(Opcodes.SIPUSH, value)); }
    public void swap() { instructionList.insert(new InsnNode(Opcodes.SWAP)); }
    public void tableswitch(int min, int max, Label default_label, Label[] labels) {
        LabelNode[] labelNodes = new LabelNode[labels.length];
        for (int i=0; i < labels.length; i++) {
            labelNodes[i] = new LabelNode(labels[i].label);
        }
        instructionList.insert(
                new TableSwitchInsnNode(min, max, new LabelNode(default_label.label), labelNodes));
    }
}
