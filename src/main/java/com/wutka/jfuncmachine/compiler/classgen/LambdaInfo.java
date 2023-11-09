package com.wutka.jfuncmachine.compiler.classgen;

import com.wutka.jfuncmachine.compiler.model.types.FunctionType;

public class LambdaInfo {
    public final String packageName;
    public final String name;
    public final LambdaInterfaceInfo intInfo;
    public final FunctionType type;

    public LambdaInfo(String packageName, String name, LambdaInterfaceInfo intInfo, FunctionType type) {
        this.packageName = packageName;
        this.name = name;
        this.intInfo = intInfo;
        this.type = type;
    }
}
