package org.jfuncmachine.compiler.model.inline;

import org.jfuncmachine.compiler.model.types.SimpleTypes;
import org.jfuncmachine.compiler.model.types.Type;
import org.objectweb.asm.Opcodes;

/** A container of pre-defined inline functions */
public class Inlines {
    /** Add two integers */
    public static final SingleInstructionInline IntAdd =
            new SingleInstructionInline(new Type[] { SimpleTypes.INT, SimpleTypes.INT}, SimpleTypes.INT, Opcodes.IADD);
    /** Bitwise-and two integers */
    public static final SingleInstructionInline IntAnd =
            new SingleInstructionInline(new Type[] { SimpleTypes.INT, SimpleTypes.INT}, SimpleTypes.INT, Opcodes.IAND);
    /** Divide two integers */
    public static final SingleInstructionInline IntDiv =
            new SingleInstructionInline(new Type[] { SimpleTypes.INT, SimpleTypes.INT}, SimpleTypes.INT, Opcodes.IDIV);
    /** Multiply two integers */
    public static final SingleInstructionInline IntMul =
            new SingleInstructionInline(new Type[] { SimpleTypes.INT, SimpleTypes.INT}, SimpleTypes.INT, Opcodes.IMUL);
    /** Negate an integer*/
    public static final SingleInstructionInline IntNeg =
            new SingleInstructionInline(new Type[] { SimpleTypes.INT }, SimpleTypes.INT, Opcodes.INEG);
    /** Bitwise-or two integers */
    public static final SingleInstructionInline IntOr =
            new SingleInstructionInline(new Type[] { SimpleTypes.INT, SimpleTypes.INT}, SimpleTypes.INT, Opcodes.IOR);
    /** Get the remainder from dividing two integers */
    public static final SingleInstructionInline IntRem =
            new SingleInstructionInline(new Type[] { SimpleTypes.INT, SimpleTypes.INT}, SimpleTypes.INT, Opcodes.IREM);
    /** Bitwise-shift left an integer */
    public static final SingleInstructionInline IntShiftLeft =
            new SingleInstructionInline(new Type[] { SimpleTypes.INT, SimpleTypes.INT}, SimpleTypes.INT, Opcodes.ISHL);
    /** Arithmetic (sign-extended) Bitwise-shift right an integer */
    public static final SingleInstructionInline IntArithShiftRight =
            new SingleInstructionInline(new Type[] { SimpleTypes.INT, SimpleTypes.INT}, SimpleTypes.INT, Opcodes.ISHR);
    /** Logical (no sign extension) Bitwise-shift right an integer */
    public static final SingleInstructionInline IntLogicalShiftRight =
            new SingleInstructionInline(new Type[] { SimpleTypes.INT, SimpleTypes.INT}, SimpleTypes.INT, Opcodes.IUSHR);
    /** Subtract two integers */
    public static final SingleInstructionInline IntSub =
            new SingleInstructionInline(new Type[] { SimpleTypes.INT, SimpleTypes.INT}, SimpleTypes.INT, Opcodes.ISUB);
    /** Bitwise-exclusive-or two integers */
    public static final SingleInstructionInline IntXor =
            new SingleInstructionInline(new Type[] { SimpleTypes.INT, SimpleTypes.INT}, SimpleTypes.INT, Opcodes.IXOR);
    /** Add two doubles */
    public static final SingleInstructionInline DoubleAdd =
            new SingleInstructionInline(new Type[] { SimpleTypes.DOUBLE, SimpleTypes.DOUBLE}, SimpleTypes.DOUBLE, Opcodes.DADD);
    /** Divide two doubles */
    public static final SingleInstructionInline DoubleDiv =
            new SingleInstructionInline(new Type[] { SimpleTypes.DOUBLE, SimpleTypes.DOUBLE}, SimpleTypes.DOUBLE, Opcodes.DDIV);
    /** Multiply two doubles */
    public static final SingleInstructionInline DoubleMul =
            new SingleInstructionInline(new Type[] { SimpleTypes.DOUBLE, SimpleTypes.DOUBLE}, SimpleTypes.DOUBLE, Opcodes.DMUL);
    /** Negate a double */
    public static final SingleInstructionInline DoubleNeg =
            new SingleInstructionInline(new Type[] { SimpleTypes.DOUBLE }, SimpleTypes.DOUBLE, Opcodes.DNEG);
    /** Get the remainder from dividing two doubles */
    public static final SingleInstructionInline DoubleRem =
            new SingleInstructionInline(new Type[] { SimpleTypes.DOUBLE, SimpleTypes.DOUBLE}, SimpleTypes.DOUBLE, Opcodes.DREM);
    /** Subtract two doubles */
    public static final SingleInstructionInline DoubleSub =
            new SingleInstructionInline(new Type[] { SimpleTypes.DOUBLE, SimpleTypes.DOUBLE}, SimpleTypes.DOUBLE, Opcodes.DSUB);
    /** Add two floats */
    public static final SingleInstructionInline FloatAdd =
            new SingleInstructionInline(new Type[] { SimpleTypes.FLOAT, SimpleTypes.FLOAT}, SimpleTypes.FLOAT, Opcodes.FADD);
    /** Divide two floats */
    public static final SingleInstructionInline FloatDiv =
            new SingleInstructionInline(new Type[] { SimpleTypes.FLOAT, SimpleTypes.FLOAT}, SimpleTypes.FLOAT, Opcodes.FDIV);
    /** Multiply two floats */
    public static final SingleInstructionInline FloatMul =
            new SingleInstructionInline(new Type[] { SimpleTypes.FLOAT, SimpleTypes.FLOAT}, SimpleTypes.FLOAT, Opcodes.FMUL);
    /** Negate a float */
    public static final SingleInstructionInline FloatNeg =
            new SingleInstructionInline(new Type[] { SimpleTypes.FLOAT }, SimpleTypes.FLOAT, Opcodes.FNEG);
    /** Get the remainder from dividing two floats */
    public static final SingleInstructionInline FloatRem =
            new SingleInstructionInline(new Type[] { SimpleTypes.FLOAT, SimpleTypes.FLOAT}, SimpleTypes.FLOAT, Opcodes.FREM);
    /** Subtract two floats */
    public static final SingleInstructionInline FloatSub =
            new SingleInstructionInline(new Type[] { SimpleTypes.FLOAT, SimpleTypes.FLOAT}, SimpleTypes.FLOAT, Opcodes.FSUB);
    /** Add two longs */
    public static final SingleInstructionInline LongAdd =
            new SingleInstructionInline(new Type[] { SimpleTypes.LONG, SimpleTypes.LONG}, SimpleTypes.LONG, Opcodes.LADD);
    /** Bitwise-and two longs */
    public static final SingleInstructionInline LongAnd =
            new SingleInstructionInline(new Type[] { SimpleTypes.LONG, SimpleTypes.LONG}, SimpleTypes.LONG, Opcodes.LAND);
    /** Divide two longs */
    public static final SingleInstructionInline LongDiv =
            new SingleInstructionInline(new Type[] { SimpleTypes.LONG, SimpleTypes.LONG}, SimpleTypes.LONG, Opcodes.LDIV);
    /** Multiply two longs */
    public static final SingleInstructionInline LongMul =
            new SingleInstructionInline(new Type[] { SimpleTypes.LONG, SimpleTypes.LONG}, SimpleTypes.LONG, Opcodes.LMUL);
    /** Negate a long */
    public static final SingleInstructionInline LongNeg =
            new SingleInstructionInline(new Type[] { SimpleTypes.LONG }, SimpleTypes.LONG, Opcodes.LNEG);
    /** Bitwise-or two longs */
    public static final SingleInstructionInline LongOr =
            new SingleInstructionInline(new Type[] { SimpleTypes.LONG, SimpleTypes.LONG}, SimpleTypes.LONG, Opcodes.LOR);
    /** Get the remainder from dividing two longs */
    public static final SingleInstructionInline LongRem =
            new SingleInstructionInline(new Type[] { SimpleTypes.LONG, SimpleTypes.LONG}, SimpleTypes.LONG, Opcodes.LREM);
    /** Bitwise-shift left a long */
    public static final SingleInstructionInline LongShiftLeft =
            new SingleInstructionInline(new Type[] { SimpleTypes.LONG, SimpleTypes.LONG}, SimpleTypes.LONG, Opcodes.LSHL);
    /** Arithmetic (sign-extended) Bitwise-shift right a long */
    public static final SingleInstructionInline LongArithShiftRight =
            new SingleInstructionInline(new Type[] { SimpleTypes.LONG, SimpleTypes.LONG}, SimpleTypes.LONG, Opcodes.LSHR);
    /** Logical (no sign extension) Bitwise-shift right a long */
    public static final SingleInstructionInline LongLogicalShiftRight =
            new SingleInstructionInline(new Type[] { SimpleTypes.LONG, SimpleTypes.LONG}, SimpleTypes.LONG, Opcodes.LUSHR);
    /** Subtract two longs */
    public static final SingleInstructionInline LongSub =
            new SingleInstructionInline(new Type[] { SimpleTypes.LONG, SimpleTypes.LONG}, SimpleTypes.LONG, Opcodes.LSUB);
    /** Bitwise-exclusive-or two longs */
    public static final SingleInstructionInline LongXor =
            new SingleInstructionInline(new Type[] { SimpleTypes.LONG, SimpleTypes.LONG}, SimpleTypes.LONG, Opcodes.LXOR);

    private Inlines() {}
}
