package com.wutka.jfuncmachine.compiler.classgen;

public class ClassGeneratorOptionsBuilder {
    public int javaVersion = 21;
    public boolean localTailCallsToLoops = true;
    public boolean fullTailCalls = false;
    public boolean autobox = true;

    public ClassGeneratorOptionsBuilder() {

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

    public ClassGeneratorOptions build() {
        return new ClassGeneratorOptions(javaVersion, localTailCallsToLoops,
                fullTailCalls, autobox);
    }

}
