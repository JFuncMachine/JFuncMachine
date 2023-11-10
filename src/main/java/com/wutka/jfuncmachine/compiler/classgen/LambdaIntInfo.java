package com.wutka.jfuncmachine.compiler.classgen;

import com.wutka.jfuncmachine.compiler.model.types.FunctionType;
import com.wutka.jfuncmachine.compiler.model.types.ObjectType;
import com.wutka.jfuncmachine.compiler.model.types.Type;

public class LambdaIntInfo {
    public final String packageName;
    public final String name;
    public final FunctionType type;

    public LambdaIntInfo(String packageName, String name, FunctionType type) {
        this.packageName = packageName;
        this.name = name;
        this.type = type;
    }

    public Type getObjectType() {
        return new ObjectType(packageName+"."+name);
    }
}
