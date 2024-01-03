package org.jfuncmachine.compiler.utils.mapper.intlangmodel;

import org.jfuncmachine.sexprlang.translate.ModelItem;

@ModelItem(includeStartSymbol = true)
public record IntBinaryExpr(ExprType exprType, IntExpr left, IntExpr right, String filename, int lineNumber)
        implements IntExpr {
    @ModelItem(isExprStart = true)
    public enum ExprType {
        Add("+"),
        Sub("-"),
        Mul("*"),
        Div("/");

        public final String symbol;
        ExprType(String symbol) {
            this.symbol = symbol;
        }
    }
}
