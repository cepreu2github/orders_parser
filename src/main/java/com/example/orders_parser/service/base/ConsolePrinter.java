package com.example.orders_parser.service.base;

import org.springframework.stereotype.Service;

/**
 * real service which do actual printing
 */
@Service
public class ConsolePrinter implements Printer {
    @Override
    public void println(String line) {
        System.out.println(line);
    }
}
