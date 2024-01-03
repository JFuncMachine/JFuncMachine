package org.jfuncmachine.compiler.utils.mapper;

import org.jfuncmachine.compiler.utils.mapper.mappermodel.ObjWithDefaultTypes;
import org.jfuncmachine.compiler.utils.mapper.mappermodel.ObjWithEnums;
import org.jfuncmachine.compiler.utils.mapper.mappermodel.ObjWithSimpleTypes;
import org.jfuncmachine.compiler.utils.mapper.mappermodel.ObjWithStringObjs;
import org.jfuncmachine.compiler.utils.mapper.mappermodel.ObjWithStrings;
import org.jfuncmachine.compiler.utils.mapper.mappermodel.ObjWithStringsAndFilename;
import org.jfuncmachine.compiler.utils.mapper.mappermodel.ObjWithVarargDoubles;
import org.jfuncmachine.compiler.utils.mapper.mappermodel.ObjWithVarargInts;
import org.jfuncmachine.compiler.utils.mapper.mappermodel.ObjWithVarargStrings;
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

public class TestMapper {
    static ModelMapper mapper;

    @BeforeAll
    public static void setUp() throws MappingException {
        mapper = new ModelMapper("org.jfuncmachine.compiler.utils.mapper.mappermodel");
    }

    @Test
    public void testStringParams() throws IOException, MappingException {
        SexprItem item = Parser.parseString("""
                (objWithStrings "foo" ("bar" "baz"))
                """, "test");

        Object obj = SexprToModel.sexprToModel(item, mapper, null);
        Assertions.assertInstanceOf(ObjWithStrings.class, obj);
        ObjWithStrings objStr = (ObjWithStrings) obj;
        Assertions.assertEquals("foo", objStr.foobar());
        Assertions.assertEquals(2, objStr.stuff().length);
        Assertions.assertEquals("bar", objStr.stuff()[0]);
        Assertions.assertEquals("baz", objStr.stuff()[1]);

    }

    @Test
    public void testVarargStringParams() throws IOException, MappingException {
        SexprItem item = Parser.parseString("""
                (objWithVarargStrings "foo" "bar" "baz")
                """, "test");

        Object obj = SexprToModel.sexprToModel(item, mapper, null);
        Assertions.assertInstanceOf(ObjWithVarargStrings.class, obj);
        ObjWithVarargStrings objStr = (ObjWithVarargStrings) obj;
        Assertions.assertEquals("foo", objStr.foobar());
        Assertions.assertEquals(2, objStr.stuff().length);
        Assertions.assertEquals("bar", objStr.stuff()[0]);
        Assertions.assertEquals("baz", objStr.stuff()[1]);
    }

    @Test
    public void testVarargIntParams() throws IOException, MappingException {
        SexprItem item = Parser.parseString("""
                (objWithVarargInts "foo" 7 42)
                """, "test");

        Object obj = SexprToModel.sexprToModel(item, mapper, null);
        Assertions.assertInstanceOf(ObjWithVarargInts.class, obj);
        ObjWithVarargInts intObj = (ObjWithVarargInts) obj;
        Assertions.assertEquals("foo", intObj.foobar());
        Assertions.assertEquals(2, intObj.stuff().length);
        Assertions.assertEquals(7, intObj.stuff()[0]);
        Assertions.assertEquals(42, intObj.stuff()[1]);
    }

    @Test
    public void testVarargDoubleParams() throws IOException, MappingException {
        SexprItem item = Parser.parseString("""
                (objWithVarargDoubles "foo" 3.14 42.42)
                """, "test");

        Object obj = SexprToModel.sexprToModel(item, mapper, null);
        Assertions.assertInstanceOf(ObjWithVarargDoubles.class, obj);
        ObjWithVarargDoubles doubleObj = (ObjWithVarargDoubles) obj;
        Assertions.assertEquals("foo", doubleObj.foobar());
        Assertions.assertEquals(2, doubleObj.stuff().length);
        Assertions.assertEquals(3.14, doubleObj.stuff()[0]);
        Assertions.assertEquals(42.42, doubleObj.stuff()[1]);
    }

    @Test
    public void testSymbolsForStringParams() throws IOException, MappingException {
        SexprItem item = Parser.parseString("""
                (objWithStrings foo (bar baz))
                """, "test");

        Object obj = SexprToModel.sexprToModel(item, mapper, null);
        Assertions.assertInstanceOf(ObjWithStrings.class, obj);
        ObjWithStrings objStr = (ObjWithStrings) obj;
        Assertions.assertEquals("foo", objStr.foobar());
        Assertions.assertEquals(2, objStr.stuff().length);
        Assertions.assertEquals("bar", objStr.stuff()[0]);
        Assertions.assertEquals("baz", objStr.stuff()[1]);
    }

    @Test
    public void testStringParamsFilename() throws IOException, MappingException {
        SexprItem item = Parser.parseString("""
                (objWithStringsAndFilename "foo" ("bar" "baz"))
                """, "test");

        Object obj = SexprToModel.sexprToModel(item, mapper, null);
        Assertions.assertInstanceOf(ObjWithStringsAndFilename.class, obj);
        ObjWithStringsAndFilename objStr = (ObjWithStringsAndFilename) obj;
        Assertions.assertEquals("foo", objStr.foobar());
        Assertions.assertEquals(2, objStr.stuff().length);
        Assertions.assertEquals("bar", objStr.stuff()[0]);
        Assertions.assertEquals("baz", objStr.stuff()[1]);
    }

