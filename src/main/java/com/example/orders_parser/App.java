package com.example.orders_parser;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.example.orders_parser.service.GreetingService;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * entry point for console run
 */
public class App {
	public static void main(String[] args) {
		setGlobalLogLevel(Level.WARNING);
		String basePackage = "com.example.orders_parser.service";
		try (ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(basePackage)) {
			GreetingService greetingService = context.getBean(GreetingService.class);
			greetingService.greet();
		}
	}

	private static void setGlobalLogLevel(Level level) {
		Logger rootLogger = LogManager.getLogManager().getLogger("");
		rootLogger.setLevel(level);
		for (Handler h : rootLogger.getHandlers()) {
			h.setLevel(level);
		}
	}
}
