package org.jfuncmachine.compiler.classgen;

/**
 * Contains the options for code generation.
 */
public class ClassGeneratorOptions {
    /** The Java version to generate class files for */
    public final int javaVersion;

    /** Convert local recursive calls (method calling itself) to loops */
    public final boolean localTailCallsToLoops;

    /** Generate non-Java-callable methods with tail-call optimization. This requires changing the return
     * type of each method to return an object that contains a reference to the next function to call, or
     * a value representing the return value of the function. This option should not be used unless it is
     * really needed as it adds overhead to each function call.
     */
    public final boolean fullTailCalls;

    /** Indicates whether the code generator should automatically convert between native types and boxed
     * types (Object-versions of each type, e.g. java.lang.Byte for byte, java.lang.Integer for int).
     */
    public final boolean autobox;

    /**
     * Indicates that any lambdas generated for one class can be shared by all classes in the same package.
     * Since the names of the lambdas are dependent on their types, two classes using the sames types for
     * a lambda should be able to share a single interface definition. */
    public final boolean sharedLambdaInterfaces;

    /**
     * The name used for the single method in a lambda interface.
     */
    public final String lambdaMethodName;

    /** Indicate whether to generate Java-compatible generic object interfaces for each lambda. This facilitates
     * lambdas that are compatible with the java.util.function package.
     */
    public final boolean genericLambdas;

    /** Currently JFuncMachine uses two features that were previews between Java 17 and Java 21. If
     * this option is true, JFuncMachine will use these features if generating for Java versions 17, 18, 19, and 20.
     */
    public final boolean usePreviewFeatures;

    /** The TypeSwitch and EnumSwitch expressions use a feature that was a preview in Java 17, and
     * became fully supported in Java 21. If this flag is true, JFuncMachine will convert these expressions
     * to If expressions if generating for a JVM where the feature is not supported. If usePreviewFeatures
     * is true, then this conversion will only happen for Java 16 or lower. If usePreviewFeatures is
     * false, then this conversion will happen for Java 20 or lower.
     */
    public final boolean convertSwitchesToIf;


    /** Create a ClassGeneratorOptions instance with the default settings */
    public ClassGeneratorOptions() {
        this.javaVersion = 21;
        this.localTailCallsToLoops = true;
        this.fullTailCalls = false;
        this.autobox = true;
        this.sharedLambdaInterfaces = true;
        this.lambdaMethodName = "apply";
        this.genericLambdas = false;
        this.usePreviewFeatures = false;
        this.convertSwitchesToIf = true;
    }

    /** Create a ClassGeneratorOptions instance with specific settings
     *
     * @param javaVersion The Java version to generate class files for
     * @param localTailCallsToLoops Convert local recursive calls (method calling itself) to loops
     * @param fullTailCalls Generate non-Java-callable methods with tail-call optimization
     * @param autobox Automatically box and unbox types as needed
     * @param sharedLambdaInterfaces Any lambdas generated for one class can be shared by all classes in the same package
     * @param lambdaMethodName The name used for the single method in a lambda interface
     * @param genericLambdas generate Java-compatible generic object interfaces for each lambda
     */
    public ClassGeneratorOptions(int javaVersion, boolean localTailCallsToLoops,
                                 boolean fullTailCalls, boolean autobox,
                                 boolean sharedLambdaInterfaces,
                                 String lambdaMethodName,
                                 boolean genericLambdas,
                                 boolean usePreviewFeatures,
                                 boolean convertSwitchesToIf) {
        this.javaVersion = javaVersion;
        this.localTailCallsToLoops = localTailCallsToLoops;
        this.fullTailCalls = fullTailCalls;
        this.autobox = autobox;
        this.sharedLambdaInterfaces = sharedLambdaInterfaces;
        this.lambdaMethodName = lambdaMethodName;
        this.genericLambdas = genericLambdas;
        this.usePreviewFeatures = usePreviewFeatures;
        this.convertSwitchesToIf = convertSwitchesToIf;
    }
}
