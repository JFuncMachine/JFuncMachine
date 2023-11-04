package com.wutka.jfuncmachine.compiler.classgen;

public class ClassGeneratorOptions {
    public int javaVersion = 21;
    public boolean localTailCallsToLoops = true;
    public boolean fullTailCalls = false;

    public ClassGeneratorOptions() {

    }

    public ClassGeneratorOptions withJavaVersion(int version) {
        this.javaVersion = version;
        return this;
    }

    public ClassGeneratorOptions withLocalTailCallsToLoops(boolean option) {
        this.localTailCallsToLoops = option;
        return this;
    }

    public ClassGeneratorOptions withFullTailCalls(boolean option) {
        this.fullTailCalls = option;
        return this;
    }

}
