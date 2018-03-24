package com.example.orders_parser.test;

import com.example.orders_parser.service.base.Printer;
import com.example.orders_parser.test.mocks.ListPrinter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * spring configuration for tests. Can override any bean here.
 */
@Configuration
@ComponentScan(basePackages = {"com.example.orders_parser.service", "com.example.orders_parser.test"})
class TestSpringConfiguration {

    @Bean
    @Primary
    Printer printerService(){
        return new ListPrinter();
    }

}
