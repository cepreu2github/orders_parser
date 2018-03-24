package com.example.orders_parser.domain;

/**
 * represents one line in source file
 */
public class Line {
    private long lineNumber;
    private String line;
    private String fileName;

    public Line(long lineNumber, String line, String fileName){
        this.fileName = fileName;
        this.lineNumber = lineNumber;
        this.line = line;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(long lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }
}
