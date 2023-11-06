package com.wutka.jfuncmachine.compiler.classgen;

import com.wutka.jfuncmachine.compiler.exceptions.JFuncMachineException;
import com.wutka.jfuncmachine.compiler.model.Method;
import com.wutka.jfuncmachine.compiler.model.expr.Binding;
import com.wutka.jfuncmachine.compiler.model.types.Type;

import java.util.*;

public class Environment {
    protected final Environment parent;
    protected final HashMap<String,EnvVar> vars = new HashMap<>();
    protected final HashMap<String, Binding> bindings = new HashMap<>();
    protected final SortedSet<Integer> holes = new TreeSet<>();
    protected int nextVar;
    protected final Method currentMethod;
    protected boolean headOfCapture;
    protected Set<EnvVar> capturedLocations;

    public Environment(Method currentMethod) {
        parent = null;
        nextVar = 0;
        this.currentMethod = currentMethod;
    }

    public Environment(Environment parent) {
        this.parent = parent;
        nextVar = parent.nextVar;
        this.currentMethod = parent.currentMethod;
    }

    public EnvVar allocate(String name, Type type) {
        int loc = nextVar;
        if (!holes.isEmpty()) {
            loc = holes.removeFirst();
        } else {
            nextVar += type.getStackSize();
        }

        EnvVar newVar = new EnvVar(name, type, loc);
        vars.put(name, newVar);
        return newVar;
    }

    public void free(int loc) {
        boolean found = false;
        for (String key: vars.keySet()) {
            if (vars.get(key).value == loc) {
                EnvVar removeVar = vars.remove(key);
                if (loc == nextVar - removeVar.type.getStackSize()) {
                    nextVar -= removeVar.type.getStackSize();
                } else {
                    holes.add(loc);
                }
                found = true;
            }
        }
        if (!found) {
            throw new JFuncMachineException(
                    String.format("Attempted to free var %d, but it wasn't allocated", loc));
        }
    }

    public void free(EnvVar envVar) {
        if (vars.containsKey(envVar.name)) {
            EnvVar removeVar = vars.remove(envVar.name);
            if (envVar.value == nextVar - removeVar.type.getStackSize()) {
                nextVar -= removeVar.type.getStackSize();
            } else {
                holes.add(envVar.value);
            }
        }
    }

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

    public void putBinding(String key, Binding binding) {
        bindings.put(key, binding);
    }

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

    public Method getCurrentMethod() {
        return currentMethod;
    }

    public void startCaptureAnalysis() {
        headOfCapture = true;
        capturedLocations = new HashSet<>();
    }

    public Set<EnvVar> getCaptured() {
        headOfCapture = false;
        return capturedLocations;
    }

    public void checkCaptured(String name) {
        if (headOfCapture) {
            if (vars.containsKey(name)) {
                capturedLocations.add(vars.get(name));
            } else if (parent != null) {
                parent.checkCaptured(name, this);
            }
        } else {
            return;
        }
    }

    public void checkCaptured(String name, Environment headOfCapture) {
        if (vars.containsKey(name)) {
            headOfCapture.capturedLocations.add(vars.get(name));
        } else if (parent != null) {
            parent.checkCaptured(name, headOfCapture);
        }
    }
}