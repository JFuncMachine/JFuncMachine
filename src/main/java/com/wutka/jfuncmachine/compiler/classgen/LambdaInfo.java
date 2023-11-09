package com.wutka.jfuncmachine.compiler.classgen;

import com.wutka.jfuncmachine.compiler.model.types.FunctionType;

public class LambdaInfo {
    public final String packageName;
    public final String name;
    public final FunctionType type;

    public LambdaInfo(String packageName, String name, FunctionType type) {
        this.packageName = packageName;
        this.name = name;
        this.type = type;
    }
}
