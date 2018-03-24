# orders_parser

Small parser application for job interview. 
Details about task can be found in `task.pdf`. Format is CSV- and
JSON-based. Parser is intended to be parallel. There is two
implementations of parallel engine. One using standard CompletableFuture
and one using Java Reactor project. Default is Reactor.

Also there is JMH tests to compare speed of those two implementations.

## usage

* `mvn package` - build JAR.

* `java -jar target/orders_parser-0.0.1-SNAPSHOT-jar-with-dependencies.jar <file1.csv> <file2.json> ...` - 
run parser for those files.

* `mvn test` - run tests

* `mvn -Pbenchmarks test` - run benchmarks (spoiler: Reactor is faster).