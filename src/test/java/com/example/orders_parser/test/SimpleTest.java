package com.example.orders_parser.test;

import com.example.orders_parser.service.parser.Parser;
import com.example.orders_parser.service.GreetingService;
import com.example.orders_parser.test.mocks.ListPrinter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

import static com.example.orders_parser.test.TestUtils.getPathInTests;
import static com.example.orders_parser.test.TestUtils.getResourceContents;
import static org.junit.jupiter.api.Assertions.assertLinesMatch;

/**
 * most base use case tests
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestSpringConfiguration.class)
class SimpleTest {

    @Autowired
    private GreetingService greetingService;
    @Autowired
    private ListPrinter out;
    @Autowired
    private ApplicationContext appContext;

    @AfterEach
    void clenup(){
        out.lines.clear();
    }

    private Parser createParser(String... files){
        List<String> realFiles = new ArrayList<>();
        for (String fileName: files){
            realFiles.add(getPathInTests(fileName));
        }
        return appContext.getBean(Parser.class, realFiles, Executors.newFixedThreadPool(8));
    }

    @Test
    void simpleTest() throws IOException, ExecutionException, InterruptedException {
        Parser parser = createParser("simpleTest1.csv", "simpleTest2.csv", "simpleTest1.json");
        parser.parse();
        List<String> expected = getResourceContents("simpleTest1.result.json");
        assertLinesMatch(expected, out.lines);
    }

    // @FIXME: test for file not found
    // @FIXME: test for empty file
}
