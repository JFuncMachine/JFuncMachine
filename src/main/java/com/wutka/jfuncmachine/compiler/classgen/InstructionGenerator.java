package com.wutka.jfuncmachine.compiler.classgen;

import com.wutka.jfuncmachine.compiler.exceptions.JFuncMachineException;
import com.wutka.jfuncmachine.compiler.model.ClassDef;
import com.wutka.jfuncmachine.compiler.model.MethodDef;
import com.wutka.jfuncmachine.compiler.model.types.*;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.lang.reflect.Method;

/**
 *  A wrapper to emit Java byte codes.
 * <p>
 *  This class is used by the various model classes to create Java byte codes for each
 *  kind of code structure implemented by this library.
 *  <p>
 *  Each method that emits an instruction returns the InstructionGenerator so that instructions can
 *  be changed, such as:
 *  <pre>
 *  gen.iload_0()
 *     .iload_1()
 *     .iadd()
 *     .ireturn()
 *  </pre>
 * <p>
 *  Any strings that refer to types in these instructions are assumed to be in the internal
 *  format where "." in the name is replaced with "/" (e.g. "java/lang/String" instead of "java.lang.String".
 * <p>
 *  For more details on each instruction, consult
 *  <a href="https://docs.oracle.com/javase/specs/jvms/se21/html/jvms-6.html">Chapter 6 of the Java
 *  Virtual Machine specification</a>.
 *  <p>
 *  If you find yourself wondering where particular parameters are, they are likely expected to be
 *  on the stack. For example, <pre>iadd</pre> takes no parameters, it expects the two integers to
 *  add to be on the stack. It pops them, adds them, and pushes the result onto the stack.
 *  Likewise, when using one of the method invocation instructions, the instruction itself has the
 *  class that contains the method, and the method name, but the object on which the method is being
 *  invoked is on the stack, as are the method parameters.
 */
@SuppressWarnings({"unused", "UnusedReturnValue"})
public class InstructionGenerator {
    /** The parent class generator */
    protected ClassGenerator classGen;

    /** The current class definition being generated */
    protected ClassDef generatingClass;

    /** The list of instructions for the current method */
    protected InsnList instructionList;

    /** Create a new instruction generator for the given class generator and class definition, appending
     * instructions to the given instruction list.
     * @param classGen The parent class generator
     * @param generatingClass The current class definition being generated
     * @param instructionList The current list of generated instructions
     */
    protected InstructionGenerator(ClassGenerator classGen, ClassDef generatingClass, InsnList instructionList) {
        this.classGen = classGen;
        this.generatingClass = generatingClass;
        this.instructionList = instructionList;
    }

