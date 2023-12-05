package org.jfuncmachine.jfuncmachine.sexprlang.translate;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(value=RUNTIME)
public @interface ModelItem {
    String symbol() default "";
    int exprLength() default 0;
    boolean isIntConstant() default false;
    boolean isDoubleConstant() default false;
    boolean isStringConstant() default false;
    boolean isSymbolExpr() default false;
    boolean isExprStart() default false;
    boolean includeStartSymbol() default false;
}
