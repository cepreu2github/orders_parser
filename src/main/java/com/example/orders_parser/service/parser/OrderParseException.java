package com.example.orders_parser.service.parser;

public class OrderParseException extends RuntimeException{

    public OrderParseException() {
    }

    public OrderParseException(String s) {
        super(s);
    }

    public OrderParseException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public OrderParseException(Throwable throwable) {
        super(throwable);
    }

    public OrderParseException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }

    public Throwable getRootCause() {
        return getRootCause(this);
    }

    private static Throwable getRootCause(Throwable throwable) {
        if (throwable.getCause() != null)
            return getRootCause(throwable.getCause());

        return throwable;
    }
}
