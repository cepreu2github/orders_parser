package com.example.orders_parser;

import com.example.orders_parser.service.parser.CompletableFutureParser;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * entry point for console run
 */
public class App {
	public static void main(String[] args) throws ExecutionException, InterruptedException {
		setGlobalLogLevel(Level.WARNING);
		String basePackage = "com.example.orders_parser.service";
		try (ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(basePackage)) {
			CompletableFutureParser parser = context.getBean(CompletableFutureParser.class, Arrays.asList(args), Executors.newFixedThreadPool(8));
			parser.parse();
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