    @Test
    public void testStringObjParams() throws IOException, MappingException {
        SexprItem item = Parser.parseString("""
                (objWithStringObjs "foo" ("bar" "baz"))
                """, "test");

        Object obj = SexprToModel.sexprToModel(item, mapper, null);
        Assertions.assertInstanceOf(ObjWithStringObjs.class, obj);
        ObjWithStringObjs objStr = (ObjWithStringObjs) obj;
        Assertions.assertEquals("foo", objStr.foobar().value());
        Assertions.assertEquals(2, objStr.stuff().length);
        Assertions.assertEquals("bar", objStr.stuff()[0].value());
        Assertions.assertEquals("baz", objStr.stuff()[1].value());
    }

    @Test
    public void testObjWithSimpleTypes() throws IOException, MappingException {
        SexprItem item = Parser.parseString("""
                (objWithSimpleTypes "foo" ("bar" "baz") 5 (1 2 3 4 5) 3.14 (2.78 4.2))
                """, "test");

        Object obj = SexprToModel.sexprToModel(item, mapper, null);
        Assertions.assertInstanceOf(ObjWithSimpleTypes.class, obj);
        ObjWithSimpleTypes simpleObj = (ObjWithSimpleTypes) obj;
        Assertions.assertEquals("foo", simpleObj.foobar());
        Assertions.assertEquals(2, simpleObj.stuff().length);
        Assertions.assertEquals("bar", simpleObj.stuff()[0]);
        Assertions.assertEquals("baz", simpleObj.stuff()[1]);
        Assertions.assertEquals(5, simpleObj.intVal());
        Assertions.assertEquals(5, simpleObj.intVals().length);
        Assertions.assertEquals(1, simpleObj.intVals()[0]);
        Assertions.assertEquals(2, simpleObj.intVals()[1]);
        Assertions.assertEquals(3, simpleObj.intVals()[2]);
        Assertions.assertEquals(4, simpleObj.intVals()[3]);
        Assertions.assertEquals(5, simpleObj.intVals()[4]);
        Assertions.assertEquals(3.14, simpleObj.doubleVal());
        Assertions.assertEquals(2, simpleObj.doubleVals().length);
        Assertions.assertEquals(2.78, simpleObj.doubleVals()[0]);
        Assertions.assertEquals(4.2, simpleObj.doubleVals()[1]);
    }

    @Test
    public void testObjWithDefaultTypes() throws IOException, MappingException {
        SexprItem item = Parser.parseString("""
                (objWithDefaultTypes "foo" ("bar" "baz") 5 (1 2 3 4 5) 3.14 (2.78 4.2))
                """, "test");

        Object obj = SexprToModel.sexprToModel(item, mapper, null);
        Assertions.assertInstanceOf(ObjWithDefaultTypes.class, obj);
        ObjWithDefaultTypes simpleObj = (ObjWithDefaultTypes) obj;
        Assertions.assertEquals("foo", simpleObj.foobar().value());
        Assertions.assertEquals(2, simpleObj.stuff().length);
        Assertions.assertEquals("bar", simpleObj.stuff()[0].value());
        Assertions.assertEquals("baz", simpleObj.stuff()[1].value());
        Assertions.assertEquals(5, simpleObj.intVal().value());
        Assertions.assertEquals(5, simpleObj.intVals().length);
        Assertions.assertEquals(1, simpleObj.intVals()[0].value());
        Assertions.assertEquals(2, simpleObj.intVals()[1].value());
        Assertions.assertEquals(3, simpleObj.intVals()[2].value());
        Assertions.assertEquals(4, simpleObj.intVals()[3].value());
        Assertions.assertEquals(5, simpleObj.intVals()[4].value());
        Assertions.assertEquals(3.14, simpleObj.doubleVal().value());
        Assertions.assertEquals(2, simpleObj.doubleVals().length);
        Assertions.assertEquals(2.78, simpleObj.doubleVals()[0].value());
        Assertions.assertEquals(4.2, simpleObj.doubleVals()[1].value());
    }

    @Test
    public void testObjsWithEnums() throws IOException, MappingException {
        SexprItem item = Parser.parseString("""
                (moe why I oughta)
                (larry ow ow ow)
                (curly nyuk nyuk nyuk)
                """, true);

        SexprList itemList = (SexprList) item;

        Object[] objs = SexprToModel.sexprsToModel(itemList, mapper, null);
        Assertions.assertEquals(3, objs.length);

        Assertions.assertInstanceOf(ObjWithEnums.class, objs[0]);
        ObjWithEnums enumObj = (ObjWithEnums) objs[0];
        Assertions.assertEquals(enumObj.foobar(), ObjWithEnums.EnumObj.Moe);
        Assertions.assertEquals(3, enumObj.stuff().length);
        Assertions.assertEquals("why", enumObj.stuff()[0]);
        Assertions.assertEquals("I", enumObj.stuff()[1]);
        Assertions.assertEquals("oughta", enumObj.stuff()[2]);

        Assertions.assertInstanceOf(ObjWithEnums.class, objs[1]);
        enumObj = (ObjWithEnums) objs[1];
        Assertions.assertEquals(enumObj.foobar(), ObjWithEnums.EnumObj.Larry);
        Assertions.assertEquals(3, enumObj.stuff().length);
        Assertions.assertEquals("ow", enumObj.stuff()[0]);
        Assertions.assertEquals("ow", enumObj.stuff()[1]);
        Assertions.assertEquals("ow", enumObj.stuff()[2]);

        Assertions.assertInstanceOf(ObjWithEnums.class, objs[2]);
        enumObj = (ObjWithEnums) objs[2];
        Assertions.assertEquals(enumObj.foobar(), ObjWithEnums.EnumObj.Curly);
        Assertions.assertEquals(3, enumObj.stuff().length);
        Assertions.assertEquals("nyuk", enumObj.stuff()[0]);
        Assertions.assertEquals("nyuk", enumObj.stuff()[1]);
        Assertions.assertEquals("nyuk", enumObj.stuff()[2]);
    }
}
