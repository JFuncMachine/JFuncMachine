package org.jfuncmachine.compiler.utils.mapper.mappermodel;

import org.jfuncmachine.sexprlang.translate.ModelItem;

@ModelItem(symbol="objWithVarargInts", varargStart = 1)
public record ObjWithVarargInts(String foobar, int[] stuff) {

}
