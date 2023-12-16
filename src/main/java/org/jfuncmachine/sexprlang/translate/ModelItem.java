package org.jfuncmachine.sexprlang.translate;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/** An annotation describing how this model item is mapped in an S-expression */
@Retention(value=RUNTIME)
public @interface ModelItem {
    /** The s-expression symbol that indicates the start of this item
     *
     * @return The default symbol for this item (empty means none)
     */
    String symbol() default "";

    /** If true, this item should be created to hold an int constant
     *
     * @return True if this item should be created to hold an int constant
     */
    boolean isIntConstant() default false;

    /** If true, this item should be created to hold a double constant
     *
     * @return True if this item should be created to hold a double constant
     */
    boolean isDoubleConstant() default false;

    /** If true, this item should be created to hold a string constant
     *
     * @return True if this item should be created to hold a string constant
     */
    boolean isStringConstant() default false;

    /** If true, this item should be created to hold a symbol
     *
     * @return True if this item should be created to hold a symbol
     */
    boolean isSymbolExpr() default false;
    /** If true, when this item is matched it is the start of an expression whose type
     * is the parent class of this item (usually used for enum values within a class)
     * @return True if this item is the start of the expression for its parent class */
    boolean isExprStart() default false;

    /** This is the class to use by default when the mapper is looking for a particular
     * type of class
     * @return The class that this item is the default for
     */
    Class defaultForClass() default Object.class;

    /** When an enum starts an expression, the expression for that enum may or may not
     * be needed to create the class. This indicates whether to include it.
     * @return True if the first symbol matched for this item should be included
     */
    boolean includeStartSymbol() default false;

    /** If an item constructor has a vararg constructor that should be used,
     * set this to true
     * @return True if the item has a vararg constructor
     */
    int varargStart() default -1;
}
