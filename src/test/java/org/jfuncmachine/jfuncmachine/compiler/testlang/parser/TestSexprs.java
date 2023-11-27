package org.jfuncmachine.jfuncmachine.compiler.testlang.parser;

import org.jfuncmachine.jfuncmachine.sexprlang.parser.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class TestSexprs {

    @Test
    public void testSymbol()
        throws IOException {
        SexprItem item = Parser.parseString("foobar", "test");
        Assertions.assertInstanceOf(SexprSymbol.class, item, "Symbol parse failed");
        Assertions.assertEquals(((SexprSymbol)item).value, "foobar", "Symbol should be foobar");
    }

    @Test
    public void testString()
            throws IOException {
        SexprItem item = Parser.parseString("\"foobar\"", "test");
        Assertions.assertInstanceOf(SexprString.class, item, "Symbol parse failed");
        Assertions.assertEquals(((SexprString)item).value, "foobar", "String should be foobar");
    }

    @Test
    public void testInt()
            throws IOException {
        SexprItem item = Parser.parseString("1234567", "test");
        Assertions.assertInstanceOf(SexprInt.class, item, "Int parse failed");
        Assertions.assertEquals(((SexprInt)item).value, 1234567, "Int should be 1234567");
    }

    @Test
    public void testDouble()
            throws IOException {
        SexprItem item = Parser.parseString("1234567.891011", "test");
        Assertions.assertInstanceOf(SexprFloat.class, item, "Int parse failed");
        Assertions.assertEquals(((SexprFloat)item).value, 1234567.891011, "Float should be 1234567.891011");
    }

    @Test
    public void testEmptyList() throws IOException {
        SexprItem item = Parser.parseString("()", "test");
        Assertions.assertInstanceOf(SexprList.class, item, "List parse failed");
        SexprList list = (SexprList) item;
        Assertions.assertEquals(0, list.value.size(), "Empty list should produce empty list");
    }

    @Test
    public void testMixedList() throws IOException {
        SexprItem item = Parser.parseString("(foo \"bar\" 1234 37.5)", "test");
        Assertions.assertInstanceOf(SexprList.class, item, "List parse failed");
        SexprList list = (SexprList) item;
        Assertions.assertEquals(4, list.value.size(), "Empty list should produce empty list");
        Assertions.assertInstanceOf(SexprSymbol.class, list.value.get(0), "First item should be a symbol");
        Assertions.assertEquals("foo", ((SexprSymbol)list.value.get(0)).value, "First item should be foobar");
        Assertions.assertInstanceOf(SexprString.class, list.value.get(1), "Second item should be a string");
        Assertions.assertEquals("bar", ((SexprString)list.value.get(1)).value, "Second item should be foobar");
        Assertions.assertInstanceOf(SexprInt.class, list.value.get(2), "Third item should be an int");
        Assertions.assertEquals(1234, ((SexprInt)list.value.get(2)).value, "Third item should be 1234");
        Assertions.assertInstanceOf(SexprFloat.class, list.value.get(3), "Fourth item should be a float");
        Assertions.assertEquals(37.5, ((SexprFloat)list.value.get(3)).value, "Fourth item should be 37.5");
    }

    @Test
    public void testNestedList() throws IOException {
        SexprItem item = Parser.parseString("((foo \"bar\" 1234 37.5))", "test");
        Assertions.assertInstanceOf(SexprList.class, item, "List parse failed");
        SexprList list = (SexprList) item;
        Assertions.assertEquals(1, list.value.size(), "Top list should have length 1");
        SexprItem nestedItem = list.value.get(0);
        Assertions.assertInstanceOf(SexprList.class, nestedItem, "Should contain nested list");
        SexprList nestedList = (SexprList) nestedItem;
        Assertions.assertEquals(4, nestedList.value.size(), "Empty nestedList should produce empty nestedList");
        Assertions.assertInstanceOf(SexprSymbol.class, nestedList.value.get(0), "First item should be a symbol");
        Assertions.assertEquals("foo", ((SexprSymbol)nestedList.value.get(0)).value, "First item should be foobar");
        Assertions.assertInstanceOf(SexprString.class, nestedList.value.get(1), "Second item should be a string");
        Assertions.assertEquals("bar", ((SexprString)nestedList.value.get(1)).value, "Second item should be foobar");
        Assertions.assertInstanceOf(SexprInt.class, nestedList.value.get(2), "Third item should be an int");
        Assertions.assertEquals(1234, ((SexprInt)nestedList.value.get(2)).value, "Third item should be 1234");
        Assertions.assertInstanceOf(SexprFloat.class, nestedList.value.get(3), "Fourth item should be a float");
        Assertions.assertEquals(37.5, ((SexprFloat)nestedList.value.get(3)).value, "Fourth item should be 37.5");
    }
}