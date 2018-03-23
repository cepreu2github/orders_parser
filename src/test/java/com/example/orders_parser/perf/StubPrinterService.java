package com.example.orders_parser.perf;

import com.example.orders_parser.service.PrinterService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * for performance measurements any printing overhead is drawback
 */
@Service
@Primary
public class StubPrinterService implements PrinterService {
    @Override
    public synchronized void println(String line) { // Synchronized to emulate real console multithreading influence.
                                                    // according to https://stackoverflow.com/a/10468250
                                                    // System.out.println() is serial inside.
        // do nothing
    }
}
