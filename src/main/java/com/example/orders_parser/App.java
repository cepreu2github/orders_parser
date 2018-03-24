package com.example.orders_parser;

import com.example.orders_parser.service.parser.CompletableFutureParser;
import com.example.orders_parser.service.parser.OrderParseException;
import com.example.orders_parser.service.parser.ReactorParser;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * entry point for console run
 */
public class App {
	private static final String envKeyLogLevel = "ORDERS_PARSER_LOGLEVEL";

	public static void main(String[] args) {
		Map<String, String> env = System.getenv();
		if (env.containsKey(envKeyLogLevel)){
			setGlobalLogLevel(Level.parse(env.get(envKeyLogLevel)));
		} else {
			setGlobalLogLevel(Level.WARNING);
		}
		String basePackage = "com.example.orders_parser.service";
		try (ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(basePackage)) {
            ReactorParser parser = context.getBean(ReactorParser.class, Arrays.asList(args),
					ForkJoinPool.commonPool());
			parser.parse();
		} catch (OrderParseException e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			Logger.getLogger(App.class.getName()).info(sw.toString());
			System.out.println("ERROR during parsing:");
			System.out.println(e.getRootCause().getMessage());
			System.out.println("Use environment variable ORDERS_PARSER_LOGLEVEL=INFO for more details");
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
