package com.example.orders_parser.service.format;

import com.example.orders_parser.domain.Line;
import com.example.orders_parser.domain.Result;
import org.apache.commons.io.FilenameUtils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * base reader for currently implemented formats
 */
public abstract class Reader {

    /**
     * Parsing specially separated from line reading, because reading can't be parallel, but parsing can.
     * But still they both realized to same object because there some possible cases when reading one parsing
     * unit may depends on file structure, so reader should be aware of it.
     *
     * @param line line to parse (special line object with file name, line number metadata)
     * @return result of parsing
     */
    public abstract Result parseLine(Line line);

    private final BufferedReader reader;
    private final String fileName;

    Reader(String fileName) throws FileNotFoundException {
        this.reader = new BufferedReader(new FileReader(fileName));
        this.fileName = FilenameUtils.getName(fileName);
    }

    public Stream<Line> lines(){
        AtomicLong index = new AtomicLong(0);
        return reader.lines().map(line -> new Line(index.incrementAndGet(), line, fileName));
    }

}
