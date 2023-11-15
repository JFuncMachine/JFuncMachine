package com.wutka.jfuncmachine.compiler.classgen;

public class ClassGeneratorOptions {
    public final int javaVersion;
    public final boolean localTailCallsToLoops;
    public final boolean fullTailCalls;
    public final boolean autobox;
    public final boolean shortCircuitBooleans;

    public ClassGeneratorOptions() {
        this.javaVersion = 21;
        this.localTailCallsToLoops = true;
        this.fullTailCalls = false;
        this.autobox = true;
        this.shortCircuitBooleans = true;
    }

    public ClassGeneratorOptions(int javaVersion, boolean localTailCallsToLoops,
                                 boolean fullTailCalls, boolean autobox,
                                 boolean shortCircuitBooleans) {
        this.javaVersion = javaVersion;
        this.localTailCallsToLoops = localTailCallsToLoops;
        this.fullTailCalls = fullTailCalls;
        this.autobox = autobox;
        this.shortCircuitBooleans = shortCircuitBooleans;
    }
}
