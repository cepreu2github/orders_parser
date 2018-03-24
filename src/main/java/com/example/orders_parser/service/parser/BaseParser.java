package com.example.orders_parser.service.parser;

import com.example.orders_parser.service.base.Printer;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

public abstract class BaseParser {
    @Autowired
    Printer out;

    List<String> inFiles;
    ExecutorService threadPool;

    BaseParser(List<String> inFiles, ExecutorService threadPool){
        this.inFiles = inFiles;
        this.threadPool = threadPool;
    }

    public void parse(){
        if (inFiles.isEmpty()){
            out.println("No files provided.");
            return;
        }
        try {
            parseReally();
        } catch (ExecutionException e) {
            if (e.getCause() instanceof OrderParseException){
                throw new OrderParseException(e);
            } else {
                throw new OrderParseUnknownException(e);
            }
        } catch (InterruptedException e) {
            throw new OrderParseUnknownException(e);
        } finally {
            threadPool.shutdown();
        }
    }

    protected abstract void parseReally() throws ExecutionException, InterruptedException;

}
