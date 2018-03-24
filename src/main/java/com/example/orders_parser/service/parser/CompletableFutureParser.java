package com.example.orders_parser.service.parser;

import com.example.orders_parser.domain.Result;
import com.example.orders_parser.service.base.Printer;
import com.example.orders_parser.service.format.ReaderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * multithreaded parser which use CompletableFuture inside to scale across CPU
 */
@Service
@Scope("prototype")
public class CompletableFutureParser {
    @Autowired
    private Printer out;

    private List<String> inFiles;
    private ExecutorService threadPool;

    public CompletableFutureParser(List<String> inFiles, ExecutorService threadPool){
        this.inFiles = inFiles;
        this.threadPool = threadPool;
    }

    private CompletableFuture<Void> printAll(Stream<CompletableFuture<Result>> list) {
        return list.reduce((allPromisedLines, nextPromisedLine) -> allPromisedLines.thenComposeAsync((line) -> {
                    out.println(line.toString());
                    return nextPromisedLine;
                })).get().thenAcceptAsync((lastLine) -> out.println(lastLine.toString()));
    }

    private String fakeProcessor(String input){
        try {
            Thread.sleep(100 + new Random().nextInt(300));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return input;
    }

    public void parse() throws ExecutionException, InterruptedException {
        inFiles.stream().map((fileName) -> CompletableFuture.supplyAsync(() -> {
            try {
                return ReaderFactory.getReader(fileName);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }, threadPool))
                .map((promisedReader) ->
                        promisedReader.thenApplyAsync((reader) ->
                                reader.lines()
                                        .map((line) ->
                                                CompletableFuture.supplyAsync(() ->
                                                        reader.parseLine(line), threadPool)).
                                        collect(Collectors.toList()) // to prevent stream laziness issue (late Futures creation)
                                        .stream()))
                .reduce((allPromisedLists, nextPromisedList) ->
                        allPromisedLists.thenComposeAsync((list) ->
                                printAll(list).thenComposeAsync((v) ->
                                        nextPromisedList)))
                .get()
                .thenComposeAsync(this::printAll).get();
        threadPool.shutdown();
    }

}