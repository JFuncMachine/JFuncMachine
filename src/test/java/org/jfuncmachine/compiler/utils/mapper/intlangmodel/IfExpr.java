package org.jfuncmachine.compiler.utils.mapper.intlangmodel;

import org.jfuncmachine.sexprlang.translate.ModelItem;

@ModelItem(symbol = "if")
public record IfExpr(Comparison comparison, IntExpr truePath, IntExpr falsePath, String filename, int lineNumber)
        implements IntExpr {
}
