package com.wutka.jfuncmachine.compiler.model.expr;

import org.objectweb.asm.Opcodes;

public class Comparison {
    protected final int opcode;
    protected int numArgs;
    protected final Expression expr1;
    protected final Expression expr2;
    protected final boolean compareFirst;
    protected final int compareOpcode;

    protected Comparison(Expression expr1, int opcode) {
        this.opcode = opcode;
        this.expr1 = expr1;
        this.expr2 = null;
        this.numArgs = 1;
        this.compareFirst = false;
        this.compareOpcode = 0;
    }

    protected Comparison(Expression expr1, Expression expr2, int opcode) {
        this.opcode = opcode;
        this.expr1 = expr1;
        this.expr2 = expr2;
        this.numArgs = 2;
        this.compareFirst = false;
        this.compareOpcode = 0;
    }

    protected Comparison(Expression expr1, int opcode, int compareOpcode) {
        this.opcode = opcode;
        this.expr1 = expr1;
        this.expr2 = null;
        this.numArgs = 1;
        this.compareFirst = true;
        this.compareOpcode = compareOpcode;
    }

    protected Comparison(Expression expr1, Expression expr2, int opcode, int compareOpcode) {
        this.opcode = opcode;
        this.expr1 = expr1;
        this.expr2 = expr2;
        this.numArgs = 2;
        this.compareFirst = true;
        this.compareOpcode = compareOpcode;
    }

    protected Comparison(int opcode) {
        this.opcode = opcode;
        this.expr1 = null;
        this.expr2 = null;
        this.numArgs = 0;
        this.compareFirst = false;
        this.compareOpcode = 0;
    }

