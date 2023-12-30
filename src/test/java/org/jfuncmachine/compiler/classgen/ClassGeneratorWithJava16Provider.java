package org.jfuncmachine.compiler.classgen;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class ClassGeneratorWithJava16Provider implements ArgumentsProvider {
    @Override public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        ClassGeneratorOptions noTailCallOptimization =
                new ClassGeneratorOptionsBuilder()
                        .withLocalTailCallsToLoops(false)
                        .withFullTailCalls(false)
                        .build();

        ClassGeneratorOptions localTailCallOptimization =
                new ClassGeneratorOptionsBuilder()
                        .withLocalTailCallsToLoops(true)
                        .withFullTailCalls(false)
                        .build();

        ClassGeneratorOptions fullTailCallOptimization =
                new ClassGeneratorOptionsBuilder()
                        .withLocalTailCallsToLoops(false)
                        .withFullTailCalls(true)
                        .build();

        ClassGeneratorOptions fullAndLocalTailCallOptimization =
                new ClassGeneratorOptionsBuilder()
                        .withLocalTailCallsToLoops(true)
                        .withFullTailCalls(true)
                        .build();
        ClassGeneratorOptions noTailCallOptimizationJava16 =
                new ClassGeneratorOptionsBuilder()
                        .withLocalTailCallsToLoops(false)
                        .withFullTailCalls(false)
                        .withJavaVersion(16)
                        .build();

        ClassGeneratorOptions localTailCallOptimizationJava16 =
                new ClassGeneratorOptionsBuilder()
                        .withLocalTailCallsToLoops(true)
                        .withFullTailCalls(false)
                        .withJavaVersion(16)
                        .build();

        ClassGeneratorOptions fullTailCallOptimizationJava16 =
                new ClassGeneratorOptionsBuilder()
                        .withLocalTailCallsToLoops(false)
                        .withFullTailCalls(true)
                        .withJavaVersion(16)
                        .build();

        ClassGeneratorOptions fullAndLocalTailCallOptimizationJava16 =
                new ClassGeneratorOptionsBuilder()
                        .withLocalTailCallsToLoops(true)
                        .withFullTailCalls(true)
                        .withJavaVersion(16)
                        .build();


        return Stream.of(
            Arguments.of("noTailCallOptimization", new ClassGenerator(noTailCallOptimization)),
            Arguments.of("localTailCallOptimization", new ClassGenerator(localTailCallOptimization)),
            Arguments.of("fullAndLocalTailCallOptimization", new ClassGenerator(fullAndLocalTailCallOptimization)),
            Arguments.of("fullTailCallOptimization", new ClassGenerator(fullTailCallOptimization)),
            Arguments.of("noTailCallOptimizationJava16", new ClassGenerator(noTailCallOptimizationJava16)),
            Arguments.of("localTailCallOptimizationJava16", new ClassGenerator(localTailCallOptimizationJava16)),
            Arguments.of("fullAndLocalTailCallOptimizationJava16", new ClassGenerator(fullAndLocalTailCallOptimizationJava16)),
            Arguments.of("fullTailCallOptimizationJava16", new ClassGenerator(fullTailCallOptimizationJava16)));

//                Arguments.of("fullTailCallOptimization", new ClassGenerator(fullTailCallOptimization)));
    }
}
