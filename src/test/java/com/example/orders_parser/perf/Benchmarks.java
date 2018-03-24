package com.example.orders_parser.perf;

import com.example.orders_parser.service.parser.BaseParser;
import com.example.orders_parser.service.parser.CompletableFutureParser;
import com.example.orders_parser.service.parser.ReactorParser;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;

import static com.example.orders_parser.test.TestUtils.getPathInTests;

/**
 * benchmarks itself with appropriate spring configuration
 */
public class Benchmarks {

    static List<String> getInFiles(){
        return Arrays.asList(getPathInTests("bigCsv.csv"), getPathInTests("bigJson.json"));
    }

    static ExecutorService getThreadPool(){
        return ForkJoinPool.commonPool();
    }

    static class BaseState{
        BaseParser service;

        <T extends BaseParser> void createService(Class<T>  clazz) {
            String testPackage = "com.example.orders_parser.perf";
            String basePackage = "com.example.orders_parser.service";
            try (ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(testPackage,
                    basePackage)) {
                service = context.getBean(clazz, getInFiles(), getThreadPool());
            }
        }

    }

    @State(Scope.Thread)
    public static class BenchmarkStateCompletable extends BaseState {

        @Setup(Level.Trial)
        public synchronized void  initialize() {
            createService(CompletableFutureParser.class);
        }

    }

    @State(Scope.Thread)
    public static class BenchmarkStateReactor extends BaseState {

        @Setup(Level.Trial)
        public synchronized void  initialize() {
            createService(ReactorParser.class);
        }

    }

    @Benchmark
    public void benchmarkCompletable(BenchmarkStateCompletable state, Blackhole bh) {
        state.service.parse();
    }

    @Benchmark
    public void benchmarkReactor(BenchmarkStateReactor state, Blackhole bh) {
        state.service.parse();
    }

}
