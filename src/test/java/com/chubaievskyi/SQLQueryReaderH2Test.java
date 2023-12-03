//package com.chubaievskyi;
//
//import com.chubaievskyi.util.SQLQueryReader;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class SQLQueryReaderH2Test {
//
//    private static SQLQueryReader sqlQueryReader;
//
//    @BeforeAll
//    public static void setUp() {
//        sqlQueryReader = new SQLQueryReader("test.sql");
//    }
//
//    @Test
//    void testGetNextQuery() {
//        String firstQuery = sqlQueryReader.getNextQuery();
//        assertNotNull(firstQuery);
//        assertTrue(firstQuery.contains("SELECT"));
//
//        String secondQuery = sqlQueryReader.getNextQuery();
//        assertNotNull(secondQuery);
//        assertTrue(secondQuery.contains("WHERE"));
//
//        String thirdQuery = sqlQueryReader.getNextQuery();
//        assertNotNull(thirdQuery);
//        assertTrue(thirdQuery.contains("WITH"));
//
//        String fourthQuery = sqlQueryReader.getNextQuery();
//        assertNull(fourthQuery);
//    }
//}
