package org.jfuncmachine.compiler.utils.mapper.mappermodel;

import org.jfuncmachine.sexprlang.translate.ModelItem;

@ModelItem(isStringConstant = true)
public record StringObj(String value) {
}
