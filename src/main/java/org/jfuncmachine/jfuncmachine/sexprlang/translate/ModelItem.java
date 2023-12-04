package org.jfuncmachine.jfuncmachine.sexprlang.translate;

public @interface ModelItem {
    String symbol() default "";
    int exprLength() default 0;
    boolean isIntConstant() default false;
    boolean isDoubleConstant() default false;
    boolean isStringConstant() default false;
    boolean isSymbolExpr() default false;
    boolean isExprStart() default false;
}
