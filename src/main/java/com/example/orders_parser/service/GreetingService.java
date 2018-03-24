package com.example.orders_parser.service;

import com.example.orders_parser.service.base.Printer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GreetingService {

    @Autowired
    private Printer out;

	public void greet() {
		out.println("Hello from GreetingService!");
	}
}
