package com.example.orders_parser.perf;

import org.junit.jupiter.api.Test;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

/**
 * benchmarks runner. Use to simplify run JMH instead of console run.
 */
class BenchmarkTest {

    @Test
    void runBenchmark() throws RunnerException {
        // lookup https://stackoverflow.com/a/30486197 for options details
        Options opt = new OptionsBuilder()
                .include(Benchmarks.class.getName() + ".*")
                .warmupTime(TimeValue.seconds(1))
                .warmupIterations(2)
                .measurementTime(TimeValue.seconds(1))
                .measurementIterations(2)
                .forks(2)
                .build();

        new Runner(opt).run();
    }

}
