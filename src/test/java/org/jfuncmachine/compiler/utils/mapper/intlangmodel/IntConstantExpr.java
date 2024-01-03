package org.jfuncmachine.compiler.utils.mapper.intlangmodel;

import org.jfuncmachine.sexprlang.translate.ModelItem;

@ModelItem(isIntConstant = true)
public record IntConstantExpr(int value, String filename, int lineNumber) implements IntExpr {
}