    /** Emit an instruction to load an object from an array */
    public InstructionGenerator aaload() { instructionList.add(new InsnNode(Opcodes.AALOAD)); return this; }
    /** Emit an instruction to store an object into an array */
    public InstructionGenerator aastore() { instructionList.add(new InsnNode(Opcodes.AASTORE)); return this; }
    /** Emit an instruction to push a null constant onto the stack */
    public InstructionGenerator aconst_null() { instructionList.add(new InsnNode(Opcodes.AASTORE)); return this; }
    /** Emit an instruction to load a reference from a local variable */
    public InstructionGenerator aload(int index) { instructionList.add(new VarInsnNode(Opcodes.ALOAD, index)); return this; }
    /** Emit an instruction to create a new array of objects of a specified type */
    public InstructionGenerator anewarray(String type) { instructionList.add(new TypeInsnNode(Opcodes.ANEWARRAY, type)); return this; }
    /** Emit an instruction to return an object from the current method */
    public InstructionGenerator areturn() { instructionList.add(new InsnNode(Opcodes.ARETURN)); return this; }
    /** Emit an instruction to retrieve the length of an array */
    public InstructionGenerator arraylength() { instructionList.add(new InsnNode(Opcodes.ARRAYLENGTH)); return this; }
    /** Emit an instruction to store a reference in a local variable */
    public InstructionGenerator astore(int index) { instructionList.add(new VarInsnNode(Opcodes.ASTORE, index)); return this; }
    /** Emit an instruction to throw an exception */
    public InstructionGenerator athrow() { instructionList.add(new InsnNode(Opcodes.ATHROW)); return this; }
    /** Emit an instruction to fetch a value from a byte array */
    public InstructionGenerator baload() { instructionList.add(new InsnNode(Opcodes.BALOAD)); return this; }
    /** Emit an instruction to store a value in a byte array */
    public InstructionGenerator bastore() { instructionList.add(new InsnNode(Opcodes.BASTORE)); return this; }
    /** Emit an instruction to push a byte value onto the stack
     * (like loading a constant, but doesn't require an entry in the constant pool) */
    public InstructionGenerator bipush(int value) { instructionList.add(new IntInsnNode(Opcodes.BIPUSH, value)); return this; }
    /** Emit an instruction to fetch a value from a char array */
    public InstructionGenerator caload() { instructionList.add(new InsnNode(Opcodes.CALOAD)); return this; }
    /** Emit an instruction to store a value in a char array */
    public InstructionGenerator castore() { instructionList.add(new InsnNode(Opcodes.CASTORE)); return this; }
    /** Emit an instruction to verify that an object can be cast to the specified type */
    public InstructionGenerator checkcast(String type) { instructionList.add(new TypeInsnNode(Opcodes.CHECKCAST, type)); return this; }
    /** Emit an instruction to convert a double value to a float */
    public InstructionGenerator d2f() { instructionList.add(new InsnNode(Opcodes.D2F)); return this; }
    /** Emit an instruction to convert a double value to an int */
    public InstructionGenerator d2i() { instructionList.add(new InsnNode(Opcodes.D2I)); return this; }
    /** Emit an instruction to convert a double value to a long */
    public InstructionGenerator d2l() { instructionList.add(new InsnNode(Opcodes.D2L)); return this; }
    /** Emit an instruction to add two doubles */
    public InstructionGenerator dadd() { instructionList.add(new InsnNode(Opcodes.DADD)); return this; }
    /** Emit an instruction to fetch a value from an array of doubles */
    public InstructionGenerator daload() { instructionList.add(new InsnNode(Opcodes.DALOAD)); return this; }
    /** Emit an instruction to store a value in an array of doubles */
    public InstructionGenerator dastore() { instructionList.add(new InsnNode(Opcodes.DASTORE)); return this; }
    /** Emit an instruction to compare two doubles where NaN is greater than a value */
    public InstructionGenerator dcmpg() { instructionList.add(new InsnNode(Opcodes.DCMPG)); return this; }
    /** Emit an instruction to compare two doubles where NaN is less than a value */
    public InstructionGenerator dcmpl() { instructionList.add(new InsnNode(Opcodes.DCMPL)); return this; }
    /** Emit an instruction to push a double value of 0.0 onto the stack */
    public InstructionGenerator dconst_0() { instructionList.add(new InsnNode(Opcodes.DCONST_0)); return this; }
    /** Emit an instruction to push a double value of 1.0 onto the stack */
    public InstructionGenerator dconst_1() { instructionList.add(new InsnNode(Opcodes.DCONST_1)); return this; }
    /** Emit an instruction to divide a double value by a double value */
    public InstructionGenerator ddiv() { instructionList.add(new InsnNode(Opcodes.DDIV)); return this; }
    /** Emit an instruction to load a double value from a local variable */
    public InstructionGenerator dload(int index) { instructionList.add(new VarInsnNode(Opcodes.DLOAD, index)); return this; }
    /** Emit an instruction to multiply two double values */
    public InstructionGenerator dmul() { instructionList.add(new InsnNode(Opcodes.DMUL)); return this; }
    /** Emit an instruction to negate a double value */
    public InstructionGenerator dneg() { instructionList.add(new InsnNode(Opcodes.DNEG)); return this; }
    /** Emit an instruction to compute the remainder when dividing one double value by another */
    public InstructionGenerator drem() { instructionList.add(new InsnNode(Opcodes.DREM)); return this; }
    /** Emit an instruction to return a double value from the current method */
    public InstructionGenerator dreturn() { instructionList.add(new InsnNode(Opcodes.DRETURN)); return this; }
    /** Emit an instruction to store a double value in a local variable */
    public InstructionGenerator dstore(int index) { instructionList.add(new VarInsnNode(Opcodes.DSTORE, index)); return this; }
    /** Emit an instruction to subtract two double values */
    public InstructionGenerator dsub() { instructionList.add(new InsnNode(Opcodes.DSUB)); return this; }
    /** Emit an instruction to duplicate the value on top of the stack.
     * The value must be a 1-slot value (i.e. not a double or a long)
     */
    public InstructionGenerator dup() { instructionList.add(new InsnNode(Opcodes.DUP)); return this; }

