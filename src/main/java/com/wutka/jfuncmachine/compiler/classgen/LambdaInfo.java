package com.wutka.jfuncmachine.compiler.classgen;

import com.wutka.jfuncmachine.compiler.model.types.FunctionType;

/** Holds information about a lambda method, including the class name, and type of its method.
 */
public class LambdaInfo {
    /** The name of the lambda method */
    public final String name;
    /** The type of the method */
    public final FunctionType type;

    /** Create a new LambdaInfo
     * @param name The name of the lambda method
     * @param type The type of the method
     */
    public LambdaInfo(String name, FunctionType type) {
        this.name = name;
        this.type = type;
    }
}
