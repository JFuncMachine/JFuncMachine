package org.jfuncmachine.compiler.classgen;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class ClassGeneratorNoAutoboxProvider implements ArgumentsProvider {
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
        ClassGeneratorOptions noTailCallOptimizationNoAutobox =
                new ClassGeneratorOptionsBuilder()
                        .withAutobox(false)
                        .withLocalTailCallsToLoops(false)
                        .withFullTailCalls(false)
                        .build();

        ClassGeneratorOptions localTailCallOptimizationNoAutobox =
                new ClassGeneratorOptionsBuilder()
                        .withAutobox(false)
                        .withLocalTailCallsToLoops(true)
                        .withFullTailCalls(false)
                        .build();

        ClassGeneratorOptions fullTailCallOptimizationNoAutobox =
                new ClassGeneratorOptionsBuilder()
                        .withAutobox(false)
                        .withLocalTailCallsToLoops(false)
                        .withFullTailCalls(true)
                        .build();

        ClassGeneratorOptions fullAndLocalTailCallOptimizationNoAutobox =
                new ClassGeneratorOptionsBuilder()
                        .withAutobox(false)
                        .withLocalTailCallsToLoops(true)
                        .withFullTailCalls(true)
                        .build();


        return Stream.of(
            Arguments.of("noTailCallOptimization", new ClassGenerator(noTailCallOptimization)),
            Arguments.of("localTailCallOptimization", new ClassGenerator(localTailCallOptimization)),
            Arguments.of("fullAndLocalTailCallOptimization", new ClassGenerator(fullAndLocalTailCallOptimization)),
            Arguments.of("fullTailCallOptimization", new ClassGenerator(fullTailCallOptimization)),
            Arguments.of("noTailCallOptimization", new ClassGenerator(noTailCallOptimizationNoAutobox)),
            Arguments.of("localTailCallOptimization", new ClassGenerator(localTailCallOptimizationNoAutobox)),
            Arguments.of("fullAndLocalTailCallOptimization", new ClassGenerator(fullAndLocalTailCallOptimizationNoAutobox)),
            Arguments.of("fullTailCallOptimization", new ClassGenerator(fullTailCallOptimizationNoAutobox)));
    }
}
