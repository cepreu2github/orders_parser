package com.example.orders_parser.test.mocks;

import com.example.orders_parser.service.base.Printer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * this printer saves messages to ArrayList, so we can make assertions for them in tests
 */
@Service
public class ListPrinter implements Printer {

    public List<String> lines = Collections.synchronizedList(new ArrayList<>());

    @Override
    public void println(String line) {
        lines.add(line);
    }
}
