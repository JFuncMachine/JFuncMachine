package org.jfuncmachine.compiler.model.types;

/** A container of static fields representing the simple types */
public class SimpleTypes {
    /** A static boolean type instance */
    public static final Type BOOLEAN = new BooleanType();
    /** A static byte type instance */
    public static final Type BYTE = new ByteType();
    /** A static char type instance */
    public static final Type CHAR = new CharType();
    /** A static double type instance */
    public static final Type DOUBLE = new DoubleType();
    /** A static float type instance */
    public static final Type FLOAT = new FloatType();
    /** A static int type instance */
    public static final Type INT = new IntType();
    /** A static long type instance */
    public static final Type LONG = new LongType();
    /** A static short type instance */
    public static final Type SHORT = new ShortType();
    /** A static string type instance */
    public static final Type STRING = new StringType();
    /** A static unit type instance */
    public static final Type UNIT = new UnitType();

    /** An unused default constructor */
    private SimpleTypes () {}
}
