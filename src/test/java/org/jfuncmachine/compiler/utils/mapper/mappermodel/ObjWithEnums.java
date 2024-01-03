package org.jfuncmachine.compiler.utils.mapper.mappermodel;

import org.jfuncmachine.sexprlang.translate.ModelItem;

@ModelItem(includeStartSymbol = true, varargStart = 1)
public record ObjWithEnums(EnumObj foobar, String[] stuff) {
    @ModelItem(isExprStart = true)
    public enum EnumObj {
        Moe("moe"),
        Larry("larry"),
        Curly("curly");

        public final String symbol;
        EnumObj(String symbol) {
            this.symbol = symbol;
        }
    }
}