    // Make the opcodes backward so it jumps on the false path
    public static Comparison objectEQ(Expression expr1, Expression expr2) {
        return new Comparison(expr1, expr2, Opcodes.IF_ACMPNE);
    }
    public static Comparison objectNE(Expression expr1, Expression expr2) {
        return new Comparison(expr1, expr2, Opcodes.IF_ACMPEQ);
    }
    public static Comparison intEQ(Expression expr1, Expression expr2) {
        return new Comparison(expr1, expr2, Opcodes.IF_ICMPNE);
    }
    public static Comparison intNE(Expression expr1, Expression expr2) {
        return new Comparison(expr1, expr2, Opcodes.IF_ICMPEQ);
    }
    public static Comparison intLT(Expression expr1, Expression expr2) {
        return new Comparison(expr1, expr2, Opcodes.IF_ICMPGE);
    }
    public static Comparison intLE(Expression expr1, Expression expr2) {
        return new Comparison(expr1, expr2, Opcodes.IF_ICMPGT);
    }
    public static Comparison intGT(Expression expr1, Expression expr2) {
        return new Comparison(expr1, expr2, Opcodes.IF_ICMPLE);
    }
    public static Comparison intGE(Expression expr1, Expression expr2) {
        return new Comparison(expr1, expr2, Opcodes.IF_ICMPLT);
    }
    public static Comparison floatEQ(Expression expr1, Expression expr2) {
        return new Comparison(expr1, expr2, Opcodes.IFNE, Opcodes.FCMPG);
    }
    public static Comparison floatNE(Expression expr1, Expression expr2) {
        return new Comparison(expr1, expr2, Opcodes.IFEQ, Opcodes.FCMPG);
    }
    public static Comparison floatLTL(Expression expr1, Expression expr2) {
        return new Comparison(expr1, expr2, Opcodes.IFGE, Opcodes.FCMPL);
    }
    public static Comparison floatLTG(Expression expr1, Expression expr2) {
        return new Comparison(expr1, expr2, Opcodes.IFGE, Opcodes.FCMPG);
    }
    public static Comparison floatLEL(Expression expr1, Expression expr2) {
        return new Comparison(expr1, expr2, Opcodes.IFGT, Opcodes.FCMPL);
    }
    public static Comparison floatLEG(Expression expr1, Expression expr2) {
        return new Comparison(expr1, expr2, Opcodes.IFGT, Opcodes.FCMPG);
    }
    public static Comparison floatGTL(Expression expr1, Expression expr2) {
        return new Comparison(expr1, expr2, Opcodes.IFLE, Opcodes.FCMPL);
    }
    public static Comparison floatGTG(Expression expr1, Expression expr2) {
        return new Comparison(expr1, expr2, Opcodes.IFLE, Opcodes.FCMPG);
    }
    public static Comparison floatGEL(Expression expr1, Expression expr2) {
        return new Comparison(expr1, expr2, Opcodes.IFLT, Opcodes.FCMPL);
    }
    public static Comparison floatGEG(Expression expr1, Expression expr2) {
        return new Comparison(expr1, expr2, Opcodes.IFLT, Opcodes.FCMPG);
    }
    public static Comparison doubleEQ(Expression expr1, Expression expr2) {
        return new Comparison(expr1, expr2, Opcodes.IFNE, Opcodes.DCMPG);
    }
    public static Comparison doubleNE(Expression expr1, Expression expr2) {
        return new Comparison(expr1, expr2, Opcodes.IFEQ, Opcodes.DCMPG);
    }
    public static Comparison doubleLTL(Expression expr1, Expression expr2) {
        return new Comparison(expr1, expr2, Opcodes.IFGE, Opcodes.DCMPL);
    }
    public static Comparison doubleLTG(Expression expr1, Expression expr2) {
        return new Comparison(expr1, expr2, Opcodes.IFGE, Opcodes.DCMPG);
    }
    public static Comparison doubleLEL(Expression expr1, Expression expr2) {
        return new Comparison(expr1, expr2, Opcodes.IFGT, Opcodes.DCMPL);
    }
    public static Comparison doubleLEG(Expression expr1, Expression expr2) {
        return new Comparison(expr1, expr2, Opcodes.IFGT, Opcodes.DCMPG);
    }
    public static Comparison doubleGTL(Expression expr1, Expression expr2) {
        return new Comparison(expr1, expr2, Opcodes.IFLE, Opcodes.DCMPL);
    }
    public static Comparison doubleGTG(Expression expr1, Expression expr2) {
        return new Comparison(expr1, expr2, Opcodes.IFLE, Opcodes.DCMPG);
    }
    public static Comparison doubleGEL(Expression expr1, Expression expr2) {
        return new Comparison(expr1, expr2, Opcodes.IFLT, Opcodes.DCMPL);
    }
    public static Comparison doubleGEG(Expression expr1, Expression expr2) {
        return new Comparison(expr1, expr2, Opcodes.IFLT, Opcodes.DCMPG);
    }
    public static Comparison longEQ(Expression expr1, Expression expr2) {
        return new Comparison(expr1, expr2, Opcodes.IFNE, Opcodes.LCMP);
    }
    public static Comparison longNE(Expression expr1, Expression expr2) {
        return new Comparison(expr1, expr2, Opcodes.IFEQ, Opcodes.LCMP);
    }
    public static Comparison longLT(Expression expr1, Expression expr2) {
        return new Comparison(expr1, expr2, Opcodes.IFGE, Opcodes.LCMP);
    }
    public static Comparison longLE(Expression expr1, Expression expr2) {
        return new Comparison(expr1, expr2, Opcodes.IFGT, Opcodes.LCMP);
    }
    public static Comparison longGT(Expression expr1, Expression expr2) {
        return new Comparison(expr1, expr2, Opcodes.IFLE, Opcodes.LCMP);
    }
    public static Comparison longGE(Expression expr1, Expression expr2) {
        return new Comparison(expr1, expr2, Opcodes.IFLT, Opcodes.LCMP);
    }
    public static Comparison isNull(Expression expr1) {
        return new Comparison(expr1, Opcodes.IFNONNULL);
    }
    public static Comparison isNotNull(Expression expr1) {
        return new Comparison(expr1, Opcodes.IFNULL);
    }

    public static Comparison EQ() { return new Comparison(Opcodes.IFNE); }
    public static Comparison isFalse() { return new Comparison(Opcodes.IFEQ); }
    public static Comparison NE() { return new Comparison(Opcodes.IFEQ); }
    public static Comparison isTrue() { return new Comparison(Opcodes.IFEQ); }
    public static Comparison LT() { return new Comparison(Opcodes.IFGE); }
    public static Comparison LE() { return new Comparison(Opcodes.IFGT); }
    public static Comparison GT() { return new Comparison(Opcodes.IFLE); }
    public static Comparison GE() { return new Comparison(Opcodes.IFLT); }
}
