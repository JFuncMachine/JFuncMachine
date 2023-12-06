package org.jfuncmachine.jfuncmachine.util.unification;

import java.util.HashSet;
import java.util.Set;

public class TypeHolder<T extends Unifiable> {
    public T concreteType;
    public Set<TypeHolder<T>> linked;

    public TypeHolder() {
        linked = new HashSet<>();
    }

    public TypeHolder(T concreteType) {
        this.concreteType = concreteType;
        linked = new HashSet<>();
    }

    public boolean isFull() {
        return concreteType != null;
    }

    public void setType(T concreteType) {
        this.concreteType = concreteType;
        for (TypeHolder<T> link: linked) {
            link.concreteType = concreteType;
        }
    }

    public void link(TypeHolder<T> other) {
        linked.add(this);
        linked.add(other);
        linked.addAll(other.linked);
        for (TypeHolder<T> link: linked) {
            link.linked = linked;
        }
    }

    public void unify(TypeHolder<T> other) throws UnificationException {
        this.link(other);
        if (isFull()) {
            if (other.isFull()) {
                concreteType.unify(other.concreteType);
            } else {
                other.setType(this.concreteType);
            }
        } else if (other.isFull()) {
            this.setType(other.concreteType);
        }
    }
}
