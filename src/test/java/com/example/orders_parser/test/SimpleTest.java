package com.example.orders_parser.test;

import com.example.orders_parser.service.GreetingService;
import com.example.orders_parser.service.PrinterService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * most base use case tests
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestSpringConfiguration.class)
class SimpleTest {

    @Autowired
    private GreetingService greetingService;
    @Autowired
    private ListPrinterService out;

    @Test
    void myFirstTest() {
        greetingService.greet();
        assertEquals(out.lines.get(0), "Hello from GreetingService!");
    }
}
