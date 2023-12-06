package org.jfuncmachine.jfuncmachine.compiler.classgen;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class ClassGeneratorProvider implements ArgumentsProvider {
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


        return Stream.of(
                Arguments.of("noTailCallOptimization", new ClassGenerator(noTailCallOptimization)),
                Arguments.of("localTailCallOptimization", new ClassGenerator(localTailCallOptimization)),
                Arguments.of("fullTailCallOptimization", new ClassGenerator(fullTailCallOptimization)),
                Arguments.of("fullAndLocalTailCallOptimization", new ClassGenerator(fullAndLocalTailCallOptimization)));
    }
}
