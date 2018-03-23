package com.example.orders_parser.service;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GreetingService {

    @Autowired
    private PrinterService out;

	public void greet() {
		out.println("Hello from GreetingService!");
	}
}
