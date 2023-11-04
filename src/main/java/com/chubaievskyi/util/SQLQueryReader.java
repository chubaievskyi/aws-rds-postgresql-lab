package com.chubaievskyi.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.Queue;

public class SQLQueryReader {

    public static final Logger LOGGER = LoggerFactory.getLogger(SQLQueryReader.class);
    private final Queue<String> queries = new LinkedList<>();

    public SQLQueryReader(String fileName) {

        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);

            if (inputStream != null) {
                InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                BufferedReader bufferedReader = new BufferedReader(reader);
                LOGGER.info("SQL queries are read from the {} file in classpath", fileName);

                StringBuilder queryBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    queryBuilder.append(line).append("\n");
                    if (line.trim().endsWith(";")) {
                        queries.add(queryBuilder.toString());
                        queryBuilder.setLength(0);
                    }
                }
            } else {
                LOGGER.info("{} not found in classpath, please check the classpath and file .sql", fileName);
                System.exit(0);
            }
        } catch (IOException e) {
            LOGGER.error("Failed to read properties from file.", e);
        }
    }

    public String getNextQuery() {
        return queries.poll();
    }

    public boolean hasNextQueries() {
        return !queries.isEmpty();
    }
}
