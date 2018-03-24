package com.example.orders_parser.service.format;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;
import com.example.orders_parser.domain.InputObject;
import com.example.orders_parser.domain.Line;
import com.example.orders_parser.domain.Result;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

/**
 * reader/parser for CSV format
 */
public class CsvReader extends Reader {
    CsvReader(String fileName) throws FileNotFoundException {
        super(fileName);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public Result parseLine(Line line) {
        CsvToBean csv = new CsvToBean();
        CSVReader csvReader = new CSVReader(new StringReader(line.getLine()));
        List<InputObject> list = csv.parse(setColumMapping(), csvReader);
        InputObject in = list.get(0);
        return new Result(in.getOrderId(), in.getAmount(), in.getComment(),
                line.getFileName(), line.getLineNumber(), "OK");
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static ColumnPositionMappingStrategy setColumMapping()
    {
        ColumnPositionMappingStrategy strategy = new ColumnPositionMappingStrategy();
        strategy.setType(InputObject.class);
        String[] columns = new String[] {"orderId", "amount", "currency", "comment"};
        strategy.setColumnMapping(columns);
        return strategy;
    }
}
