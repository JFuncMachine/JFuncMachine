package org.jfuncmachine.compiler.utils.mapper.intlangmodel;

import org.jfuncmachine.sexprlang.translate.ModelItem;

@ModelItem(symbol = "print", varargStart = 1)
public record PrintExpr(String format, IntExpr[] params) {
}
