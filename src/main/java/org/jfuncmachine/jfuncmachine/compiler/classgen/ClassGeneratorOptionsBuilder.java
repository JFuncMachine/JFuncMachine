package org.jfuncmachine.jfuncmachine.compiler.classgen;

/**
 * A builder used to create a ClassGeneratorOptions class.
 *
 * This builder allows you to easily override just one or two defaults in the ClassGeneratorOptions
 * without having to provide all the constructor parameters.
 *
 * Example:
 * ClassGeneratorOptions options =
 *      new ClassGeneratorOptionsBuilder().withAutobox(false).javaVersion(20).build();
 *
 */
public class ClassGeneratorOptionsBuilder {
    public int javaVersion;
    public boolean localTailCallsToLoops;
    public boolean fullTailCalls;
    public boolean autobox;
    public boolean sharedLambdaInterfaces;
    public String lambdaMethodName;
    public boolean genericLambdas;

    public ClassGeneratorOptionsBuilder() {
        ClassGeneratorOptions defaults = new ClassGeneratorOptions();
        this.javaVersion = defaults.javaVersion;
        this.localTailCallsToLoops = defaults.localTailCallsToLoops;
        this.fullTailCalls = defaults.fullTailCalls;
        this.autobox = defaults.autobox;
        this.sharedLambdaInterfaces = defaults.sharedLambdaInterfaces;
        this.lambdaMethodName = defaults.lambdaMethodName;
        this.genericLambdas = defaults.genericLambdas;

    }

    /**
     * Change the Java version in the options
     * @param version The version of Java to generate class files for
     */
    public ClassGeneratorOptionsBuilder withJavaVersion(int version) {
        this.javaVersion = version;
        return this;
    }

    /**
     * Change the option to replace local recursive method calls with loops
     * @param option True if local recursive calls should be turned into loops
     */
    public ClassGeneratorOptionsBuilder withLocalTailCallsToLoops(boolean option) {
        this.localTailCallsToLoops = option;
        return this;
    }

    /**
     * Change the option to generate full tail calls for all methods (except ones marked as Java-callable)
     * @param option True if each non-Java-callable method should be generated to do tail-call optimization
     */
    public ClassGeneratorOptionsBuilder withFullTailCalls(boolean option) {
        this.fullTailCalls = option;
        return this;
    }

    /**
     * Change the option to generate autoboxing to automatically convert between native and box types.
     * @param option True if autoboxing should be on
     */
    public ClassGeneratorOptionsBuilder withAutobox(boolean option) {
        this.autobox = option;
        return this;
    }

    /**
     * Change the option indicating whether all classes in the package should share generated lambda interfaces.
     * @param option True if all the classes in the the package share the same lambda interfaces
     */
    public ClassGeneratorOptionsBuilder withSharedLambdas(boolean option) {
        this.sharedLambdaInterfaces = option;
        return this;
    }

    /**
     * Change the name of the single method in a generated lambda interface
     * @param lambdaMethodName The name to use for lambda interface methods
     */
    public ClassGeneratorOptionsBuilder withLambdaMethodName(String lambdaMethodName) {
        this.lambdaMethodName = lambdaMethodName;
        return this;
    }

    /**
     * Change the option to generate generic lambda interfaces that use boxed types
     * @param option True if lambda interfaces should always box native types
     */
    public ClassGeneratorOptionsBuilder withGenericLambdas(boolean option) {
        this.genericLambdas = option;
        return this;
    }

    /**
     * Generate a new ClassGeneratorOptions object with the desired changes.
     */
    public ClassGeneratorOptions build() {
        return new ClassGeneratorOptions(javaVersion, localTailCallsToLoops,
                fullTailCalls, autobox, sharedLambdaInterfaces,
                lambdaMethodName, genericLambdas);
    }

}
