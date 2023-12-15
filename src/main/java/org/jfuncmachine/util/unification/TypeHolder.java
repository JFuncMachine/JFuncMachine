package org.jfuncmachine.util.unification;

import java.util.HashSet;
import java.util.Set;

/** A type holder for unification
 *
 * A TypeHolder holds a type for unification. It can hold a concrete type, which means
 * that it is not expected to change any more, or it can be empty, and when it is unified
 * with a TypeHolder that contains a concrete type, it is given to hold that type, and any
 * other empty TypeHolders it was previously unified with also are given that concrete type.
 * @param <T> The type of types that the holder can hold
 */
public class TypeHolder<T extends Unifiable> {
    /** The type held by this holder, null if there isn't one yet */
    public T concreteType;
    /** The TypeHolders that this holder has been linked with */
    public Set<TypeHolder<T>> linked;

    /** Create a new TypeHolder */
    public TypeHolder() {
        linked = new HashSet<>();
    }

    /** Create a new TypeHolder already filled with a type
     *
     * @param concreteType The type to fill the holder with
     */
    public TypeHolder(T concreteType) {
        this.concreteType = concreteType;
        linked = new HashSet<>();
    }

    /** Tests whether this TypeHolder already has a type
     *
     * @return True if this holder has a type
     */
    public boolean isFull() {
        return concreteType != null;
    }

    /** Set the type for this TypeHolder.
     *
     * This will cause any empty holders linked to this type to be populated as well.
     *
     * @param concreteType The type the holder should contain
     */
    public void setType(T concreteType) {
        this.concreteType = concreteType;
        for (TypeHolder<T> link: linked) {
            link.concreteType = concreteType;
        }
    }

    /** Link this TypeHolder to another TypeHolder, and link them both together with
     * all the other TypeHolders they were each linked with
     *
     * @param other The other TypeHolder to link with
     */
    public void link(TypeHolder<T> other) {
        linked.add(this);
        linked.add(other);
        linked.addAll(other.linked);
        for (TypeHolder<T> link: linked) {
            link.linked = linked;
        }
    }

    /** Try to unify this TypeHolder with another.
     *
     * <ul>
     * <li>If this TypeHolder is empty and the other is full, this TypeHolder and the
     * other TypeHolders it is linked to are populated with the value in other. </li>
     * <li>If this TypeHolder is full and the other is empty, the other TypeHolder and
     * the TypeHolders it is linked to are all populated with the type in this TypeHolder.</li>
     * <li>If both TypeHolders are empty, they are linked together, along with any TypeHolders
     * they are already linked to.</li>
     * <li>If both TypeHolders are full, their types are unified to ensure that they match</li>
     * </ul>
     * @param other The TypeHolder to unify with
     * @throws UnificationException If there is an error unifying the types
     */
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
