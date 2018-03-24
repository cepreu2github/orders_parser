package com.example.orders_parser.service.format;

import org.apache.commons.io.FilenameUtils;

import java.io.FileNotFoundException;

public class ReaderFactory {

    private enum SupportedReaders{
        CSV {
            @Override
            Reader getInstance(String fileName) throws FileNotFoundException {
                return new CsvReader(fileName);
            }
        },
        JSON {
            @Override
            Reader getInstance(String fileName) throws FileNotFoundException {
                return new JsonReader(fileName);
            }
        };

        abstract Reader getInstance(String fileName) throws FileNotFoundException;
    }

    public static Reader getReader(String fileName) throws FileNotFoundException {
        String extension = FilenameUtils.getExtension(fileName);
        return SupportedReaders.valueOf(extension.toUpperCase()).getInstance(fileName);
    }

}
