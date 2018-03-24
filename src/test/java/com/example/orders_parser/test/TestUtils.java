package com.example.orders_parser.test;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

class TestUtils {

    static String getPathInTests(String fileName){
        return TestUtils.class.getClassLoader().getResource(fileName).getFile();
    }

    static List<String> getResourceContents(String name) throws IOException {
        InputStream resource = TestUtils.class.getClassLoader().getResourceAsStream(name);
        return IOUtils.readLines(resource, "UTF-8");
    }

}
