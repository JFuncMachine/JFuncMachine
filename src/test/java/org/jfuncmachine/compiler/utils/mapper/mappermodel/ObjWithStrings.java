package org.jfuncmachine.compiler.utils.mapper.mappermodel;

import org.jfuncmachine.sexprlang.translate.ModelItem;

@ModelItem(symbol="objWithStrings")
public record ObjWithStrings(String foobar, String[] stuff) {

}
