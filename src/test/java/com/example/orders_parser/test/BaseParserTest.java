package com.example.orders_parser.test;

import com.example.orders_parser.service.GreetingService;
import com.example.orders_parser.service.parser.CompletableFutureParser;
import com.example.orders_parser.service.parser.OrderParseException;
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
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.example.orders_parser.test.TestUtils.getPathInTests;
import static com.example.orders_parser.test.TestUtils.getResourceContents;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertLinesMatch;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * most base use case
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestSpringConfiguration.class)
class BaseParserTest {

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

    private ExecutorService getThreadPool(){
        return Executors.newFixedThreadPool(8);
    }

    private CompletableFutureParser createParser(String... files){
        List<String> realFiles = new ArrayList<>();
        for (String fileName: files){
            realFiles.add(getPathInTests(fileName));
        }
        return appContext.getBean(CompletableFutureParser.class, realFiles, getThreadPool());
    }

    private void baseTest(String result, String... inputs) throws IOException{
        CompletableFutureParser parser = createParser(inputs);
        parser.parse();
        List<String> expected = getResourceContents(result);
        assertLinesMatch(expected, out.lines);
    }

    @Test
    void simpleTest() throws IOException {
        baseTest("simpleTest1.result.json", "simpleTest1.csv", "simpleTest2.csv", "simpleTest1.json");
    }

    @Test
    void complexTest() throws IOException {
        baseTest("complexTest1.result.json","complexTest1.csv", "complexTest2.csv",
                "complexTest1.json");
    }

    @Test
    void notExistFile() {
        CompletableFutureParser parser = new CompletableFutureParser(Arrays.asList("noSuchFile.csv",
                "complexTest2.csv"), getThreadPool());
        try{
            parser.parse();
        } catch (OrderParseException e){
            assertTrue(e.getRootCause().getMessage().contains("noSuchFile.csv"));
        }
    }

    @Test
    void emptyFile() throws IOException {
        baseTest("emptyTest.result.json", "emptyCsv.csv", "emptyJson.json", "simpleTest1.json");
    }

    @Test
    void emptyListOfFiles() {
        CompletableFutureParser parser = createParser();
        parser.parse();
        assertEquals("No files provided.", out.lines.get(0));
    }

    @Test
    void unsupportedExtension() {
        CompletableFutureParser parser = new CompletableFutureParser(Arrays.asList("wtf.exe"), getThreadPool());
        try{
            parser.parse();
        } catch (OrderParseException e){
            assertTrue(e.getRootCause().getMessage().contains("unknown file type"));
        }
    }

    @Test
    void noExtension() {
        CompletableFutureParser parser = new CompletableFutureParser(Arrays.asList("justfile"), getThreadPool());
        try{
            parser.parse();
        } catch (OrderParseException e){
            assertTrue(e.getRootCause().getMessage().contains("unknown file type"));
        }
    }

    @Test
    void corruptedJson() throws IOException {
        baseTest("corruptedJson.result.json", "corruptedJson.json");
    }

    @Test
    void corruptedCsv() throws IOException {
        baseTest("corruptedCsv.result.json", "corruptedCsv.csv");
    }
}
