package org.jfuncmachine.compiler.utils.mapper.mappermodel;

import org.jfuncmachine.sexprlang.translate.ModelItem;

@ModelItem(symbol="objWithSimpleTypes")
public record ObjWithSimpleTypes(String foobar, String[] stuff, int intVal, int[] intVals,
                                 double doubleVal, double[] doubleVals) {

}
