package org.jfuncmachine.compiler.utils.mapper.mappermodel;

import org.jfuncmachine.sexprlang.translate.ModelItem;

@ModelItem(isDoubleConstant = true)
public record DoubleObj(double value) {
}
