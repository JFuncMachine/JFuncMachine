package org.jfuncmachine.compiler.utils.mapper.intlangmodel;

import org.jfuncmachine.sexprlang.translate.ModelItem;

@ModelItem(defaultForClass = IntExpr.class, includeStartSymbol = true, varargStart = 1)
public record FunctionCall(String name, IntExpr[] args, String filename, int lineNumber)
        implements IntExpr {
}
