package org.jfuncmachine.compiler.utils.mapper.intlangmodel;

import org.jfuncmachine.sexprlang.translate.ModelItem;

@ModelItem(includeStartSymbol = true)
public record Comparison(CompType compType, IntExpr left, IntExpr right) {
    @ModelItem(isExprStart = true)
    public enum CompType {
        Equal("="),
        NotEqual("!="),
        Less("<"),
        LessEqual("<="),
        Greater(">"),
        GreaterEqual(">=");

        public final String symbol;
        CompType(String symbol) {
            this.symbol = symbol;
        }
    }
}
