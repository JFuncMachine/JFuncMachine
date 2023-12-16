package org.jfuncmachine.compiler.model.expr;

import org.jfuncmachine.compiler.classgen.ClassGenerator;
import org.jfuncmachine.compiler.classgen.EnvVar;
import org.jfuncmachine.compiler.classgen.Environment;
import org.jfuncmachine.compiler.classgen.Label;
import org.jfuncmachine.compiler.model.types.*;
import org.objectweb.asm.Opcodes;

/** Bind expressions to variable names, and then execute an expression with those variables available.
 *  This is how you assign local variables, other than the ones that are parameters to a function.
 *  For example, this expression assigns a value to a variable named foo with a value of 42,
 *  and then adds 5 to that value.
 *  new Binding(new BindingPair("foo", new IntConstant(42)), Visibility.Separate,
 *      new InlineCall(Inlines.IntAdd, GetValue("foo", SimpleTypes.INT), IntConstant(5)))
 * <p>
 * A binding may contain an optional name that can be used with the BindingRecurse expression to loop
 * back to the beginning of the binding, updating the bound variables. This is one way to simulate
 * the functionality of recursive call.
 * <p>
 * The variables defined by a binding are local variables within the current Java method, but are only
 * visibile within the binding. It is possible to update the value of a local variable using
 * either the BindingRecurse expression, or the SetValue expression.
 */
public class Binding extends Expression {
    /**
     * The Visibility parameter controls whether or not binding variables are visible to other binding pair
     * expressions within the same binding.
     * <ul>
     * <li><pre>Separate</pre> means that a binding expression does not see its own variable nor those of any of the
     * other BindingPair objects in this binding (but if this binding occurs inside another binding, it can see
     * all the variables in the outer binding).
     * </li>
     * <li>
     * <pre>Previous</pre> means that each binding pair can see the variables in the binding pairs that came before
     * it in the current binding (as well as any variables defined in a parent binding).
     * </li>
     * </ul>
     */
    public enum Visibility {
        /** The binding expression does not see its own variable or any others in the current binding */
        Separate,
        /** The binding expression does not see its own variable but sees those in the current binding that
         * occur before it
         */
        Previous
    }

    /** The pairs of variables and expressions in this binding */
    public final BindingPair[] bindings;
    /** The expression to be executed in the context of these bindings */
    public final Expression expr;
    /** Controls whether binding pairs in this binding can see themselves or the bindings that came before them */
    public final Visibility visibility;
    /** An optional name that can be used to loop back to the beginning of the binding */
    public final String name;
    /** A label used by the recurse expression to loop back to the top of the binding */
    protected Label label;

    /** Create a new binding
     *
     * @param bindings The pairs of variable names and the expressions they are bound to
     * @param visibility Specify how binding pairs are able to see one another
     * @param expr An expression to execute in the context of this binding
     */
    public Binding(BindingPair[] bindings, Visibility visibility, Expression expr) {
        super(null, 0);
        this.bindings = bindings;
        this.visibility = visibility;
        this.expr = expr;
        this.name = null;
        label = null;
    }

    /** Create a new binding
     *
     * @param bindings The pairs of variable names and the expressions they are bound to
     * @param visibility Specify how binding pairs are able to see one another
     * @param expr An expression to execute in the context of this binding
     * @param filename The source filename this expression is associated with
     * @param lineNumber The source line number this expression is associated with
     */
    public Binding(BindingPair[] bindings, Visibility visibility, Expression expr,
                    String filename, int lineNumber) {
        super(filename, lineNumber);
        this.bindings = bindings;
        this.visibility = visibility;
        this.expr = expr;
        this.name = null;
        label = null;
    }

    /** Create a new binding
     *
     * @param name A name for this binding that can be used to create a recursive loop
     * @param bindings The pairs of variable names and the expressions they are bound to
     * @param visibility Specify how binding pairs are able to see one another
     * @param expr An expression to execute in the context of this binding
     */
    public Binding(String name, BindingPair[] bindings, Visibility visibility, Expression expr) {
        super(null, 0);
        this.bindings = bindings;
        this.visibility = visibility;
        this.expr = expr;
        this.name = name;
        this.label = new Label();
    }

    /** Create a new binding
     *
     * @param name A name for this binding that can be used to create a recursive loop
     * @param bindings The pairs of variable names and the expressions they are bound to
     * @param visibility Specify how binding pairs are able to see one another
     * @param expr An expression to execute in the context of this binding
     * @param filename The source filename this expression is associated with
     * @param lineNumber The source line number this expression is associated with
     */
    public Binding(String name, BindingPair[] bindings, Visibility visibility, Expression expr,
                   String filename, int lineNumber) {
        super(filename, lineNumber);
        this.bindings = bindings;
        this.visibility = visibility;
        this.expr = expr;
        this.name = name;
        this.label = new Label();
    }

    public Type getType() {
        return expr.getType();
    }

    public void reset() {
        label = null;
        if (name != null) {
            label = new Label();
        }
        for (BindingPair pair: bindings) {
            pair.value.reset();
        }
        expr.reset();
    }

    public void findCaptured(Environment env) {
        Environment newEnv = new Environment(env);
        for (BindingPair pair: bindings) {
            if (visibility == Visibility.Previous) {
                pair.value.findCaptured(newEnv);
            } else {
                pair.value.findCaptured(env);
            }
        }
        expr.findCaptured(newEnv);
    }

    @Override
    public void generate(ClassGenerator generator, Environment env, boolean inTailPosition) {
        Environment newEnv = new Environment(env);

        label = new Label();
        Label bindingEnd = new Label();

        for (BindingPair pair: bindings) {
            EnvVar envVar = null;

            if (visibility == Visibility.Separate) {
                pair.value.generate(generator, env, false);
            } else {
                pair.value.generate(generator, newEnv, false);
            }

            envVar = newEnv.allocate(pair.name, pair.value.getType());

            env.putBinding(envVar.name, this);

            Label bindingVarStart = new Label();

            generator.instGen.generateLocalVariable(pair.name, pair.value.getType(),
                    bindingVarStart, bindingEnd, envVar.index);
            generator.instGen.label(bindingVarStart);

            int opcode = switch (pair.value.getType()) {
                case BooleanType b -> Opcodes.ISTORE;
                case ByteType b -> Opcodes.ISTORE;
                case CharType c -> Opcodes.ISTORE;
                case DoubleType d -> Opcodes.DSTORE;
                case FloatType f -> Opcodes.FSTORE;
                case IntType i -> Opcodes.ISTORE;
                case LongType l -> Opcodes.LSTORE;
                case ShortType s -> Opcodes.ISTORE;
                default -> Opcodes.ASTORE;
            };
            generator.instGen.rawIntOpcode(opcode, envVar.index);
        }
        if (name != null) {
            generator.instGen.label(label);
            newEnv.putBinding(name, this);
        }
        expr.generate(generator, newEnv, true);
        generator.instGen.label(bindingEnd);
    }

    /** An association of a variable name with an expression value */
    public static class BindingPair {
        /** The name of the variable */
        public final String name;
        /** The value the variable is bound to */
        public final Expression value;

        /** Create a new binding pair
         *
         * @param name The name of the variable
         * @param value The value the variable is bound to
         */
        public BindingPair (String name, Expression value) {
            this.name = name;
            this.value = value;
        }
    }
}
