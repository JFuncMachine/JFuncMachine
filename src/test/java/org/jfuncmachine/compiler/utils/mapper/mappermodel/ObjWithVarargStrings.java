package org.jfuncmachine.compiler.utils.mapper.mappermodel;

import org.jfuncmachine.sexprlang.translate.ModelItem;

@ModelItem(symbol="objWithVarargStrings", varargStart = 1)
public record ObjWithVarargStrings(String foobar, String[] stuff) {

}
