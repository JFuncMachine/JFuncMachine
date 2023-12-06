package org.jfuncmachine.jfuncmachine.sexprlang.translate;

import org.jfuncmachine.jfuncmachine.sexprlang.parser.*;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class SexprToModel {
    public static Object[] sexprsToModel(SexprList sexprs, SexprMapper mapper, Class targetClass)
        throws MappingException {
        List<Object> objects = new ArrayList<>();
        for (SexprItem item: sexprs.value) {
            objects.add(sexprToModel(item, mapper, targetClass));
        }
        return objects.toArray();
    }

    public static Object sexprToModel(SexprItem item, SexprMapper mapper, Class targetClass)
        throws MappingException {
        switch(item) {
            case SexprDouble d -> { return mapper.mapDouble(d); }
            case SexprInt i -> { return mapper.mapInt(i); }
            case SexprString s -> { return mapper.mapString(s); }
            case SexprSymbol s -> {
                if (targetClass == null) {
                    Object result = mapper.mapSymbol(s);
                    if (!(result instanceof Class)) return result;
                    targetClass = (Class) result;
                }

                if (targetClass.equals(String.class)) {
                    return s.value;
                } else if (targetClass.isEnum()) {
                    return mapper.mapSymbol(s);
                } else {
                    try {
                        Object[] params = new Object[]{s.value};
                        for (Constructor cons : targetClass.getConstructors()) {
                            if (matchesWithLocation(cons, params)) {
                                Object[] paramsExt = new Object[params.length + 2];
                                System.arraycopy(params, 0, paramsExt, 0, params.length);
                                paramsExt[params.length] = item.filename;
                                paramsExt[params.length + 1] = item.lineNumber;
                                return cons.newInstance(paramsExt);
                            }
                        }
                        for (Constructor cons : targetClass.getConstructors()) {
                            if (matches(cons, params)) {
                                return cons.newInstance(params);
                            }
                        }
                    } catch (InvocationTargetException e) {
                        throw new MappingException(e, item.filename, item.lineNumber);
                    } catch (InstantiationException e) {
                        throw new MappingException(e, item.filename, item.lineNumber);
                    } catch (IllegalAccessException e) {
                        throw new MappingException(e, item.filename, item.lineNumber);
                    }
                }

                Object result = mapper.mapSymbol(s);
                if (result.getClass().isEnum()) {
                    Class containingClass = mapper.mapSymbolToClass(s);
                    if (containingClass == null) {
                        return result;
                    }
                    try {
                        Object[] params = new Object[]{result};
                        for (Constructor cons : containingClass.getConstructors()) {
                            if (matchesWithLocation(cons, params)) {
                                Object[] paramsExt = new Object[params.length + 2];
                                System.arraycopy(params, 0, paramsExt, 0, params.length);
                                paramsExt[params.length] = item.filename;
                                paramsExt[params.length + 1] = item.lineNumber;
                                return cons.newInstance(paramsExt);
                            }
                        }
                        for (Constructor cons : containingClass.getConstructors()) {
                            if (matches(cons, params)) {
                                return cons.newInstance(params);
                            }
                        }
                    } catch (InvocationTargetException e) {
                        throw new MappingException(e, item.filename, item.lineNumber);
                    } catch (InstantiationException e) {
                        throw new MappingException(e, item.filename, item.lineNumber);
                    } catch (IllegalAccessException e) {
                        throw new MappingException(e, item.filename, item.lineNumber);
                    }
                    throw new MappingException(
                            String.format("Unable to find a constructor in class %s for symbol %s",
                                    targetClass.getName(), s.value));
                } else if (!(result instanceof Class)) {
                    return result;
                }
                targetClass = (Class) result;
                try {
                    Object[] params = new Object[]{s.value};
                    for (Constructor cons : targetClass.getConstructors()) {
                        if (matchesWithLocation(cons, params)) {
                            Object[] paramsExt = new Object[params.length + 2];
                            System.arraycopy(params, 0, paramsExt, 0, params.length);
                            paramsExt[params.length] = item.filename;
                            paramsExt[params.length + 1] = item.lineNumber;
                            return cons.newInstance(paramsExt);
                        }
                    }
                    for (Constructor cons : targetClass.getConstructors()) {
                        if (matches(cons, params)) {
                            return cons.newInstance(params);
                        }
                    }
                } catch (InvocationTargetException e) {
                    throw new MappingException(e, item.filename, item.lineNumber);
                } catch (InstantiationException e) {
                    throw new MappingException(e, item.filename, item.lineNumber);
                } catch (IllegalAccessException e) {
                    throw new MappingException(e, item.filename, item.lineNumber);
                }
                throw new MappingException(
                        String.format("Unable to find a constructor in class %s for symbol %s",
                                targetClass.getName(), s.value));
            }
            case SexprList l ->  {
                Object result = mapper.mapList(l, targetClass);
                if (result == null) {
                    if (targetClass != null && targetClass.isArray() &&
                            targetClass.getComponentType().getAnnotation(ModelItem.class) != null) {
                        Object[] values = new Object[l.value.size()];
                        for (int i=0; i < l.value.size(); i++) {
                            values[i] = sexprToModel(l.value.get(i), mapper, targetClass.getComponentType());
                        }
                        return values;
                    } else if (targetClass != null &&
                            targetClass.getAnnotation(ModelItem.class) != null) {
                        return sexprListToModel(targetClass, l, mapper);
                    } else {
                        return sexprListToItemList(l, mapper);
                    }
                } else if (result instanceof Class) {
                    ModelItem modelItem = ((Class<?>) result).getAnnotation(ModelItem.class);
                    if (modelItem.includeStartSymbol()) {
                        return sexprListToModel((Class) result, l, mapper);
                    } else {
                        return sexprListToModel((Class) result,
                                new SexprList(new ArrayList<>(l.value.subList(1, l.value.size())),
                                        l.filename, l.lineNumber), mapper);
                    }
                } else {
                    return result;
                }
            }
        }
    }

    public static Object sexprListToItemList(SexprList list, SexprMapper mapper) throws MappingException {
        Object[] items = new Object[list.value.size()];
        for (int i=0; i < items.length; i++) {
            items[i] = sexprToModel(list.value.get(i), mapper, null);
        }
        return items;
    }

    public static Object sexprListToModel(Class clazz, SexprList list, SexprMapper mapper)
        throws MappingException {
        ArrayList<SexprItem> items = list.value;
        Object[] params = new Object[items.size()];

        int varargStart = -1;
        ModelItem modelItem = (ModelItem) clazz.getAnnotation(ModelItem.class);
        if (modelItem != null) {
            varargStart = modelItem.varargStart();
        }

        if (varargStart >= 0) {
            params = new Object[varargStart+1];
            ArrayList<SexprItem> itemsArray = new ArrayList<>();
            String filename = items.get(varargStart).filename;
            int lineNumber = items.get(varargStart).lineNumber;
            for (int i=varargStart; i < items.size(); i++) {
                itemsArray.add(items.get(i));
            }

            ArrayList<SexprItem> newItems = new ArrayList<>(items);

            for (int i=items.size()-1; i >= varargStart; i--) {
                newItems.remove(i);
            }
            newItems.add(new SexprList(itemsArray, filename, lineNumber));
            items = newItems;
        }

        try {
            for (Constructor cons: clazz.getConstructors()) {
                if (matchesWithLocation(cons, params, items, mapper)) {
                    Class[] paramTypes = cons.getParameterTypes();
                    convertArrayTypes(paramTypes, params);
                    Object[] paramsExt = new Object[params.length + 2];
                    System.arraycopy(params, 0, paramsExt, 0, params.length);
                    paramsExt[params.length] = list.filename;
                    paramsExt[params.length + 1] = list.lineNumber;
                    return cons.newInstance(paramsExt);
                }
            }
            for (Constructor cons: clazz.getConstructors()) {
                if (SexprToModel.matches(cons, params, items, mapper)) {
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
                            ((Object[]) params[i]).length);
                    System.arraycopy(params[i], 0, newArray, 0, ((Object[])params[i]).length);
                    params[i] = newArray;
                }
            }
        }
    }

    public static boolean matches(Constructor cons, Object[] params, List<SexprItem> items,
                                  SexprMapper mapper) throws MappingException {
        Class[] paramTypes = cons.getParameterTypes();
        if (paramTypes.length != params.length) return false;
        for (int i=0; i < paramTypes.length; i++) {
            params[i] = sexprToModel(items.get(i), mapper, paramTypes[i]);
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

    public static boolean matchesWithLocation(Constructor cons, Object[] params,
                                              List<SexprItem> items, SexprMapper mapper) throws MappingException {
        Class[] paramTypes = cons.getParameterTypes();
        if (paramTypes.length != params.length + 2) return false;
        if (!paramTypes[paramTypes.length-2].getName().equals("java.lang.String")) return false;
        if (!(paramTypes[paramTypes.length-1].isPrimitive() &
                paramTypes[paramTypes.length-1].getName().equals("int")) &&
            !paramTypes[paramTypes.length-1].getName().equals("java.lang.Integer")) return false;
        for (int i=0; i < params.length; i++) {
            params[i] = sexprToModel(items.get(i), mapper, paramTypes[i]);
            if (paramTypes[i].isArray()) {
                if (!params[i].getClass().isArray()) return false;
                if (((Object[])params[i]).length > 0 &&
                        !paramTypes[i].getComponentType().isAssignableFrom(
                                ((Object[])params[i])[0].getClass())) {
                    return false;
                }
            } else if (!paramTypes[i].isAssignableFrom(params[i].getClass())) {
                return false;
            }
        }
        return true;
    }
    public static boolean matches(Constructor cons, Object[] params) throws MappingException {
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

    public static boolean matchesWithLocation(Constructor cons, Object[] params) throws MappingException {
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
            } else if (!paramTypes[i].equals(params[i].getClass()) &&
                    !paramTypes[i].isAssignableFrom(params[i].getClass()) &&
                !(paramTypes[i].getName().equals("int") && (params[i] instanceof Integer)) &&
                !(paramTypes[i].getName().equals("double") && (params[i] instanceof Double))) {
                return false;
            }
        }
        return true;
    }
}