    /** Emit an instruction to duplicate the top value on the stack and insert it two positions back.
     * <p>
     * The top two values on the stack must be 1-slot values (i.e. not double or long)
     * <pre>Stack:  x3 x2 (x1) becomes  x3 x1 x2 (x1)</pre>
     */
    public InstructionGenerator dup_x1() { instructionList.add(new InsnNode(Opcodes.DUP_X1)); return this; }
    /** Emit an instruction to duplicate the top two values on the stack (or the top value
     * if it is a 2-slot value (long or double) and place it two or three positions back
     * <p>
     * The top must be a 1-slot value, if x3, x2, and x1 are all 1-slot values, this is:
     * <pre>Stack: x3 x2 x1 becomes  x1 x3 x2 x1</pre>
     * <p>
     * If x2 is a two-slot value (x1 must still be a 1-slot), this is:
     * <pre>Stack: x3 x2 x1 becomes x3 x1 x2 x1</pre>
     */
    public InstructionGenerator dup_x2() { instructionList.add(new InsnNode(Opcodes.DUP_X2)); return this; }
    /** Emit an instruction to duplicate the top two slots on the stack.
     * <p>
     * If x2 and x1 are 1-slot values, this is:
     * <pre>Stack: x2 x1  becomes  x2 x1 x2 x1</pre>
     * <p>
     * If x1 is a 2-slot value, this is:
     * <pre>Stack: x2 x1 becomes x2 x1 x1</pre>
     */
    public InstructionGenerator dup2() { instructionList.add(new InsnNode(Opcodes.DUP2)); return this; }
    /** Emit an instruction to duplicate the top one or two values on the stack and
     * insert them two or three slots back.
     * <p>
     * If x2 and x1 are 1-slot values, this is:
     * <pre>Stack: x3 x2 x1 becomes x2 x1 x3 x2 x1</pre>
     * <p>
     * If x2 is a 1-slot value and x1 is a two slot value, this is:
     * <pre>Stack: x3 x2 x1 becomes x3 x1 x2 x1</pre>
     */
    public InstructionGenerator dup2_x1() { instructionList.add(new InsnNode(Opcodes.DUP2_X1)); return this; }
    /** Emit an instruction to duplicate the top one or two values on the stack and
     * insert them two, three, or four slots back.
     * <p>
     * If x4, x3, x2, and x1 are all 1-slot values, this is:
     * <pre>Stack: x4 x3 x2 x1 becomes x2 x1 x4 x3 x2 x1</pre>
     * <p>
     * If x3 and x2 are 1-slot values and x1 is a two-slot value, this is:
     * <pre>Stack: x3 x2 x1 becomes x1 x3 x2 x1</pre>
     * <p>
     * If x3 is a two-slot value (double or long) and x2 and x1 are 1-slot values, this is:
     * <pre>Stack: x2 x1 x3 x2 x1</pre>
     * <p>
     * If x2 and x1 are both 2-slot values (double or long), this is:
     * <pre>Stack: x2 x1 becomes x1 x2 x1</pre>
     */
    public InstructionGenerator dup2_x2() { instructionList.add(new InsnNode(Opcodes.DUP2_X2)); return this; }
    /** Emit an instruction to convert a float to a double */
    public InstructionGenerator f2d() { instructionList.add(new InsnNode(Opcodes.F2D)); return this; }
    /** Emit an instruction to convert a float to an int */
    public InstructionGenerator f2i() { instructionList.add(new InsnNode(Opcodes.F2I)); return this; }
    /** Emit an instruction to convert a float to a long */
    public InstructionGenerator f2l() { instructionList.add(new InsnNode(Opcodes.F2L)); return this; }
    /** Emit an instruction to add two float values */
    public InstructionGenerator fadd() { instructionList.add(new InsnNode(Opcodes.FADD)); return this; }
    /** Emit an instruction to fetch a float value from an array */
    public InstructionGenerator faload() { instructionList.add(new InsnNode(Opcodes.FALOAD)); return this; }
    /** Emit an instruction to store a float value in an array */
    public InstructionGenerator fastore() { instructionList.add(new InsnNode(Opcodes.FASTORE)); return this; }
    /** Emit an instruction to compare two floats where NaN is greater than a value */
    public InstructionGenerator fcmpg() { instructionList.add(new InsnNode(Opcodes.FCMPG)); return this; }
    /** Emit an instruction to compare two floats where NaN is less than a value */
    public InstructionGenerator fcmpl() { instructionList.add(new InsnNode(Opcodes.FCMPL)); return this; }
    /** Emit an instruction to push a float constant 0.0 onto the stack */
    public InstructionGenerator fconst_0() { instructionList.add(new InsnNode(Opcodes.FCONST_0)); return this; }
    /** Emit an instruction to push a float constant 1.0 onto the stack */
    public InstructionGenerator fconst_1() { instructionList.add(new InsnNode(Opcodes.FCONST_1)); return this; }
    /** Emit an instruction to push a float constant 2.0 onto the stack */
    public InstructionGenerator fconst_2() { instructionList.add(new InsnNode(Opcodes.FCONST_2)); return this; }
    /** Emit an instruction to divide two float values */
    public InstructionGenerator fdiv() { instructionList.add(new InsnNode(Opcodes.FDIV)); return this; }
    /** Emit an instruction to load a float from a local variable */
    public InstructionGenerator fload(int index) { instructionList.add(new VarInsnNode(Opcodes.FLOAD, index)); return this; }
    /** Emit an instruction to multiple two float values */
    public InstructionGenerator fmul() { instructionList.add(new InsnNode(Opcodes.FMUL)); return this; }
    /** Emit an instruction to negate a float value */
    public InstructionGenerator fneg() { instructionList.add(new InsnNode(Opcodes.FNEG)); return this; }
    /** Emit an instruction to compute the remainder when dividing two float values */
    public InstructionGenerator frem() { instructionList.add(new InsnNode(Opcodes.FREM)); return this; }
    /** Emit an instruction to return a float value from the current method */
    public InstructionGenerator freturn() { instructionList.add(new InsnNode(Opcodes.FRETURN)); return this; }
    /** Emit an instruction to store a float value in a local variable */
    public InstructionGenerator fstore(int index) { instructionList.add(new VarInsnNode(Opcodes.FSTORE, index)); return this; }
    /** Emit an instruction to two subtract two float values */
    public InstructionGenerator fsub() { instructionList.add(new InsnNode(Opcodes.FSUB)); return this; }

