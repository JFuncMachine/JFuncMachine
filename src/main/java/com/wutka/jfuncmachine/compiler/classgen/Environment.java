package com.wutka.jfuncmachine.compiler.classgen;

import com.wutka.jfuncmachine.compiler.exceptions.JFuncMachineException;
import com.wutka.jfuncmachine.compiler.model.MethodDef;
import com.wutka.jfuncmachine.compiler.model.expr.Binding;
import com.wutka.jfuncmachine.compiler.model.types.Type;

import java.util.*;

/**
 * A map of the local variables available within a method.
 * Environments can be chained, so that within a method, if there is a block defined that adds local variables
 * within a particular scope, an environment can be chained to the existing one defining just those variables
 * added by the block. When the block is no longer in scope, the chained environment is discarded so its variables
 * are no longer visible.
 *
 * This class also assists in identifying variables captured by a lambda.
 *
 */
public class Environment {

    /** The parent environment */
    protected final Environment parent;

    /** A map of this environment's variables */
    protected final HashMap<String,EnvVar> vars = new HashMap<>();

    /** A map of this environment's bindings */
    protected final HashMap<String, Binding> bindings = new HashMap<>();

    /** A set of the local variable indices currently unassigned */
    protected final SortedSet<Integer> holes = new TreeSet<>();

    /** The next variable index to assign if there are no holes */
    protected int nextVar;

    /** The current method being defined */
    protected final MethodDef currentMethodDef;

    /** Indicates whether this environment is the top level of the environments being captured.
     * When determining what variables a lambda captures, the environment where the lambda is defined is
     * marked at the head of the capture, and any variables references before hitting the head of the capture
     * are within the lambda and not captured.
     */
    protected boolean headOfCapture;

    /** The set of variables captured by the current lambda */
    protected Set<EnvVar> capturedLocations;

    /**
     * Create a new environment for the current method definition
     *
     * @param currentMethodDef The current method definition
     */
    public Environment(MethodDef currentMethodDef) {
        parent = null;
        nextVar = 0;
        this.currentMethodDef = currentMethodDef;
    }

    /**
     * Create a new environment as the child of an existing environment
     *
     * @param parent The parent environment
     */
    public Environment(Environment parent) {
        this.parent = parent;
        nextVar = parent.nextVar;
        this.currentMethodDef = parent.currentMethodDef;
    }

    /**
     * Allocate a new environment variable
     * @param name The name of the variable
     * @param type The type of the variable
     * @return An object containing the name, type and index of the variable
     */
    public EnvVar allocate(String name, Type type) {
        // First assign the loc as the next index
        int loc = nextVar;

        // If there are holes, use one of those
        if (!holes.isEmpty()) {

            // If this type only occupies one slot, just use it
            if (type.getStackSize() == 1) {
                loc = holes.removeFirst();
            } else {
                // Otherwise, we need two holes that are consecutive
                boolean found = false;
                for (int hole: holes) {
                    // If there is a hole right next to this one, use it
                    if (holes.contains(hole+1)) {
                        loc = hole;
                        found = true;
                        break;
                    }
                }
                // If we found two consecutive holes, remove them from the set
                if (found) {
                    holes.remove(loc);
                    holes.remove(loc+1);
                } else {
                    // Otherwise, just use nextVar
                    nextVar += type.getStackSize();
                }
            }
        } else {
            nextVar += type.getStackSize();
        }

        EnvVar newVar = new EnvVar(name, type, loc);
        vars.put(name, newVar);
        return newVar;
    }

    /**
     * Allocate an anonymous variable.
     *
     * @param type The type of the anonymous variable
     * @return An object containing the name, type and index of the variable
     */
    public EnvVar allocate(Type type) {
        String name = UUID.randomUUID().toString();
        return allocate(name, type);
    }

    /**
     * Free a previously-allocated variable by location
     * @param loc The location of the variable to release
     */
    public void free(int loc) {
        boolean found = false;
        for (String key: vars.keySet()) {
            if (vars.get(key).value == loc) {
                EnvVar removeVar = vars.remove(key);

                // If the loc being freed is adjacent to nextVar, move nextVar back
                if (loc == nextVar - removeVar.type.getStackSize()) {
                    nextVar -= removeVar.type.getStackSize();
                } else {
                    // Otherwise add it to the holes
                    holes.add(loc);

                    // If the type requires two slots, add loc+1 to the holes as well
                    if (removeVar.type.getStackSize() == 2) {
                        holes.add(loc+1);
                    }
                }
                found = true;
            }
        }
        if (!found) {
            throw new JFuncMachineException(
                    String.format("Attempted to free var %d, but it wasn't allocated", loc));
        }
    }

