package org.jfuncmachine.jfuncmachine.sexprlang.translate;

import io.github.classgraph.*;
import org.jfuncmachine.jfuncmachine.sexprlang.parser.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModelMapper implements SexprMapper {
    public final String packageName;
    protected Map<String, Class> symbolToClassMap;
    protected Map<String, Object> symbolToEnumMap;
    protected Class stringValueClass;
    protected Class intValueClass;
    protected Class doubleValueClass;
    protected Class symbolExprClass;

    public ModelMapper(String packageName) throws MappingException {
        this.packageName = packageName;
        buildModelMaps();
    }

    public void buildModelMaps() throws MappingException {
        symbolToClassMap = new HashMap<>();
        symbolToEnumMap = new HashMap<>();

        try (ScanResult scanResult =
                     new ClassGraph()
                             .enableAllInfo()         // Scan classes, methods, fields, annotations
                             .acceptPackages(packageName)     // Scan com.xyz and subpackages (omit to scan all packages)
                             .scan()) {               // Start the scan
            for (ClassInfo modelItemClassInfo : scanResult.getClassesWithAnnotation(ModelItem.class.getName())) {
                try {
                    Class modelItemClass = Class.forName(modelItemClassInfo.getName());
                    Annotation[] annotations = modelItemClass.getAnnotations();
                    for (Annotation annotation: annotations) {
                        if (annotation instanceof ModelItem modelItem) {
                            if (!modelItem.symbol().isEmpty()) {
                                symbolToClassMap.put(modelItem.symbol(), modelItemClass);
                            } else if (modelItemClass.isEnum()) {
                                addEnumMapping(modelItemClass, modelItem.isExprStart());
                            } else if (modelItem.isStringConstant()) {
                                stringValueClass = modelItemClass;
                            } else if (modelItem.isIntConstant()) {
                                intValueClass = modelItemClass;
                            } else if (modelItem.isDoubleConstant()) {
                                doubleValueClass = modelItemClass;
                            } else if (modelItem.isSymbolExpr()) {
                                symbolExprClass = modelItemClass;
                            }
                        }
                    }
                } catch (ClassNotFoundException exc) {
                    throw new MappingException("Unable to load class "+modelItemClassInfo.getName(), exc);
                }
            }
        }
    }

    protected void addEnumMapping(Class clazz, boolean isExprStart) throws MappingException {
        for (Field field: clazz.getFields()) {
            if (field.getName().equals("symbol") && field.getType().equals("java.lang.String")) {
                for (Object enumVal: clazz.getEnumConstants()) {
                    try {
                        String sym = (String) field.get(enumVal);
                        symbolToEnumMap.put(sym, enumVal);
                        if (isExprStart) {
                            symbolToClassMap.put(sym, clazz.getEnclosingClass());
                        }
                    } catch (IllegalAccessException e) {
                        throw new MappingException("Unable to fetch symbol value from enum in "+clazz.getName(), e);
                    }
                }
            }
        }
        for (Object enumVal: clazz.getEnumConstants()) {
            symbolToEnumMap.put(((Enum)enumVal).name(), enumVal);
        }
    }

    @Override
    public Object mapSymbol(SexprSymbol symbol) throws MappingException {
        Class symClass = symbolToClassMap.get(symbol.value);
        if (symClass != null) {
            return symClass;
        } else {
            Object enumVal = symbolToEnumMap.get(symbol.value);
            if (enumVal != null) {
                return enumVal;
            }

            if (symbolExprClass == null) {
                throw new MappingException(
                        String.format("No mapping for symbol %s and no symbol value class was found"));
            }
            return symbolExprClass;
        }
    }

    @Override
    public Object mapDouble(SexprDouble d) throws MappingException {
        if (doubleValueClass == null) {
            throw new MappingException(
                    String.format("No mapping for double value class was found"));
        }
        try {
            for (Constructor cons : doubleValueClass.getConstructors()) {
                Object[] args = new Object[] { d.value };
                if (SexprToModel.matchesWithLocation(cons, args)) {
                    return cons.newInstance(new Object[]{d.value, d.filename, d.lineNumber});
                } else if (SexprToModel.matches(cons, args)) {
                    return cons.newInstance(args);
                }
            }
            throw new MappingException(
                    String.format("No viable constructor in double value class was found"));
        } catch (InvocationTargetException e) {
            throw new MappingException(e, d.filename, d.lineNumber);
        } catch (InstantiationException e) {
            throw new MappingException(e, d.filename, d.lineNumber);
        } catch (IllegalAccessException e) {
            throw new MappingException(e, d.filename, d.lineNumber);
        }
    }

    @Override
    public Object mapInt(SexprInt i) throws MappingException {
        if (intValueClass == null) {
            throw new MappingException(
                    String.format("No mapping for int value class was found"));
        }
        try {
            for (Constructor cons : intValueClass.getConstructors()) {
                Object[] args = new Object[] { i.value };
                if (SexprToModel.matchesWithLocation(cons, args)) {
                    return cons.newInstance(new Object[]{i.value, i.filename, i.lineNumber});
                } else if (SexprToModel.matches(cons, args)) {
                    return cons.newInstance(args);
                }
            }
            throw new MappingException(
                    String.format("No viable constructor in int value class was found"));
        } catch (InvocationTargetException e) {
            throw new MappingException(e, i.filename, i.lineNumber);
        } catch (InstantiationException e) {
            throw new MappingException(e, i.filename, i.lineNumber);
        } catch (IllegalAccessException e) {
            throw new MappingException(e, i.filename, i.lineNumber);
        }
    }

    @Override
    public Object mapString(SexprString s) throws MappingException {
        if (stringValueClass == null) {
            throw new MappingException(
                    String.format("No mapping for string value class was found"));
        }
        try {
            for (Constructor cons : stringValueClass.getConstructors()) {
                Object[] args = new Object[] { s.value };
                if (SexprToModel.matchesWithLocation(cons, args)) {
                    return cons.newInstance(new Object[]{s.value, s.filename, s.lineNumber});
                } else if (SexprToModel.matches(cons, args)) {
                    return cons.newInstance(args);
                }
            }
            throw new MappingException(
                    String.format("No viable constructor in double value class was found"));
        } catch (InvocationTargetException e) {
            throw new MappingException(e, s.filename, s.lineNumber);
        } catch (InstantiationException e) {
            throw new MappingException(e, s.filename, s.lineNumber);
        } catch (IllegalAccessException e) {
            throw new MappingException(e, s.filename, s.lineNumber);
        }
    }

    @Override
    public Object mapList(SexprList l) throws MappingException {
        ArrayList<SexprItem> list = l.value;
        if (list.size() < 1) {
            throw new MappingException("Can't map empty list", l.filename, l.lineNumber);
        }

        SexprItem first = list.get(0);
        if (!(first instanceof SexprSymbol symbol)) {
            return null;
        }

        return symbolToClassMap.get(symbol.value);
    }
}
