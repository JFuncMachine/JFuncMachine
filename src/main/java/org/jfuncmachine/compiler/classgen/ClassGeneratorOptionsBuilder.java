package org.jfuncmachine.compiler.classgen;

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
    /** The Java version to generate class files for */
    public int javaVersion;
    /** Convert local recursive calls (method calling itself) to loops */
    public boolean localTailCallsToLoops;

    /** Generate non-Java-callable methods with tail-call optimization. This requires changing the return
     * type of each method to return an object that contains a reference to the next function to call, or
     * a value representing the return value of the function. This option should not be used unless it is
     * really needed as it adds overhead to each function call.
     */
    public boolean fullTailCalls;

    /** Indicates whether the code generator should automatically convert between native types and boxed
     * types (Object-versions of each type, e.g. java.lang.Byte for byte, java.lang.Integer for int).
     */
    public boolean autobox;

    /**
     * Indicates that any lambdas generated for one class can be shared by all classes in the same package.
     * Since the names of the lambdas are dependent on their types, two classes using the sames types for
     * a lambda should be able to share a single interface definition. */
    public boolean sharedLambdaInterfaces;

    /**
     * The name used for the single method in a lambda interface.
     */
    public String lambdaMethodName;

    /** Indicate whether to generate Java-compatible generic object interfaces for each lambda. This facilitates
     * lambdas that are compatible with the java.util.function package.
     */
    public boolean genericLambdas;

    /** Create a ClassGeneratorOptionsBuilder instance with the default settings */
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
     * @return The current ClassGeneratorOptionsBuilder (for chaining calls)
     */
    public ClassGeneratorOptionsBuilder withJavaVersion(int version) {
        this.javaVersion = version;
        return this;
    }

    /**
     * Change the option to replace local recursive method calls with loops
     * @param option True if local recursive calls should be turned into loops
     * @return The current ClassGeneratorOptionsBuilder (for chaining calls)
     */
    public ClassGeneratorOptionsBuilder withLocalTailCallsToLoops(boolean option) {
        this.localTailCallsToLoops = option;
        return this;
    }

    /**
     * Change the option to generate full tail calls for all methods (except ones marked as Java-callable)
     * @param option True if each non-Java-callable method should be generated to do tail-call optimization
     * @return The current ClassGeneratorOptionsBuilder (for chaining calls)
     */
    public ClassGeneratorOptionsBuilder withFullTailCalls(boolean option) {
        this.fullTailCalls = option;
        return this;
    }

    /**
     * Change the option to generate autoboxing to automatically convert between native and box types.
     * @param option True if autoboxing should be on
     * @return The current ClassGeneratorOptionsBuilder (for chaining calls)
     */
    public ClassGeneratorOptionsBuilder withAutobox(boolean option) {
        this.autobox = option;
        return this;
    }

    /**
     * Change the option indicating whether all classes in the package should share generated lambda interfaces.
     * @param option True if all the classes in the the package share the same lambda interfaces
     * @return The current ClassGeneratorOptionsBuilder (for chaining calls)
     */
    public ClassGeneratorOptionsBuilder withSharedLambdas(boolean option) {
        this.sharedLambdaInterfaces = option;
        return this;
    }

    /**
     * Change the name of the single method in a generated lambda interface
     * @param lambdaMethodName The name to use for lambda interface methods
     * @return The current ClassGeneratorOptionsBuilder (for chaining calls)
     */
    public ClassGeneratorOptionsBuilder withLambdaMethodName(String lambdaMethodName) {
        this.lambdaMethodName = lambdaMethodName;
        return this;
    }

    /**
     * Change the option to generate generic lambda interfaces that use boxed types
     * @param option True if lambda interfaces should always box native types
     * @return The current ClassGeneratorOptionsBuilder (for chaining calls)
     */
    public ClassGeneratorOptionsBuilder withGenericLambdas(boolean option) {
        this.genericLambdas = option;
        return this;
    }

    /**
     * Generate a new ClassGeneratorOptions object with the desired changes.
     * @return a new ClassGeneratorOptions containing all the options set by this builder
     */
    public ClassGeneratorOptions build() {
        return new ClassGeneratorOptions(javaVersion, localTailCallsToLoops,
                fullTailCalls, autobox, sharedLambdaInterfaces,
                lambdaMethodName, genericLambdas);
    }
}
