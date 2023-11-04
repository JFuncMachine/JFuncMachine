package com.wutka.jfuncmachine.compiler.classgen;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

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


}
