package org.jfuncmachine.compiler.utils.mapper;

import org.jfuncmachine.compiler.utils.mapper.intlangmodel.IntlangSymbolMatcher;
import org.jfuncmachine.compiler.utils.mapper.intlangmodel.PrintExpr;
import org.jfuncmachine.sexprlang.parser.Parser;
import org.jfuncmachine.sexprlang.parser.SexprItem;
import org.jfuncmachine.sexprlang.parser.SexprList;
import org.jfuncmachine.sexprlang.translate.MappingException;
import org.jfuncmachine.sexprlang.translate.ModelMapper;
import org.jfuncmachine.sexprlang.translate.SexprToModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class TestIntlangModel {
    static ModelMapper mapper;

    @BeforeAll
    public static void setUp() throws MappingException {
        mapper = new ModelMapper("org.jfuncmachine.compiler.utils.mapper.intlangmodel");
    }

    @Test
    public void testPrintFunc() throws IOException, MappingException {
        SexprItem item = Parser.parseString("""
(define fact (n acc)
  (if (< n 2) acc
    (fact (- n 1) (* acc n))))

(print "10! is %d" (fact 10 1))
                """, null, true, new IntlangSymbolMatcher());

        Object[] obj = SexprToModel.sexprsToModel((SexprList) item, mapper, null);
    }
}
