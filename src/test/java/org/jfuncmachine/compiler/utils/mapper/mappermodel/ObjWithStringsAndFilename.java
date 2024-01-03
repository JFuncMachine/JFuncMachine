package org.jfuncmachine.compiler.utils.mapper.mappermodel;

import org.jfuncmachine.sexprlang.translate.ModelItem;

@ModelItem(symbol="objWithStringsAndFilename")
public record ObjWithStringsAndFilename(String foobar, String[] stuff, String filename, int lineNumber) {

}
