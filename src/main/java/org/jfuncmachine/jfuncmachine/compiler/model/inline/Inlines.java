package org.jfuncmachine.jfuncmachine.compiler.model.inline;

import org.jfuncmachine.jfuncmachine.compiler.model.types.SimpleTypes;
import org.jfuncmachine.jfuncmachine.compiler.model.types.Type;
import org.objectweb.asm.Opcodes;

public class Inlines {
    public static final SingleInstructionInline IntAdd =
            new SingleInstructionInline(new Type[] { SimpleTypes.INT, SimpleTypes.INT}, SimpleTypes.INT, Opcodes.IADD);
    public static final SingleInstructionInline IntAnd =
            new SingleInstructionInline(new Type[] { SimpleTypes.INT, SimpleTypes.INT}, SimpleTypes.INT, Opcodes.IAND);
    public static final SingleInstructionInline IntDiv =
            new SingleInstructionInline(new Type[] { SimpleTypes.INT, SimpleTypes.INT}, SimpleTypes.INT, Opcodes.IDIV);
    public static final SingleInstructionInline IntMul =
            new SingleInstructionInline(new Type[] { SimpleTypes.INT, SimpleTypes.INT}, SimpleTypes.INT, Opcodes.IDIV);
    public static final SingleInstructionInline IntNeg =
            new SingleInstructionInline(new Type[] { SimpleTypes.INT }, SimpleTypes.INT, Opcodes.IDIV);
    public static final SingleInstructionInline IntOr =
            new SingleInstructionInline(new Type[] { SimpleTypes.INT, SimpleTypes.INT}, SimpleTypes.INT, Opcodes.IOR);
    public static final SingleInstructionInline IntRem =
            new SingleInstructionInline(new Type[] { SimpleTypes.INT, SimpleTypes.INT}, SimpleTypes.INT, Opcodes.IREM);
    public static final SingleInstructionInline IntShiftLeft =
            new SingleInstructionInline(new Type[] { SimpleTypes.INT, SimpleTypes.INT}, SimpleTypes.INT, Opcodes.ISHL);
    public static final SingleInstructionInline IntArithShiftRight =
            new SingleInstructionInline(new Type[] { SimpleTypes.INT, SimpleTypes.INT}, SimpleTypes.INT, Opcodes.ISHR);
    public static final SingleInstructionInline IntLogicalShiftRight =
            new SingleInstructionInline(new Type[] { SimpleTypes.INT, SimpleTypes.INT}, SimpleTypes.INT, Opcodes.IUSHR);
    public static final SingleInstructionInline IntSub =
            new SingleInstructionInline(new Type[] { SimpleTypes.INT, SimpleTypes.INT}, SimpleTypes.INT, Opcodes.ISUB);
    public static final SingleInstructionInline IntXor =
            new SingleInstructionInline(new Type[] { SimpleTypes.INT, SimpleTypes.INT}, SimpleTypes.INT, Opcodes.IXOR);
    public static final SingleInstructionInline DoubleAdd =
            new SingleInstructionInline(new Type[] { SimpleTypes.DOUBLE, SimpleTypes.DOUBLE}, SimpleTypes.DOUBLE, Opcodes.DADD);
    public static final SingleInstructionInline DoubleDiv =
            new SingleInstructionInline(new Type[] { SimpleTypes.DOUBLE, SimpleTypes.DOUBLE}, SimpleTypes.DOUBLE, Opcodes.DDIV);
    public static final SingleInstructionInline DoubleMul =
            new SingleInstructionInline(new Type[] { SimpleTypes.DOUBLE, SimpleTypes.DOUBLE}, SimpleTypes.DOUBLE, Opcodes.DMUL);
    public static final SingleInstructionInline DoubleNeg =
            new SingleInstructionInline(new Type[] { SimpleTypes.DOUBLE }, SimpleTypes.DOUBLE, Opcodes.DNEG);
    public static final SingleInstructionInline DoubleRem =
            new SingleInstructionInline(new Type[] { SimpleTypes.DOUBLE, SimpleTypes.DOUBLE}, SimpleTypes.DOUBLE, Opcodes.DREM);
    public static final SingleInstructionInline DoubleSub =
            new SingleInstructionInline(new Type[] { SimpleTypes.DOUBLE, SimpleTypes.DOUBLE}, SimpleTypes.DOUBLE, Opcodes.DSUB);
    public static final SingleInstructionInline FloatAdd =
            new SingleInstructionInline(new Type[] { SimpleTypes.FLOAT, SimpleTypes.FLOAT}, SimpleTypes.FLOAT, Opcodes.FADD);
    public static final SingleInstructionInline FloatDiv =
            new SingleInstructionInline(new Type[] { SimpleTypes.FLOAT, SimpleTypes.FLOAT}, SimpleTypes.FLOAT, Opcodes.FDIV);
    public static final SingleInstructionInline FloatMul =
            new SingleInstructionInline(new Type[] { SimpleTypes.FLOAT, SimpleTypes.FLOAT}, SimpleTypes.FLOAT, Opcodes.FMUL);
    public static final SingleInstructionInline FloatNeg =
            new SingleInstructionInline(new Type[] { SimpleTypes.FLOAT }, SimpleTypes.FLOAT, Opcodes.FNEG);
    public static final SingleInstructionInline FloatRem =
            new SingleInstructionInline(new Type[] { SimpleTypes.FLOAT, SimpleTypes.FLOAT}, SimpleTypes.FLOAT, Opcodes.FREM);
    public static final SingleInstructionInline FloatSub =
            new SingleInstructionInline(new Type[] { SimpleTypes.FLOAT, SimpleTypes.FLOAT}, SimpleTypes.FLOAT, Opcodes.FSUB);
    public static final SingleInstructionInline LongAdd =
            new SingleInstructionInline(new Type[] { SimpleTypes.LONG, SimpleTypes.LONG}, SimpleTypes.LONG, Opcodes.LADD);
    public static final SingleInstructionInline LongAnd =
            new SingleInstructionInline(new Type[] { SimpleTypes.LONG, SimpleTypes.LONG}, SimpleTypes.LONG, Opcodes.LAND);
    public static final SingleInstructionInline LongDiv =
            new SingleInstructionInline(new Type[] { SimpleTypes.LONG, SimpleTypes.LONG}, SimpleTypes.LONG, Opcodes.LDIV);
    public static final SingleInstructionInline LongMul =
            new SingleInstructionInline(new Type[] { SimpleTypes.LONG, SimpleTypes.LONG}, SimpleTypes.LONG, Opcodes.LDIV);
    public static final SingleInstructionInline LongNeg =
            new SingleInstructionInline(new Type[] { SimpleTypes.LONG }, SimpleTypes.LONG, Opcodes.LDIV);
    public static final SingleInstructionInline LongOr =
            new SingleInstructionInline(new Type[] { SimpleTypes.LONG, SimpleTypes.LONG}, SimpleTypes.LONG, Opcodes.LOR);
    public static final SingleInstructionInline LongRem =
            new SingleInstructionInline(new Type[] { SimpleTypes.LONG, SimpleTypes.LONG}, SimpleTypes.LONG, Opcodes.LREM);
    public static final SingleInstructionInline LongShiftLeft =
            new SingleInstructionInline(new Type[] { SimpleTypes.LONG, SimpleTypes.LONG}, SimpleTypes.LONG, Opcodes.LSHL);
    public static final SingleInstructionInline LongArithShiftRight =
            new SingleInstructionInline(new Type[] { SimpleTypes.LONG, SimpleTypes.LONG}, SimpleTypes.LONG, Opcodes.LSHR);
    public static final SingleInstructionInline LongLogicalShiftRight =
            new SingleInstructionInline(new Type[] { SimpleTypes.LONG, SimpleTypes.LONG}, SimpleTypes.LONG, Opcodes.LUSHR);
    public static final SingleInstructionInline LongSub =
            new SingleInstructionInline(new Type[] { SimpleTypes.LONG, SimpleTypes.LONG}, SimpleTypes.LONG, Opcodes.LSUB);
    public static final SingleInstructionInline LongXor =
            new SingleInstructionInline(new Type[] { SimpleTypes.LONG, SimpleTypes.LONG}, SimpleTypes.LONG, Opcodes.LXOR);
}
