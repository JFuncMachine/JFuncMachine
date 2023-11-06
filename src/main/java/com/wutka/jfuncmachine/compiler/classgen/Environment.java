package com.wutka.jfuncmachine.compiler.classgen;

import com.wutka.jfuncmachine.compiler.exceptions.JFuncMachineException;
import com.wutka.jfuncmachine.compiler.model.types.Type;

import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;

public class Environment {
    protected final Environment parent;
    protected final HashMap<String,EnvVar> vars = new HashMap<>();
    protected final SortedSet<Integer> holes = new TreeSet<>();
    protected int nextVar;

    public Environment() {
        parent = null;
        nextVar = 0;
    }

    public Environment(Environment parent) {
        this.parent = parent;
        nextVar = parent.nextVar;
    }

    public EnvVar allocate(String name, Type type) {
        int loc = nextVar;
        if (!holes.isEmpty()) {
            loc = holes.removeFirst();
        } else {
            nextVar++;
        }

        EnvVar newVar = new EnvVar(name, type, loc);
        vars.put(name, newVar);
        return newVar;
    }

    public void free(int loc) {
        boolean found = false;
        for (String key: vars.keySet()) {
            if (vars.get(key).value == loc) {
                vars.remove(key);
                if (loc == nextVar - 1) {
                    nextVar--;
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
            vars.remove(envVar.name);
            if (envVar.value == nextVar - 1) {
                nextVar--;
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
}