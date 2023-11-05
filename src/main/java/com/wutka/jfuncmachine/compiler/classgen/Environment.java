package com.wutka.jfuncmachine.compiler.classgen;

import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;

public class Environment {
    protected final Environment parent;
    protected final HashMap<String,Integer> vars = new HashMap<>();
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

    public int allocate(String name) {
        int loc = nextVar;
        if (holes.size() > 0) {
            loc = holes.removeFirst();
        } else {
            nextVar++;
        }

        vars.put(name, loc);
        return loc;
    }

    public void free(int loc) {
        boolean found = false;
        for (String key: vars.keySet()) {
            if (vars.get(key) == loc) {
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
            throw new RuntimeException(
                    String.format("Attempted to free var %d, but it wasn't allocated", loc));
        }
    }

    public int getVar(String name) {
        Integer loc = vars.get(name);
        if (loc == null) {
            if (parent == null) {
                throw new RuntimeException(
                    String.format("Attempted to fetch var %s, but it does not exist", name));
            } else {
                return parent.getVar(name);
            }
        } else {
            return loc;
        }

    }
}