package org.jfuncmachine.compiler.utils.mapper.mappermodel;

import org.jfuncmachine.sexprlang.translate.ModelItem;

@ModelItem(symbol="objWithStringObjs")
public record ObjWithStringObjs(StringObj foobar, StringObj[] stuff) {

}
