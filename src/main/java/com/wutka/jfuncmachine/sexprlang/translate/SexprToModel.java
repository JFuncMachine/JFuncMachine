package com.wutka.jfuncmachine.sexprlang.translate;

import com.wutka.jfuncmachine.compiler.model.Access;
import com.wutka.jfuncmachine.compiler.model.ClassDef;
import com.wutka.jfuncmachine.compiler.model.ClassField;
import com.wutka.jfuncmachine.compiler.model.MethodDef;
import com.wutka.jfuncmachine.compiler.model.expr.*;
import com.wutka.jfuncmachine.compiler.model.expr.bool.And;
import com.wutka.jfuncmachine.compiler.model.expr.bool.BinaryComparison;
import com.wutka.jfuncmachine.compiler.model.expr.bool.InstanceofComparison;
import com.wutka.jfuncmachine.compiler.model.expr.bool.Not;
import com.wutka.jfuncmachine.compiler.model.expr.bool.Or;
import com.wutka.jfuncmachine.compiler.model.expr.bool.UnaryComparison;
import com.wutka.jfuncmachine.compiler.model.expr.bool.tests.Test;
import com.wutka.jfuncmachine.compiler.model.expr.bool.tests.Tests;
import com.wutka.jfuncmachine.compiler.model.expr.boxing.Box;
import com.wutka.jfuncmachine.compiler.model.expr.boxing.Unbox;
import com.wutka.jfuncmachine.compiler.model.expr.constants.*;
import com.wutka.jfuncmachine.compiler.model.expr.conv.*;
import com.wutka.jfuncmachine.compiler.model.expr.javaintop.*;
import com.wutka.jfuncmachine.compiler.model.inline.Inlines;
import com.wutka.jfuncmachine.compiler.model.types.*;
import com.wutka.jfuncmachine.sexprlang.parser.*;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SexprToModel {
    public static Map<String, String> modelSymbolToClassMap = Stream.of(new String[][] {
            { "And", And.class.getName() },
            { "BinaryComparison", BinaryComparison.class.getName() },
            { "InstanceofComparison", InstanceofComparison.class.getName() },
            { "Not", Not.class.getName() },
            { "Or", Or.class.getName() },
            { "UnaryComparison", UnaryComparison.class.getName() },
            { "Box", Box.class.getName() },
            { "Unbox", Unbox.class.getName() },
            { "ByteConstant", ByteConstant.class.getName() },
            { "CharConstant", CharConstant.class.getName() },
            { "DoubleConstant", DoubleConstant.class.getName() },
            { "FloatConstant", FloatConstant.class.getName() },
            { "IntConstant", IntConstant.class.getName() },
            { "LongConstant", LongConstant.class.getName() },
            { "ShortConstant", ShortConstant.class.getName() },
            { "StringConstant", StringConstant.class.getName() },
            { "ToByte", ToByte.class.getName() },
            { "ToChar", ToChar.class.getName() },
            { "ToDouble", ToDouble.class.getName() },
            { "ToFloat", ToFloat.class.getName() },
            { "ToInt", ToInt.class.getName() },
            { "ToLong", ToLong.class.getName() },
            { "ToShort", ToShort.class.getName() },
            { "ToUnit", ToUnit.class.getName() },
            { "CallJavaConstructor", CallJavaConstructor.class.getName() },
            { "CallJavaInterface", CallJavaInterface.class.getName() },
            { "CallJavaMethod", CallJavaMethod.class.getName() },
            { "CallJavaStaticMethod", CallJavaStaticMethod.class.getName() },
            { "GetJavaField", GetJavaField.class.getName() },
            { "GetJavaStaticField", GetJavaStaticField.class.getName() },
            { "SetJavaField", SetJavaField.class.getName() },
            { "SetJavaStaticField", SetJavaStaticField.class.getName() },
            { "ArrayGet", ArrayGet.class.getName() },
            { "ArraySet", ArraySet.class.getName() },
            { "Binding", Binding.class.getName() },
            { "BindingPair", Binding.BindingPair.class.getName() },
            { "BindingRecurse", BindingRecurse.class.getName() },
            { "Block", Block.class.getName() },
            { "CallMethod", CallMethod.class.getName() },
            { "CallStaticMethod", CallStaticMethod.class.getName() },
            { "Catch", Catch.class.getName() },
            { "GetValue", GetValue.class.getName() },
            { "If", If.class.getName() },
            { "InlineCall", InlineCall.class.getName() },
            { "Invoke", Invoke.class.getName() },
            { "Lambda", Lambda.class.getName() },
            { "NewArray", NewArray.class.getName() },
            { "NewArrayWithValues", NewArrayWithValues.class.getName() },
            { "Recurse", Recurse.class.getName() },
            { "SetValue", SetValue.class.getName() },
            { "Switch", Switch.class.getName() },
            { "SwitchCase", SwitchCase.class.getName() },
            { "Throw", Throw.class.getName() },
            { "TryCatchFinally", TryCatchFinally.class.getName() },
            { "ClassDef", ClassDef.class.getName() },
            { "MethodDef", MethodDef.class.getName() },
            { "ClassField", ClassField.class.getName() },
            { "Field", com.wutka.jfuncmachine.compiler.model.types.Field.class.getName() }
    }).collect(Collectors.toMap(data->data[0], data->data[1]));

    public static Set<String> inlineSet = Stream.of(new String[] {
            "IntAdd", "IntAnd", "IntDiv", "IntMul", "IntNeg", "IntOr", "IntRem", "IntShiftLeft",
            "IntArithShiftRight", "IntLogicalShiftRight", "IntSub", "IntXor", "DoubleAdd",
            "DoubleDiv", "DoubleMul", "DoubleNeg", "DoubleRem", "DoubleSub", "FloatAdd", "FloatDiv",
            "FloatMul", "FloatNeg", "FloatRem", "FloatSub", "LongAdd", "LongAnd", "LongDiv",
            "LongMul", "LongNeg", "LongOr", "LongRem", "LongShiftLeft", "LongArithShiftRight",
            "LongLogicalShiftRight", "LongSub", "LongXor"
    }).collect(Collectors.toSet());

    public static Map<String, Binding.Visibility> visibilityMap = Stream.of(new Object[][] {
        { "Visibility.Separate", Binding.Visibility.Separate },
        { "Visibility.Next", Binding.Visibility.Next },
        { "Visibility.Recursive", Binding.Visibility.Recursive }
    }).collect(Collectors.toMap(data-> (String)data[0], data->(Binding.Visibility) data[1]));

    public static Set<String> comparisonSet = Stream.of(new String[] {
        "isNull", "isNotNull", "isTrue", "isFalse",
            "EQ", "NE", "LT", "LE", "GT", "GE",
            "EQ_IgnoreCase", "NE_IgnoreCase", "LT_IgnoreCase", "LE_IgnoreCase", "GT_IgnoreCase", "GE_IgnoreCase",
    }).collect(Collectors.toSet());

    public static Map<String, Test> testTypes = Stream.of(new Object[][] {
            { "isNull", Tests.IsNull },
            { "isNotNull", Tests.IsNotNull },
            { "isTrue", Tests.IsTrue },
            { "isFalse", Tests.IsFalse },
            { "EQ", Tests.EQ },
            { "NE", Tests.NE },
            { "LT", Tests.LT },
            { "LE", Tests.LE },
            { "GT", Tests.GT },
            { "GE", Tests.GE },
            { "EQ_IgnoreCase", Tests.EQ_IgnoreCase },
            { "NE_IgnoreCase", Tests.NE_IgnoreCase },
            { "LT_IgnoreCase", Tests.LT_IgnoreCase },
            { "LE_IgnoreCase", Tests.LE_IgnoreCase },
            { "GT_IgnoreCase", Tests.GT_IgnoreCase },
            { "GE_IgnoreCase", Tests.GE_IgnoreCase }
    }).collect(Collectors.toMap(data -> (String) data[0], data->(Test) data[1]));

    public static Map<String, Type> simpleTypes = Stream.of(new Object[][] {
            { "boolean", SimpleTypes.BOOLEAN },
            { "byte", SimpleTypes.BYTE },
            { "char", SimpleTypes.CHAR },
            { "double", SimpleTypes.DOUBLE },
            { "float", SimpleTypes.FLOAT },
            { "int", SimpleTypes.INT },
            { "long", SimpleTypes.LONG },
            { "short", SimpleTypes.SHORT },
            { "string", SimpleTypes.STRING },
            { "unit", SimpleTypes.UNIT }
    }).collect(Collectors.toMap(data -> (String) data[0], data-> (Type) data[1]));

    public static Set<String> typeSet = Stream.of(new String[] {
            "array", "function", "object"
    }).collect(Collectors.toSet());

    public static ClassDef translateClass(SexprList list) {
        Object translated = translate(list);
        if (!(translated instanceof ClassDef)) {
            throw new RuntimeException(
                    String.format("Resulting object was %s not a Class", translated.getClass().getName()));
        }
        return (ClassDef) translated;
    }

    public static Object translate(SexprItem item) {
        if (item instanceof SexprSymbol sym) {
            if (inlineSet.contains(sym.value)) {
                return translateInline(sym);
            } else if (simpleTypes.containsKey(sym.value)) {
                return simpleTypes.get(sym.value);
            } else if (visibilityMap.containsKey(sym.value)) {
                return visibilityMap.get(sym.value);
            } else if (testTypes.containsKey(sym.value)) {
                return testTypes.get(sym.value);
            } else if (sym.value.equals("true")) {
                return Boolean.TRUE;
            } else if (sym.value.equals("false")) {
                return Boolean.FALSE;
            } else {
                throw new RuntimeException(
                        String.format("Unexpected symbol %s in %s line %d", sym.value, sym.filename, sym.lineNumber));
            }
        } else if (item instanceof SexprList list) {
            return translateList(list);
        } else {
            throw new RuntimeException(
                    String.format("Can't translate item of type %s in %s line %d",
                            item.getClass().getSimpleName(), item.filename, item.lineNumber));
        }
    }

    public static Object translateList(SexprList list) {
        SexprItem item = list.value.get(0);
        if (!(item instanceof SexprSymbol sym)) {
            throw new RuntimeException(
                    String.format("Expected first list item to be a symbol, not %s", item.getClass().getSimpleName()));
        }
        if (modelSymbolToClassMap.containsKey(sym.value)) {
            return translateModelSymbol(sym, list.value);
        } else if (typeSet.contains(sym.value)) {
            return translateComplexType(sym, list.value);
        } else if (visibilityMap.containsKey(sym.value)) {
            return visibilityMap.get(sym.value);
        } else if (testTypes.containsKey(sym.value)) {
            return testTypes.get(sym.value);
        }
        throw new RuntimeException(
                String.format("Unexpected symbol %s in %s at line %s", sym.value, sym.filename, sym.lineNumber));
    }

    public static Object translateModelSymbol(SexprSymbol symbolSexpr, ArrayList<SexprItem> items) {
        String symbol = symbolSexpr.value;
       try {
           Class<?> modelClass = Class.forName(modelSymbolToClassMap.get(symbol));
           SexprItem[] params = new SexprItem[items.size()-1];
           for (int i=0; i < params.length; i++) params[i] = items.get(i+1);

           for (Constructor<?> cons: modelClass.getConstructors()) {
               Class<?>[] parameterTypes = cons.getParameterTypes();

               Object[] parameters = matchParameterList(symbolSexpr, parameterTypes, params);
               if (parameters != null) {
                   return cons.newInstance(parameters);
               }
           }
           throw new RuntimeException(
                   String.format("Unable to find matching parameter list for %s in %s at line %d",
                           symbol, symbolSexpr.filename, symbolSexpr.lineNumber));
       } catch (ClassNotFoundException exc) {
           throw new RuntimeException(exc);
       } catch (InvocationTargetException e) {
           throw new RuntimeException(e);
       } catch (InstantiationException e) {
           throw new RuntimeException(e);
       } catch (IllegalAccessException e) {
           throw new RuntimeException(e);
       }
    }

    public static Object[] matchParameterList(SexprSymbol classSym, Class<?>[] parameterTypes, SexprItem[] params) {
        if ((parameterTypes.length != params.length) && (parameterTypes.length != params.length + 2)) {
            return null;
        }

        boolean addFilenameLineNumber = false;

        if (parameterTypes.length == params.length + 2) {
            if (parameterTypes[parameterTypes.length - 2].getName().equals("java.lang.String") &&
                    parameterTypes[parameterTypes.length - 1].getSimpleName().equals("int")) {
                addFilenameLineNumber = true;
            } else {
                return null;
            }
        }

        Object[] results = new Object[parameterTypes.length];
        for (int i=0; i < params.length; i++) {
            if (params[i] instanceof SexprSymbol sym) {
                if (sym.value.equals("null")) {
                    if (parameterTypes[i].isPrimitive()) {
                        throw new RuntimeException(
                                String.format("Can't pass null for type %s in %s line %d",
                                        parameterTypes[i].getSimpleName(),
                                        sym.filename, sym.lineNumber));
                    }
                    results[i] = null;
                    continue;
                }
            }
            Object result = matchParameter(parameterTypes[i], params[i]);
            if (result == null) {
                return null;
            }
            results[i] = result;
        }

        if (addFilenameLineNumber) {
            results[params.length] = classSym.filename;
            results[params.length+1] = classSym.lineNumber;
        }
        return results;
    }

    public static Object matchParameter(Class<?> parameterType, SexprItem param) {
        if (parameterType.isPrimitive()) {
            switch (parameterType.getSimpleName()) {
                case "boolean" -> {
                    if (param instanceof SexprInt intVal) {
                        return intVal.value != 0;
                    } else if (param instanceof SexprSymbol sym) {
                        if (sym.value.equals("true")) {
                            return Boolean.TRUE;
                        } else if (sym.value.equals("false")) {
                            return Boolean.FALSE;
                        } else {
                            return null;
                        }
                    } else {
                        return null;
                    }
                }
                case "byte" -> {
                    if (param instanceof SexprInt intVal) {
                        return (byte) intVal.value;
                    } else {
                        return null;
                    }
                }
                case "char" -> {
                    if (param instanceof SexprInt intVal) {
                        return (char) intVal.value;
                    } else {
                        return null;
                    }
                }
                case "double" -> {
                    if (param instanceof SexprFloat floatVal) {
                        return floatVal.value;
                    } else if (param instanceof SexprInt intVal) {
                        return (double) intVal.value;
                    } else {
                        return null;
                    }
                }
                case "float" -> {
                    if (param instanceof SexprFloat floatVal) {
                        return (float) floatVal.value;
                    } else if (param instanceof SexprInt intVal) {
                        return (float) intVal.value;
                    } else {
                        return null;
                    }
                }
                case "int" -> {
                    if (param instanceof SexprInt intVal) {
                        return intVal.value;
                    } else if (isAccessList(param)) {
                        return translateAccessList(param);
                    } else {
                        return null;
                    }
                }
                case "long" -> {
                    if (param instanceof SexprInt intVal) {
                        return (long) intVal.value;
                    } else {
                        return null;
                    }
                }
                case "short" -> {
                    if (param instanceof SexprInt intVal) {
                        return (short) intVal.value;
                    } else {
                        return null;
                    }
                }
                default -> { return null; }
            }
        }
        if (parameterType.getName().startsWith("com.wutka.jfuncmachine")) {
            if (param instanceof SexprList list) {
                return translate(list);
            } else if (param instanceof SexprSymbol sym) {
                if (inlineSet.contains(sym.value)) {
                    return translateInline(sym);
                } else if (simpleTypes.containsKey(sym.value)) {
                    return simpleTypes.get(sym.value);
                }
                ArrayList<SexprItem> newList = new ArrayList<>();
                newList.add(sym);
                Object result = translate(new SexprList(
                        newList, sym.filename, sym.lineNumber));
                if (parameterType.isAssignableFrom(result.getClass())) {
                    return result;
                } else {
                    throw new RuntimeException(
                            String.format("Can't use %s as a %s parameter in %s line %d",
                                    result.getClass().getSimpleName(),
                                    parameterType.getSimpleName(),
                                    sym.filename, sym.lineNumber));
                }

            }
        }

        if (parameterType.isArray()) {
            Class<?> arrayType = parameterType.getComponentType();
            if (param instanceof SexprList paramList) {
                ArrayList<Object> parametersList = new ArrayList<>();
                for (SexprItem item: paramList.value) {
                    if (item instanceof SexprSymbol sym) {
                        if (sym.value.equals("null")) {
                            parametersList.add(null);
                        }
                    }
                    Object matchedParam = matchParameter(arrayType, item);
                    if (matchedParam == null) return null;
                    parametersList.add(matchedParam);
                }

                Object arrayResult = Array.newInstance(arrayType, parametersList.size());
                for (int i=0; i < parametersList.size(); i++) {
                    Array.set(arrayResult, i, parametersList.get(i));
                }
                return arrayResult;
            } else {
                return null;
            }
        }
        switch (parameterType.getName()) {
            case "java.lang.Boolean" -> {
                if (param instanceof SexprInt intVal) {
                    return intVal.value != 0;
                } else {
                    return null;
                }
            }
            case "java.lang.Byte" -> {
                if (param instanceof SexprInt intVal) {
                    return (byte) intVal.value;
                } else {
                    return null;
                }
            }
            case "java.lang.Char" -> {
                if (param instanceof SexprInt intVal) {
                    return (char) intVal.value;
                } else {
                    return null;
                }
            }
            case "java.land.Double" -> {
                if (param instanceof SexprFloat floatVal) {
                    return floatVal.value;
                } else if (param instanceof SexprInt intVal) {
                    return (double) intVal.value;
                } else {
                    return null;
                }
            }
            case "java.lang.Float" -> {
                if (param instanceof SexprFloat floatVal) {
                    return (float) floatVal.value;
                } else if (param instanceof SexprInt intVal) {
                    return (float) intVal.value;
                } else {
                    return null;
                }
            }
            case "java.lang.Integer" -> {
                if (param instanceof SexprInt intVal) {
                    return intVal.value;
                } else {
                    return null;
                }
            }
            case "java.lang.Long" -> {
                if (param instanceof SexprInt intVal) {
                    return (long) intVal.value;
                } else {
                    return null;
                }
            }
            case "java.lang.Short" -> {
                if (param instanceof SexprInt intVal) {
                    return (short) intVal.value;
                } else {
                    return null;
                }
            }
            case "java.lang.String" -> {
                if (param instanceof SexprString stringVal) {
                    return stringVal.value;
                } else if (param instanceof SexprSymbol symbolVal) {
                    return symbolVal.value;
                } else {
                    throw new RuntimeException(
                            String.format("Can't convert %s value to string in %s line %d",
                                    param.getClass().getSimpleName(), param.filename, param.lineNumber));
                }
            }
            default -> { return null; }
        }
    }

    public static Object translateInline(SexprSymbol sym) {
        for (java.lang.reflect.Field field: Inlines.class.getFields()) {
            if (field.getName().equals(sym.value)) {
                try {
                    return field.get(null);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        throw new RuntimeException(
                String.format("Internal error, %s should be an inline function but there is no matching field in Inlines",
                        sym.value, sym.filename, sym.lineNumber));

    }

    public static Type translateType(SexprItem item) {
        if (item instanceof SexprSymbol sym) {
            if (simpleTypes.containsKey(sym.value)) {
                return simpleTypes.get(sym.value);
            } else {
                throw new RuntimeException(
                        String.format("Invalid type name %s in %s line %d",
                                sym.value, sym.filename, sym.lineNumber));
            }
        } else if (item instanceof SexprList list) {
            SexprItem firstItem = list.value.get(0);
            if (firstItem instanceof SexprSymbol sym) {
                if (typeSet.contains(sym.value)) {
                    return translateComplexType(sym, list.value);
                } else {
                    throw new RuntimeException(
                            String.format("Invalid type name %s in %s line %d",
                                    sym.value, sym.filename, sym.lineNumber));
                }
            } else {
                throw new RuntimeException(
                        String.format("Expected type name symbol, got %s in %s line %d",
                                firstItem.getClass().getSimpleName(),
                                firstItem.filename, firstItem.lineNumber));
            }
        } else {
            throw new RuntimeException(
                    String.format("Expecting type symbol or list, but got %s in %s line %d",
                            item.getClass().getSimpleName(),
                            item.filename, item.lineNumber));
        }
    }

    public static Type translateComplexType(SexprSymbol sym, ArrayList<SexprItem> items) {
        switch (sym.value) {
            case "array" -> {
                if (items.size() != 2) {
                    throw new RuntimeException(
                            String.format("array type parameter should have exactly one parameter in %s line %d",
                                    sym.filename, sym.lineNumber));
                }
                SexprItem typeParam = items.get(1);
                return new ArrayType(translateType(typeParam));
            }
            case "object" -> {
                if (items.size() == 1) {
                    return new ObjectType();
                } else if (items.size() != 2) {
                    throw new RuntimeException(
                            String.format("object type parameter should have exactly one parameter in %s line %d",
                                    sym.filename, sym.lineNumber));
                }
                SexprItem typeParam = items.get(1);
                if (typeParam instanceof SexprSymbol typeSym) {
                    return new ObjectType(typeSym.value);
                } else if (typeParam instanceof SexprString typeString) {
                    return new ObjectType(typeString.value);
                } else {
                    throw new RuntimeException(
                            String.format("Invalid object type specifier of type %s in %s line %d",
                                    typeParam.getClass().getSimpleName(), sym.filename, sym.lineNumber));
                }
            }
            case "function" -> {
                if (items.size() != 3) {
                    throw new RuntimeException(
                            String.format("function type parameter should have exactly three parameters in %s line %d",
                                    sym.filename, sym.lineNumber));

                }

                Type[] paramTypes;

                SexprItem params = items.get(1);
                if (params instanceof SexprList paramList) {
                    paramTypes = new Type[paramList.value.size()];
                    for (int i=0; i < paramTypes.length; i++) {
                        paramTypes[i] = translateType(paramList.value.get(i));
                    }
                } else {
                    throw new RuntimeException(
                            String.format("Invalid type parameter list for function, should be list, got %s in %s line %d",
                            params.getClass().getSimpleName(), params.filename, params.lineNumber));
                }
                Type returnType = translateType(items.get(2));
                return new FunctionType(paramTypes, returnType);
            }
            default -> throw new RuntimeException(
                        String.format("Invalid type name %s in %s line %d", sym.value, sym.filename, sym.lineNumber));
        }
    }

    public static boolean isAccessList(SexprItem item) {
        if (!(item instanceof SexprList list)) {
            return false;
        }

        if (list.value.isEmpty()) return false;

        if (!(list.value.get(0) instanceof SexprSymbol sym)) return false;

        return sym.value.equals("Access");
    }

    public static int translateAccessList(SexprItem item) {
        SexprList list = (SexprList) item;

        int retval = 0;

        for (int i=1; i < list.value.size(); i++) {
            SexprItem listItem = list.value.get(i);
            if (!(listItem instanceof SexprSymbol sym)) {
                throw new RuntimeException(
                        String.format("Invalid access specifier type %s in %s line %d",
                                listItem.getClass().getSimpleName(), listItem.filename, listItem.lineNumber));
            }

            try {
                java.lang.reflect.Field field = Access.class.getField(sym.value);
                retval += field.getInt(null);
            } catch (Exception exc) {
                throw new RuntimeException(
                    String.format("Invalid access specifier %s in %s line %d",
                        sym.value, sym.filename, sym.lineNumber));
            }
        }
        return retval;
    }
}