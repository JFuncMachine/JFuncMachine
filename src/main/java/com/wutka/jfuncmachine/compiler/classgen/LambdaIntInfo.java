package com.wutka.jfuncmachine.compiler.classgen;

import com.wutka.jfuncmachine.compiler.model.types.FunctionType;
import com.wutka.jfuncmachine.compiler.model.types.ObjectType;
import com.wutka.jfuncmachine.compiler.model.types.Type;

/** Holds information about a lambda interface. */
public class LambdaIntInfo {
    /** The package name of the interface */
    public final String packageName;
    /** The name of the interface */
    public final String name;
    /** The type of the interface's one method */
    public final FunctionType type;

    /** Creates a new LambdaIntInfo
     * @param packageName The package name of the interface
     * @param name The name of the interface
     * @param type The type of the interface's one method
     */
    public LambdaIntInfo(String packageName, String name, FunctionType type) {
        this.packageName = packageName;
        this.name = name;
        this.type = type;
    }

    /** Returns an ObjectType for this interface */
    public Type getObjectType() {
        return new ObjectType(packageName+"."+name);
    }
}
