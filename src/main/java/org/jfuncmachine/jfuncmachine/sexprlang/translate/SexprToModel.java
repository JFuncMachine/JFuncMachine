package org.jfuncmachine.jfuncmachine.sexprlang.translate;

import org.jfuncmachine.jfuncmachine.sexprlang.parser.*;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class SexprToModel {
    public static Object[] sexprsToModel(SexprList sexprs, SexprMapper mapper)
        throws MappingException {
        List<Object> objects = new ArrayList<>();
        for (SexprItem item: sexprs.value) {
            objects.add(sexprToModel(item, mapper));
        }
        return objects.toArray();
    }

    public static Object sexprToModel(SexprItem item, SexprMapper mapper)
        throws MappingException {
        switch(item) {
            case SexprDouble d -> { return mapper.mapDouble(d); }
            case SexprInt i -> { return mapper.mapInt(i); }
            case SexprString s -> { return mapper.mapString(s); }
            case SexprSymbol s -> { return mapper.mapSymbol(s); }
            case SexprList l ->  {
                Object result = mapper.mapList(l);
                if (result == null) {
                    return sexprListToItemList(l, mapper);
                } else if (result instanceof Class) {
                    return sexprListToModel((Class) result, l, mapper);
                } else {
                    return result;
                }
            }
        }
    }

    public static Object sexprListToItemList(SexprList list, SexprMapper mapper) throws MappingException {
        Object[] items = new Object[list.value.size()];
        for (int i=0; i < items.length; i++) {
            items[i] = sexprToModel(list.value.get(i), mapper);
        }
        return items;
    }

    public static Object sexprListToModel(Class clazz, SexprList list, SexprMapper mapper)
        throws MappingException {
        ArrayList<SexprItem> items = list.value;
        Object[] params = new Object[items.size()-1];
        for (int i=1; i < items.size(); i++) {
            params[i-1] = sexprToModel(items.get(i), mapper);
        }

        try {
            for (Constructor cons: clazz.getConstructors()) {
                if (matchesWithLocation(cons, params)) {
                    Class[] paramTypes = cons.getParameterTypes();
                    convertArrayTypes(paramTypes, params);
                    Object[] paramsExt = new Object[params.length + 2];
                    paramsExt[params.length] = list.filename;
                    paramsExt[params.length + 1] = list.lineNumber;
                    return cons.newInstance(paramsExt);
                }
            }
            for (Constructor cons: clazz.getConstructors()) {
                if (SexprToModel.matches(cons, params)) {
                    Class[] paramTypes = cons.getParameterTypes();
                    convertArrayTypes(paramTypes, params);
                    return cons.newInstance(params);
                }
            }
        } catch (InvocationTargetException e) {
            throw new MappingException(e, list.filename, list.lineNumber);
        } catch (InstantiationException e) {
            throw new MappingException(e, list.filename, list.lineNumber);
        } catch (IllegalAccessException e) {
            throw new MappingException(e, list.filename, list.lineNumber);
        }
        throw new MappingException(
                String.format("Unable to find a constructor in class %s that matches %s",
                        clazz.getName(), list));
    }

    public static void convertArrayTypes(Class[] paramTypes, Object[] params) {
        for (int i=0; i < params.length; i++) {
            if (paramTypes[i].isArray()) {
                if (!paramTypes[i].getComponentType().equals(Object.class)) {
                    Object newArray = Array.newInstance(paramTypes[i].getComponentType(),
                            ((Object[]) params).length);
                    System.arraycopy(params[i], 0, newArray, 0, ((Object[])params).length);
                    params[i] = newArray;
                }
            }
        }
    }

    public static boolean matches(Constructor cons, Object[] params) {
        Class[] paramTypes = cons.getParameterTypes();
        if (paramTypes.length != params.length) return false;
        for (int i=0; i < paramTypes.length; i++) {
            if (paramTypes[i].isArray()) {
                if (!params[i].getClass().isArray()) return false;
                if (!paramTypes[i].getComponentType().isAssignableFrom(params[i].getClass().getComponentType())) {
                    return false;
                }
            }  else if (!paramTypes[i].isAssignableFrom(params[i].getClass())) {
                return false;
            }
        }
        return true;
    }

    public static boolean matchesWithLocation(Constructor cons, Object[] params) {
        Class[] paramTypes = cons.getParameterTypes();
        if (paramTypes.length != params.length + 2) return false;
        if (!paramTypes[paramTypes.length-2].getName().equals("java.lang.String")) return false;
        if (!(paramTypes[paramTypes.length-1].isPrimitive() &
                paramTypes[paramTypes.length-1].getName().equals("int")) &&
            !paramTypes[paramTypes.length-1].getName().equals("java.lang.Integer")) return false;
        for (int i=0; i < params.length; i++) {
            if (paramTypes[i].isArray()) {
                if (!params[i].getClass().isArray()) return false;
                if (!paramTypes[i].getComponentType().isAssignableFrom(params[i].getClass().getComponentType())) {
                    return false;
                }
            } else if (!paramTypes[i].isAssignableFrom(params[i].getClass())) {
                return false;
            }
        }
        return true;
    }
}