    /** Emit an instruction to fetch the value of a field
     * <p>
     * @param owner The class to which the field belongs
     * @param name The name of the field
     * @param descriptor The type descriptor of the field
     * @return
     */
    public InstructionGenerator getfield(String owner, String name, String descriptor) {
        instructionList.add(new FieldInsnNode(Opcodes.GETFIELD, owner, name, descriptor));
        return this;
    }
    /** Emit an instruction to fetch the value of a static field
     * <p>
     * @param owner The class to which the field belongs
     * @param name The name of the field
     * @param descriptor The type descriptor of the field
     * @return
     */
    public InstructionGenerator getstatic(String owner, String name, String descriptor) {
        instructionList.add(new FieldInsnNode(Opcodes.GETSTATIC, owner, name, descriptor));
        return this;
    }
    /** Emit an instruction to jump to a label */
    public InstructionGenerator gotolabel(Label label) { instructionList.add(new JumpInsnNode(Opcodes.GOTO, new LabelNode(label.label))); return this; }
    /** Emit an instruction to convert an int to a byte */
    public InstructionGenerator i2b() { instructionList.add(new InsnNode(Opcodes.I2B)); return this; }
    /** Emit an instruction to convert an int to a char */
    public InstructionGenerator i2c() { instructionList.add(new InsnNode(Opcodes.I2C)); return this; }
    /** Emit an instruction to convert an int to a double */
    public InstructionGenerator i2d() { instructionList.add(new InsnNode(Opcodes.I2D)); return this; }
    /** Emit an instruction to convert an int to a float */
    public InstructionGenerator i2f() { instructionList.add(new InsnNode(Opcodes.I2F)); return this; }
    /** Emit an instruction to convert an int to a long */
    public InstructionGenerator i2l() { instructionList.add(new InsnNode(Opcodes.I2L)); return this; }
    /** Emit an instruction to convert an int to a short */
    public InstructionGenerator i2s() { instructionList.add(new InsnNode(Opcodes.I2S)); return this; }
    /** Emit an instruction to add two integers */
    public InstructionGenerator iadd() { instructionList.add(new InsnNode(Opcodes.IADD)); return this; }
    /** Emit an instruction to fetch a value from an array of ints */
    public InstructionGenerator iaload() { instructionList.add(new InsnNode(Opcodes.IALOAD)); return this; }
    /** Emit an instruction to perform a bitwise and between two integers */
    public InstructionGenerator iand() { instructionList.add(new InsnNode(Opcodes.IAND)); return this; }
    /** Emit an instruction to store a value into an array of ints */
    public InstructionGenerator iastore() { instructionList.add(new InsnNode(Opcodes.IASTORE)); return this; }
    /** Emit an instruction to push the int constant -1 onto the stack */
    public InstructionGenerator iconst_m1() { instructionList.add(new InsnNode(Opcodes.ICONST_M1)); return this; }
    /** Emit an instruction to push the int constant 0 onto the stack */
    public InstructionGenerator iconst_0() { instructionList.add(new InsnNode(Opcodes.ICONST_0)); return this; }
    /** Emit an instruction to push the int constant 1 onto the stack */
    public InstructionGenerator iconst_1() { instructionList.add(new InsnNode(Opcodes.ICONST_1)); return this; }
    /** Emit an instruction to push the int constant 2 onto the stack */
    public InstructionGenerator iconst_2() { instructionList.add(new InsnNode(Opcodes.ICONST_2)); return this; }
    /** Emit an instruction to push the int constant 3 onto the stack */
    public InstructionGenerator iconst_3() { instructionList.add(new InsnNode(Opcodes.ICONST_3)); return this; }
    /** Emit an instruction to push the int constant 4 onto the stack */
    public InstructionGenerator iconst_4() { instructionList.add(new InsnNode(Opcodes.ICONST_4)); return this; }
    /** Emit an instruction to push the int constant 5 onto the stack */
    public InstructionGenerator iconst_5() { instructionList.add(new InsnNode(Opcodes.ICONST_5)); return this; }
    /** Emit an instruction to divide two integers */
    public InstructionGenerator idiv() { instructionList.add(new InsnNode(Opcodes.IDIV)); return this; }
    /** Emit an instruction to compare two objects for equality, and if true, to jump to the specified label */
    public InstructionGenerator if_acmpeq(Label label) { instructionList.add(new JumpInsnNode(Opcodes.IF_ACMPEQ, new LabelNode(label.label))); return this; }
    /** Emit an instruction to compare two objects for non-equality, and if true, to jump to the specified label */
    public InstructionGenerator if_acmpne(Label label) { instructionList.add(new JumpInsnNode(Opcodes.IF_ACMPNE, new LabelNode(label.label))); return this; }
    /** Emit an instruction to compare two ints for equality, and if true, to jump to the specified label */
    public InstructionGenerator if_icmpeq(Label label) { instructionList.add(new JumpInsnNode(Opcodes.IF_ICMPEQ, new LabelNode(label.label))); return this; }
    /** Emit an instruction to compare two ints for non-equality, and if true, to jump to the specified label */
    public InstructionGenerator if_icmpne(Label label) { instructionList.add(new JumpInsnNode(Opcodes.IF_ICMPNE, new LabelNode(label.label))); return this; }
    /** Emit an instruction to compare two ints for less-than, and if true, to jump to the specified label */
    public InstructionGenerator if_icmplt(Label label) { instructionList.add(new JumpInsnNode(Opcodes.IF_ICMPLT, new LabelNode(label.label))); return this; }
    /** Emit an instruction to compare two ints for greater-or-equal, and if true, to jump to the specified label */
    public InstructionGenerator if_icmpge(Label label) { instructionList.add(new JumpInsnNode(Opcodes.IF_ICMPGE, new LabelNode(label.label))); return this; }
    /** Emit an instruction to compare two ints for greater-than, and if true, to jump to the specified label */
    public InstructionGenerator if_icmpgt(Label label) { instructionList.add(new JumpInsnNode(Opcodes.IF_ICMPGT, new LabelNode(label.label))); return this; }
    /** Emit an instruction to compare two ints for less-or-equal, and if true, to jump to the specified label */
    public InstructionGenerator if_icmple(Label label) { instructionList.add(new JumpInsnNode(Opcodes.IF_ICMPLE, new LabelNode(label.label))); return this; }
    /** Emit an instruction to test whether the result of a comparison was equal (0), and if true, jump to the specified label */
    public InstructionGenerator ifeq(Label label) { instructionList.add(new JumpInsnNode(Opcodes.IFEQ, new LabelNode(label.label))); return this; }
    /** Emit an instruction to test whether the result of a comparison was equal (!= 0), and if true, jump to the specified label */
    public InstructionGenerator ifne(Label label) { instructionList.add(new JumpInsnNode(Opcodes.IFNE, new LabelNode(label.label))); return this; }
    /** Emit an instruction to test whether the result of a comparison was less (&lt; 0), and if true, jump to the specified label */
    public InstructionGenerator iflt(Label label) { instructionList.add(new JumpInsnNode(Opcodes.IFLT, new LabelNode(label.label))); return this; }
    /** Emit an instruction to test whether the result of a comparison was greater-or-equal (&gt;= 0), and if true, jump to the specified label */
    public InstructionGenerator ifge(Label label) { instructionList.add(new JumpInsnNode(Opcodes.IFGE, new LabelNode(label.label))); return this; }
    /** Emit an instruction to test whether the result of a comparison was greater-than (&gt; 0), and if true, jump to the specified label */
    public InstructionGenerator ifgt(Label label) { instructionList.add(new JumpInsnNode(Opcodes.IFGT, new LabelNode(label.label))); return this; }
    /** Emit an instruction to test whether the result of a comparison was less-or-equal (&lt;= 0), and if true, jump to the specified label */
    public InstructionGenerator ifle(Label label) { instructionList.add(new JumpInsnNode(Opcodes.IFLE, new LabelNode(label.label))); return this; }
    /** Emit an instruction to test whether an object reference is not null, and if true, jump to the specified label */
    public InstructionGenerator ifnonnull(Label label) { instructionList.add(new JumpInsnNode(Opcodes.IFNONNULL, new LabelNode(label.label))); return this; }
    /** Emit an instruction to test whether an object reference is null, and if true, jump to the specified label */
    public InstructionGenerator ifnull(Label label) { instructionList.add(new JumpInsnNode(Opcodes.IFNULL, new LabelNode(label.label))); return this; }
    /** Emit an instruction to increment a local variable by an amount specified by a byte
     * @param index The index of the local variable
     * @param incr The byte value to add to the local variable
     */
    public InstructionGenerator iinc(int index, int incr) { instructionList.add(new IincInsnNode(index, incr)); return this; }
    /** Emit an instruction to load a value from a local variable */
    public InstructionGenerator iload(int index) { instructionList.add(new VarInsnNode(Opcodes.ILOAD, index)); return this; }
    /** Emit an instruction to multiple to int values */
    public InstructionGenerator imul() { instructionList.add(new InsnNode(Opcodes.IMUL)); return this; }
    /** Emit an instruction to negate an int value */
    public InstructionGenerator ineg() { instructionList.add(new InsnNode(Opcodes.INEG)); return this; }
    /** Emit an instruction to test whether an object reference is an instance of the given class name,
     * pushing 1 onto the stack if true, and 0 if not. If the object reference is null, this
     * pushes a 0 onto the stack, which differs from Java's usual notion that a null can be
     * of any object type.
     */
    public InstructionGenerator instance_of(String type) { instructionList.add(new TypeInsnNode(Opcodes.INSTANCEOF, type)); return this; }
    /** Emit an instruction to create a dynamic method invocation. A bootstrap method is invoked the first time this
     * is invoked, and the bootstrap generates a MethodHandle that may invoke a method, or access a field. This is
     * mostly used for lambdas, but can be used for other features as well.
     * @param name The name of the method to invoke
     * @param descriptor The type descriptor of the method
     * @param handle A handle to the bootstrap method
     * @param bootstrapArgs The arguments for the bootstrap method
     */
    public InstructionGenerator invokedynamic(String name, String descriptor, Handle handle, Object... bootstrapArgs) {
        for (int i=0; i < bootstrapArgs.length; i++) {
            if (bootstrapArgs[i] instanceof Handle jfHandle) {
                bootstrapArgs[i] = jfHandle.getAsmHandle();
            }
        }
        instructionList.add(new InvokeDynamicInsnNode(name, descriptor, handle.getAsmHandle(), bootstrapArgs));
        return this;
    }

