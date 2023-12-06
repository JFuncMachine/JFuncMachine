package org.jfuncmachine.jfuncmachine.sexprlang.translate;

import org.jfuncmachine.jfuncmachine.sexprlang.parser.*;

public interface SexprMapper {
    Object mapSymbol(SexprSymbol symbol) throws MappingException;
    Class mapSymbolToClass(SexprSymbol symbol) throws MappingException;
    Object mapDouble(SexprDouble d) throws MappingException;
    Object mapInt(SexprInt i) throws MappingException;
    Object mapString(SexprString s) throws MappingException;
    Object mapList(SexprList l, Class targetClass) throws MappingException;
}
