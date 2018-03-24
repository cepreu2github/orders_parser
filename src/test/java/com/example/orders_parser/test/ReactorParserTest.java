package com.example.orders_parser.test;

import com.example.orders_parser.service.parser.BaseParser;
import com.example.orders_parser.service.parser.ReactorParser;
import org.junit.jupiter.api.Tag;

import java.util.List;
import java.util.concurrent.ExecutorService;

@Tag("func")
public class ReactorParserTest extends BaseParserTest {

    @Override
    protected BaseParser getParserBean(List<String> inFiles, ExecutorService threadPool) {
        return appContext.getBean(ReactorParser.class, inFiles, getThreadPool());
    }
}
