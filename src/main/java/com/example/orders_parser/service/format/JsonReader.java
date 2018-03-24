package com.example.orders_parser.service.format;

import com.example.orders_parser.domain.InputObject;
import com.example.orders_parser.domain.Line;
import com.example.orders_parser.domain.Result;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.MalformedJsonException;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * reader/parser for JSON format
 */
public class JsonReader extends Reader {
    private Gson gson;

    JsonReader(String fileName) throws FileNotFoundException {
        super(fileName);
        this.gson = new Gson();
    }

    @Override
    public Result parseLine(Line line) {
        try {
            InputObject in = gson.fromJson(line.getLine(), InputObject.class);
            in.check();
            return new Result(in.getOrderId(), in.getAmount(), in.getComment(),
                    line.getFileName(), line.getLineNumber(), "OK");
        } catch (JsonSyntaxException | NullPointerException e) {
            return new Result(null, null, null,
                    line.getFileName(), line.getLineNumber(),
                    e.getMessage() != null ? e.getMessage(): "required field not exist in input");
        }
    }
}
