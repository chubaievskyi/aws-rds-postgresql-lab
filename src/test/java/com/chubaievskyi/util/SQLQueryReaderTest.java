package com.chubaievskyi.util;

import com.chubaievskyi.exceptions.FileNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SQLQueryReaderTest {

    private SQLQueryReader reader;

    @BeforeEach
    public void setUp() {
        reader = new SQLQueryReader("test.sql");
    }

    @Test
    void testHasNextQueries() {
        assertTrue(reader.hasNextQueries());
    }

    @Test
    void testGetNextQuery() {
        assertEquals("SELECT * FROM Test;\n", reader.getNextQuery());
    }

    @Test
    void testFileNotFound() {
        Exception exception = assertThrows(FileNotFoundException.class, () -> new SQLQueryReader("NotExist.sql"));
        assertTrue(exception.getMessage().contains("not found in classpath"));
    }
}