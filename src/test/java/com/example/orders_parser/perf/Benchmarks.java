package com.example.orders_parser.perf;

import com.example.orders_parser.service.GreetingService;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * benchmarks itself with appropriate spring configuration
 */
public class Benchmarks {
    @State(Scope.Thread)
    public static class BenchmarkState {

        GreetingService service;

        @Setup(Level.Trial)
        public synchronized void  initialize() {
            String testPackage = "com.example.orders_parser.perf";
            String basePackage = "com.example.orders_parser.service";
            try (ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(testPackage,
                    basePackage)) {
                service = context.getBean(GreetingService.class);
            }
        }

    }

    @Benchmark
    public void benchmark1(BenchmarkState state, Blackhole bh) {
        state.service.greet();
    }

}
