package org.jfuncmachine.compiler.model.expr;

import org.jfuncmachine.compiler.classgen.ClassGenerator;
import org.jfuncmachine.compiler.classgen.EnvVar;
import org.jfuncmachine.compiler.classgen.Environment;
import org.jfuncmachine.compiler.model.types.SimpleTypes;
import org.jfuncmachine.compiler.model.types.Type;

/** Loops back to the beginning of the named binding, updating each of the binding variables to have
 * new values.
 * There are two ways to provide values for the recursion:
 * <ul>
 *    <li>Provide an expression for each variable defined in the binding in the same order as they
 *    are defined in the binding.</li>
 *    <li>Provide a list of binding pairs containing the variables to be updates and their new
 *    values. This can be handy if the binding has many variables and only a few need to be
 *    updated when recursing.</li>
 * </ul>
 */
public class BindingRecurse extends Expression {
    /** The name of the binding to recurse back to */
    public final String name;
    /** The expressions for each binding variable in the binding being recursed to */
    public final Expression[] nextValues;
    /** The bindings specifying which values in the binding should be updated */
    public final Binding.BindingPair[] bindingPairs;

    /** Create a binding recurse expression providing new values for each bound variable
     *
     * @param name The name of the binding to recurse to
     * @param nextValues The new values for each of the binding's variables
     */
    public BindingRecurse(String name, Expression[] nextValues) {
        super(null, 0);
        this.name = name;
        this.nextValues = nextValues;
        this.bindingPairs = null;
    }

    /** Create a binding recurse expression providing new values for each bound variable
     *
     * @param name The name of the binding to recurse to
     * @param nextValues The new values for each of the binding's variables
     * @param filename The source filename this expression is associated with
     * @param lineNumber The source line number this expression is associated with
     */
    public BindingRecurse(String name, Expression[] nextValues,
                          String filename, int lineNumber) {
        super(filename, lineNumber);
        this.name = name;
        this.nextValues = nextValues;
        this.bindingPairs = null;
    }

    /** Create a binding recurse expression providing binding pairs for the variables that should be updated
     *
     * @param name The name of the binding to recurse to
     * @param bindingPairs The binding pairs containing the new values for some variables in the binding.
     */
    public BindingRecurse(String name, Binding.BindingPair[] bindingPairs) {
        super(null, 0);
        this.name = name;
        this.nextValues = null;
        this.bindingPairs = bindingPairs;
    }

    /** Create a binding recurse expression providing binding pairs for the variables that should be updated
     *
     * @param name The name of the binding to recurse to
     * @param bindingPairs The binding pairs containing the new values for some variables in the binding.
     * @param filename The source filename this expression is associated with
     * @param lineNumber The source line number this expression is associated with
     */
    public BindingRecurse(String name, Binding.BindingPair[] bindingPairs,
                          String filename, int lineNumber) {
        super(filename, lineNumber);
        this.name = name;
        this.nextValues = null;
        this.bindingPairs = bindingPairs;
    }

    public Type getType() {
        return SimpleTypes.UNIT;  // The recurse is not a function, so it shouldn't have a value
    }

    @Override
    public void reset() {
        if (nextValues != null) {
            for (Expression expr : nextValues) {
                expr.reset();
            }
        }
    }

    public void findCaptured(Environment env) {
        if (nextValues != null) {
            for (Expression expr : nextValues) {
                expr.findCaptured(env);
            }
        }
        if (bindingPairs != null) {
            for (Binding.BindingPair pair: bindingPairs) {
                pair.value.findCaptured(env);
            }
        }
    }

    @Override
    public void generate(ClassGenerator generator, Environment env, boolean inTailPosition) {
        Binding binding = env.getBinding(name);
        if (nextValues != null) {
            if (nextValues.length != binding.bindings.length) {
                throw generateException(
                        String.format("Number of values (%d) does not match number of bindings (%d) for binding %s",
                                nextValues.length, binding.bindings.length, name));
            }
            for (int i = 0; i < nextValues.length; i++) {
                if (!nextValues[i].getType().equals(binding.bindings[i].value.getType())) {
                    throw generateException(
                            String.format("In recurse to named binding %s, binding %s had initial type %s, but new value is type %s",
                                    name, binding.bindings[i].name, binding.bindings[i].value.getType(),
                                    nextValues[i].getType()));
                }
            }
            for (int i = 0; i < nextValues.length; i++) {
                nextValues[i].generate(generator, env, false);
            }

            for (int i = nextValues.length - 1; i >= 0; i--) {
                EnvVar envVar = env.getVar(binding.bindings[i].name);

                envVar.generateSet(generator);
            }
        } else if (bindingPairs != null) {
            for (Binding.BindingPair pair: bindingPairs) {
                boolean found = false;
                for (Binding.BindingPair boundPair: binding.bindings) {
                    if (boundPair.name.equals(pair.name)) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    throw generateException(String.format("Variable %s does not exist in binding %s",
                            pair.name, binding.name));
                }
                pair.value.generate(generator, env, false);
                EnvVar envVar = env.getVar(pair.name);
                envVar.generateSet(generator);
            }
        }
        generator.instGen.gotolabel(binding.label);
    }
}