    /**
     * Free a previously allocated variable
     * @param envVar The variable to free
     */
    public void free(EnvVar envVar) {
        // Make sure the var is actually allocated
        if (vars.containsKey(envVar.name)) {

            // Remove it from the map
            EnvVar removeVar = vars.remove(envVar.name);

            // If the var is adjacent to nextVar, move nextVar back
            if (envVar.value == nextVar - removeVar.type.getStackSize()) {
                nextVar -= removeVar.type.getStackSize();
            } else {
                // Otherwise just add it to the holes
                holes.add(envVar.value);

                // If the type requires two slots, add the next index to the holes as well
                if (removeVar.type.getStackSize() == 2) {
                    holes.add(envVar.value+1);
                }
            }
        }
    }

    /**
     * Retrieves a variable by name
     * @param name The name of the variable to retrieve
     * @return An object containing the name, type, and index of the variable
     */
    public EnvVar getVar(String name) {
        EnvVar envVar = vars.get(name);
        if (envVar == null) {
            if (parent == null) {
                throw new JFuncMachineException(
                    String.format("Attempted to fetch var %s, but it does not exist", name));
            } else {
                return parent.getVar(name);
            }
        } else {
            return envVar;
        }
    }

    /**
     * Stores the binding associated with a particular variable name
     * @param key The variable name associated with the binding
     * @param binding The binding the key is associated with
     */
    public void putBinding(String key, Binding binding) {
        bindings.put(key, binding);
    }

    /**
     * Retrieves the binding that a particular variable is associated with
     * @param key The variable name to look up
     * @return The binding associated with the variable identified by key
     */
    public Binding getBinding(String key) {
        Binding binding = bindings.get(key);
        if (binding == null) {
            if (parent == null) {
                throw new JFuncMachineException(
                        String.format("Attemped to locate unknown named binding %s", key));
            } else {
                return parent.getBinding(key);
            }
        } else {
            return binding;
        }
    }

    /**
     * Return the current method being processed
     * @return The current method being processed
     */
    public MethodDef getCurrentMethod() {
        return currentMethodDef;
    }

    /**
     * Start a capture analysis to find variables captured by a closure
     */
    public void startCaptureAnalysis() {
        headOfCapture = true;
        capturedLocations = new HashSet<>();
    }

    /**
     * Return a set of the variables captured during the analysis
     * @return A set containing the values identified as captured
     */
    public Set<EnvVar> getCaptured() {
        headOfCapture = false;
        return capturedLocations;
    }

    /**
     * Check to see if the named variable is captured
     * @param name The name to check
     */
    public void checkCaptured(String name) {
        // If this is the head of capture, then if the variable is contained here or in any
        // parent, the variable is captured
        if (headOfCapture) {
            if (vars.containsKey(name)) {
                // If this environment contains the var, add it to the captured locations
                capturedLocations.add(vars.get(name));
            } else if (parent != null) {
                // Otherwise, check with the parent, using the alternative checkCaptured that knows
                // it is above the head of capture
                parent.checkCaptured(name, this);
            }
        } else {
            if (vars.containsKey(name)) {
                // If this variable has belongs here, this environment is below the head of capture,
                // so it is not captured
                return;
            } else if (parent != null) {
                // Check with the parent to see if the variable is captured
                parent.checkCaptured(name);
            }
        }
    }

    /**
     * Check to see if a variable has been captured.
     * This version of checkCaptured is above the head of capture, so any variables checked are definitely
     * captured, so they are added to the capture list in the headOfCapture environment
     * @param name The variable name to check
     * @param headOfCapture The environment that contains the list of captured variables
     */
    public void checkCaptured(String name, Environment headOfCapture) {
        if (vars.containsKey(name)) {
            // If this environment contains the variable, add it to the list of captured variables
            // in the headOfCapture environment
            headOfCapture.capturedLocations.add(vars.get(name));
        } else if (parent != null) {
            // Otherwise, see if the parent environment has the variable
            parent.checkCaptured(name, headOfCapture);
        }
    }
}