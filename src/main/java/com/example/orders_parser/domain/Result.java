package com.example.orders_parser.domain;

import com.google.gson.Gson;

/**
 * represents result of parsing for one line
 */
public class Result {
    private Long id;
    private Long amount;
    private String comment;
    private String filename;
    private Long line;
    private String result;

    public Result(Long id, Long amount, String comment, String filename, Long line, String result) {
        this.id = id;
        this.amount = amount;
        this.comment = comment;
        this.filename = filename;
        this.line = line;
        this.result = result;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Long getLine() {
        return line;
    }

    public void setLine(Long line) {
        this.line = line;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
