package com.example.orders_parser.service.parser;

public class OrderParseUnknownException extends RuntimeException{
    public OrderParseUnknownException() {
    }

    public OrderParseUnknownException(String s) {
        super(s);
    }

    public OrderParseUnknownException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public OrderParseUnknownException(Throwable throwable) {
        super(throwable);
    }

    public OrderParseUnknownException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
