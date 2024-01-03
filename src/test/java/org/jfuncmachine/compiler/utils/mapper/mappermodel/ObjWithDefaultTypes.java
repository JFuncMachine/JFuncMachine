package org.jfuncmachine.compiler.utils.mapper.mappermodel;

import org.jfuncmachine.sexprlang.translate.ModelItem;

@ModelItem(symbol="objWithDefaultTypes")
public record ObjWithDefaultTypes(StringObj foobar, StringObj[] stuff, IntObj intVal, IntObj[] intVals,
                                  DoubleObj doubleVal, DoubleObj[] doubleVals) {

}
