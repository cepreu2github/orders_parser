package com.example.orders_parser.service.parser;

import com.example.orders_parser.domain.Result;
import com.example.orders_parser.service.format.ReaderFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.concurrent.ExecutorService;

@Service
@Scope("prototype")
public class ReactorParser extends BaseParser{
    public ReactorParser(List<String> inFiles, ExecutorService threadPool){
        super(inFiles, threadPool);
    }

    @Override
    protected void parseReally() {
        Flux.fromIterable(inFiles)
                .map((fileName) -> {
            try {
                return ReaderFactory.getReader(fileName);
            } catch (FileNotFoundException e) {
                throw new OrderParseException(e);
            } catch (IllegalArgumentException e) {
                throw new OrderParseException("unknown file type " + fileName);
            }
        }).flatMapSequential((reader ->
                Flux.fromStream(reader.lines())
                        .map(reader::parseLine)
                        .map(Result::toString)
                        .subscribeOn(Schedulers.fromExecutorService(threadPool))))
                .map(outLine -> {
                    out.println(outLine);
                    return outLine;
                })
                .then()
                .block();
        threadPool.shutdown();
    }

}
