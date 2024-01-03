package org.jfuncmachine.compiler.utils.mapper.intlangmodel;

import org.jfuncmachine.sexprlang.translate.ModelItem;

@ModelItem(symbol = "define")
public record FunctionDef(String name, Param[] params, IntExpr body) {

}
