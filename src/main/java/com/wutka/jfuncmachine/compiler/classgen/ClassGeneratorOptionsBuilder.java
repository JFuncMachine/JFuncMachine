package com.wutka.jfuncmachine.compiler.classgen;

public class ClassGeneratorOptionsBuilder {
    public int javaVersion;
    public boolean localTailCallsToLoops;
    public boolean fullTailCalls;
    public boolean autobox;
    public boolean shortCircuitBooleans;

    public ClassGeneratorOptionsBuilder() {
        ClassGeneratorOptions defaults = new ClassGeneratorOptions();
        this.javaVersion = defaults.javaVersion;
        this.localTailCallsToLoops = defaults.localTailCallsToLoops;
        this.fullTailCalls = defaults.fullTailCalls;
        this.autobox = defaults.autobox;
        this.shortCircuitBooleans = defaults.shortCircuitBooleans;

    }

    public ClassGeneratorOptionsBuilder withJavaVersion(int version) {
        this.javaVersion = version;
        return this;
    }

    public ClassGeneratorOptionsBuilder withLocalTailCallsToLoops(boolean option) {
        this.localTailCallsToLoops = option;
        return this;
    }

    public ClassGeneratorOptionsBuilder withFullTailCalls(boolean option) {
        this.fullTailCalls = option;
        return this;
    }

    public ClassGeneratorOptionsBuilder withAutobox(boolean option) {
        this.autobox = option;
        return this;
    }

    public ClassGeneratorOptionsBuilder withShortCircuitBooleans(boolean option) {
        this.shortCircuitBooleans = option;
        return this;
    }

    public ClassGeneratorOptions build() {
        return new ClassGeneratorOptions(javaVersion, localTailCallsToLoops,
                fullTailCalls, autobox, shortCircuitBooleans);
    }

}
