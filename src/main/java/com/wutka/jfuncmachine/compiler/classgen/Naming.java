package com.wutka.jfuncmachine.compiler.classgen;

import com.wutka.jfuncmachine.compiler.model.Class;


public class Naming {
    public static String className(Class clazz) {
        return className(clazz.packageName, clazz.name);
    }

    public static String className(String packageName, String name) {
        return packageName.replace('.', '/')+"/"+name;
    }

    public static String classSignature(Class clazz) {
        return "L"+className(clazz)+";";
    }
}