    /** Emit an instruction to invoke an interface method
     * @param owner The class/interface that owns the method
     * @param name The name of the method
     * @param descriptor The type descriptor of the method
     */
    public InstructionGenerator invokeinterface(String owner, String name, String descriptor) {
        instructionList.add(new MethodInsnNode(Opcodes.INVOKEINTERFACE, owner, name, descriptor));
        return this;
    }

    /** Emit an instruction to invoke a constructor, super, or private method
     * @param owner The class that owns the method
     * @param name The name of the method
     * @param descriptor The type descriptor of the method
     */
    public InstructionGenerator invokespecial(String owner, String name, String descriptor) {
        instructionList.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, owner, name, descriptor));
        return this;
    }
    /** Emit an instruction to invoke a static method
     * @param owner The class that owns the method
     * @param name The name of the method
     * @param descriptor The type descriptor of the method
     */
    public InstructionGenerator invokestatic(String owner, String name, String descriptor) {
        instructionList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, owner, name, descriptor));
        return this;
    }

    /** Emit an instruction to invoke a virtual method
     * @param owner The class that owns the method
     * @param name The name of the method
     * @param descriptor The type descriptor of the method
     */
    public InstructionGenerator invokevirtual(String owner, String name, String descriptor) {
        instructionList.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, owner, name, descriptor));
        return this;
    }
    /** Emit an instruction to perform a bitwise or of two ints */
    public InstructionGenerator ior() { instructionList.add(new InsnNode(Opcodes.IOR)); return this; }
    /** Emit an instruction to compute the remainder from dividing two ints */
    public InstructionGenerator irem() { instructionList.add(new InsnNode(Opcodes.IREM)); return this; }
    /** Emit an instruction to return an int value from the current method */
    public InstructionGenerator ireturn() { instructionList.add(new InsnNode(Opcodes.IRETURN)); return this; }
    /** Emit an instruction to shift an int left by a number of bits */
    public InstructionGenerator ishl() { instructionList.add(new InsnNode(Opcodes.ISHL)); return this; }
    /** Emit an instruction to shift an int right by a number of bits, extending the sign bit */
    public InstructionGenerator ishr() { instructionList.add(new InsnNode(Opcodes.ISHR)); return this; }
    /** Emit an instruction to store an int in a local variable */
    public InstructionGenerator istore(int index) { instructionList.add(new VarInsnNode(Opcodes.ISTORE, index)); return this; }
    /** Emit an instruction to subtract two ints*/
    public InstructionGenerator isub() { instructionList.add(new InsnNode(Opcodes.ISUB)); return this; }
    /** Emit an instruction to shift an int right by a number of bits, not extending the sign bit */
    public InstructionGenerator iushr() { instructionList.add(new InsnNode(Opcodes.IUSHR)); return this; }
    /** Emit an instruction to perform a bitwise exclusive-or on two ints */
    public InstructionGenerator ixor() { instructionList.add(new InsnNode(Opcodes.IXOR)); return this; }
    /** Emit an instruction to jump to a subroutine
     * @param label The label to jump to
     */
    public InstructionGenerator jsr(Label label) { instructionList.add(new JumpInsnNode(Opcodes.JSR, new LabelNode(label.label))); return this; }
    /** Emit an instruction to convert a long to a double */
    public InstructionGenerator l2d() { instructionList.add(new InsnNode(Opcodes.L2D)); return this; }
    /** Emit an instruction to convert a long to a float */
    public InstructionGenerator l2f() { instructionList.add(new InsnNode(Opcodes.L2F)); return this; }
    /** Emit an instruction to convert a long to an int */
    public InstructionGenerator l2i() { instructionList.add(new InsnNode(Opcodes.L2I)); return this; }
    /** Emit an instruction to generate a label at the current instruction position */
    public InstructionGenerator label(Label label) { instructionList.add(new LabelNode(label.label)); return this; }
    /** Emit an instruction to add to longs */
    public InstructionGenerator ladd() { instructionList.add(new InsnNode(Opcodes.LADD)); return this; }
    /** Emit an instruction to fetch a value from an array of longs */
    public InstructionGenerator laload() { instructionList.add(new InsnNode(Opcodes.LALOAD)); return this; }
    /** Emit an instruction to do a bitwise and of two longs */
    public InstructionGenerator land() { instructionList.add(new InsnNode(Opcodes.LAND)); return this; }
    /** Emit an instruction to store a value in an array of longs */
    public InstructionGenerator lastore() { instructionList.add(new InsnNode(Opcodes.LASTORE)); return this; }
    /** Emit an instruction to compare two long values */
    public InstructionGenerator lcmp() { instructionList.add(new InsnNode(Opcodes.LCMP)); return this; }
    /** Emit an instruction to push a long constant 0 onto the stack */
    public InstructionGenerator lconst_0() { instructionList.add(new InsnNode(Opcodes.LCONST_0)); return this; }
    /** Emit an instruction to push a long constant 1 onto the stack */
    public InstructionGenerator lconst_1() { instructionList.add(new InsnNode(Opcodes.LCONST_1)); return this; }
    /** Emit an instruction to push a constant double onto the stack.
     * <p>
     * This method will insert a dconst_0 or dconst_1 instead of ldc if the constant is 0.0 or 1.0
     */
    public InstructionGenerator ldc(double d) {
        if (d == 0.0) {
            return dconst_0();
        } else if (d == 1.0) {
            return dconst_1();
        } else {
            instructionList.add(new LdcInsnNode(d));
            return this;
        }
    }
    /** Emit an instruction to push a constant int onto the stack.
     * <p>
     * This method will insert an iconst_m1, iconst_0, iconst_1, iconst_2, iconst_3, iconst_4, or iconst_5
     * instead of ldc if the int value is -1, 0, 1, 2, 3, 4, or 5.
     */
    public InstructionGenerator ldc(int i) {
        switch (i) {
            case -1 -> iconst_m1();
            case 0 -> iconst_0();
            case 1 -> iconst_1();
            case 2 -> iconst_2();
            case 3 -> iconst_3();
            case 4 -> iconst_4();
            case 5 -> iconst_5();
            default -> instructionList.add(new LdcInsnNode(i));
        }
        return this;
    }
    /** Emit an instruction to push a constant float onto the stack.
     * <p>
     *  This method will insert an fconst_0, fconst_1, or fconst_2 instead of ldc
     *  if the constant value is 0.0, 1.0, or 2.0
     */
    public InstructionGenerator ldc(float f) {
        if (f == 0.0) {
            return fconst_0();
        } else if (f == 1.0) {
            return fconst_1();
        } else if (f == 2.0) {
            return fconst_2();
        } else {
            instructionList.add(new LdcInsnNode(f));
            return this;
        }
    }
    /** Emit an instruction to push a constant long onto the stack.
     * <p>
     * This method will insert lconst_0 or lconst_1 instead of ldc if the constant is 0 or 1
     */
    public InstructionGenerator ldc(long l) {
        if (l == 0) {
            return lconst_0();
        } else if (l == 1) {
            return lconst_1();
        } else {
            instructionList.add(new LdcInsnNode(l));
            return this;
        }
    }
    /** Emit an instruction to push a reference to a string constant onto the stack */
    public InstructionGenerator ldc(String s) { instructionList.add(new LdcInsnNode(s)); return this; }
    /** Emit an instruction to push a reference to a method onto the stack */
    public InstructionGenerator ldc(Method m) { instructionList.add(new LdcInsnNode(m)); return this; }
    /** Emit an instruction to push a method referenced by a handle onto the stack */
    public InstructionGenerator ldc(Handle h) { instructionList.add(new LdcInsnNode(h.getAsmHandle())); return this; }
    /** Emit an instruction to push a dynamic constant onto the stack
     * <p>
     * A dynamic constant is computed at runtime once by a bootstrap method
     */
    public InstructionGenerator ldc(ConstantDynamic cd) { instructionList.add(new LdcInsnNode(cd.getAsmConstantDynamic())); return this; }
    /** Emit an instruction to push a constant object onto the stack */
    public InstructionGenerator ldc(Object o) { instructionList.add(new LdcInsnNode(o)); return this; }
    /** Emit an instruction to divide two longs */
    public InstructionGenerator ldiv() { instructionList.add(new InsnNode(Opcodes.LDIV)); return this; }
    /** Emit an instruction to load a long from a local variable */
    public InstructionGenerator lload(int index) { instructionList.add(new VarInsnNode(Opcodes.LLOAD, index)); return this; }
    /** Emit an instruction to multiple two longs */
    public InstructionGenerator lmul() { instructionList.add(new InsnNode(Opcodes.LMUL)); return this; }
    /** Emit an instruction to negate a long value*/
    public InstructionGenerator lneg() { instructionList.add(new InsnNode(Opcodes.LNEG)); return this; }
    /** Emit an instruction to perform a switch using a sorted array of keys, and their corresponding labels.
     * <p>
     * The JVM will do a binary search to find the matching key and jump to the label associated with that key,
     * or else jump to the default label.
     * @param default_label The label to jump to if the key isn't found
     * @param keys A sorted list of integer keys
     * @param labels The corresponding label for each key
     */
    public InstructionGenerator lookupswitch(Label default_label, int[] keys, Label[] labels) {
        LabelNode[] labelNodes = new LabelNode[labels.length];
        for (int i=0; i < labels.length; i++) {
            labelNodes[i] = new LabelNode(labels[i].label);
        }
        instructionList.add(
                new LookupSwitchInsnNode(new LabelNode(default_label.label), keys, labelNodes));
        return this;
    }
    /** Emit an instruction to perform a bitwise or between two longs */
    public InstructionGenerator lor() { instructionList.add(new InsnNode(Opcodes.LOR)); return this; }
    /** Emit an instruction to compute the remainder of dividing two longs */
    public InstructionGenerator lrem() { instructionList.add(new InsnNode(Opcodes.LREM)); return this; }
    /** Emit an instruction to return a long value from the current method*/
    public InstructionGenerator lreturn() { instructionList.add(new InsnNode(Opcodes.LRETURN)); return this; }
    /** Emit an instruction to perform a bitwise left shift on a long for some number of bits */
    public InstructionGenerator lshl() { instructionList.add(new InsnNode(Opcodes.LSHL)); return this; }
    /** Emit an instruction to perform a bitwise right shift on a long value, extending the sign bit
     * (i.e. do an arithmetic right shift)
     */
    public InstructionGenerator lshr() { instructionList.add(new InsnNode(Opcodes.LSHR)); return this; }
    /** Emit an instruction to store a long in a local variable */
    public InstructionGenerator lstore(int index) { instructionList.add(new VarInsnNode(Opcodes.LSTORE, index)); return this; }
    /** Emit an instruction to subtract two longs */
    public InstructionGenerator lsub() { instructionList.add(new InsnNode(Opcodes.LSUB)); return this; }
    /** Emit an instruction to perform a bitwise right shift on a long value, not extending the sign bit
     * (i.e. do a logical right shift)
     */
    public InstructionGenerator lushr() { instructionList.add(new InsnNode(Opcodes.LUSHR)); return this; }
    /** Emit an instruction to perform a bitwise exclusive-or on two longs */
    public InstructionGenerator lxor() { instructionList.add(new InsnNode(Opcodes.LXOR)); return this; }
    /** Emit an instruction to enter a monitor (i.e. synchronize on an object) */
    public InstructionGenerator monitorenter() { instructionList.add(new InsnNode(Opcodes.MONITORENTER)); return this; }
    /** Emit an instruction to exit a monitor (i.e. synchronize on an object) */
    public InstructionGenerator monitorexit() { instructionList.add(new InsnNode(Opcodes.MONITOREXIT)); return this; }

    /** Create a multi-dimentional array
     * @param descriptor The type descriptor of the array
     * @param dims The number of dimensions
     */
    public InstructionGenerator multianewarray(String descriptor, int dims) {
        instructionList.add(new MultiANewArrayInsnNode(descriptor, dims));
        return this;
    }

    /** Emit an instruction to allocate a new object
     * <p>
     *     Note: This only allocates the object in memory, you have to call the constructor immediately afterwards.
     *     The typical sequence is:
     *     <pre>
     *       new_object(something)
     *       dup      ; You have to dup the object reference because the constructor consumes it and doesn't return one
     *       invokespecial(constructor)
     *     </pre>
     * @param type The type of object to allocate
     */
    public InstructionGenerator new_object(String type) { instructionList.add(new TypeInsnNode(Opcodes.NEW, type)); return this; }

    /** Emit an instruction to create a new array of some native type
     * @param type The type of the array elements
     */
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
    /** Emit an instruction to do nothing */
    public InstructionGenerator nop() { instructionList.add(new InsnNode(Opcodes.NOP)); return this; }
    /** Emit an instruction to pop a value off the stack */
    public InstructionGenerator pop() { instructionList.add(new InsnNode(Opcodes.POP)); return this; }
    /** Emit an instruction to pop two values off the stack (or one 2-slot value) */
    public InstructionGenerator pop2() { instructionList.add(new InsnNode(Opcodes.POP2)); return this; }

    /** Emit an instruction to store a value in a field
     * @param owner The class that owns the field
     * @param name The name of the field
     * @param descriptor The type descriptor of the field
     */
    public InstructionGenerator putfield(String owner, String name, String descriptor) {
        instructionList.add(new FieldInsnNode(Opcodes.PUTFIELD, owner, name, descriptor));
        return this;
    }

    /** Emit an instruction to store a value in a static field
     * @param owner The class that owns the field
     * @param name The name of the field
     * @param descriptor The type descriptor of the field
     */
    public InstructionGenerator putstatic(String owner, String name, String descriptor) {
        instructionList.add(new FieldInsnNode(Opcodes.PUTSTATIC, owner, name, descriptor));
        return this;
    }

    /** Returns from a subroutine using the return address stored in a local variable
     * @param index The index of the local variable containing the return address
     */
    public InstructionGenerator ret(int index) { instructionList.add(new VarInsnNode(Opcodes.RET, index)); return this; }
    /** Returns from the current method without returning a value */
    public InstructionGenerator return_void() { instructionList.add(new InsnNode(Opcodes.RETURN)); return this; }
    /** Emit an instruction to load a value from an array of shorts */
    public InstructionGenerator saload() { instructionList.add(new InsnNode(Opcodes.SALOAD)); return this; }
    /** Emit an instruction to store a value in an array of shorts */
    public InstructionGenerator sastore() { instructionList.add(new InsnNode(Opcodes.SASTORE)); return this; }

    /** Emit an instruction to push an immediate short value onto the stack.
     * This instruction lets you push short constants without having to store the value in
     * the constant pool and use ldc. The two bytes of the value are encoded in instructions.
     * @param value The value to push
     */
    public InstructionGenerator sipush(int value) { instructionList.add(new IntInsnNode(Opcodes.SIPUSH, value)); return this; }
    /** Emit an instruction to swap the top 2 1-slot values on the stack.
     * There is no 2-slot equivalent for this.
     */
    public InstructionGenerator swap() { instructionList.add(new InsnNode(Opcodes.SWAP)); return this; }

    /** Emit the labels and class name for a try-catch block.
     * For a chain of catches after a try, just generate multiple of these with the same start and end
     * but different handlers and catch classes for each different catch.
     * @param start The label indicating the start of the try block
     * @param end The label indicating the end of the try block
     * @param handler The label indicating the location of the exception handler
     * @param catchClass The class of exception that the handler catches
     */
    public InstructionGenerator trycatch(Label start, Label end, Label handler, String catchClass) {
        if (catchClass != null) {
            catchClass = catchClass.replace('.', '/');
        }
        classGen.addTryCatch(new TryCatchBlockNode(new LabelNode(start.label), new LabelNode(end.label),
                new LabelNode(handler.label), catchClass));
        return this;
    }

    /** Emit an instruction to perform a switch using a table lookup.
     * The table of labels should be of the size max-min+1. If the argument to the switch is a value between min and
     * max inclusive, the switch subtracts min from it, and uses it as an index into labels, and jumps to whatever
     * label is at that location. If the value is not between min and max, it jumps to the default label.
     * @param min The minimum value that can match
     * @param max The maximum value that can match
     * @param default_label The label to jump to if the value is &lt; min or &gt; max
     * @param labels The array of labels indicating where to jump for a matching value
     */
    public InstructionGenerator tableswitch(int min, int max, Label default_label, Label[] labels) {
        LabelNode[] labelNodes = new LabelNode[labels.length];
        for (int i=0; i < labels.length; i++) {
            labelNodes[i] = new LabelNode(labels[i].label);
        }
        instructionList.add(
                new TableSwitchInsnNode(min, max, new LabelNode(default_label.label), labelNodes));
        return this;
    }

    /** Returns the type of return instruction needed for a value of a specific type
     * @param type The type to get a return instruction for
     */
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

    /** Inserts a raw opcode into the instruction stream
     * @param opcode The opcode to insert
     */
    public InstructionGenerator rawOpcode(int opcode) { instructionList.add(new InsnNode(opcode)); return this; }

    /** Inserts a raw opcode that takes an int argument into the instruction stream
     * @param opcode The opcode to insert
     * @param index The int argument to insert
     */
    public InstructionGenerator rawIntOpcode(int opcode, int index) {
        instructionList.add(new IntInsnNode(opcode, index));
        return this;
    }

    /** Inserts a raw opcode for jumping to a label into the instruction stream.
     * This is typically used for if instructions where the opcode is computed based on
     * the kind of test being done.
     * @param opcode The opcode to insert
     * @param label The label the opcode should jump to under some condition
     */
    public InstructionGenerator rawJumpOpcode(int opcode, Label label) {
        instructionList.add(new JumpInsnNode(opcode, new LabelNode(label.label)));
        return this;
    }

    /** Schedules the generation of a lambda method
     * @param lambda The method to generate
     */
    public InstructionGenerator generateLambda(MethodDef lambda) {
        classGen.addMethodToGenerate(lambda);
        return this;
    }

    /** Creates a new local variable with a specific name and type, and a particular range
     * of instructions that it is valid over.
     * @param name The name of the variable
     * @param type The type of the variable
     * @param startLoc The label of the first instruction where the variable is valid
     * @param endLoc The label just past the last instruction where the variable is valid
     * @param index The index number of the local variable
     */
    public InstructionGenerator generateLocalVariable(String name, Type type,
                                                      Label startLoc, Label endLoc,
                                                      int index) {
        classGen.addLocalVariable(new LocalVariableNode(name, classGen.getTypeDescriptor(type),
                    classGen.getTypeDescriptor(type),
                new LabelNode(startLoc.label), new LabelNode(endLoc.label), index));
        return this;
    }
}
