package com.example.orders_parser.service;

import org.springframework.stereotype.Service;

/**
 * real service which do actual printing
 */
@Service
public class ConsolePrinterService implements PrinterService {
    @Override
    public void println(String line) {
        System.out.println(line);
    }
}
