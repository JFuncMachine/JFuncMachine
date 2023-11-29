package org.jfuncmachine.jfuncmachine.util.unification;

import java.util.HashSet;
import java.util.Set;

public class TypeHolder {
    public Unifiable concreteType;
    public Set<TypeHolder> linked;

    public TypeHolder() {
        linked = new HashSet<>();
    }

    public TypeHolder(Unifiable concreteType) {
        this.concreteType = concreteType;
        linked = new HashSet<>();
    }

    public boolean isFull() {
        return concreteType != null;
    }

    public void setType(Unifiable concreteType) {
        this.concreteType = concreteType;
        for (TypeHolder link: linked) {
            link.concreteType = concreteType;
        }
    }

    public void link(TypeHolder other) {
        linked.addAll(other.linked);
        for (TypeHolder link: linked) {
            link.linked.addAll(linked);
        }
    }

    public void unify(TypeHolder other) throws UnificationException {
        if (isFull()) {
            if (other.isFull()) {
                concreteType.unify(other.concreteType);
            } else {
                other.setType(this.concreteType);
            }
        } else if (other.isFull()) {
            this.setType(other.concreteType);
            this.link(other);
        } else {
            this.link(other);
        }
    }
}
