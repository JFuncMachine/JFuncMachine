package org.jfuncmachine.compiler.utils.mapper.mappermodel;

import org.jfuncmachine.sexprlang.translate.ModelItem;

@ModelItem(symbol="objWithVarargDoubles", varargStart = 1)
public record ObjWithVarargDoubles(String foobar, double[] stuff) {

}
