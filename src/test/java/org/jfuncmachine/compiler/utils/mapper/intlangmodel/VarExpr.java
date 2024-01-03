package org.jfuncmachine.compiler.utils.mapper.intlangmodel;

import org.jfuncmachine.sexprlang.translate.ModelItem;

@ModelItem(isSymbolExpr = true)
public record VarExpr(String symbol, String filename, int lineNumber) implements IntExpr {
}
